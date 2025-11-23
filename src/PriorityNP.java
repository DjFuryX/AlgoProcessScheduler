//Author: Hasani Malcolm
/*
Simualtes a Non preemptive Priority based Scheduling algorithm
*/

import java.util.LinkedList;

public class PriorityNP extends Algo {

    PriorityNP(LinkedList<Process> originalList, boolean showProcessing) {

        super(originalList, "priority Non-Preemptive", showProcessing);
    }

    PriorityNP(boolean showProcessing) {

        super("priority Non-Preemptive", showProcessing);
    }

    public void runProcesses() {

        if (showProcessing) {
            System.out.println("===================" + name + "=========================");
        }

        int index = 0;
        while (true) {
            if (showProcessing) {
                System.out.println("Sytem Time: " + cycle + "-------------------------------------------");
            }
            while (index != processList.size() && processList.get(index).getArrivalTime() == cycle) {

                if (showProcessing) {
                    System.out.println("P[" + processList.get(index).getPid() + "] Arrives");
                }
                executeQueue.addLast(processList.get(index));// all process with the same arrival time added

                index++;

            }

            evaluateProcess();
            cycle++;

            if (isComplete()) {
                break;
            }
        }

    }

    public void evaluateProcess() {

        Process minProcess = null;

        if (executeQueue.size() != 0) {

            minProcess = executeQueue.getFirst();
            for (int x = 1; x < executeQueue.size(); x++) { // get process with lowest priority

                if (executeQueue.get(x).getPriority() < minProcess.getPriority()) {

                    if (!minProcess.Started()) { // check if process has not started to prevent interuption
                        minProcess = executeQueue.get(x);
                    }
                }
            }
        }

        Process liveProcess; // current process being executed

        for (int i = 0; i < executeQueue.size(); i++) {

            liveProcess = executeQueue.get(i);

            if (minProcess.getPid() == liveProcess.getPid() && liveProcess.getBurstTimeLeft() != 0) {

                cPU_BusyCount++;

                liveProcess.calcResponseTime(cycle);

                liveProcess.decrementBurstTime(1);

                if (liveProcess.getBurstTimeLeft() == 0) {
                    if (showProcessing) {
                        System.out.println("P[" + liveProcess.getPid() + "] Completed");
                    }
                    liveProcess.calcTurnaroundTime(cycle + 1);
                    liveProcess.calcWaitTime();
                    executeQueue.remove(i);
                    pCount++;
                } else if (showProcessing) {

                    System.out.println(
                            "P[" + liveProcess.getPid() + "] Executing (" + liveProcess.getBurstTimeLeft() + " left)");

                }

            } else if (showProcessing) {
                System.out.println("P[" + liveProcess.getPid() + "] Waiting");
            }

        }

    }

}

import java.util.LinkedList;

public class PriorityNP extends Algo {

    private LinkedList<Process> executeQueue;// list of processes waiting to execute

    PriorityNP(LinkedList<Process> originalList) {

        super(originalList, "priority Non-Preemptive");

        executeQueue = new LinkedList<>();
    }

    public void runProcesses() {

        System.out.println("===================" + name + "=========================");

        int index = 0;

        while (true) {
            System.out.println("Sytem Time: " + cycle + "-------------------------------------------");

            while (index != processList.size() && processList.get(index).getArrivalTime() == cycle) {

                System.out.println("P[" + processList.get(index).getPid() + "] Arrives");

                executeQueue.addLast(processList.get(index));// all process with the same arrival time added

                index++;

            }

            evaluateProcess();
            cycle++;

            if (executeQueue.size() == 0 && index == processList.size()) {
                break;
            }
        }

    }

    public void evaluateProcess() {

        Process minProcess = null;

        if (executeQueue.size() != 0 ) {

            minProcess = executeQueue.getFirst();
            for (int x = 1; x < executeQueue.size(); x++) { // get process with lowest priority

                if (executeQueue.get(x).getPriority() < minProcess.getPriority()) {

                    if (!minProcess.Started()){
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
                    System.out.println("P[" + liveProcess.getPid() + "] Completed");
                    liveProcess.calcTurnaroundTime(cycle + 1);
                    liveProcess.calcWaitTime();
                    executeQueue.remove(i);
                } else {

                    System.out.println("P[" + liveProcess.getPid() + "] Executing");

                }

            } else {
                System.out.println("P[" + liveProcess.getPid() + "] Waiting");
            }

        }

    }

}

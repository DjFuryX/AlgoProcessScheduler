//Author: Norman Martin
/*Simualtes a Multilevel Queue using a premptive approach using three queues governened by 
    a round robin with a quantum of 8
    a round robin with a quantum of 4
    First come first serve
*/

import java.util.LinkedList;

public class MLqueue extends Algo {

    RoundRobin rr8 = null; // round robin with a quantum of 8 for priority 1
    RoundRobin rr4 = null; // round robin with a quantum of 4 for priorities 2-3
    FCFS fcfs = null; // first come first serve for priorities 4-5

    MLqueue(LinkedList<Process> originalList, boolean showProcessing) {

        super(originalList, "Multi Level Queue", showProcessing);
        fcfs = new FCFS(showProcessing);
        rr8 = new RoundRobin(showProcessing, 8);
        rr4 = new RoundRobin(showProcessing, 4);

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
                executeQueue.addLast(processList.get(index));

                index++;
            }

            evaluateProcess();
            cycle++;

            if (fcfs.pCount + rr4.pCount + rr8.pCount == processList.size()) {
                break;
            }
        }
    }

    void evaluateProcess() {

        // assign each process to a specific algorithm
        for (int i = 0; i < executeQueue.size(); i++) {

            if (executeQueue.size() == 0) {
                break;
            }
            /* priorites 1 uses the round robin with a quantum of 8 */
            else if (executeQueue.get(i).getPriority() == 1) {

                rr8.insertProcess(executeQueue.get(i));
                executeQueue.remove(i);
            }

            /* priorites 2 and 3 uses the round robin with a quantum of 4 */
            else if (executeQueue.get(i).getPriority() == 2 || executeQueue.get(i).getPriority() == 3) {

                rr4.insertProcess(executeQueue.get(i));
                executeQueue.remove(i);
            }

            /* prorities 4 and 5 uses first come fist serve */
            else if (executeQueue.get(i).getPriority() == 4 || executeQueue.get(i).getPriority() == 5) {

                fcfs.insertProcess(executeQueue.get(i));

                executeQueue.remove(i);

            }
        }

        // run each queue with preference for the higher priority
        if (!rr8.isEmpty()) {
            if (showProcessing) {
                System.out.println("===================" + rr8.name + "=========================");
            }

            rr8.cycle = this.cycle;
            rr8.evaluateProcess();
            cPU_BusyCount++;
            return;
        }

        if (!rr4.isEmpty()) {
            if (showProcessing) {
                System.out.println("===================" + rr4.name + "=========================");
            }

            rr4.cycle = this.cycle;
            rr4.evaluateProcess();
            cPU_BusyCount++;
            return;
        }

        if (!fcfs.isEmpty()) {
            if (showProcessing) {
                System.out.println("===================" + fcfs.name + "=========================");
            }

            fcfs.cycle = this.cycle;
            fcfs.evaluateProcess();
            cPU_BusyCount++;
            return;

        }

    }

}

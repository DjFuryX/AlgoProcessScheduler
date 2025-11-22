
import java.util.LinkedList;

public class RoundRobin extends Algo {

    private int quantum; // time slice of roun robin algorithim
    private int quantumLeft; //time slice left before rotation

    public RoundRobin(LinkedList<Process> originalList, int q, boolean showProcessing) {
        super(originalList, "Round Robin (q=" + q + ")", showProcessing);
        this.quantum = q;
        this.quantumLeft = q;
    }

    public void runProcesses() {

        if (showProcessing) {
            System.out.println("===================" + name + "=========================");
        }

        int index = 0;

        while (true) {

            if (showProcessing) {
                System.out.println("System Time: " + cycle + "-------------------------------------------");
            }

            // Add newly arriving processes
            while (index < processList.size() && processList.get(index).getArrivalTime() == cycle) {
                if (showProcessing) {
                    System.out.println("P[" + processList.get(index).getPid() + "] Arrives");
                }
                executeQueue.addLast(processList.get(index));
                index++;
            }

            evaluateProcess();
            cycle++;

            if (executeQueue.size() == 0 && index == processList.size()) {
                break;
            }
        }
    }

    private void evaluateProcess() {

        // CPU idle
        if (executeQueue.size() == 0) {
            if (showProcessing) System.out.println("CPU Idle");
            quantumLeft = quantum;
            return;
        }

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


        // Current executing process
        Process live = minProcess;

        live.calcResponseTime(cycle); // first CPU time
        cPU_BusyCount++;

        live.decrementBurstTime(1);
        quantumLeft--;

        if (showProcessing) {
            System.out.println("P[" + live.getPid() + "] Executing (" + live.getBurstTimeLeft() + " left)");
        }

        // Process completed
        if (live.getBurstTimeLeft() == 0 && minProcess.getPid() == live.getPid() ) {
            if (showProcessing) {
                System.out.println("P[" + live.getPid() + "] Completed");
            }

            live.calcTurnaroundTime(cycle + 1);
            live.calcWaitTime();

            executeQueue.remove(live);
            quantumLeft = quantum;
            return;
        }

        // Quantum expired → rotate process
        if (quantumLeft == 0) {
            executeQueue.removeFirst();
            executeQueue.addLast(live);
            quantumLeft = quantum;
        }

        // Print waiting processes
        if (showProcessing) {
            for (int i = 1; i < executeQueue.size(); i++) {
                System.out.println("P[" + executeQueue.get(i).getPid() + "] Waiting");
            }
        }
    }
}

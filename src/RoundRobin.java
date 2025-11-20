
import java.util.LinkedList;

public class RoundRobin extends Algo {

    private int quantum;
    private int timeLeft;

    public RoundRobin(LinkedList<Process> originalList, int q, boolean showProcessing) {
        super(originalList, "Round Robin (q=" + q + ")", showProcessing);
        this.quantum = q;
        this.timeLeft = q;
    }

    @Override
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
            timeLeft = quantum;
            return;
        }

        // Current executing process
        Process live = executeQueue.getFirst();

        live.calcResponseTime(cycle); // first CPU time
        cPU_BusyCount++;

        live.decrementBurstTime(1);
        timeLeft--;

        if (showProcessing) {
            System.out.println("P[" + live.getPid() + "] Executing (" + live.getBurstTimeLeft() + " left)");
        }

        // Process completed
        if (live.getBurstTimeLeft() == 0) {
            if (showProcessing) {
                System.out.println("P[" + live.getPid() + "] Completed");
            }

            live.calcTurnaroundTime(cycle + 1);
            live.calcWaitTime();

            executeQueue.removeFirst();
            timeLeft = quantum;
            return;
        }

        // Quantum expired → rotate process
        if (timeLeft == 0) {
            executeQueue.removeFirst();
            executeQueue.addLast(live);

            if (showProcessing) {
                System.out.println("P[" + live.getPid() + "] Quantum Expired → Moved to Back");
            }

            timeLeft = quantum;
        }

        // Print waiting processes
        if (showProcessing) {
            for (int i = 1; i < executeQueue.size(); i++) {
                System.out.println("P[" + executeQueue.get(i).getPid() + "] Waiting");
            }
        }
    }
}

import java.util.LinkedList;

public class FCFS extends Algo {

    
     public FCFS( boolean showProcessing) {

        super("First Come First Serve", showProcessing);
    }

    public FCFS(LinkedList<Process> originalList, boolean showProcessing) {

        super(originalList, "First Come First Serve", showProcessing);
    }

    public void runProcesses() {
        if (showProcessing) {

            System.out.println("===================" + name + "=========================");
        }
        int index= 0;
        while (true) {

            // System.out.println("J: " + index + " Cycle: " + cycle + " SIze: " +
            // processList.size());
            if (showProcessing) {
                System.out.println("Sytem Time: " + cycle + "-------------------------------------------");
            }
            /*
             * && operator has  short-circuiting behavior. If the left operand is
             * false, the right operand is notevaluated.
             */
            
            while (index != processList.size() && processList.get(index).getArrivalTime() == cycle) {
                if (showProcessing) {
                    System.out.println("P[" + processList.get(index).getPid() + "] Arrives");
                }
                executeQueue.addLast(processList.get(index));

                index++;
            }

            evaluateProcess();
            cycle++;

            if (pCount == processList.size()) {
                break;
            }
        }

    }

    public void evaluateProcess() {

        Process liveProcess;

        for (int i = 0; i < executeQueue.size(); i++) {

            liveProcess = executeQueue.get(i);

            if (i == 0 && liveProcess.getBurstTimeLeft() != 0) {

                cPU_BusyCount++;

                liveProcess.calcResponseTime(cycle);

                liveProcess.decrementBurstTime(1);

                if (liveProcess.getBurstTimeLeft() == 0) {
                    if (showProcessing) {
                        System.out.println("P[" + liveProcess.getPid() + "] Completed");
                        pCount++;
                    }
                    liveProcess.calcTurnaroundTime(cycle + 1);
                    liveProcess.calcWaitTime();
                    executeQueue.removeFirst();
                } else if (showProcessing) {

                    System.out.println("P[" + liveProcess.getPid() + "] Executing");

                }

            } else if (showProcessing) {
                System.out.println("P[" + liveProcess.getPid() + "] Waiting");
            }

        }

    }

}

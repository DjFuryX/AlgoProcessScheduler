import java.util.LinkedList;

public class FCFS extends Algo {

    private LinkedList<Process> executeQueue;

    public FCFS(LinkedList<Process> originalList) {

        super(originalList, "First Come First Serve");
        executeQueue = new LinkedList<>();

    }

    public void runProcesses() {

        System.out.println("===================" + name + "=========================");

        int index = 0;
        while (true) {

            // System.out.println("J: " + index + " Cycle: " + cycle + " SIze: " +
            // processList.size());
            System.out.println("Sytem Time: " + cycle + "-------------------------------------------");

            if (index != processList.size()) {
                if (processList.get(index).getArrivalTime() == cycle) {

                    while (processList.get(index).getArrivalTime() == cycle) {

                        System.out.println("P[" + processList.get(index).getPid() + "] Arrives");

                        executeQueue.addLast(processList.get(index));

                        index++;
                        if (index == processList.size()) {
                            break;
                        }
                    }

                }
            }
            evaluateProcess();
            cycle++;

            if (executeQueue.size() == 0 && index == processList.size()) {
                break;
            }
        }

    }

    public void evaluateProcess() {

        for (int i = 0; i < executeQueue.size(); i++) {

            if (i == 0 && executeQueue.get(i).getBurstTimeLeft() != 0) {

                cPU_BusyCount++;
                executeQueue.get(i).calcResponseTime(cycle);

                executeQueue.get(i).decrementBurstTime(1);

                if (executeQueue.get(i).getBurstTimeLeft() == 0) {
                    System.out.println("P[" + executeQueue.get(i).getPid() + "] Completed");
                    executeQueue.get(i).calcTurnaroundTime(cycle + 1);
                    executeQueue.get(i).calcWaitTime();
                    executeQueue.removeFirst();
                } else {
                    
                    System.out.println("P[" + executeQueue.get(i).getPid() + "] Executing");
                      
                }

            } else {
                System.out.println("P[" + executeQueue.get(i).getPid() + "] Waiting");
            }

        }

    }

    public void showProcessMetrics() {

        System.out.println("\nProcess Metrics");
        System.out.println(
                "Process\t\tArrival Time\tBurst Time\tPriorty\t\tWaiting Time\tTurnaround Time\tResponse Time");
        for (Process process : processList) {
            process.display();

            avgResponseTime += process.getResponseTime();
            avgTurnaroundTime += process.getTurnaroundTime();
            avgWaitingTime += process.getWaitingime();
        }
        int count = processList.size();

        avgResponseTime /= count;
        avgTurnaroundTime /= count;
        avgWaitingTime /= count;
        System.out.println("\nKey Stats");
        System.out.println("Average Waiting Time\t\tAverage Turnaround Time\t\tAverage Response Time");
        System.out.println(avgWaitingTime + "\t\t\t\t" + avgTurnaroundTime + "\t\t\t\t" + avgResponseTime);
        System.out.println("Busy Count: "+cPU_BusyCount+" Cycles: "+cycle);
        System.out.println("ThroughPut: "+(count/(float)cycle)+" process/unit");

    }

}

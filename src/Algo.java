import java.util.LinkedList;

public abstract class Algo { // abstract algorithm class with all attributes and main methods
    protected String name; // name of scheduling algorithm
    protected int cycle; // current execution time unit
    protected float avgWaitingTime;
    protected float avgTurnaroundTime;
    protected float avgResponseTime;
    protected int cPU_BusyCount; // total time the CPU is actively processing a proces
    protected float cPU_Usage; // CPU Utilization = (Total CPU Busy Time / cyle) * 100%
    protected float throughput; // the number of processes that complete their execution per time unit (total
                                // Processes/cycle)

    int pCount; // number of completed processes

    protected LinkedList<Process> processList; // list of all processes

    protected LinkedList<Process> executeQueue;// list of processes waiting to execute

    protected boolean showProcessing; // show processing info

    public Algo() {// default constructor
        cycle = 0;
        avgWaitingTime = 0;
        avgTurnaroundTime = 0;
        avgResponseTime = 0;
        cPU_BusyCount = 0;
        cPU_Usage = 0;
        throughput = 0;
        pCount = 0;

        showProcessing = true;
        name = "NotAssigned";
        processList = new LinkedList<>();
        executeQueue = new LinkedList<>();
    }

    public Algo(LinkedList<Process> originalList, String algorithmName, boolean showProcessingQueue) {// primary
                                                                                                      // constrcutor

        cycle = 0;
        avgWaitingTime = 0;
        avgTurnaroundTime = 0;
        avgResponseTime = 0;
        cPU_BusyCount = 0;
        cPU_Usage = 0;
        throughput = 0;
        pCount = 0;
        name = algorithmName;
        processList = new LinkedList<>();
        executeQueue = new LinkedList<>();
        showProcessing = showProcessingQueue;

        for (Process process : originalList) {// make a copy of the original list of processes
            processList.addLast(new Process(process));
        }

    }

    public Algo(String algorithmName, boolean showProcessingQueue) {// primary
                                                                    // constrcutor

        cycle = 0;
        avgWaitingTime = 0;
        avgTurnaroundTime = 0;
        avgResponseTime = 0;
        cPU_BusyCount = 0;
        cPU_Usage = 0;
        throughput = 0;
        pCount = 0;
        name = algorithmName;
        processList = new LinkedList<>();
        executeQueue = new LinkedList<>();
        showProcessing = showProcessingQueue;
    }

    public abstract void runProcesses();// main algorithm function

    public void insertProcess(Process newProcess) {// adds a process to the main process list

        executeQueue.addLast(newProcess); // process is added to the end of the list

    }

    public boolean isComplete() {

        return (pCount == processList.size());
    }

    public boolean isEmpty() {

        return (0 == executeQueue.size());
    }

    public void calculateProcessMetrics() {

        for (Process process : processList) {
            avgResponseTime += process.getResponseTime();
            avgTurnaroundTime += process.getTurnaroundTime();
            avgWaitingTime += process.getWaitingime();
        }

        int count = processList.size();

        avgResponseTime /= count;
        avgTurnaroundTime /= count;
        avgWaitingTime /= count;
        throughput = (count / (float) cycle);

    }

    // show algorithm stats
    public void showProcessMetrics() {

        System.out.println("\n===================" + name + "=========================");
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
        throughput = (count / (float) cycle);
        System.out.println("\nKey Stats");
        System.out.println("Average Waiting Time\t\tAverage Turnaround Time\t\tAverage Response Time");
        System.out.println(avgWaitingTime + "\t\t\t\t" + avgTurnaroundTime + "\t\t\t\t" + avgResponseTime);
        System.out.println("Busy Count: " + cPU_BusyCount + " Cycles: " + cycle);
        System.out.println("ThroughPut: " + throughput + " process/unit");

    }

}

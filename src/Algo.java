import java.util.LinkedList;

public abstract class Algo {
    protected String name; // name of scheduling algorithm
    protected int cycle; // current execution time unit
    protected float avgWaitingTime;
    protected float avgTurnaroundTime;
    protected float avgResponseTime;
    protected int cPU_BusyCount; // total time the CPU is actively processing a proces
    protected float cPU_Usage; // CPU Utilization = (Total CPU Busy Time / cyle) * 100%
    protected float throughput; // the number of processes that complete their execution per time unit (total
                                // Processes/cycle)

    LinkedList<Process> processList;

    public Algo() {
        cycle = 0;
        avgWaitingTime = 0;
        avgTurnaroundTime = 0;
        avgResponseTime = 0;
        cPU_BusyCount = 0;
        cPU_Usage = 0;
        throughput = 0;
        processList = new LinkedList<>();
    }

    public Algo(LinkedList<Process> originalList, String algorithmName) {

        cycle = 0;
        avgWaitingTime = 0;
        avgTurnaroundTime = 0;
        avgResponseTime = 0;
        cPU_BusyCount = 0;
        cPU_Usage = 0;
        throughput = 0;

        name = algorithmName;
        processList = new LinkedList<>(originalList);// make a copy of the original list of processes 

    }

    public abstract void runProcesses();

}

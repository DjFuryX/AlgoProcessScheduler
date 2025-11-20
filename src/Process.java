import java.util.InputMismatchException;
import java.util.Scanner;

public class Process {

    private int pid; // Unique identifier for each process
    private static int processCount; // keeps track of all process created

    private int arrivalTime; // Time the process was created
    private int burstTime; // Original Amount of time require to complete task. The time it must spend
                           // running on Processor
    private int burstTimeLeft; // Amount of time left to complete a task that is partially complete

    private int priority; // Level of importance/urgency given to a process (lower is preferred)

    private int turnaroundTime; // time finished – arrival time
    private int waitingime; // turnaround time – service time
    private int responseTime; // first CPU time –arrival time

    private boolean started;//

    private static Scanner scanner;// for user input

    public Process() {// default constructor
        pid = 0;
        arrivalTime = 0;
        burstTime = 0;
        burstTimeLeft = 0;
        priority = 0;
        turnaroundTime = 0;
        waitingime = 0;
        responseTime = 0;
        started = false;
    }

    public Process(Process newProcess) {
        pid = newProcess.pid;
        arrivalTime = newProcess.arrivalTime;
        burstTime = newProcess.burstTime;
        burstTimeLeft = newProcess.burstTimeLeft;
        priority = newProcess.priority;
        turnaroundTime = newProcess.turnaroundTime;
        waitingime = newProcess.waitingime;
        responseTime = newProcess.responseTime;
        started = newProcess.started;

    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void calcTurnaroundTime(int timeFinished) {
        this.turnaroundTime = timeFinished - this.arrivalTime;
    }

    public void calcResponseTime(int firstCPUtime) {
        if (!started) {
            this.responseTime = firstCPUtime - this.arrivalTime;
            // System.out.println("Response Time: "+responseTime+" Cycle:
            // "+firstCPUtime+"Arrival Time: "+this.arrivalTime);
            started = true;
        }
    }

    public void calcWaitTime() {
        this.waitingime = this.turnaroundTime - this.burstTime;
    }

    public void decrementBurstTime(int amount) {
        this.burstTimeLeft -= amount;
    }

    public int getBurstTimeLeft() {
        return burstTimeLeft;
    }

    public int getPriority() {
        return priority;
    }

    public int getPid() {
        return pid;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public boolean Started() {
        return started;
    }

    public int getWaitingime() {
        return waitingime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public Process createProcessManual() {

        this.pid = processCount;
        System.out.println("P [" + this.pid + "]--------------------------------------------------");
        this.arrivalTime = getUserInputInt("Please enter process Arrival Time: ");
        this.burstTime = getUserInputInt("Please enter process Burst Time: ");
        this.priority = getUserInputInt("Please enter process Priority: ");
        this.burstTimeLeft = burstTime;
        processCount++;
        return this;
    }

    public Process createProcessRandom(boolean showProcesses) {
        this.pid = processCount;
        // this.arrivalTime = (int)((Math.random() * 3) + processCount);
        this.arrivalTime = (int) ((Math.random() * 10) + processCount);
        this.burstTime = (int) ((Math.random() * 16) + 1);
        this.priority = (int) ((Math.random() * 5) + 1); // priority is from 1 to 5

        if (showProcesses) {
            System.out.println("P [" + this.pid + "]--------------------------------------------------");
            System.out.println("Arrival Time: " + this.arrivalTime);
            System.out.println("Burst Time: " + this.burstTime);
            System.out.println("Priority: " + this.priority);
        }

        this.burstTimeLeft = burstTime;
        processCount++;

        return this;
    }

    private int getUserInputInt(String userText) {
        int value = 0;
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println(userText);

            try {
                value = scanner.nextInt();

                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Value must be greater than 1");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); // Consume the invalid input to clear the scanner buffer
            }
        }

    }

    public void display() {

        System.out.println("P[" + this.pid + "]\t\t" + this.arrivalTime + "\t\t" + this.burstTime + "\t\t"
                + this.priority + "\t\t" + this.waitingime
                + "\t\t" + this.turnaroundTime + "\t\t" + this.responseTime);
    }

    public static void closeScanner() {
        try {
            scanner.close();
        } catch (java.lang.NullPointerException e) {
        }

    }

}

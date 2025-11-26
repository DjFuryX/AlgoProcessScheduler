import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.LinkedList;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("********************Welcome to Our Process Scheduler Simulator*************************\n\n");

        //get number of processes 
        int processNum = 0;
        while (true) {
            System.out.println("Please Enter number of processes to simulate");

            try {
                processNum = scanner.nextInt();

                if (processNum > 0) {
                    break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); // Consume the invalid input to clear the scanner buffer
            }
        }
      
        // option to write data to file or on screen
        while (true) {
            System.out.println("Would you like to simulate processes (1) or write data to file (2) ");

            try {
                int option = scanner.nextInt();

                if (option == 1) {
                    break;
                } else if (option == 2) {
                    generateAlanysis(processNum); // write data to file
                    return;//exit program
                } else {
                    System.out.println("Please enter 1 or 2");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); // Consume the invalid input to clear the scanner buffer
            }
        }


        // option to generate processes or enter manually
        int option = 0;
        while (true) {
            System.out.println("Would you like to generate values (1) or enter manually (2)");

            try {
                option = scanner.nextInt();

                if (option == 1 || option == 2) {
                    break;
                } else {
                    System.out.println("Please enter 1 or 2");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); // Consume the invalid input to clear the scanner buffer
            }
        }

        // Create a LinkedList of processes
        LinkedList<Process> processList = new LinkedList<>();

        for (int i = 0; i < processNum; i++) {

            if (option == 1) {

                processList.addLast(new Process().createProcessRandom(false));

            } else {

                processList.addLast(new Process().createProcessManual());
            }

        }
          // option to show simulated queue or just the results
        while (true) {
            System.out.println("Would you like to see the process simulation  (1) Yes (2) NO");

            try {
                option = scanner.nextInt();

                if (option == 1 || option == 2) {
                    break;
                } else {
                    System.out.println("Please enter 1 or 2");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); // Consume the invalid input to clear the scanner buffer
            }
        }
        boolean showSimulation = (option == 1) ? true : false; // change option to a bool

        // get time quantum for round robin
        while (true) {
            System.out.println("Please Enter Quantum for the Round Robin Algorithm");

            try {
                option = scanner.nextInt();

                if (option > 0) {
                    break;
                } else {
                    System.out.println("Please enter a numner greater than 0");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); // Consume the invalid input to clear the scanner buffer
            }
        }

        int quantum = option;

        // Sort list by arrival time using bubble sort
        bubbleSort(processList);

        /* Process alogorithms */

        // First Come First Serve
        FCFS fcfs = new FCFS(processList, showSimulation);
        fcfs.runProcesses();

        // Non Pre-emptive Priority
        PriorityNP pnp = new PriorityNP(processList, showSimulation);
        pnp.runProcesses();

        // Round Robin
        RoundRobin rr = new RoundRobin(processList, quantum, showSimulation);
        rr.runProcesses();

        // MultiLevel Queue
        MLqueue MLQ = new MLqueue(processList, showSimulation);
        MLQ.runProcesses();

        /* show alogortihm statistics */
        pnp.showProcessMetrics();
        fcfs.showProcessMetrics();
        rr.showProcessMetrics();
        MLQ.showProcessMetrics();

        // show comparitve analysis
        System.out.println("\n------------Comparitive Analysis-------------------\n");

        // create list of All the algorithms
        LinkedList<Algo> algoList = new LinkedList<>();
        algoList.add(fcfs);
        algoList.add(pnp);
        algoList.add(rr);
        algoList.add(MLQ);

        // Find and output the highest and lowest waiting time
        System.out.println("Average Waiting Time");
        System.out.println("Highest");
        Algo high = fcfs;

        for (Algo algo : algoList) {
            if (algo.avgWaitingTime > high.avgWaitingTime) {
                high = algo;
            }
        }
        System.out.println(high.name + "\t" + high.avgWaitingTime);

        System.out.println("Lowest");
        Algo low = fcfs;

        for (Algo algo : algoList) {
            if (algo.avgWaitingTime < low.avgWaitingTime) {
                low = algo;
            }
        }
        System.out.println(low.name + "\t" + low.avgWaitingTime);

        // Find and output the highest and lowest turnaround time
        System.out.println("\nAverage Turnaround Time");
        System.out.println("Highest");
        high = fcfs;

        for (Algo algo : algoList) {
            if (algo.avgTurnaroundTime > high.avgTurnaroundTime) {
                high = algo;
            }
        }
        System.out.println(high.name + "\t" + high.avgTurnaroundTime);

        System.out.println("Lowest");
        low = fcfs;

        for (Algo algo : algoList) {
            if (algo.avgTurnaroundTime < low.avgTurnaroundTime) {
                low = algo;
            }
        }
        System.out.println(low.name + "\t" + low.avgTurnaroundTime);
        // Find and output the highest and lowest Response time
        System.out.println("\nAverage Response Time");
        System.out.println("Highest");
        high = fcfs;

        for (Algo algo : algoList) {
            if (algo.avgResponseTime > high.avgResponseTime) {
                high = algo;
            }
        }
        System.out.println(high.name + "\t" + high.avgResponseTime);

        System.out.println("Lowest");
        low = fcfs;

        for (Algo algo : algoList) {
            if (algo.avgResponseTime < low.avgResponseTime) {
                low = algo;
            }
        }
        System.out.println(low.name + "\t" + low.avgResponseTime);

        scanner.close();// for user input
        Process.closeScanner();// close input object

    }

    // Simulates multiple runs of each algorithm then writes the data to a file 
    static void generateAlanysis(int max) {

        // Create a list of processes
        LinkedList<Process> processList = new LinkedList<>();
        // create files
        Path fcfsPath = Paths.get("FCFS/fcfs processes.csv");
        Path pnpPath = Paths.get("NPPriority/pnp processes.csv");
        Path mlqPath = Paths.get("MLQ/mlq processes.csv");
        Path rrPath = Paths.get("RR/rr processes quantum.csv");

        try {

            // Create parent directories if they don't exists
            Files.createDirectories(fcfsPath.getParent());
            Files.createDirectories(pnpPath.getParent());
            Files.createDirectories(mlqPath.getParent());

            // create file object
            PrintWriter fcfswriter = new PrintWriter(Files.newBufferedWriter(fcfsPath), true);
            PrintWriter pnpwriter = new PrintWriter(Files.newBufferedWriter(pnpPath), true);
            PrintWriter mlqwriter = new PrintWriter(Files.newBufferedWriter(mlqPath), true);

            // write file headers
            fcfswriter.println("Process Count,Average Waiting Time,Average Turnaround Time,Average Response Time");
            pnpwriter.println("Process Count,Average Waiting Time,Average Turnaround Time,Average Response Time");
            mlqwriter.println("Process Count,Average Waiting Time,Average Turnaround Time,Average Response Time");
            PrintWriter rrwriter = null;

            for (int x = 1; x <= max; x++) { // for max number of processes

                System.out.print("");
                System.out.print("\r Writing to file " + x + "/" + max);// show progress

                for (int i = 1; i <= x; i++) { // generate test from 1 up the current max number

                    processList.addLast(new Process().createProcessRandom(false));

                }

                bubbleSort(processList);// sort process list by arrival time

                // First Come First Serve
                FCFS fcfs = new FCFS(processList, false);
                fcfs.runProcesses();
                fcfs.calculateProcessMetrics();

                // Non Pre-emptive Priority
                PriorityNP pnp = new PriorityNP(processList, false);
                pnp.runProcesses();
                pnp.calculateProcessMetrics();

                // MultiLevel Queue
                MLqueue mlq = new MLqueue(processList, false);
                mlq.runProcesses();
                mlq.calculateProcessMetrics();

                // Round Robin for quantum 1 to 10
                for (int j = 1; j <= 10; j++) {

                    rrPath = Paths.get("RR/rr processes quantum " + j + ".csv");
                    Files.createDirectories(rrPath.getParent());
                    rrwriter = new PrintWriter(new FileWriter(rrPath.toString(), true));
                    if (x == 1)// print file header
                    {
                        rrwriter.close();
                        rrwriter = new PrintWriter(new FileWriter(rrPath.toString(), false));
                        rrwriter.println(
                                "Process Count,Average Waiting Time,Average Turnaround Time,Average Response Time");

                    }

                    RoundRobin rr = new RoundRobin(processList, j, false);
                    rr.runProcesses();
                    rr.calculateProcessMetrics();
                    // write data to file
                    rrwriter.println(
                            x + "," + rr.avgWaitingTime + "," + rr.avgTurnaroundTime + "," + rr.avgResponseTime);
                    rrwriter.close();
                }

                // write data to file
                fcfswriter.println( x + "," + fcfs.avgWaitingTime + "," + fcfs.avgTurnaroundTime + "," + fcfs.avgResponseTime);
                pnpwriter.println( x + "," + pnp.avgWaitingTime + "," + pnp.avgTurnaroundTime + "," + pnp.avgResponseTime);
                mlqwriter.println( x + "," + mlq.avgWaitingTime + "," + mlq.avgTurnaroundTime + "," + mlq.avgResponseTime);

            }
            // close file
            fcfswriter.close();
            pnpwriter.close();
            mlqwriter.close();

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        // show complete message
        System.out.println("\n Done Saved to:");
        System.out.println(fcfsPath.toAbsolutePath().getParent());
        System.out.println(mlqPath.toAbsolutePath().getParent());
        System.out.println(pnpPath.toAbsolutePath().getParent());
        System.out.println(rrPath.toAbsolutePath().getParent());

    }

    // Sort list by arrival time using bubble sort
    static void bubbleSort(LinkedList<Process> processList) {

        boolean swapped = false;
        int size = processList.size() - 1;

        for (int i = 0; i < size; i++) {
            swapped = false;
            for (int j = 0; j < size - i; j++) {
                if (processList.get(j).getArrivalTime() >= processList.get(j + 1).getArrivalTime()) {
                    Process temp = processList.get(j);
                    processList.set(j, processList.get(j + 1));
                    processList.set(j + 1, temp);
                    swapped = true;
                }
            }
            // If no two elements were swapped, then break
            if (!swapped) {
                break;
            }
        }

    }
}

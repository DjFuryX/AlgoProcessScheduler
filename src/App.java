import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.LinkedList;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        int processNum = 0;

        System.out.println("********************Welcome to Our Process Scheduler Simulator*************************\n\n");

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
        System.out.println(processNum + " Processses ");

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

        boolean showSimulation = (option == 1)? true:false; // change option to a bool


        while (true) {
            System.out.println("Please Enter Quantum for the Round Robin Algorithm");

            try {
                option = scanner.nextInt();

                if (option>0) {
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


        /*Process alogorithms*/

        // First Come First Serve
        FCFS fcfs = new FCFS(processList, showSimulation);
        //fcfs.runProcesses();
        
        // Non Pre-emptive Priority
        PriorityNP pnp = new PriorityNP(processList, showSimulation);
        //pnp.runProcesses();

        //Round Robin
        RoundRobin rr = new RoundRobin(processList, quantum, showSimulation);
       // rr.runProcesses();
   
        //MultiLevel Queue
        MLqueue MLQ = new MLqueue(processList, showSimulation);
       MLQ.runProcesses();

        /*show alogortihm statistics*/
        pnp.showProcessMetrics();
        fcfs.showProcessMetrics(); 
        rr.showProcessMetrics();
        MLQ.showProcessMetrics();


        scanner.close();// for user input
        Process.closeScanner();

    }
}

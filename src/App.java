import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.LinkedList;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        int processNum = 0;

        while (true) {
            System.out.println("Please Enter number of processes");

            try {
                processNum = scanner.nextInt();

                if (processNum >0){
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

                processList.addLast(new Process().createProcessRandom());

            } else {

                processList.addLast(new Process().createProcessManual());
            }

        }

        // Sort list by arrival time using bubble sort
        boolean swapped = false;
        int size = processList.size()-1;

        for (int i = 0; i < size ; i++) {
            swapped = false;
            for (int j = 0; j < size -i; j++) {
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
        

        // Process alogorithms
        //
       // FCFS fcfs = new FCFS(processList);

        //fcfs.runProcesses();// simulate first come first serve algorithm
       // fcfs.showProcessMetrics(); // show alogortihm statistics
        
        PriorityNP pnp = new PriorityNP(processList);

        pnp.runProcesses();
        pnp.showProcessMetrics();


        scanner.close();// for user input
        Process.closeScanner();

    }
}

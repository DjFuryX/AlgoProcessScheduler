import java.util.LinkedList;

public class MLqueue extends Algo {

    FCFS fcfs = null; // first come first serve for priorities 4-5


    MLqueue(LinkedList<Process> originalList, boolean showProcessing) {

        super(originalList, "Multi Level Queue", showProcessing);
        fcfs = new FCFS(showProcessing);
    }

    public void runProcesses() {
        if (showProcessing) {

            System.out.println("===================" + name + "=========================");
        }

        int index=0;
        while (true) {

             if (showProcessing) {
                System.out.println("Sytem Time: " + cycle + "-------------------------------------------");
            }
            /*
             * && operator has short-circuiting behavior. If the left operand is
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

            if (fcfs.pCount/*+rr4Count+rr8sCount*/ == processList.size()){
                break;
            }
          

        }

    }

    void evaluateProcess() {

        // assign each process to a specific algorithm
        for (int i = 0; i < executeQueue.size(); i++) {

            if (executeQueue.size() == 0) {
                break;
            }
            /* priorites 3 uses the round robin with a quantum of 8 */
            else if (executeQueue.get(i).getPriority() == 1) {

                executeQueue.remove(i);// remove later TESTING
             

            }
            /* priorites 3 uses the round robin with a quantum of 4 */
            else if (executeQueue.get(i).getPriority() == 2 || executeQueue.get(i).getPriority() == 3) {

                executeQueue.remove(i); // remove later TESTING
           

            }
            /* prorities 4 and 5 uses first come fist serve */
            else if (executeQueue.get(i).getPriority() == 4 || executeQueue.get(i).getPriority() == 5) {

                fcfs.insertProcess(executeQueue.get(i));
            

                executeQueue.remove(i);

            }
        }

        // run each queue with preference for the higher priority

        // roundrobin.runProcess(quantum 8)
        // roundrobin.runProcess(quantum 4)

        fcfs.cycle = this.cycle;
        fcfs.evaluateProcess();
     
       

    };

}

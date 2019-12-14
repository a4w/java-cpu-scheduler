import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

public class priorityScheduler extends Scheduler {

    public static Vector<Process> processes = new Vector<Process>();
    public static Vector<ExecutionSegment> completedProcesses = new Vector<ExecutionSegment>();
    public static Vector<Process> readyQ = new Vector<Process>();

    priorityScheduler(GUIScheduler gui) {
        super(gui);
    }

    public Process[] getProcesses(){
        Process[] arr = new Process[processes.size()];
        for(int i = 0; i < arr.length; ++i){
            arr[i] = processes.get(i);
        } 
        return arr;
    }

    public void addProcess(Process process) {
        processes.add(process);
    }

    /*
     * public static void main(String[] args) { Scanner sc = new Scanner(System.in);
     * System.out.println("num of processes :"); int numOfProcesses =
     * Integer.parseInt(sc.nextLine()); sc.reset(); String name = ""; int arrival =
     * 0; int burst = 0; int priority = 0; for(int i = 0 ; i < numOfProcesses ; i++)
     * { System.out.println("process name :"); name = sc.nextLine(); sc.reset();
     * System.out.println("process arrival :"); arrival =
     * Integer.parseInt(sc.nextLine()); sc.reset();
     * System.out.println("process burst :"); burst =
     * Integer.parseInt(sc.nextLine()); sc.reset();
     * System.out.println("process priority :"); priority =
     * Integer.parseInt(sc.nextLine()); sc.reset(); Process process = new
     * Process(arrival, burst, priority, name); processes.add(process); }
     * sc.close();
     * processes.sort(Comparator.comparing(Process::getArrivalTime).thenComparingInt
     * (Process::getPriority)); priorityScheduling(); }
     */
    public static void addToReadyQ(int time) {
        readyQ.clear();
        for (int i = 0; i < processes.size(); i++) {
            if (!processes.elementAt(i).getIsCompleted() && processes.elementAt(i).getArrivalTime() <= time)
                readyQ.add(processes.elementAt(i));
        }
        readyQ.sort(Comparator.comparing(Process::getPriority));
    }

    public void priorityScheduling() {
        processes.sort(Comparator.comparing(Process::getArrivalTime).thenComparingInt(Process::getPriority));
        int time = processes.elementAt(0).getArrivalTime();
        int completed = 0;
        double averageTurnaroundTime = 0;
        double averageWaitingTime = 0;
        while (true) {
            addToReadyQ(time);
            if (readyQ.size() != 0) {
                completed++;
                ExecutionSegment executionSegment = new ExecutionSegment();
                executionSegment.process = readyQ.elementAt(0);
                executionSegment.start_time = time;
                readyQ.elementAt(0).setIsCompleted(true);
                time += readyQ.elementAt(0).getBurstTime();
                executionSegment.end_time = time;
                executionSegment.process
                        .setTurnAround(executionSegment.end_time - executionSegment.process.getArrivalTime());
                executionSegment.process.setWaitingTime(
                        executionSegment.process.getTurnAround() - executionSegment.process.getBurstTime());
                averageTurnaroundTime += executionSegment.process.getTurnAround();
                averageWaitingTime += executionSegment.process.getWaitingTime();
                // for aging:
                for (int i = 1; i < readyQ.size(); i++) {
                    if (readyQ.elementAt(i).getPriority() > 1)
                        readyQ.elementAt(i).setPriority(readyQ.elementAt(i).getPriority() - 1);
                }
                gui.switchExecution(executionSegment);
                completedProcesses.add(executionSegment);
                
            } else if (completed != processes.size())
                time = processes.elementAt(completed).getArrivalTime();
            else
                break;
        }
        averageTurnaroundTime /= completedProcesses.size();
        averageWaitingTime /= completedProcesses.size();
        for (int i = 0; i < completedProcesses.size(); i++)
            System.out.println("Process : " + completedProcesses.elementAt(i).process.getName() + "  starts : "
                    + completedProcesses.elementAt(i).start_time + "  ends : "
                    + completedProcesses.elementAt(i).end_time + "  , waiting time : "
                    + completedProcesses.elementAt(i).process.getWaitingTime() + "  , turnaround time : "
                    + completedProcesses.elementAt(i).process.getTurnAround());
        System.out.println(
                "avg waiting time : " + averageWaitingTime + "  avg turnaround time : " + averageTurnaroundTime);
    }
}

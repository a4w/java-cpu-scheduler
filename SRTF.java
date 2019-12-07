import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

public class SRTF extends Scheduler{
    public static Vector<Process> processes = new Vector<Process>();
    public static Vector<ExecutionSegment> completedProcesses = new Vector<ExecutionSegment>();
    public static Vector<Process> readyQ = new Vector<Process>();

    public SRTF(GUIScheduler gui) {
        super(gui);
    }
    public Process[] getProcesses() {
        Vector<Process> allProcesses = new Vector<Process>();
        for(int i = 0 ; i < completedProcesses.size() ; i++)
            allProcesses.add(completedProcesses.elementAt(i).process);
        return (Process[]) processes.toArray();
    }

    public void addProcess(Process process) {
        processes.add(process);
    }

    /*public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("num of processes :");
        int numOfProcesses = Integer.parseInt(sc.nextLine());
        sc.reset();
        String name = "";
        int arrival = 0;
        int remaining = 0;
        for(int i = 0 ; i < numOfProcesses ; i++) {
            System.out.println("process name :");
            name = sc.nextLine();
            sc.reset();
            System.out.println("process arrival :");
            arrival = Integer.parseInt(sc.nextLine());
            sc.reset();
            System.out.println("process time :");
            remaining = Integer.parseInt(sc.nextLine());
            sc.reset();
            Process process = new Process(name, arrival, remaining, false);
            process.setBurstTime(remaining);
            processes.add(process);
        }
        sc.close();
        runSRTF();
    }*/

    public static void addToReadyQ(int time) {
        readyQ.clear();
        for(int i = 0 ; i < processes.size() ; i++)
        {
            if(!processes.elementAt(i).getIsCompleted() && processes.elementAt(i).getArrivalTime() <= time)
                readyQ.add(processes.elementAt(i));
        }
        readyQ.sort(Comparator.comparing(Process::getRemainingTime));
    }

    public static void runSRTF(){
        processes.sort(Comparator.comparing(Process::getArrivalTime).thenComparingInt(Process::getRemainingTime));
        int time = processes.elementAt(0).getArrivalTime();
        int completed = 0;
        double averageTurnaroundTime = 0;
        double averageWaitingTime = 0;
        while(true) {
            addToReadyQ(time);
            if(readyQ.size() != 0) {
                ExecutionSegment executionSegment = new ExecutionSegment();
                executionSegment.process = readyQ.elementAt(0);
                executionSegment.start_time = time;
                time++;
                readyQ.elementAt(0).setRemainingTime(readyQ.elementAt(0).getRemainingTime()-1);
                if(readyQ.elementAt(0).getRemainingTime() == 0) {
                    readyQ.elementAt(0).setIsCompleted(true);
                    completed++;
                }
                executionSegment.end_time = time;
                if(readyQ.elementAt(0).getIsCompleted()) {
                    executionSegment.process.setTurnAround(executionSegment.end_time - executionSegment.process.getArrivalTime());
                    executionSegment.process.setWaitingTime(executionSegment.process.getTurnAround() - executionSegment.process.getBurstTime());
                    averageTurnaroundTime += executionSegment.process.getTurnAround();
                    averageWaitingTime += executionSegment.process.getWaitingTime();
                }
                completedProcesses.add(executionSegment);
            }
            else if(completed != processes.size())
                time = processes.elementAt(completed).getArrivalTime();
            else
                break;
        }
        averageTurnaroundTime /= processes.size();
        averageWaitingTime /= processes.size();
        for(int i = 0 ; i < completedProcesses.size() ; i++)
            System.out.println("Process : " + completedProcesses.elementAt(i).process.getName() + "  starts : " + completedProcesses.elementAt(i).start_time + "  ends : " + completedProcesses.elementAt(i).end_time);
        System.out.println("avg waiting time : " + averageWaitingTime + "  avg turnaround time : " + averageTurnaroundTime);
    }
}

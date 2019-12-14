import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class agScheduler extends Scheduler {

    private static ExecutionSegment current = new ExecutionSegment();
    private static List<agProcess> processes = new ArrayList<agProcess>();
    private static Set<agProcess> arrivedAndWaiting = new LinkedHashSet<agProcess>();
    private static int numOfProcesses = 0;
    private static double avgWaitingTime = 0.0;
    private static double avgTurnAroundTime = 0.0;
    int q = 0;
    public agScheduler(GUIScheduler gui, int q) {
        super(gui);
        this.q = q;
    }
    public agScheduler(GUIScheduler gui){
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
        agProcess p = new agProcess(process, this.q);
        processes.add(p);
        numOfProcesses += 1;
    }
    public void executeProcesses() {
        //Sort Processes by arrival time
        processes.sort(Comparator.comparing(Process::getArrivalTime));
        int lastArrival = processes.get(0).getArrivalTime();
        int lastArrivalIndex = 0;
        int finished = 0;
        agProcess p = null;
        agProcess currentProcess = null;
        for(int currentTime = 0; finished != numOfProcesses; ++currentTime) { //Time Loop
            System.out.println("<----------- Current Time: " + currentTime + " -----------> ");
            if(currentProcess != null && currentProcess.getRemainingTime() != 0) {
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);
                this.traverseProcess(currentProcess);
            }
            //Loop to get all new arrival processes processes at a certain time unit
            while(lastArrival <= currentTime) {
                //Check bounds of processes list
                if(lastArrivalIndex > agScheduler.numOfProcesses - 1) break;
                //Get processes and update index and last arrival time
                if(processes.get(lastArrivalIndex).getArrivalTime() > currentTime) break;
                //Add process to arrived list & move last arrived index to the next process in line
                arrivedAndWaiting.add(processes.get(lastArrivalIndex));
                lastArrival = processes.get(lastArrivalIndex++).getArrivalTime();
            }
            if(currentProcess == null) {
                currentProcess = getFirstInWaiting(currentTime);
                if(currentProcess != null)System.out.println("Process: " +currentProcess.getName() + " Occupied Idle CPU after");
                traverseProcess(currentProcess);
            }else if(currentProcess.getRemainingTime() == 0) {
                currentProcess.endTime = currentTime;
                current.end_time = currentTime;
                current.process = currentProcess;
                gui.switchExecution(current);
                currentProcess.quantum = 0;
                //Get first process in waiting queue
                p = getFirstInWaiting(currentTime);
                System.out.println(currentProcess.getName() + " completed it's burst & Left CPU");
                ++finished;
                if(p != null)System.out.println("Process: " +p.getName() + " Occupied the CPU");
                currentProcess = p;
                p = null;
                this.traverseProcess(currentProcess);
            }else if(currentProcess.getRemainingTime() != 0){
                //If quantum time passed
                if(currentProcess.startTime + currentProcess.quantum == currentTime){
                    // If it's quantum has finished the update quantum = ceil(quantum + 10%(mean quantum)) 
                    System.out.println("process: " + currentProcess.getName() + " quantum time has finished");
                    currentProcess.quantum += getMeanQuantum(currentProcess);
                    currentProcess.endTime = currentTime;
                    current.end_time = currentTime;
                    current.process = currentProcess;
                    System.out.println(currentProcess.getName() + "'s new quantum is: " + currentProcess.quantum);
                     gui.switchExecution(current);
                    arrivedAndWaiting.add(currentProcess);
                    p = getFirstInWaiting(currentTime);
                    if(p != null)System.out.println("Process "+ p.getName() + " Occupied the CPU after " + currentProcess.getName() + "'s quantum finished");
                    currentProcess = p;
                    p = null;
                    traverseProcess(currentProcess);
                }
                //If quantum hasn't passed yet but 50% of in has elapsed 
                else if(currentProcess.getMidRange() <= currentTime) {
                    p = getLeastAGfactor();
                    if(p != null && p.agFactor < currentProcess.agFactor) {
                        //SWAP!!
                        //Update quantum to old quantum + remaining of current quantum --> expected end time
                        currentProcess.quantum += ((currentProcess.startTime + currentProcess.quantum) - currentTime);
                        currentProcess.endTime = currentTime;
                        current.end_time = currentTime;
                        current.process = currentProcess;
                        gui.switchExecution(current);
                        System.out.println(currentProcess.getName() + "'s new quantum is: " + currentProcess.quantum);
                        //Append running in waiting queue
                        arrivedAndWaiting.add(currentProcess);
                        //Switch process ad delete from waiting
                        System.out.println("Process "+ p.getName() + " kicked out " + currentProcess.getName() + " forcefully");
                        currentProcess = p;
                        traverseProcess(currentProcess);
                        currentProcess.startTime = currentTime;
                        current.start_time = currentTime;
                        arrivedAndWaiting.remove(p);
                        p = null;
                    }

                }
            }
        }
        calculateTurnAround();
        calculateWaiting();
    }
    private int getMeanQuantum(agProcess runningProcess) {
        int mean = 0;
        for(int i = 0; i < arrivedAndWaiting.size(); ++i)
            mean += agScheduler.get(i).quantum;
        mean += runningProcess.quantum;
        mean = (int) Math.ceil((10.0/100.0)*((double)mean/(double)(arrivedAndWaiting.size() + 1)));
        return mean;
    }
    void traverseLists() {
        System.out.println();
        System.out.print("Waiting :"+ arrivedAndWaiting.size()  +": --");
        for(int i = 0; i < arrivedAndWaiting.size(); ++i)
            System.out.print(" -> " + agScheduler.get(i).getName());
        System.out.println();
    }
    void traverseProcess(agProcess currentProcess) {
        if(currentProcess != null) 
            System.out.println(currentProcess.getName() + " Running" +
                                "\nStart Time: " + currentProcess.startTime + 
                                "\nRemaining Time: " + currentProcess.getRemainingTime() + "" );
    }
    private static agProcess get(int i) {
        List<agProcess> list = new ArrayList<agProcess>(arrivedAndWaiting);
        agProcess p = list.get(i);
        return p;
    }
    private static void calculateTurnAround() {
        for(agProcess p: processes) {
            p.setTurnAround(p.endTime - p.getArrivalTime());
            avgTurnAroundTime += p.getTurnAround();
        }
        avgTurnAroundTime /= (double)processes.size();
    }
    private static void calculateWaiting() {
        for(agProcess p: processes) {
            p.setWaitingTime(p.getTurnAround() - p.getBurstTime());
            avgWaitingTime += p.getWaitingTime();
        }
        avgWaitingTime /= (double)processes.size();
    }
    public double getAvgTurnAroundTime() {
        return avgTurnAroundTime;
    }
    
    public double getAvgWaitingTime() {
        return avgWaitingTime;
    }
    private static agProcess getLeastAGfactor() {
        List<agProcess> list = new ArrayList<agProcess>(arrivedAndWaiting);
        agProcess p = null;
        for(int i = 0; i < list.size(); ++i) {
            if(p == null) p = list.get(0);
            if(p.agFactor > list.get(i).agFactor)
                p = list.get(i);
        }
        return p;
    }
    private static agProcess getFirstInWaiting(int currentTime){
        agProcess currentProcess = null;
        if(arrivedAndWaiting.size() >= 1) {
            currentProcess = agScheduler.get(0);
            currentProcess.startTime = currentTime; //Set start Time
            current.start_time = currentTime;
            arrivedAndWaiting.remove(currentProcess); //Remove from waiting queue
        }
        return currentProcess;
    }
}

class agProcess extends Process{
    int quantum = 0;
    int agFactor = 0;
    int startTime = 0;
    int endTime = Integer.MAX_VALUE;
    public agProcess() {
        super("",0,0,0, Color.BLACK);
    }
    public agProcess(Process p, int q) {
        super(p);
        this.quantum = q;
        this.agFactor = p.getArrivalTime() + p.getBurstTime() + p.getPriority();
    }
    public int getMidRange() {
        return (int) (this.startTime + Math.ceil(((double)this.quantum)/2.0));
    }
}

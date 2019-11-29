import java.util.ArrayList;
import java.util.List;

public class agScheduler extends Scheduler {

	ExecutionSegment current = new ExecutionSegment();
	private static List<agProcess> processes = new ArrayList<agProcess>();
	private static List<agProcess> arrivedAndWaitig = new ArrayList<agProcess>();
	public static int numOfProcesses = 0;
	int q = 0;
	public agScheduler(GUIScheduler gui, int q) {
		super(gui);
		this.q = q;
	}
	public agScheduler(GUIScheduler gui){
		super(gui);
	}
	public Process[] getProcesses() {
		return (Process[]) processes.toArray();
	}
    public void addProcess(Process process) {
    	agProcess p = new agProcess(process, this.q);
    	processes.add(p);
    	numOfProcesses += 1;
    }
    public void executeProcesses() {
		int lastArrival = processes.get(0).getArrivalTime();
		int lastArrivalIndex = 0;
		agProcess p = processes.get(numOfProcesses);
		agProcess currentProcess = null;
		int endOfExcutionTime = p.getArrivalTime() + p.getBurstTime() ; 
		p = null;
		for(int currentTime = 0; currentTime <= endOfExcutionTime; ++currentTime) { //Time Loop
			//Loop to get all arrived processes at a certain time unit
			while(lastArrival <= currentTime) {
				//Check bounds of processes list
				if(lastArrivalIndex > agScheduler.numOfProcesses) break;
				//Get processes and update index and last arrival time
				if(processes.get(lastArrivalIndex).getArrivalTime() > currentTime) break;
				p = processes.get(lastArrivalIndex++);
				lastArrival = p.getArrivalTime();
				//Add process to arrived list
				arrivedAndWaitig.add(p);
				p = null;
			}
			// If CPU is idle
			if(currentProcess == null) {
				//Choose the process with the least AG Factor
				currentProcess = arrivedAndWaitig.get(0);
				for(int i = 1; i < arrivedAndWaitig.size(); ++i) 
					if(currentProcess.agFactor > arrivedAndWaitig.get(i).agFactor ) {
						currentProcess = arrivedAndWaitig.get(i);
					}
				//Remove process from waiting q
				arrivedAndWaitig.remove(currentProcess);
				currentProcess.startTime = currentTime; //Set start Time 
				boolean lastExec = updateQuanntum(processes.indexOf(currentProcess), currentTime, false);
				if(lastExec) {
					currentProcess.endTime = currentTime;
					currentProcess = null;
				} 
			}
			//CPU is not idle and there is a process in execution 
			for(int i = 1; i < arrivedAndWaitig.size(); ++i)
				if(currentProcess.agFactor > arrivedAndWaitig.get(i).agFactor
						&& currentProcess.getMidRange() <= currentTime) 
					p = arrivedAndWaitig.get(i);
			if(p == null) { //If no interruptions continue on executing
				boolean lastExec = updateQuanntum(processes.indexOf(currentProcess), currentTime, false);
				if(lastExec) { //If quantum time has ended
					currentProcess.endTime = currentTime;
					currentProcess = null;
				} //Else continue on executing
			}else { //If there is a process with lower AG && 50% of q has passed
				boolean lastExec = updateQuanntum(processes.indexOf(currentProcess), currentTime, true);
				if(lastExec) { //If switch conditions match, switch Processes
					p.startTime = currentTime;
					currentProcess.endTime = currentTime;
					currentProcess = p;
				} 
			}
			
		}
		
	}
    private int getMeanQuantum() {
		int mean = 0;
		for(int i = 0; i < arrivedAndWaitig.size(); ++i)
			mean += arrivedAndWaitig.get(i).quantum;
		mean = (int) Math.ceil((10.0/100.0)*((double)mean/(double)arrivedAndWaitig.size()));
		return mean;
	}
    private boolean updateQuanntum(int index, int currentTime, boolean quantumInterruption) {
    	agProcess runningProcess = processes.get(index);
    	if(runningProcess.getRemainingTime() - 1 == 0 && !quantumInterruption) {
    		runningProcess.setRemainingTime(runningProcess.getRemainingTime() - 1);
    		runningProcess.setIsCompleted(true);
    		//If the process completed return true to set current to null
    		return true;
		}else if(runningProcess.startTime + runningProcess.quantum == currentTime){
			// If it's quantum has finished the update quantum = ceil(quantum + 10%(mean quantum)) interruption 
			runningProcess.setRemainingTime(runningProcess.getRemainingTime() - 1);
			runningProcess.quantum += getMeanQuantum();
			return true; //Process has finished execution 
		}else if(quantumInterruption){
			if(runningProcess.getMidRange() <= currentTime) { //Maybe redundancy in code
				//Update quantum to old quantum + remaining of current quantum
				runningProcess.quantum += currentTime - runningProcess.getMidRange();
				return true; //Switch to other process			
			}else {
				runningProcess.setRemainingTime(runningProcess.getRemainingTime() - 1);
				return false; //Keep running same process
			}
		}else {
			runningProcess.setRemainingTime(runningProcess.getRemainingTime() - 1);
			return false; //Keep running
		}
    } 
	
}

class agProcess extends Process{
	int quantum = 0;
	int agFactor = 0;
	int startTime = 0;
	int endTime = 0;
	public agProcess() {
		super("",0,0,0,0,0,0,0);
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

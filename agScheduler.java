import java.util.ArrayList;
import java.util.List;

class agProcess extends Process{
	int quantum;
	int agFactor;
	public agProcess(Process p, int q) {
		super(p);
		this.quantum = q;
		this.agFactor = p.getArrivalTime() + p.getBurstTime() + p.getPriority();
	}
}

public class agScheduler extends Scheduler {

	ExecutionSegment current = new ExecutionSegment();
	private static List<agProcess> processes = new ArrayList<agProcess>();
	List<agProcess> arrivedAndWaitig = new ArrayList<agProcess>();
	int numOfProcesses = 0;
	int q = 0;
	public agScheduler(GUIScheduler gui, int q) {
		super(gui);
		numOfProcesses = processes.size() - 1;
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
    }
 }
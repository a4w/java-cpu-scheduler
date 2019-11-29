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
    private int getMeanQuantum() {
		int mean = 0;
		for(int i = 0; i < arrivedAndWaitig.size(); ++i)
			mean += arrivedAndWaitig.get(i).quantum;
		mean = (int) Math.ceil((10.0/100.0)*((double)mean/(double)arrivedAndWaitig.size()));
		return mean;
	}
	
}

class agProcess extends Process{
	int quantum = 0;
	int agFactor = 0;
	public agProcess() {
		super("",0,0,0,0,0,0,0);
		quantum = 0;
		agFactor = 0;
	}
	public agProcess(Process p, int q) {
		super(p);
		this.quantum = q;
		this.agFactor = p.getArrivalTime() + p.getBurstTime() + p.getPriority();
	}
}


public class Main{
	public static void main(String[] args){
		GUIScheduler gui = null;
		agScheduler ag = new agScheduler(gui, 4);
        Process p1 = new Process("P1", 0, 17, 17, 0, 0, 0, 4 );
        Process p2 = new Process("P2", 3, 6, 6, 0, 0, 0, 9 );
        Process p3 = new Process("P3", 4, 10, 10, 0, 0, 0, 3 );
        Process p4 = new Process("P4", 29, 4, 4, 0, 0, 0, 8 );
        ag.addProcess(p1);
        ag.addProcess(p2);
        ag.addProcess(p3);
        ag.addProcess(p4);
        ag.executeProcesses();
    }
}
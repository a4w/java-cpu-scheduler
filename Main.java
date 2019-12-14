
public class Main{
	public static void main(String[] args){
        Process process = new Process("Process 1", 2,10);
        Process p = new Process("Process 2",5,4);
        Process p1 = new Process("Process 3",6,3);
        Process p2 = new Process("Process 4",7,1);
        SRT srt = new SRT();
        srt.addProcess(process);
        srt.addProcess(p);
        srt.addProcess(p1);
        srt.addProcess(p2);

        GUIScheduler gui = new GUIScheduler(srt.getProcesses());
        srt.setGUI(gui);
        
        srt.executeProcesses(2);
    }
}
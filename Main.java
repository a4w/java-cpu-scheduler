import java.awt.Color;

public class Main{
    public static void main(String[] args){

        Process process = new Process("Process 1", 2,10, Color.RED);
        Process p = new Process("Process 2",5,4, Color.PINK);
        Process p1 = new Process("Process 3",6,3, Color.CYAN);
        Process p2 = new Process("Process 4",7,1, Color.MAGENTA);

        GUIScheduler gui = new GUIScheduler();

        SRT srt = new SRT(gui);
        srt.addProcess(process);
        srt.addProcess(p);
        srt.addProcess(p1);
        srt.addProcess(p2);

        gui.setProcesses(srt.getProcesses());


        srt.executeProcesses(2);
    }
}

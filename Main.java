import java.awt.Color;

public class Main {
    public static void main(String[] args) {

        GUIScheduler gui = new GUIScheduler();
        SRTF srtf = new SRTF(gui);

        Process P1 = new Process("P1", 0, 7, Color.RED);
        Process P2 = new Process("P2", 1, 5, Color.PINK);
        Process P3 = new Process("P3", 2, 3, Color.CYAN);
        Process P4 = new Process("P4", 3, 1, Color.MAGENTA);
        Process P5 = new Process("P5", 4, 2, Color.BLUE);
        Process P6 = new Process("P6", 5, 1, Color.YELLOW);

        srtf.addProcess(P1);
        srtf.addProcess(P2);
        srtf.addProcess(P3);
        srtf.addProcess(P4);
        srtf.addProcess(P5);
        srtf.addProcess(P6);

        gui.setProcesses(srtf.getProcesses());

        srtf.runSRTF();

    }
}

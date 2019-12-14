import java.awt.Color;

public class Main {
    public static void main(String[] args) {

        GUIScheduler gui = new GUIScheduler();
        SJF sjf = new SJF(gui);

        Process P1 = new Process("P1", 0, 7, Color.RED);
        Process P2 = new Process("P2", 2, 4, Color.PINK);
        Process P3 = new Process("P3", 4, 1, Color.CYAN);
        Process P4 = new Process("P4", 5, 4, Color.MAGENTA);

        sjf.addProcess(P1);
        sjf.addProcess(P2);
        sjf.addProcess(P3);
        sjf.addProcess(P4);

        gui.setProcesses(sjf.getProcesses());

        sjf.SJFScheduler();

    }
}

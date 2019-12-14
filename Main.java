import java.awt.Color;

public class Main {
    public static void main(String[] args) {

        GUIScheduler gui = new GUIScheduler();
        priorityScheduler ps = new priorityScheduler(gui);

        Process P1 = new Process("P1", 0, 3, 2, Color.RED);
        Process P2 = new Process("P2", 2, 5, 6, Color.PINK);
        Process P3 = new Process("P3", 1, 4, 3, Color.CYAN);
        Process P4 = new Process("P4", 4, 2, 5, Color.MAGENTA);
        Process P5 = new Process("P5", 6, 9, 7, Color.MAGENTA);
        Process P6 = new Process("P6", 5, 4, 4, Color.MAGENTA);
        Process P7 = new Process("P7", 7, 10, 10, Color.MAGENTA);

        ps.addProcess(P1);
        ps.addProcess(P2);
        ps.addProcess(P3);
        ps.addProcess(P4);
        ps.addProcess(P5);
        ps.addProcess(P6);
        ps.addProcess(P7);

        gui.setProcesses(ps.getProcesses());

        ps.priorityScheduling();

    }
}

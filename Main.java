import java.awt.Color;

public class Main{
    public static void main(String[] args){

        GUIScheduler gui = new GUIScheduler();
        agScheduler ag = new agScheduler(gui, 4);

        Process P1 = new Process("P1",0,17,4,Color.RED);
        Process P2 = new Process("P2",3,6,9,Color.PINK);
        Process P3 = new Process("P3",4,10,3,Color.CYAN);
        Process P4 = new Process("P4",29,4,8,Color.MAGENTA);

        ag.addProcess(P1);
        ag.addProcess(P2);
        ag.addProcess(P3);
        ag.addProcess(P4);

        gui.setProcesses(ag.getProcesses());

        ag.executeProcesses();

    }
}

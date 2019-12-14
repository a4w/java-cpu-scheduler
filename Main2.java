import java.awt.Color;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        GUIScheduler srtfGUI = new GUIScheduler();
        SRTF srtf = new SRTF(srtfGUI);

        GUIScheduler sjfGUI = new GUIScheduler();
        SJF sjf = new SJF(sjfGUI);

        GUIScheduler agGUI = new GUIScheduler();
        agScheduler ag = new agScheduler(agGUI);

        GUIScheduler pGUI = new GUIScheduler();
        priorityScheduler p = new priorityScheduler(pGUI);

        Scanner in = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        final int n = in.nextInt();

        for(int i = 0; i < n; ++i){
            System.out.print("Process name: ");
            String name = in.next();
            System.out.print("Process arrival: ");
            int arrival = in.nextInt();
            System.out.print("Process burst: ");
            int burst = in.nextInt();
            System.out.print("Process priority: ");
            int priority = in.nextInt();
            System.out.print("Process color: ");
            String col = in.next();
            Color color = Color.decode(col);
            Process process = new Process(name, arrival, burst, priority, color);
            srtf.addProcess(process);
            sjf.addProcess(process);
            ag.addProcess(process);
            p.addProcess(process);
        }
        srtfGUI.setProcesses(srtf.getProcesses());
        sjfGUI.setProcesses(sjf.getProcesses());
        agGUI.setProcesses(ag.getProcesses());
        pGUI.setProcesses(p.getProcesses());

        srtf.runSRTF();
        sjf.SJFScheduler();
        ag.executeProcesses();
        p.priorityScheduling();

        in.close();

    }
}

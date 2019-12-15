import java.awt.Color;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

    	Scanner inputStr = new Scanner (System.in);
    	Scanner inputInt = new Scanner (System.in);
    	int procNum;
    	String name, color;
    	int arrival, burst, priorty, rrQuantum;
    	
        GUIScheduler SJFgui = new GUIScheduler();
        GUIScheduler SRTFgui = new GUIScheduler();
        GUIScheduler Priortygui = new GUIScheduler();
        GUIScheduler AGgui = new GUIScheduler();

		System.out.println("enter number of processes: ");
		procNum = inputInt.nextInt();
		System.out.println("enter round robin time quantum: ");
		rrQuantum = inputInt.nextInt();
		
		SJF sjf = new SJF(SJFgui);
		SRTF srtf = new SRTF(SRTFgui);
		priorityScheduler p = new priorityScheduler(Priortygui);
		agScheduler ag = new agScheduler(AGgui, rrQuantum);
		
		for (int i=0; i<procNum; i++)
		{
			System.out.println("enter name, arrival, burst, priorty and color of process number " + (i+1));
			name = inputStr.nextLine();
			arrival = inputInt.nextInt();
			burst = inputInt.nextInt();
			priorty = inputInt.nextInt();
			color = inputStr.nextLine();
			Process process = new Process(name, arrival, burst, priorty,Color.getColor(color));
			
			sjf.addProcess(process);
			srtf.addProcess(process);
			p.addProcess(process);
			ag.addProcess(process);
		}
		inputInt.close();
		inputStr.close();
		
		SJFgui.setProcesses(sjf.getProcesses());
		sjf.SJFScheduler();
        SRTFgui.setProcesses(srtf.getProcesses());
        srtf.runSRTF();
        Priortygui.setProcesses(p.getProcesses());
        p.priorityScheduling();
        AGgui.setProcesses(ag.getProcesses());
        ag.executeProcesses();

    }
}

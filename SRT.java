import java.util.ArrayList;


class SRT extends Scheduler {
    ArrayList<Process> processes;

    SRT(GUIScheduler gui){
        super(gui);
        this.processes = new ArrayList<>();
    }
    public void addProcess(Process process){
        processes.add(process);
    }
    void executeProcesses(int contextSwitchTime){
        int[] remaining_time = new int[this.processes.size()];
        for(int i = 0; i < this.processes.size(); ++i)
            remaining_time[i] = processes.get(i).getBurstTime();
        int complete = 0;
        int time = 0;
        int minTime = Integer.MAX_VALUE;
        int minIdx = -1;
        int lastIdx = -2;
        while(complete != this.processes.size()){
            //System.out.println("time: " + time);
            minTime = Integer.MAX_VALUE;
            minIdx = -1;
            for(int i = 0; i < processes.size(); ++i){
                if(processes.get(i).getArrivalTime() <= time && remaining_time[i] < minTime && remaining_time[i] > 0){
                    minTime = remaining_time[i];
                    minIdx = i;
                }
            }
            if(minIdx != -1){
                if(lastIdx != minIdx){
                    ExecutionSegment segment = new ExecutionSegment();
                    segment.start_time = time;
                    segment.end_time = time+contextSwitchTime;
                    segment.process = null;
                    gui.switchExecution(segment);
                    time += contextSwitchTime;
                    lastIdx = minIdx;
                    time--;
                    continue;
                }
                ExecutionSegment segment = new ExecutionSegment();
                segment.start_time = time;
                segment.end_time = time+1;
                segment.process = this.processes.get(minIdx);
                gui.switchExecution(segment);
                remaining_time[minIdx]--;
                if(remaining_time[minIdx] == 0){
                    complete++;
                    int turnaround = time - processes.get(minIdx).getArrivalTime() + 1;
                    int waiting = turnaround - processes.get(minIdx).getBurstTime();
                    System.out.println("Finished " + processes.get(minIdx).getName());
                    System.out.println("Turnaround time: " + turnaround);
                    System.out.println("Waiting time: " + waiting);
                    processes.get(minIdx).setWaitingTime(waiting);
                    processes.get(minIdx).setTurnAround(turnaround);
                }
            }
            time++;
        }


    }
    public Process[] getProcesses(){
        Process[] arr = new Process[processes.size()];
        for(int i = 0; i < arr.length; ++i){
            arr[i] = processes.get(i);
        } 
        return arr;
    }

}

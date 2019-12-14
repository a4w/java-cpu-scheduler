abstract class Scheduler{
    GUIScheduler gui;
    Scheduler(GUIScheduler gui){
        this.gui = gui;
    }
    Scheduler(){
    }
    abstract public Process[] getProcesses();
    abstract public void addProcess(Process process);
    public void setGUI(GUIScheduler gui){
        this.gui = gui;
    }
}

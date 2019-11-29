abstract class Scheduler{
    GUIScheduler gui;
    Scheduler(GUIScheduler gui){
        this.gui = gui;
    }
    abstract public Process[] getProcesses();
    abstract public void addProcess();
}

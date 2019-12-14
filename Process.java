import java.awt.Color;

public class Process{
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;
    private int lastEntered;
    private int waitingTime;
    private int turnAround;
    private int priority;
    private boolean isCompleted;
    private Color color;

    public Process(String name, int arrivalTime, int burstTime, Color color) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.isCompleted = false;
        this.color = color;
    }
    public Process(String name, int arrivalTime, int burstTime, int priority, Color color) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.isCompleted = false;
        this.priority = priority;
        this.color = color;
    }

    public Process(Process p) {
        this.name = p.getName();
        this.arrivalTime = p.getArrivalTime();
        this.burstTime = p.getBurstTime();
        this.remainingTime = p.getBurstTime();
        this.lastEntered = p.getLastEntered();
        this.waitingTime = p.getWaitingTime();
        this.turnAround = p.getTurnAround();
        this.priority = p.getPriority();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setLastEntered(int lastEntered) {
        this.lastEntered = lastEntered;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnAround(int turnAround) {
        this.turnAround = turnAround;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getLastEntered() {
        return lastEntered;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnAround() {
        return turnAround;
    }

    public int getPriority() {
        return priority;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public Color getColor(){
        return this.color;
    }

}

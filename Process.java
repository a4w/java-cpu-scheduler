import java.util.*;
import java.lang.*;

public class Process{
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;
    private int lastEntered;
    private int waitingTime;
    private int turnAround;
    private int priority;
    private int quantum;


    public Process(String name, int arrivalTime, int burstTime, int remainingTime, int lastEntered, int waitingTime, int turnAround, int priority, int quantum) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = remainingTime;
        this.lastEntered = lastEntered;
        this.waitingTime = waitingTime;
        this.turnAround = turnAround;
        this.priority = priority;
        this.quantum = quantum;
    }


    public Process(String name, int arrivalTime, int burstTime, int waitingTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = waitingTime;
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

    public void setQuantum(int quantum) {
        this.quantum = quantum;
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

    public int getQuantum() {
        return quantum;
    }

    public static void Main(String[] args){
        Process p = new Process("okasha",1,2,3,4,5,6,7,8);
        System.out.println(p.getName());
    }
}
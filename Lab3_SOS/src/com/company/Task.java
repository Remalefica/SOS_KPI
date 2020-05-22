package com.company;

public class Task implements Comparable<Task> {
    private int time;
    private int waiting;

    public Task(int time) {
        super();
        this.time = time;
        waiting = 0;
    }

    @Override
    public int compareTo(Task t) {
        return getTime() - t.getTime();
    }

    @Override
    public String toString() {
        return Integer.toString(time);
    }

    public synchronized int getWaiting() {
        return waiting;
    }

    public void decTime() {
        time--;
    }

    public void waitOne() {
        waiting++;
    }

    public int getTime() {
        return time;
    }

}
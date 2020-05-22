package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {

    private int idle;
    private Task CPU;
    private ArrayList<Task> queue;

    private final double lambda;
    private final int minTime, maxTime;
    private final ArrayList<Integer> waiting;
    private final double range = 0.5;

    public Main(double lambda, int minTime, int maxTime) {
        super();

        this.lambda = lambda;
        this.minTime = minTime;
        this.maxTime = maxTime;

        idle = 0;
        CPU = new Task(nextTime(minTime, maxTime));
        queue = new ArrayList<Task>();
        waiting = new ArrayList<Integer>();
        queue.add(new Task(nextTime(minTime, maxTime)));
        queue.add(new Task(nextTime(minTime, maxTime)));
    }

    public static void main(String[] args) {
        Main s = new Main(3, 1, 5);

        for (int i = 0; i < 50; i++) {
            s.nextIteration();
            System.out.println("#" + (i + 1) + " CPU: " + s.getCPU() + " Queue: " + s.getQueue());
        }

        System.out.println("waiting: " + s.getWaiting());
        System.out.println("idle: " + s.getIdle());
    }

    private double exp(double lambda) {
        return (1 - Math.exp(-lambda * Math.random()));
    }

    public synchronized int getIdle() {
        return idle;
    }

    public synchronized Task getCPU() {
        return CPU;
    }

    public synchronized ArrayList<Task> getQueue() {
        return queue;
    }

    public synchronized ArrayList<Integer> getWaiting() {
        return waiting;
    }

    private void addTask() {
        Task task = new Task(nextTime(minTime, maxTime));
        boolean p = exp(lambda) > range;
        if (p) {
            int i = Collections.binarySearch(queue, task);
            if (i < 0)
                i = -i - 1;
            queue.add(i, task);
        }
    }

    public int nextTime(int minTime, int maxTime) {
        if (minTime <= 0 || maxTime <= 0)
            throw new IllegalArgumentException("minTime & maxTime cannot be lower than 0");

        if (minTime > maxTime)
            throw new IllegalArgumentException("minTime cannot be less than maxTime");

        Random r = new Random();
        int time = minTime;
        time += r.nextInt(maxTime - minTime + 1);
        return time;
    }

    public void nextIteration() {
        if ( CPU != null && CPU.getTime() != 0)
            CPU.decTime();
        else if (queue.size() > 0) {
            if (CPU != null) {
                waiting.add(CPU.getWaiting());
            }
            CPU = queue.get(0);
            queue.remove(0);
        } else {
            CPU = null;
            idle++;
        }
        queueWaiting();
        addTask();
    }

    public int averageWaiting() {
        int sum = 0;
        for (Integer wait : waiting)
            sum += wait;
        sum /= waiting.size();
        return sum;
    }

    public void queueWaiting() {
        for (Task task : queue)
            task.waitOne();
    }
}
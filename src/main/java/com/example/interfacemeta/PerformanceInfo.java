package com.example.interfacemeta;

import java.util.ArrayList;

public class PerformanceInfo {
    public long getTime() {
        return Time;
    }

    public double getEvaluation() {
        return evaluation;
    }

    public int getNodes() {
        return nodes;
    }

    private long Time;
    private double evaluation;
    private int nodes;

    public int getTotalValue() {
        return totalValue;
    }

    private int totalValue;

    public ArrayList<Integer> getSolution() {
        return solution;
    }

    public void setSolution(ArrayList<Integer> solution) {
        this.solution = solution;
    }

    private ArrayList<Integer> solution;

    public void setTime(long time) {
        Time = time;
    }

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }

    public void setNodeNumber(int nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Time : "+Time+"\t"+
               "Evaluation : "+ evaluation + " "+
               "Total : "+ totalValue + "\t"+ 
               "Number of developed nodes : "+nodes + " ";
    }


    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }
}

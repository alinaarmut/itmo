package com.example.mainweb2;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Point implements Serializable{
    private double x;
    private double y;
    private double r;
    private boolean res;

    public void setError(boolean error) {
        this.error = error;
    }

    private boolean error;
private String  currentTime;

    private long executionTime;
    public Point(double x, double y, double r, boolean res, String currentTime, long executionTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.res = res;
        this.currentTime = currentTime;
        this.executionTime = executionTime;
        this.error = error;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isError() {
        return error;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String  getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0 &&
                Double.compare(point.r, r) == 0 &&
                res == point.res &&
                Objects.equals(currentTime, point.currentTime) &&
                Objects.equals(executionTime, point.executionTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r, res, currentTime, executionTime);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", res=" + res +
                ", currentTime=" + currentTime +
                ", executionTime=" + executionTime +
                '}';
    }
}

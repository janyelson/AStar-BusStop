package model;

import java.util.ArrayList;

public class StopNode
{
    private long id;
    private ArrayList<Long> lines;
    private String stop;
    private ArrayList<Long> neighbors;
    private double coordinateX;
    private double coordinateY;

    private double gScore;
    private double fScore;
    private Long cameFrom;

    public StopNode(long id, ArrayList<Long> lines, String stop, ArrayList<Long> neighbors, double coordinateX, double coordinateY)
    {
        this.id = id;
        this.lines = lines;
        this.stop = stop;
        this.neighbors = neighbors;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;

        this.gScore = Double.MAX_VALUE;
        this.fScore = Double.MAX_VALUE;
        cameFrom = 0L;
    }

    public long getId() {
        return id;
    }

    public ArrayList<Long> getLines() {
        return lines;
    }

    public String getStop() {
        return stop;
    }

    public ArrayList<Long> getNeighbors() {
        return neighbors;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public double getgScore() {
        return gScore;
    }

    public void setgScore(double gScore) {
        this.gScore = gScore;
    }

    public double getfScore() {
        return fScore;
    }

    public void setfScore(double fScore) {
        this.fScore = fScore;
    }

    public Long getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(Long cameFrom) {
        this.cameFrom = cameFrom;
    }

    public int getNeighborsSize()
    {
        return neighbors.size();
    }

    public Long getNeighborId(int point)
    {
        return neighbors.get(point);
    }

    public boolean hasRoute(Long route)
    {
        return lines.contains(route);
    }

    public String toString()
    {
        return "ID: " + this.id + "\n"
                + "Line: " + lines.get(0) + "\n"
                + "Stop: " + stop + "\n"
                + "Cordinate X" + coordinateX + "\n"
                + "Cordinate Y" + coordinateY + "\n";
    }
}

package model;

import java.util.ArrayList;

public class StopsGraph {

    private ArrayList<StopNode> stops;
    public StopsGraph()
    {
        stops = new ArrayList<>();
    }

    public void addStop(StopNode stop)
    {
        stops.add(stop);
    }

    public StopNode getStop(int point)
    {
        return stops.get(point);
    }

    public StopNode getStopByID(Long id)
    {
        for(StopNode stopNode : stops)
        {
            if(stopNode.getId() == id)
            {
                return stopNode;
            }
        }

        return null;
    }

    public StopNode getStopByName(String stop)
    {
        for(StopNode stopNode : stops)
        {
            if(stopNode.getStop().contains(stop))
            {
                return stopNode;
            }
        }

        return null;
    }

    public boolean isEmpty()
    {
        return stops.isEmpty();
    }

    public int size()
    {
        return stops.size();
    }

    public void remove(StopNode stopNode)
    {
        stops.remove(stopNode);
    }

    public void showNodes()
    {
        for(StopNode stopNode : stops)
        {
            System.out.println(stopNode.toString());
        }
    }
}

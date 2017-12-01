package heuristic;

import infra.StopsReader;
import model.StopNode;
import model.StopsGraph;

import java.util.ArrayList;

public class AStar
{
    private StopNode start;
    private StopNode goal;
    private StopsGraph graph;

    public AStar(Long initial, Long destiny)
    {
        this.graph = new StopsReader().load();
        this.start = graph.getStopByID(initial);
        this.goal = graph.getStopByID(destiny);
    }

    public AStar()
    {
        this(1L, 7L);
    }

    public ArrayList<StopNode> run()
    {
        StopsGraph closedSet = new StopsGraph();
        StopsGraph openSet = new StopsGraph();
        openSet.addStop(start);

        start.setgScore(0);
        start.setfScore(heuristicCostEstimate(start));

        while(!openSet.isEmpty())
        {

            StopNode current = lowestFScore(openSet);

            if(current.getId() == goal.getId()) return recontructPath(current);


            openSet.remove(current);
            closedSet.addStop(current);

            for(int i = 0; i < current.getNeighborsSize(); i++)
            {
                StopNode neighbor = graph.getStopByID(current.getNeighborId(i));

                if(hasStopNode(neighbor, closedSet)) continue;

                if(!hasStopNode(neighbor, openSet))
                {
                    openSet.addStop(neighbor);
                }

                double tentativeScore = current.getgScore() + distanceInKmBetweenEarthCoordinates(current, neighbor) + changeRoute(current, neighbor);
                if(tentativeScore >= neighbor.getgScore()) continue;


                neighbor.setCameFrom(current.getId());
                neighbor.setgScore(tentativeScore);
                neighbor.setfScore(tentativeScore + heuristicCostEstimate(neighbor));

            }

        }

        return new ArrayList<StopNode>();
    }

    private boolean hasStopNode(StopNode stopNode, StopsGraph closedSet) {
        StopNode anotherNode = closedSet.getStopByID(stopNode.getId());
        return anotherNode != null;
    }

    private ArrayList<StopNode> recontructPath(StopNode current)
    {
        ArrayList<StopNode> opostPath = new ArrayList<>();
        ArrayList<StopNode> realPath = new ArrayList<>();

        opostPath.add(current);

        while(current.getCameFrom() != 0L)
        {
            current = graph.getStopByID(current.getCameFrom());
            opostPath.add(current);
        }

        for(int i = opostPath.size() - 1; i >= 0; i--)
        {
            realPath.add(opostPath.get(i));
        }

        return realPath;
    }

    private StopNode lowestFScore(StopsGraph stopsGraph)
    {
        if(stopsGraph.isEmpty()) return null;

        StopNode lowestNode = stopsGraph.getStop(0);

        for(int i = 1; i < stopsGraph.size(); i++)
        {
            if(lowestNode.getfScore() > stopsGraph.getStop(i).getfScore()) lowestNode = stopsGraph.getStop(i);
        }

        return lowestNode;
    }

    private double degreesToRadians(double degrees) {
        return (degrees * Math.PI / 180);
    }

    private double changeRoute(StopNode current, StopNode nextNode)
    {
        for(Long currentLine : current.getLines())
        {
            if(nextNode.hasRoute(currentLine))
            {
                return 0.0;
            }
        }

        return 3.33;
    }

    private double heuristicCostEstimate(StopNode current)
    {
        double extra = 0;

        if(!current.getStop().contains("Normal")) extra -= 3.0;

        return distanceInKmBetweenEarthCoordinates(current, goal) + extra;
    }

    private double distanceInKmBetweenEarthCoordinates(StopNode stopNode1, StopNode stopNode2) {
        double earthRadiusKm = 6371;

        double dLat = degreesToRadians(stopNode2.getCoordinateX()-stopNode1.getCoordinateX());
        double dLon = degreesToRadians(stopNode2.getCoordinateY()-stopNode1.getCoordinateY());

        double lat1 = degreesToRadians(stopNode1.getCoordinateX());
        double lat2 = degreesToRadians(stopNode2.getCoordinateX());

        double a = (Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2));
        double c = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)));
        return earthRadiusKm * c;
    }
}

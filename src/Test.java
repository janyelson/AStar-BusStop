import heuristic.AStar;
import model.StopNode;

import java.util.ArrayList;

public class Test
{
    public static void main(String args[])
    {
        Long initial = 1L;
        Long destiny = 14L;

        ArrayList<StopNode> resultPath = new AStar(initial, destiny).run();

        System.out.print("Best Path: ");
        for(StopNode stopNode : resultPath)
        {
            System.out.print(stopNode.getId() + " ");
        }

        System.out.println();

    }
}

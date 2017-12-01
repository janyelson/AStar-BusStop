package infra;

import model.StopNode;
import model.StopsGraph;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StopsReader {

    private final static String URL = "src/infra/bus_stop.json";

    public StopsGraph load()
    {
        StopsGraph stops = new StopsGraph();
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(URL));
            JSONArray results = (JSONArray) jsonObject.get("results");

            for (Object result : results) {

                JSONObject stopJson = (JSONObject) result;
                long id = (Long) stopJson.get("id");
                ArrayList<Long> lines = getLines((JSONArray) stopJson.get("lines"));
                String stop = (String) stopJson.get("Stop");
                ArrayList<Long> neighbors = getNeighbors((JSONArray) stopJson.get("neighbors"));
                double coordinateX = (double) ((JSONArray) stopJson.get("coordinates")).get(0);
                double coordinateY = (double) ((JSONArray) stopJson.get("coordinates")).get(1);

                stops.addStop(new StopNode(id, lines, stop, neighbors, coordinateX, coordinateY));
            }

            return stops;

        } catch (IOException | ParseException e) {

            e.printStackTrace();
        }

        return stops;
    }

    private ArrayList<Long> getLines(JSONArray linesArray)
    {
        ArrayList<Long> lines = new ArrayList<Long>();

        for (Object aLinesArray : linesArray) {
            lines.add((Long) aLinesArray);
        }

        return lines;
    }

    private ArrayList<Long> getNeighbors(JSONArray neighborsArray)
    {
        ArrayList<Long> neighbors = new ArrayList<Long>();

        for (Object aLinesArray : neighborsArray) {
            neighbors.add((Long) aLinesArray);
        }

        return neighbors;
    }

}

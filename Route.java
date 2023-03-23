import java.util.HashMap;
import java.util.Map;

public class Route {

    private String name;
    private Map<Integer, Integer> dest_distance = new HashMap<Integer, Integer>();

    public Route(String name, Map<Integer, Integer> dest_distance) {
        this.name = name;
        this.dest_distance = dest_distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Integer> getDest_distance() {
        return dest_distance;
    }

    public void addDest_distance(Map<Integer, Integer> dest_dist) {
        this.dest_distance = dest_dist;
    }

}

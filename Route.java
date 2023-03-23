
public class Route {

    private String name;
    private int dest_id;
    private int dest_distance;

    public Route(String name, int dest_id, int dest_distance) {
        this.name = name;
        this.dest_id = dest_id;
        this.dest_distance = dest_distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDest_id() {
        return dest_id;
    }

    public void setDest_id(int dest_id) {
        this.dest_id = dest_id;
    }

    public int getDest_distance() {
        return dest_distance;
    }

    public void setDest_distance(int dest_distance) {
        this.dest_distance = dest_distance;
    }

}

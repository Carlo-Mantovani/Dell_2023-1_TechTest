import java.util.HashMap;
import java.util.Map;

public class Destination {

    private Map<Integer, String> dest = new HashMap<Integer, String>();

    public Destination(Map<Integer, String> dest) {
        this.dest = dest;

    }

    public Map<Integer, String> getDest() {
        return dest;
    }

    public void setDest(Map<Integer, String> dest) {
        this.dest = dest;
    }

}

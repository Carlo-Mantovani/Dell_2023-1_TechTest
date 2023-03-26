public class SubRoute {

    private String origin;//origem do trecho
    private String destination;//destino do trecho
    private int distance;//distancia do trecho

    //construtor
    public SubRoute(String origin, String destination, int distance) {
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
    }


    //getters e setters
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

}

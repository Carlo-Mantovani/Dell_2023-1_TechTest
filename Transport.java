import java.util.List;

public class Transport {
    private List<String> cities;//cidades que o transporte passa
    private List<Product> products;//produtos que o transporte transporta
    public Transport(List<String> cities, List<Product> products) {
        this.cities = cities;
        this.products = products;
    }
    public List<String> getCities() {
        return cities;
    }
    public void setCities(List<String> cities) {
        this.cities = cities;
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
}
    


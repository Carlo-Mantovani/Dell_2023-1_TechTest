import java.util.List;
import java.util.Map;

public class Transport {
    private List<String> cities;//cidades que o transporte passa
    private List<Product> products;//produtos que o transporte transporta
    private Map<String,Map<String,Integer>> deposit;//depositos que o transporte faz em cada cidade
    public Transport(List<String> cities, List<Product> products, Map<String,Map<String,Integer>> deposit) {
        this.cities = cities;
        this.products = products;
        this.deposit = deposit;
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
    public Map<String, Map<String, Integer>> getDeposit() {
        return deposit;
    }
    public void setDeposit(Map<String, Map<String, Integer>> deposit) {
        this.deposit = deposit;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
}
    


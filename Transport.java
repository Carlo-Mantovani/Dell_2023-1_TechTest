import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transport {
    private List<String> cities;// cidades que o transporte passa
    private List<Product> products;// produtos que o transporte transporta
    private Map<String, Map<String, Integer>> deposit;// depositos que o transporte faz em cada cidade

    public Transport(List<String> cities, List<Product> products, Map<String, Map<String, Integer>> deposit) {
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

    public String toString(Map<String, Map<String, Integer>> routes, Map<Integer, Double> costs) {
        String origin = cities.get(0);
        String destination = cities.get(cities.size() - 1);
        System.out.println(products.size());
        Map<String, Integer> bestTransportOptions = getBestTransportOption(products, costs);
        int totalDistance = calculateTotalDistance(routes);

        if (cities.size() <= 2) {
            double totalCost = getCostBetweenCities(totalDistance, bestTransportOptions, costs);
            double averageCostPerKm = totalCost / totalDistance;
            double productQuantity = 0;
            for (Product p: products){
                productQuantity += p.getQuantity();
            }
            double averageCostPerProduct = totalCost / productQuantity;
            
            return "\nDe " + origin + " para " + destination + " transportando:" + printListProducts(products)
                    + "\nA distância total é de: "
                    + totalDistance + "km"
                    + " e os transportes a serem utilizados para resultar no menor custo de transporte sao:\n"
                    + printBestTransportOption(bestTransportOptions) + "\n"
                    + "O custo total do transporte é de: R$" + totalCost + "\n"
                    + "O custo médio por km rodado é de: R$" + averageCostPerKm + "\n"
                    + "O custo médio por produto transportado é de: R$" + averageCostPerProduct + "\n";
        }
        return "De [cities=" + cities + ", products=" + products + ", deposit=" + deposit + "]";
    }

    private String printListProducts(List<Product> products) {
        String list = "";
        for (int i = 0; i < products.size(); i++) {
            list += products.get(i).toString();

        }
        return list;
    }

    private int calculateTotalDistance(Map<String, Map<String, Integer>> routes) {
        int totalDistance = 0;
        for (int i = 0; i < cities.size(); i++) {
            if (i == cities.size() - 1) {
                break;
            }
            totalDistance += routes.get(cities.get(i)).get(cities.get(i + 1));
        }
        return totalDistance;
    }

    private String printBestTransportOption(Map<String, Integer> bestTransport) {
        String bestTransportOption = "";
        for (Map.Entry<String, Integer> entry : bestTransport.entrySet()) {
            bestTransportOption += entry.getValue() + " caminhao(s) do tipo " + entry.getKey() + "\n";
        }
        bestTransportOption += "para resultar no menor custo de transporte por km rodado.";
        return bestTransportOption;
    }

    private Map<String, Integer> getBestTransportOption(List<Product> products, Map<Integer, Double> costs) {
        Map<String, Integer> bestTransport = new HashMap<String, Integer>();
        int smallCapacity = 1000;
        int mediumCapacity = 4000;
        int bigCapacity = 10000;

        double totalWeight = 0;
        for (Product p : products) {
            totalWeight = totalWeight + (p.getWeight() * p.getQuantity());
        }
        if (totalWeight <= smallCapacity) {
            bestTransport.put("Pequeno Porte", 1);
        } else if (totalWeight <= mediumCapacity) {
            bestTransport.put("Medio Porte", 1);
        } else if (totalWeight <= bigCapacity) {
            bestTransport.put("Grande Porte", 1);
        } else {
            int small = (int) (totalWeight / smallCapacity);
            int medium = (int) (totalWeight / mediumCapacity);
            int big = (int) (totalWeight / bigCapacity);
            if (totalWeight % smallCapacity != 0) {
                small++;
            }
            if (totalWeight % mediumCapacity != 0) {
                medium++;
            }
            if (totalWeight % bigCapacity != 0) {
                big++;
            }
            bestTransport.put("Pequeno Porte", small);
            bestTransport.put("Medio Porte", medium);
            bestTransport.put("Grande Porte", big);
        }
        return bestTransport;
    }

    private double getCostBetweenCities(int distance, Map<String, Integer> transport, Map<Integer, Double> costs) {
        double cost = 0;
        for (Map.Entry<String, Integer> entry : transport.entrySet()) {
            if (entry.getKey().equals("Pequeno Porte")) {
                cost += entry.getValue() * costs.get(1) * distance;
            } else if (entry.getKey().equals("Medio Porte")) {
                cost += entry.getValue() * costs.get(2) * distance;
            } else if (entry.getKey().equals("Grande Porte")) {
                cost += entry.getValue() * costs.get(3) * distance;
            }
        }
        return cost;

    }

}

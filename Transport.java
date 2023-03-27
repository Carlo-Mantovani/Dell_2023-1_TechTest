import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Transport {
    private List<String> cities;// cidades que o transporte passa
    private List<Product> products;// produtos que o transporte transporta
    private Map<String, Map<String, Integer>> deposit;// depositos que o transporte faz em cada cidade
    private String company;// empresa que faz o transporte
    private double cost1 = 0.0;// custo total do transporte de pequeno porte
    private double cost2 = 0.0;// custo total do transporte de medio porte
    private double cost3 = 0.0;// custo total do transporte de grande porte
    private int totalVehicleAmount = 0;// quantidade total de veiculos utilizados no transporte

    // construtor
    public Transport(List<String> cities, List<Product> products, Map<String, Map<String, Integer>> deposit,
            String company) {
        this.cities = cities;
        this.products = products;
        this.deposit = deposit;
        this.company = company;
    }

    // getters e setters
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    // metodo para imprimir os dados do transporte
    public String toString(Map<String, Map<String, Integer>> routes, Map<Integer, Double> costs) {

        // reseta os custos
        this.cost1 = 0.0;
        this.cost2 = 0.0;
        this.cost3 = 0.0;
        // reseta a quantidade de veiculos
        this.totalVehicleAmount = 0;

        // pega a origem e o destino do transporte
        String origin = cities.get(0);
        String destination = cities.get(cities.size() - 1);

        // pega as melhores opcoes de transporte para o transporte
        Map<String, Integer> bestTransportOptions = getBestTransportOption(products, costs);

        String result = "\nEmpresa : " + company;// imprime a empresa que faz o transporte

        if (cities.size() <= 2) {// se o transporte passa por apenas duas cidades, sem nenhuma cidade
                                 // intermediaria

            for (Map.Entry<String, Integer> entry : bestTransportOptions.entrySet()) {// obtem a quantidade de veiculos
                                                                                      // utilizados
                this.totalVehicleAmount += entry.getValue();
            }

            int totalDistance = calculateTotalDistance(routes, cities);// calcula a distancia total do transporte
            double totalCost = getCostBetweenCities(totalDistance, bestTransportOptions, costs);// calcula o custo total
                                                                                                // do transporte
            double averageCostPerKm = totalCost / totalDistance;// calcula o custo medio por km do transporte
            double productQuantity = 0;// quantidade total de produtos transportados
            for (Product p : products) {
                productQuantity += p.getQuantity();
            }
            double averageCostPerProduct = totalCost / productQuantity;// calcula o custo medio por produto transportado

            // formata os valores a serem impressos para duas casas decimais
            String totalCostString = String.format("%.2f", totalCost);
            String averageCostPerKmString = String.format("%.2f", averageCostPerKm);
            String averageCostPerProductString = String.format("%.2f", averageCostPerProduct);
            double averageCostPerProductType = totalCost / products.size();
            String averageCostPerProductTypeString = String.format("%.2f", averageCostPerProductType);
            String cost1 = String.format("%.2f", this.cost1);
            String cost2 = String.format("%.2f", this.cost2);
            String cost3 = String.format("%.2f", this.cost3);

            result += "\nDe " + origin + " para " + destination + " transportando:" + printListProducts(products)
                    + "\nA distância total é de: "
                    + totalDistance + "km"
                    + " e os transportes a serem utilizados para resultar no menor custo de transporte sao:\n"
                    + printBestTransportOption(bestTransportOptions) + "\n"
                    + "O custo total do transporte é de: R$" + totalCostString + "\n"
                    + "O custo médio por km é de: R$" + averageCostPerKmString + "\n"
                    + "O custo médio por produto transportado é de: R$" + averageCostPerProductString + "\n"
                    + "O custo médio por tipo de produto transportado é de: R$" + averageCostPerProductTypeString + "\n"
                    + "O custo total de cada tipo de transporte é de: R$" + cost1 + " para pequeno porte, R$" + cost2
                    + " para médio porte e R$" + cost3 + " para grande porte.\n"
                    + "O número total de veículos utilizados é de: " + totalVehicleAmount + "\n"
                    + "O total de itens transportados é de: " + productQuantity + "\n";
            return result;
        }

        result += printWithDeposit(routes, costs, products, deposit);// imprime os dados do transporte com depositos, ou
                                                                     // seja, com cidades intermediarias
        return result;
    }

    // metodo para imprimir os dados do transporte com depositos
    private String printWithDeposit(Map<String, Map<String, Integer>> routes, Map<Integer, Double> costs,
            List<Product> products, Map<String, Map<String, Integer>> deposit) {
        List<String> cityList = new LinkedList<>(cities);// lista de cidades do transporte auxiliar para nao alterar a
                                                         // lista original
        List<Product> auxProducts = new LinkedList<>();// lista de produtos auxiliar para nao alterar a lista original
        for (Product p : products) {

            auxProducts.add(new Product(p));
        }

        // pega as melhores opcoes de transporte para o transporte
        Map<String, Integer> bestTransportOptions = getBestTransportOption(auxProducts, costs);//

        List<SubRoute> subRoutes = new LinkedList<SubRoute>();// lista de subrotas do transporte

        int totalProductQuantity = 0;// quantidade total de produtos transportados
        for (Product p : auxProducts) {
            totalProductQuantity += p.getQuantity();
        }

        int totalDistance = 0;// distancia total do transporte
        double totalCost = 0;// custo total do transporte

        // calcula a distancia e o custo total do transporte e adiciona cada trecho do
        // transporte na lista de subrotas
        for (int i = 0; i < cityList.size() - 1; i++) {
            List<String> auxCities = new LinkedList<String>();
            auxCities.add(cityList.get(i));
            auxCities.add(cityList.get(i + 1));
            int subRouteDistance = calculateTotalDistance(routes, auxCities);
            totalDistance += subRouteDistance;
            subRoutes.add(new SubRoute(cityList.get(i), cityList.get(i + 1), subRouteDistance));// adiciona a subrota na
                                                                                                // lista de subrotas
                                                                                                // sendo que cada
                                                                                                // subrota possui
                                                                                                // origem, destino e
                                                                                                // distancia
        }

        String list = "";
        for (int j = 0; j < auxProducts.size(); j++) {// formata a lista de produtos para ser impressa
            list += auxProducts.get(j).toString();
        }

        String result = "";// string que sera impressa
        result += "\nDe " + cities.get(0) + " para " + cities.get(cities.size() - 1) + " transportando:" + list
                + "\nA distância é de: "
                + totalDistance + "km.\n";

        // imprime o trajeto do transporte
        result += "\nTrajeto:";
        for (String c : cityList) {
            if (c.equals(cityList.get(cities.size() - 1))) {
                result += c + "\n";
                break;
            }
            result += c + " -> ";
        }
        int subRoutesIndex = 0;// indice da subrota que esta sendo analisada
        while (cityList.size() >= 2) {// enquanto houver cidades intermediaria no transporte

            list = "";
            for (int j = 0; j < auxProducts.size(); j++) {// formata a lista de produtos para ser impressa
                list += auxProducts.get(j).toString();
            }

            // pega a subrota que esta sendo analisada e seus dados
            SubRoute subRoute = subRoutes.get(subRoutesIndex);
            String subRouteOrigin = subRoute.getOrigin();
            String subRouteDestination = subRoute.getDestination();
            int subRouteDistance = subRoute.getDistance();

            // adiciona a subrota na string que sera impressa
            result += "\nDe " + subRouteOrigin + " para " + subRouteDestination + " transportando:" + list
                    + "\nA distância é de: "
                    + subRouteDistance + "km. ";
            bestTransportOptions = getBestTransportOption(auxProducts, costs);// pega as melhores modalidades de
                                                                              // transporte para o trecho

            for (Map.Entry<String, Integer> entry : bestTransportOptions.entrySet()) {// soma a quantidade de veiculos
                                                                                      // de cada modalidade de
                                                                                      // transporte
                this.totalVehicleAmount += entry.getValue();
            }
            if (deposit.containsKey(subRouteDestination)) {// se a cidade destino da subrota tiver deposito
                for (Map.Entry<String, Map<String, Integer>> entry : deposit.entrySet()) {

                    if (entry.getKey().equalsIgnoreCase(subRouteDestination)) {// se a cidade destino da subrota for a
                                                                               // cidade destino do deposito

                        // adiciona a cidade destino do deposito na string que sera impressa
                        result += "\nNa cidade de " + subRouteDestination + " o transporte ira fazer deposito de:\n";

                        // percorre os produtos do deposito na cidade destino da subrota
                        for (Map.Entry<String, Integer> entry2 : entry.getValue().entrySet()) {
                            for (Product p : auxProducts) {

                                // se o produto do deposito for igual ao produto da lista de produtos
                                if (p.getName().equalsIgnoreCase(entry2.getKey())) {

                                    // adiciona o produto e a quantidade do deposito na string que sera impressa
                                    result += p.getName() + " - " + entry2.getValue() + " unidades\n";

                                    // subtrai a quantidade de produtos do deposito da quantidade de produtos da
                                    // lista de produtos
                                    p.setQuantity(p.getQuantity() - entry2.getValue());
                                }
                            }
                        }
                    }
                }

            }
            double subRouteCost = getCostBetweenCities(subRouteDistance, bestTransportOptions, costs);// pega o custo do
                                                                                                      // transporte da
                                                                                                      // subrota
            totalCost += subRouteCost;// soma o custo do transporte da subrota ao custo total do transporte
            String subRouteCostString = String.format("%.2f", subRouteCost);// formata o custo do transporte da subrota
                                                                            // para ser impresso

            // adiciona as modalidades de transporte escolhidas para a subrota e seu custo
            // na string que sera impressa
            result += "\nOs transportes a serem utilizados para resultar no menor custo de transporte sao:\n "
                    + printBestTransportOption(bestTransportOptions) + "\n"
                    + "O custo total do transporte nesse trecho é de: R$" + subRouteCostString + "\n";
            subRoutesIndex++;// incrementa o indice da subrota que esta sendo analisada para a proxima
                             // subrota
            cityList.remove(1);// remove a cidade intermediaria do transporte
        }

        double averageCostPerKm = totalCost / totalDistance;// calcula o custo medio por km
        double averageCostPerProduct = totalCost / totalProductQuantity;// calcula o custo medio por produto
        double averageCostPerProductType = totalCost / auxProducts.size();// calcula o custo medio por tipo de produto

        // formata os valores para serem impressos
        String averageCostPerKmString = String.format("%.2f", averageCostPerKm);
        String averageCostPerProductString = String.format("%.2f", averageCostPerProduct);
        String totalCostString = String.format("%.2f", totalCost);
        String averageCostPerProductTypeString = String.format("%.2f", averageCostPerProductType);
        String cost1 = String.format("%.2f", this.cost1);
        String cost2 = String.format("%.2f", this.cost2);
        String cost3 = String.format("%.2f", this.cost3);

        // adiciona os valores calculados na string que sera impressa
        result += "\nO custo total do transporte é de: R$" + totalCostString
                + "\nO custo médio por km é de: R$" + averageCostPerKmString
                + "\nO custo médio por produto é de: R$" + averageCostPerProductString
                + "\nO custo médio por tipo de produto transportado é de: R$" + averageCostPerProductTypeString
                + "\nO custo total de cada tipo de transporte é de: R$" + cost1 + " para pequeno porte, R$" + cost2
                + " para médio porte e R$" + cost3 + " para grande porte."
                + "\nO número total de veículos utilizados, considerando troca de veiculos entre trechos, é de: "
                + totalVehicleAmount
                + "\nO total de itens transportados é de: " + totalProductQuantity + "\n";

        return result;// retorna a string com os resultados

    }

    // metodo que imprime a lista de produtos
    private String printListProducts(List<Product> products) {
        String list = "";
        for (int i = 0; i < products.size(); i++) {
            list += products.get(i).toString();

        }
        return list;
    }

    // metodo que calcula a distancia total entre as cidades dentro de um trajeto
    private int calculateTotalDistance(Map<String, Map<String, Integer>> routes, List<String> cities) {
        int totalDistance = 0;
        for (int i = 0; i < cities.size(); i++) {
            if (i == cities.size() - 1) {
                break;
            }
            totalDistance += routes.get(cities.get(i)).get(cities.get(i + 1));// soma a distancia entre a cidade atual e
                                                                             // a proxima cidade
        }
        return totalDistance;
    }

    // metodo que imprime as modalidades de transporte calculadas para o trecho
    private String printBestTransportOption(Map<String, Integer> bestTransport) {
        String bestTransportOption = "";
        for (Map.Entry<String, Integer> entry : bestTransport.entrySet()) {// percorre o map com as modalidades de
                                                                           // transporte e a quantidade de veiculos de
                                                                           // cada modalidade
            bestTransportOption += entry.getValue() + " caminhao(s) do tipo " + entry.getKey() + "\n";
        }
        bestTransportOption += "Para resultar no menor custo de transporte por km rodado.";
        return bestTransportOption;// retorna a string com as melhores modalidades de transporte
    }

    // metodo que calcula a melhor combinacao de modalidades de transporte para
    // obter o menor custo
    private Map<String, Integer> getBestTransportOption(List<Product> products, Map<Integer, Double> costs) {

        // determina as capacidades de cada modalidade de transporte
        int smallCapacity = 1000;
        int mediumCapacity = 4000;
        int bigCapacity = 10000;

        // inicializa as variaveis que armazenam a quantidade de veiculos de cada
        // modalidade
        int small = 0;
        int medium = 0;
        int big = 0;

        // cria um map com as modalidades de transporte e a quantidade de veiculos de
        // cada modalidade
        Map<String, Integer> bestTransport = new HashMap<String, Integer>();
        double totalWeight = 0;// variavel que armazena o peso total dos produtos
        for (Product p : products) {// percorre a lista de produtos obtendo o peso total dos produtos
            totalWeight += p.getWeight() * p.getQuantity();
        }

        // faz um calculo estimativo para determinar a melhor modalidade de transporte
        // para o trecho
        big = (int) (totalWeight / bigCapacity);
        medium = (int) ((totalWeight % bigCapacity) / mediumCapacity);
        small = (int) ((totalWeight % bigCapacity) % mediumCapacity / smallCapacity);

        // verifica se o peso é divisivel pela capacidade do menor transporte, caso nao
        // seja, incrementa a quantidade de veiculos do menor transporte
        // caso contrario sobraria peso entre 1kg a 999kg para ser transportado
        if (totalWeight % smallCapacity != 0) {
            small++;
        }

        // armazena a quantidade de veiculos calculadas para cada modalidade de
        // transporte em uma lista
        List<Integer> quantities = new LinkedList<>();
        quantities.add(small);
        quantities.add(medium);
        quantities.add(big);

        // armazena as capacidades de cada modalidade de transporte em uma lista
        List<Integer> capacities = new LinkedList<>();
        capacities.add(smallCapacity);
        capacities.add(mediumCapacity);
        capacities.add(bigCapacity);

        // chama o metodo que realiza um reajuste na quantidade de veiculos de cada
        // modalidade de transporte para obter o menor custo
        List<Integer> newQuantities = quantityAdjustment(quantities, capacities, totalWeight, costs);

        // armazena a quantidade de veiculos calculadas para cada modalidade de
        // transporte em um map
        bestTransport.put("Pequeno Porte", newQuantities.get(0));
        bestTransport.put("Medio Porte", newQuantities.get(1));
        bestTransport.put("Grande Porte", newQuantities.get(2));

        return bestTransport; // retorna o map com a melhor combinacao de modalidades de transporte para o
                              // trecho
    }

    // metodo que realiza um reajuste na quantidade de veiculos de cada modalidade
    // de transporte para obter o menor custo
    private List<Integer> quantityAdjustment(List<Integer> quantities, List<Integer> capacities, double totalWeight,
            Map<Integer, Double> costs) {

        int small_medium = 0;// variavel que armazena o limite de veiculos de pequeno porte em que é mais
                             // benéfico em termos de custo em comparacao a veiculos de medio porte
        int medium_big = 0;// variavel que armazena o limite de veiculos de medio porte em que é mais
                           // benéfico em termos de custo em comparacao a veiculos de grande porte
        int smallMedium_big = 0;// variavel que armazena o limite de veiculos de pequeno porte (ja se
                                // considerando veiculos de medio porte), em que é mais benéfico em termos de
                                // custo em comparacao a veiculos de grande porte
        double small_cost = costs.get(1);// variavel que armazena o custo por km de um veiculo de pequeno porte
        double medium_cost = costs.get(2);// variavel que armazena o custo por km de um veiculo de medio porte

        while (medium_cost < costs.get(3)) {// enquanto o custo de um veiculo de medio porte for menor que o custo de um
                                            // veiculo de grande porte
            medium_big++;// incrementa o limite de veiculos de medio porte
            medium_cost += costs.get(2);
        }
        while (small_cost < costs.get(2)) {// enquanto o custo de um veiculo de pequeno porte for menor que o custo de
                                           // um veiculo de medio porte
            small_medium++;// incrementa o limite de veiculos de pequeno porte
            small_cost += costs.get(1);
        }
        int auxCapacity = medium_big * capacities.get(1);// variavel que armazena uma capacidade ja parcialmente
                                                         // preenchida com o limite de veiculos de medio porte em
                                                         // relacao a veiculos de grande porte
        while (auxCapacity < capacities.get(2)) {// enquanto essa capacidade nao for exceder a capacidade de um veiculo
                                                 // de grande porte
            smallMedium_big++;// incrementa o limite de veiculos de pequeno porte (ja se considerando veiculos
                              // de medio porte)
            auxCapacity += capacities.get(0);
        }

        // verifica se é benefico converter veiculos de pequeno porte em veiculos de
        // medio porte
        if (quantities.get(0) > small_medium) {
            quantities.set(0, quantities.get(0) - (small_medium + 1));
            quantities.set(1, quantities.get(1) + 1);
        }
    
        // verifica se é benefico converter veiculos de pequeno porte, somado com
        // veiculos de medio porte, em veiculos de grande porte
        if (quantities.get(0) == smallMedium_big && quantities.get(1) == medium_big) {
            quantities.set(0, quantities.get(0) - smallMedium_big);
            quantities.set(1, quantities.get(1) - medium_big);
            quantities.set(2, quantities.get(2) + 1);
        }

        return quantities;// retorna a lista com a quantidade de veiculos de cada modalidade de transporte
    }

    // metodo que calcula o custo total de um trecho
    private double getCostBetweenCities(int distance, Map<String, Integer> transport, Map<Integer, Double> costs) {
        double cost = 0;// variavel que armazena o custo total do trecho
        for (Map.Entry<String, Integer> entry : transport.entrySet()) {// percorre o mapa obtendo a quantidade de
                                                                       // veiculos de cada modalidade de transporte e
                                                                       // multiplica o seu custo por km pelo numero de
                                                                       // veiculos e pela distancia do trecho
            if (entry.getKey().equals("Pequeno Porte")) {
                cost += entry.getValue() * costs.get(1) * distance;
                this.cost1 += entry.getValue() * costs.get(1) * distance;
            } else if (entry.getKey().equals("Medio Porte")) {
                cost += entry.getValue() * costs.get(2) * distance;
                this.cost2 += entry.getValue() * costs.get(2) * distance;
            } else if (entry.getKey().equals("Grande Porte")) {
                cost += entry.getValue() * costs.get(3) * distance;
                this.cost3 += entry.getValue() * costs.get(3) * distance;
            }

        }
        return cost;

    }

}

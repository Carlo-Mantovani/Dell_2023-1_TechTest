import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

public class App {

    private static Scanner kb = new Scanner(System.in);

    private static List<String> listCities(Map<String, Map<String, Integer>> routes) {
        List<String> cities = new LinkedList<String>();
        String option = "";
        // Scanner city = new Scanner(System.in);
        // Scanner opt2 = new Scanner(System.in);
        while (!option.equals("N")) {
            System.out.print("Digite o nome da cidade: ");
            String cityName = kb.nextLine();
            cityName = cityName.toUpperCase();
            if (!routes.containsKey(cityName)) {
                System.out.print("Cidade nao encontrada na base de dados, tente novamente.\n");
                continue;
            }
            cities.add(cityName);
            if (cities.size() <= 1) {
                System.out.print("Cidade adicionada com sucesso, agora adicione mais uma cidade.\n");
                continue;
            }
            System.out.print("Deseja adicionar outra cidade? (S/N): ");
            option = kb.nextLine();
            option = option.toUpperCase();

        }
        return cities;
    }

    private static List<Product> listProducts() {
        List<Product> products = new LinkedList<Product>();
        String option = "";
        while (!option.equals("N")) {
            System.out.print("Digite o nome do produto: ");
            String prodName = kb.nextLine();
            if (prodName.length() <= 0) {
                System.out.print("Nome invalido, tente novamente.\n");
                continue;
            }
            System.out.println("Digite o peso do produto: ");
            String prodWeightString = kb.nextLine();
            if (!tryDouble(prodWeightString)) {
                System.out.print("Peso invalido, tente novamente.\n");
                continue;
            }
            double prodWeight = Double.parseDouble(prodWeightString);
            if (prodWeight <= 0) {
                System.out.print("Peso invalido, tente novamente.\n");
                continue;
            }
            System.out.println("Digite o quantidade do produto: ");
            String prodQuantityString = kb.nextLine();
            if (!tryInt(prodQuantityString)) {
                System.out.print("Quantidade invalida, tente novamente.\n");
                continue;
            }
            int prodQuantity = Integer.parseInt(prodQuantityString);
            if (prodQuantity <= 0) {
                System.out.print("Quantidade invalida, tente novamente.\n");
                continue;
            }
            Product product = new Product(prodName, prodWeight, prodQuantity);
            products.add(product);
            System.out.print(
                    "Produto adicionado com sucesso. Deseja adicionar outro produto? (S/N): \n");
            option = kb.nextLine();
            option = option.toUpperCase();

        }

        return products;
    }

    private static boolean tryDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean tryInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // determina quais depositos serao feitos em quais cidades
    private static Map<String, Map<String, Integer>> deposit(List<String> cities, List<Product> products) {
        Map<String, Map<String, Integer>> deposit = new HashMap<String, Map<String, Integer>>();
        System.out.println("Sera feito algum deposito em alguma cidade? (S/N): ");
        String option = kb.nextLine();
        option = option.toUpperCase();

        while (option.equals("S")) {

            System.out.println("Digite o nome da cidade: ");
            String cityName = kb.nextLine();
            cityName = cityName.toUpperCase();
            if (!cities.contains(cityName)) {
                System.out.println("Cidade nao encontrada, tente novamente.\n");
                continue;
            }
            if (cityName.equals(cities.get(0))){
                System.out.println("A cidade de origem nao pode receber deposito, tente novamente.\n");
                continue;
            }
            System.out.println("Digite o nome do produto: ");
            String prodName = kb.nextLine();
            for (Product p : products) {
                if (!p.getName().equalsIgnoreCase(prodName)) {
                    System.out.println("Produto nao encontrado, tente novamente.\n");
                    continue;
                }
            }
            System.out.println("Digite a quantidade de deposito (Quantidade Atual: "
                    + currentProductQuantity(prodName, products) + "): ");
            String depositQuantityString = kb.nextLine();
            if (!tryInt(depositQuantityString)) {
                System.out.print("Quantidade invalida, tente novamente.\n");
                continue;
            }
            int depositQuantity = Integer.parseInt(depositQuantityString);
            if (depositQuantity <= 0 || !checkDepositLimit(prodName, depositQuantity, products)) {
                System.out.print("Quantidade invalida, tente novamente.\n");
                continue;
            }
            Map<String, Integer> prodDeposit = new HashMap<String, Integer>();
            prodDeposit.put(prodName, depositQuantity);
            deposit.put(cityName, prodDeposit);
            System.out.println("Deseja fazer mais algum deposito? (S/N): ");
            option = kb.nextLine();
            option = option.toUpperCase();

        }

        return deposit;
    }

    private static boolean checkDepositLimit(String productName, int quantity, List<Product> products) {
        for (Product p : products) {
            if (p.getName().equals(productName)) {
                if (p.getQuantity() < quantity) {
                    return false;
                }
            }
        }
        return true;

    }

    private static int currentProductQuantity(String productName, List<Product> products) {
        int quantity = 0;
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(productName)) {
                quantity = p.getQuantity();
            }
        }
        return quantity;
    }

    public static void main(String[] args) {

        ReadFile fr = new ReadFile();
        String data = "DNIT-Distancias.csv";
        Map<String, Map<String, Integer>> routes = new HashMap<String, Map<String, Integer>>();
        fr.readFile(data, routes);

        // for (Map.Entry<String, Map<String, Integer>> entry : routes.entrySet()) {
        // System.out.println(entry.getKey());
        // for (Map.Entry<String, Integer> entry2 : entry.getValue().entrySet()) {
        // System.out.println(entry.getKey() + " " + entry2.getKey() + " " +
        // entry2.getValue());
        // }
        // }
        Map<Integer, Double> costs = new HashMap<Integer, Double>();
        String costFile = "custos.txt";
        fr.readCost(costFile, costs);
        // for (Map.Entry<Integer, Double> entry : costs.entrySet()) {
        // System.out.println(entry.getKey() + " " + entry.getValue());
        // }
        List<Transport> transports = new LinkedList<Transport>();

        System.out.print("\nBem-vindo!\n");

        // Scanner menu = new Scanner(System.in);
        // O que estabelece o loop + o menu
        while (true) {
            System.out.print("\n");
            System.out.print("|-----------------------------------------------|\n");
            System.out.print("| Opcao 1 - Consultar Trechos x Modalidade      |\n");
            System.out.print("| Opcao 2 - Cadastrar Rota                      |\n");
            System.out.print("| Opcao 3 - Sair                                |\n");
            System.out.print("|-----------------------------------------------|\n");
            System.out.print("Selecione uma opcao: ");

            String opt = kb.nextLine();

            switch (opt) {

                case "1":
                    System.out.print("\n");
                    System.out.print("Digite a cidade de origem: \n");
                    // menu.nextLine();
                    String origin = kb.nextLine();

                    origin = origin.toUpperCase();

                    if (!routes.containsKey(origin)) {

                        System.out.println("Cidade de origem nao encontrada");
                        break;
                    }
                    System.out.print("Digite a cidade de destino: \n");
                    String destination = kb.nextLine();
                    destination = destination.toUpperCase();

                    if (!routes.get(origin).containsKey(destination)) {
                        System.out.println("\nCidade de destino nao encontrada");
                        break;
                    }

                    System.out.print("Digite o tipo de veiculo: \n");
                    System.out.print("1 - Pequeno Porte\n");
                    System.out.print("2 - Medio Porte\n");
                    System.out.print("3 - Grande Porte\n");
                    String vehicle_type = kb.nextLine();
                    destination.toUpperCase();

                    String cost;
                    switch (vehicle_type) {
                        case "1":
                            cost = String.format("%.2f", (costs.get(1) * routes.get(origin).get(destination)));
                            System.out.print("De " + origin + " para " + destination
                                    + ", em transporte de Pequeno Porte, o custo do trecho é de R$" + cost
                                    + " e a distancia é de " + routes.get(origin).get(destination) + "km");
                            break;
                        case "2":
                            cost = String.format("%.2f", (costs.get(2) * routes.get(origin).get(destination)));
                            System.out.print("De " + origin + " para " + destination
                                    + ", em transporde de Medio Porte, o custo do trecho é de R$" + cost
                                    + " e a distancia é de " + routes.get(origin).get(destination) + "km");

                            break;
                        case "3":
                            cost = String.format("%.2f", (costs.get(3) * routes.get(origin).get(destination)));
                            System.out.print("De " + origin + " para " + destination
                                    + ", em transporte de Grande Porte, o custo do trecho é de R$" + cost
                                    + " e a distancia é de " + routes.get(origin).get(destination) + "km");
                            break;
                        default:
                            System.out.print("Opçao invalida");
                            break;
                    }

                    break;

                case "2":
                    List<String> cities = listCities(routes);
                    // for (String city : cities) {
                    // System.out.println(city);
                    // }
                    List<Product> products = listProducts();
                    // for (Product product : products) {
                    // System.out.println(product.getName());
                    // System.out.println(product.getWeight());
                    // System.out.println(product.getQuantity());
                    // }
                    Map<String, Map<String, Integer>> deposit = deposit(cities, products);
                    // for (Map.Entry<String, Map<String, Integer>> entry : deposit.entrySet()) {
                    // System.out.println(entry.getKey());
                    // for (Map.Entry<String, Integer> entry2 : entry.getValue().entrySet()) {
                    // System.out.println(entry.getKey() + " " + entry2.getKey() + " " +
                    // entry2.getValue());
                    // }
                    // }
                    transports.add(new Transport(cities, products, deposit));
                    //System.out.println(products.size());
                    System.out.println(transports.get(0).toString(routes, costs));
                    // System.out.println("hi");

                    break;

                case "3":
                    System.exit(0);
                    kb.close();
                default:

                    System.out.print("\nOpcao inválida\n");
                    break;

            }
        }

    }
}
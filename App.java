import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

public class App {

    private static Scanner kb = new Scanner(System.in);// inicializa a instancia do teclado a ser usado no programa

    // metodo responsavel pelo cadastro das cidades em um trajeto
    private static List<String> listCities(Map<String, Map<String, Integer>> routes) {
        List<String> cities = new LinkedList<String>(); // lista que armazena as cidades do trajeto
        String option = "";
        while (!option.equals("N")) {// enquanto o usuario nao digitar N, o programa continua a pedir para o usuario
                                     // digitar as cidades do trajeto
            System.out.print("Digite o nome da cidade a ser cadastrada no trajeto: ");// pede para o usuario digitar o
                                                                                      // nome da cidade
            String cityName = kb.nextLine();
            cityName = cityName.toUpperCase();
            if (!routes.containsKey(cityName)) {// verifica se a cidade existe na base de dados
                System.out.print("Cidade nao encontrada na base de dados, tente novamente.\n");
                continue;
            }
            if (checkRepeatedCity(cities, cityName)) {// verifica se a cidade ja foi cadastrada
                System.out.print("Cidade ja cadastrada, tente novamente.\n");
                continue;
            }

            cities.add(cityName);// adiciona a cidade na lista de cidades do trajeto
            if (cities.size() <= 1) {// verifica se a cidade foi a primeira a ser cadastrada, para garantir que pelo
                                     // menos duas cidades foram cadastradas
                System.out.print("Cidade adicionada com sucesso, agora adicione mais uma cidade.\n");
                continue;
            }
            System.out.print("Cidade adicionada com sucesso.\n");

            System.out.print("Desejas adicionar outra cidade? (S/N): ");// pergunta se o usuario deseja adicionar mais
                                                                        // uma cidade
            option = kb.nextLine();
            option = option.toUpperCase();
            if (!option.equals("N") && !option.equals("S")) {// verifica se a opcao digitada eh valida
                while (!option.equals("N") && !option.equals("S")) {// enquanto a opcao digitada nao for valida, o
                                                                    // programa pede para o usuario digitar novamente
                    System.out.print("Opcao invalida, tente novamente.\n");
                    System.out.print("Desejas adicionar outra cidade? (S/N): ");
                    option = kb.nextLine();
                    option = option.toUpperCase();
                }
                continue;
            }

        }
        return cities;// retorna a lista de cidades do trajeto
    }

    // metodo responsavel pelo cadastro dos produtos a serem transportados
    private static List<Product> listProducts() {
        List<Product> products = new LinkedList<Product>();// lista que armazena os produtos a serem transportados
        String option = "";
        while (!option.equals("N")) {// enquanto o usuario nao digitar N, o programa continua a pedir para o usuario
                                     // digitar os produtos a serem transportados
            System.out.print("\nDigite o nome do produto a ser adicionado no transporte: ");// pede para o usuario
                                                                                            // digitar o nome do produto
            String prodName = kb.nextLine();
            if (prodName.length() <= 0) {// verifica se o nome do produto eh valido
                System.out.print("Nome invalido, tente novamente.\n");
                continue;
            }
            System.out.println("Digite o peso do produto em kg: ");
            String prodWeightString = kb.nextLine();
            if (!tryDouble(prodWeightString)) {// verifica se o peso do produto eh um Double valido
                System.out.print("Peso invalido, tente novamente.\n");
                continue;
            }
            double prodWeight = Double.parseDouble(prodWeightString);
            if (prodWeight <= 0) {// verifica se o peso do produto eh valido
                System.out.print("Peso invalido, tente novamente.\n");
                continue;
            }
            System.out.println("Digite o quantidade do produto: ");// pede para o usuario digitar a quantidade do
                                                                   // produto
            String prodQuantityString = kb.nextLine();
            if (!tryInt(prodQuantityString)) {// verifica se a quantidade do produto eh um Int valido
                System.out.print("Quantidade invalida, tente novamente.\n");
                continue;
            }
            int prodQuantity = Integer.parseInt(prodQuantityString);
            if (prodQuantity <= 0) {// verifica se a quantidade do produto eh valida
                System.out.print("Quantidade invalida, tente novamente.\n");
                continue;
            }
            Product product = new Product(prodName, prodWeight, prodQuantity);// cria um novo produto com os dados
                                                                              // digitados pelo usuario
            products.add(product);
            System.out.print(
                    "Produto adicionado com sucesso. Desejas adicionar outro produto? (S/N): \n");
            option = kb.nextLine();
            option = option.toUpperCase();
            if (!option.equals("N") && !option.equals("S")) {// verifica se a opcao digitada eh valida
                while (!option.equals("N") && !option.equals("S")) {// enquanto a opcao digitada nao for valida, o
                                                                    // programa pede para o usuario digitar novamente
                    System.out.print("Opcao invalida, tente novamente.\n");
                    System.out.print("Desejas adicionar outro produto? (S/N): ");
                    option = kb.nextLine();
                    option = option.toUpperCase();
                }
                continue;
            }

        }

        return products;
    }

    // metodo responsavel por verificar se a cidade ja foi cadastrada
    private static boolean checkRepeatedCity(List<String> cities, String cityName) {
        for (String city : cities) {
            if (city.equals(cityName)) {
                return true;
            }
        }
        return false;
    }

    // metodo responsavel por verificar se uma String eh um Double valido
    private static boolean tryDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // metodo responsavel por verificar se uma String eh um Int valido
    private static boolean tryInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // metodo responsavel por imprimir o trajeto
    private static String displayRoute(List<String> cities) {
        String result = "";
        for (int i = 0; i < cities.size(); i++) {
            result += cities.get(i);
            if (i != cities.size() - 1) {
                result += " -> ";
            }
        }
        return result;
    }

    // metodo responsavel por verificar se serao feitos depositos em alguma cidade
    // intermediaria
    private static Map<String, Map<String, Integer>> deposit(List<String> cities, List<Product> products) {

        // mapa que armazena os depositos a serem feitos em cada cidade, sendo que a
        // chave eh o nome da cidade e o valor eh um mapa que armazena o nome do produto
        // e a quantidade a ser depositada
        Map<String, Map<String, Integer>> deposit = new HashMap<String, Map<String, Integer>>();

        List<Product> auxProducts = new LinkedList<>();// lista auxiliar que armazena os produtos a serem transportados
                                                       // para que nao seja alterada a lista original
        for (Product p : products) {

            auxProducts.add(new Product(p));
        }

        System.out.println("Sera feito algum deposito em alguma cidade? (S/N): ");// pergunta ao usuario se serao feitos
                                                                                  // depositos em alguma cidade
        String option = kb.nextLine();
        option = option.toUpperCase();

        while (option.equals("S")) {// enquanto o usuario digitar S, o programa continua a pedir para o usuario
                                    // digitar as cidades que receberao depositos

            System.out.println("Digite o nome da cidade intermediária a se fazer depósito: ");// pede para o usuario
                                                                                              // digitar o nome da
                                                                                              // cidade que recebera o
                                                                                              // deposito
            System.out.println("Trajeto: " + displayRoute(cities) + "\n");
            String cityName = kb.nextLine();
            cityName = cityName.toUpperCase();
            if (!cities.contains(cityName)) {// verifica se a cidade digitada eh valida
                System.out.println("Cidade nao encontrada, tente novamente.\n");
                continue;
            }
            if (cityName.equals(cities.get(0))) {// verifica se a cidade digitada eh a cidade de origem
                System.out.println("A cidade de origem nao pode receber deposito, tente novamente.\n");
                continue;
            }
            if (cityName.equals(cities.get(cities.size() - 1))) {// verifica se a cidade digitada eh a cidade de destino
                System.out.println(
                        "A cidade de destino ja recebera o deposito dos produtos remanescentes, tente novamente.\n");
                continue;
            }
            System.out.println("Digite o nome do produto: ");// pede para o usuario digitar o nome do produto que sera
                                                             // depositado
            displayProducts(products);
            String prodName = kb.nextLine();
            boolean prodFound = false;

            for (Product p : products) {// verifica se o produto digitado eh um produto cadastrado
                if (p.getName().equalsIgnoreCase(prodName)) {
                    prodFound = true;
                    continue;
                }

            }
            if (!prodFound) {// se o produto nao for encontrado, o programa pede para o usuario digitar
                             // novamente
                System.out.println("Produto nao encontrado, tente novamente.\n");
                continue;
            }

            System.out.println("Digite a quantidade de deposito (Quantidade Atual: "
                    + currentProductQuantity(prodName, auxProducts) + "): ");// pede para o usuario digitar a quantidade
                                                                             // de deposito, demonstrando a quantia
                                                                             // atual do produto
            String depositQuantityString = kb.nextLine();
            if (!tryInt(depositQuantityString)) {// verifica se a quantidade digitada eh um Int valido
                System.out.print("Quantidade invalida, tente novamente.\n");
                continue;
            }
            int depositQuantity = Integer.parseInt(depositQuantityString);// converte a String digitada para um Int
            if (depositQuantity <= 0 || !checkDepositLimit(prodName, depositQuantity, products)) {// verifica se a
                                                                                                  // quantidade digitada
                                                                                                  // eh valida
                System.out.print("Quantidade invalida, tente novamente.\n");
                continue;
            }

            if (deposit.containsKey(cityName)) {// verifica se a cidade ja recebeu algum deposito
                Map<String, Integer> prodDeposit = deposit.get(cityName);

                if (prodDeposit.containsKey(prodName)) {// verifica se o produto ja foi depositado na cidade

                    int currentQuantity = prodDeposit.get(prodName);// pega a quantidade atual de deposito do produto na
                                                                    // cidade
                    prodDeposit.put(prodName, currentQuantity + depositQuantity);// atualiza a quantidade de deposito do
                                                                                 // produto na cidade

                } else {// se o produto nao foi depositado na cidade, o programa adiciona o produto na
                        // lista de depositos da cidade
                    prodDeposit.put(prodName, depositQuantity);
                }
            } else {// se a cidade nao recebeu nenhum deposito, o programa adiciona a cidade na
                    // lista de depositos
                Map<String, Integer> prodDeposit = new HashMap<String, Integer>();
                prodDeposit.put(prodName, depositQuantity);
                deposit.put(cityName, prodDeposit);
            }

            for (Product p : auxProducts) {// atualiza a quantidade de produtos a serem transportados
                if (p.getName().equalsIgnoreCase(prodName)) {
                    p.setQuantity(p.getQuantity() - depositQuantity);
                }
            }

            System.out.println("Desejas fazer mais algum deposito? (S/N): ");// pergunta ao usuario se ele deseja fazer
                                                                             // mais algum deposito
            option = kb.nextLine();
            option = option.toUpperCase();
            if (!option.equals("N") && !option.equals("S")) {// verifica se a opcao digitada eh valida
                while (!option.equals("N") && !option.equals("S")) {// enquanto a opcao digitada nao for valida, o
                                                                    // programa pede para o usuario digitar novamente
                    System.out.print("Opcao invalida, tente novamente.\n");
                    System.out.print("Desejas fazer mais algum deposito? (S/N): ");
                    option = kb.nextLine();
                    option = option.toUpperCase();
                }
                continue;
            }

        }

        return deposit;
    }

    // metodo que verifica se a quantidade digitada excede a quantidade de produtos
    // disponiveis
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

    // metodo que retorna a quantidade atual de um produto
    private static int currentProductQuantity(String productName, List<Product> products) {
        int quantity = 0;
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(productName)) {
                quantity = p.getQuantity();
            }
        }
        return quantity;
    }

    // metodo que imprime a lista de produtos disponiveis para deposito
    private static void displayProducts(List<Product> products) {
        System.out.println("Produtos disponiveis: ");
        for (Product p : products) {
            System.out.print(p.getName());
            if (p != products.get(products.size() - 1)) {
                System.out.print(", ");
            }
        }
        System.out.println("\n");
    }
    private static String printCityList(Map<String,Map<String,Integer>> routes) {
        String cityList = "";
        for (String city : routes.keySet()) {
            cityList += city + ", ";
        }
        return cityList;
    }

    public static void main(String[] args) { // main

        // instancia estrutura de dados para leitura do arquivo de rotas
        ReadFile fr = new ReadFile();
        String data = "DNIT-Distancias.csv";

        // leitura do arquivo de rotas e armazenamento em um mapa. Segue o formato:
        // <cidade de origem, <cidade de destino, distancia>>
        Map<String, Map<String, Integer>> routes = new HashMap<String, Map<String, Integer>>();
        fr.readFile(data, routes);

        // leitura do arquivo de custos e armazenamento em um mapa. Segue o formato:
        // <modalidade de transporte, custo>
        Map<Integer, Double> costs = new HashMap<Integer, Double>();
        String costFile = "custos.txt";
        fr.readCost(costFile, costs);

        List<Transport> transports = new LinkedList<Transport>();// lista de transportes a serem realizados

        System.out.print("\nBem-vindo!\n");

        // menu de opcoes
        while (true) {
            System.out.print("\n");
            System.out.print("|-----------------------------------------------|\n");
            System.out.print("| Opcao 1 - Consultar Trechos x Modalidade      |\n");
            System.out.print("| Opcao 2 - Cadastrar Rota                      |\n");
            System.out.print("| Opcao 3 - Consultar Dados Estatisticos        |\n");
            System.out.print("| Opcao 4 - Sair                                |\n");
            System.out.print("|-----------------------------------------------|\n");
            System.out.print("Selecione uma opcao: ");

            String opt = kb.nextLine();

            switch (opt) {

                // opcao 1 - Consultar Trechos x Modalidade
                case "1":
                    System.out.print("\n");
                    System.out.println("Cidades disponiveis: " + printCityList(routes));
                    System.out.print("Digite a cidade de origem: \n");// leitura da cidade de origem

                    String origin = kb.nextLine();

                    origin = origin.toUpperCase();// converte a cidade de origem para maiusculo para ser comparada com
                                                  // as cidades do mapa de rotas

                    if (!routes.containsKey(origin)) { // verifica se a cidade de origem existe no mapa de rotas

                        System.out.println("Cidade de origem nao encontrada");
                        break;
                    }
                    System.out.print("Digite a cidade de destino: \n"); // leitura da cidade de destino
                    String destination = kb.nextLine();
                    destination = destination.toUpperCase();

                    if (!routes.get(origin).containsKey(destination)) { // verifica se a cidade de destino existe no
                                                                        // mapa de rotas
                        System.out.println("\nCidade de destino nao encontrada");
                        break;
                    }

                    System.out.print("Digite o tipo de veiculo: \n");// leitura do tipo de veiculo
                    System.out.print("1 - Pequeno Porte\n");
                    System.out.print("2 - Medio Porte\n");
                    System.out.print("3 - Grande Porte\n");
                    String vehicle_type = kb.nextLine();
                    destination.toUpperCase();

                    String cost;// variavel que armazena o custo do trecho

                    switch (vehicle_type) {// imprime o custo do trecho de acordo com o tipo de veiculo, realizando o
                                           // calculo de custo da modalide x distancia. Resultando no custo formatado
                                           // para duas casas decimais
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

                        // caso o tipo de veiculo nao seja valido
                        default:
                            System.out.print("Opçao invalida");
                            break;
                    }

                    break;

                // opcao 2 - Cadastrar Rota
                case "2":
                    System.out.print("\n");
                    System.out.print("Digite o nome da empresa que fará o transporte \n");// leitura do nome da empresa
                    String company = kb.nextLine();
                    company.toUpperCase();

                    System.out.println("Cidades disponiveis: " + printCityList(routes));

                    List<String> cities = listCities(routes);// cadastro de cidades a serem percorridas

                    List<Product> products = listProducts();// cadastro de produtos a serem transportados

                    Map<String, Map<String, Integer>> deposit;// cadastro de depositos

                    if (cities.size() > 2) {// se a quantidade de cidades for maior que 2, o usuario pode cadastrar
                                            // depositos em cidades intermediarias
                        deposit = deposit(cities, products);

                    } else {
                        deposit = null;
                    }

                    Transport newTransport = new Transport(cities, products, deposit, company);// cria um novo
                                                                                               // transporte
                    transports.add(newTransport);// adiciona o transporte a lista de transportes

                    System.out.println(newTransport.toString(routes, costs));// imprime os dados do transporte
                                                                             // cadastrado

                    break;

                // opcao 3 - Consultar Dados Estatisticos
                case "3":

                    System.out.print("\n");

                    if (transports.isEmpty()) {// verifica se ha transportes cadastrados
                        System.out.print("Nenhum transporte cadastrado\n");
                        break;
                    }
                    for (Transport transport : transports) {// imprime os dados de todos os transportes cadastrados
                        System.out.println(transport.toString(routes, costs));
                    }

                    break;

                // opcao 4 - Sair
                case "4":
                    System.exit(0);// encerra o programa
                    kb.close();// fecha o scanner

                    // caso a opcao digitada nao seja valida
                default:

                    System.out.print("\nOpcao inválida\n");
                    break;

            }
        }

    }
}
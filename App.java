import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;


public class App {

    
    public static void main(String[] args) {

        Scanner menu = new Scanner(System.in);
        ReadFile fr = new ReadFile();
        String data = "DNIT-Distancias.csv";
        Map<String,Map<String,Integer>> routes = new HashMap<String,Map<String,Integer>>();
        fr.readFile(data, routes);

        //for (Map.Entry<String, Map<String, Integer>> entry : routes.entrySet()) {
        //    System.out.println(entry.getKey());
        //    for (Map.Entry<String, Integer> entry2 : entry.getValue().entrySet()) {
        //        System.out.println(entry.getKey() + " " + entry2.getKey() + " " + entry2.getValue());
        //    }
        //}
        Map<Integer, Double> costs = new HashMap<Integer, Double>();
        String costFile = "custos.txt";
        fr.readCost(costFile, costs);
        for (Map.Entry<Integer, Double> entry : costs.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

     
        System.out.print("\nBem-vindo!\n");

        // O que estabelece o loop + o menu
        while (true) {
            System.out.print("\n");
            System.out.print("|-----------------------------------------------|\n");
            System.out.print("| Opcao 1 - Consultar Trechos x Modalidade      |\n");
            System.out.print("| Opcao 2 - Sair                                |\n");
            System.out.print("|-----------------------------------------------|\n");
            System.out.print("Selecione uma opcao: ");

            int opt = menu.nextInt();

            switch (opt) {

                case 1: 
                    System.out.print("\n");
                    System.out.print("Digite a cidade de origem: \n");
                    menu.nextLine();
                    String origin = menu.nextLine();

                    origin = origin.toUpperCase();

                    if (!routes.containsKey(origin)){

                        System.out.println("Cidade de origem nao encontrada");
                        break;
                    }
                    System.out.print("Digite a cidade de destino: \n");
                    String destination = menu.nextLine();
                    destination = destination.toUpperCase();

                    if (!routes.get(origin).containsKey(destination)){
                        System.out.println("\nCidade de destino nao encontrada");
                        break;
                    }
                    
                    System.out.print("Digite o tipo de veiculo: \n");
                    System.out.print("1 - Pequeno Porte\n");
                    System.out.print("2 - Medio Porte\n");
                    System.out.print("3 - Grande Porte\n");
                    int vehicle_type = menu.nextInt();
                    
                    destination.toUpperCase();
                    String cost;
                    switch (vehicle_type) {
                        case 1:
                            cost = String.format("%.2f",(costs.get(1) * routes.get(origin).get(destination)));
                            System.out.print("O custo do trecho é: R$" + cost);
                            break;
                        case 2:
                            cost = String.format("%.2f",(costs.get(2) * routes.get(origin).get(destination)));
                            System.out.print("O custo do trecho é: R$" + cost);
                            break;
                        case 3:
                            cost = String.format("%.2f",(costs.get(3) * routes.get(origin).get(destination)));
                            System.out.print("O custo do trecho é: R$" + cost);
                            break;
                        default:
                            System.out.print("Opçao invalida");
                            break;
                    }


                    break;

                case 2:
                    System.exit(0);
                    menu.close();

                    break;

                default:
                  
                    System.out.print("\nOpção inválida\n");
                    break;

            }
        }

    }
}
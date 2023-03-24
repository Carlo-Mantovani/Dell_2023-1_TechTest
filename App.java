import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;


public class App {

    public static void main(String[] args) {

        Scanner menu = new Scanner(System.in);
        ReadFile fr = new ReadFile();
        String file = "DNIT-Distancias.csv";
        Map<String,Map<String,Integer>> routes = new HashMap<String,Map<String,Integer>>();
        fr.readFile(file, routes);
        //for (Origin origin : origins) {
        //    System.out.println(origin.getName());
        //    for (Map.Entry<String, Integer> entry : origin.getDest_distance().entrySet()) {
        //        System.out.println(entry.getKey() + " " + entry.getValue());
        //    }
        //}
        for (Map.Entry<String, Map<String, Integer>> entry : routes.entrySet()) {
            System.out.println(entry.getKey());
            for (Map.Entry<String, Integer> entry2 : entry.getValue().entrySet()) {
                System.out.println(entry.getKey() + " " + entry2.getKey() + " " + entry2.getValue());
            }
        }

        // for (Map.Entry<Integer, String> entry : destinations.getDest().entrySet()) {
        // System.out.println(entry.getKey() + " " + entry.getValue());
        // }
        //for (Route route : routes) {
        //    System.out.println(route.getName() + " " + destinations.getDest().get(route.getDest_id()) + " "
        //            + route.getDest_distance());
        //}

        // System.out.print("\f");
        System.out.print("\nBem-vindo!\n");

        // O que estabelece o loop + o menu
        while (true) {
            System.out.print("\n");
            System.out.print("|-----------------------------------------------|\n");
            System.out.print("| Opcao 1 - Sair                                |\n");
            System.out.print("|-----------------------------------------------|\n");
            System.out.print("Selecione uma opcao: ");

            int opt = menu.nextInt();

            switch (opt) {

                case 1:
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
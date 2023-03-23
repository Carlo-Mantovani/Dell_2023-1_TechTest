import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;

public class App {

    public static void main(String[] args) {

        Scanner menu = new Scanner(System.in);
        ReadFile fr = new ReadFile();

        String city_name = "";
        Map<Integer, String> dest = new HashMap<Integer, String>();

        List<Route> routes = new LinkedList<Route>();
        Destination destinations = new Destination(dest);
        fr.readFile("DNIT-Distancias.csv", routes, destinations);

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
            System.out.print("| Opção 1 - Sair                                |\n");
            System.out.print("|-----------------------------------------------|\n");
            System.out.print("Selecione uma opção: ");

            int opt = menu.nextInt();

            switch (opt) {

                case 1:
                    System.exit(0);

                    break;

                default:
                  
                    System.out.print("\nOpção inválida\n");
                    break;

            }
        }

    }
}
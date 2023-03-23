import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    private static void readFile(String file, Map<Integer, String> dest, Map<Integer, Integer> dest_distance) {

        Path path = Paths.get(file);

        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line = reader.readLine();
            line = reader.readLine();
            String[] aux = line.split(";");
            int id = 0;
            for (String s : aux) {
                dest.put(id, s);
                id++;
                System.out.println(s);
                System.out.println(id);
            }

            while ((line = reader.readLine()) != null) {
                aux = line.split(";");

            }

            System.out.println("Destinations:");
            for (Map.Entry<Integer, String> entry : dest.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

        } catch (IOException e) {
            System.err.format("Error", e);
        }

    }

    public static void main(String[] args) {

        Scanner menu = new Scanner(System.in);

        String city_name = "";
        Map<Integer, String> dest = new HashMap<Integer, String>();
        Map<Integer, Integer> dest_distance = new HashMap<Integer, Integer>();

        Route r = new Route(city_name, dest_distance);
        Destination d = new Destination(dest);
        readFile("DNIT-Distancias.csv", dest, dest_distance);

        d.setDest(dest);

        for (Map.Entry<Integer, String> entry : d.getDest().entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.print("\f");
        System.out.print("\n Bem-vindo!\n");

        // O que estabele o loop + o menu
        while (true) {
            System.out.print("\n");
            System.out.print("|-----------------------------------------------|\n");
            System.out.print("| Opção 1 - Relatório Semanal                   |\n");
            System.out.print("| Opção 2 - Categoria Especial Mais Utilizada   |\n");
            System.out.print("| Opção 3 - Retorno de Pacientes Idosos         |\n");
            System.out.print("| Opção 4 - Sair                                |\n");
            System.out.print("|-----------------------------------------------|\n");
            System.out.print("Selecione uma opção: ");

            int opt = menu.nextInt();

            switch (opt) {

                case 1:
                    System.exit(0);

                    break;

                default:
                    System.out.print("\f");
                    System.out.print("\n Opção inválida \n");
                    menu.close();
                    break;

            }
        }

    }
}
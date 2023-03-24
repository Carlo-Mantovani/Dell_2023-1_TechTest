import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class ReadFile {

    public void readFile(String file, Map<String,Map<String,Integer>> routes) {

        Path path = Paths.get(file);

        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line = reader.readLine();
            String[] aux = line.split(";");
            int id = 0;
            Map<Integer,String> dest = new HashMap<Integer,String>();
            for (String s : aux) {
                dest.put(id, s);
                id++;
            }
            int depId = 0;
            int arrId = 0;

           
            while ((line = reader.readLine()) != null) {

                aux = line.split(";");

                Map <String, Integer> auxMap = new HashMap<String, Integer>();
                arrId = 0;
                for (String s : aux) {
                    auxMap.put(dest.get(arrId), Integer.parseInt(s));
                    //System.out.println(destinations.getDest().get(depId) + " " + destinations.getDest().get(arrId) + " " + s);
                    arrId++;
                }
                routes.put(dest.get(depId), auxMap);
                //origins.add(auxOrigin);
                depId++;
            }

        } catch (IOException e) {
            System.err.format("Error", e);
        }

    }
    //le o arquivo de custos, e adiciona no map seguindo o padrão de id e custo
    // Id = 1 -> Pequeno Porte
    // Id = 2 -> Médio Porte
    // Id = 3 -> Grande Porte
    public void readCost(String file, Map <Integer, Double> costs) {
        Path path = Paths.get(file);
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                costs.put(Integer.parseInt(aux[0]), Double.parseDouble(aux[1]));
            }

        } catch (IOException e) {
            System.err.format("Error", e);
        }
    }
}

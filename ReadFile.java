import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ReadFile {

    public void readFile(String file, Map<String, Map<String, Integer>> routes) {// le o arquivo de rotas, e adiciona no
                                                                                 // map seguindo o padrão de
                                                                                 // <origem,<destino, distancia>>

        Path path = Paths.get(file);

        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line = reader.readLine();// le a primeira linha do arquivo, que contem o nome das cidades
            String[] aux = line.split(";");
            int id = 0;
            Map<Integer, String> dest = new HashMap<Integer, String>(); // mapa auxiliar para armazenar o id e o nome da
                                                                        // cidade
            for (String s : aux) {// adiciona no mapa auxiliar o id e o nome da cidade
                dest.put(id, s);
                id++;
            }

            // inicializa variaveis auxiliares
            int depId = 0;
            int arrId = 0;

            while ((line = reader.readLine()) != null) {// le o arquivo linha por linha

                aux = line.split(";");

                Map<String, Integer> auxMap = new HashMap<String, Integer>();// mapa auxiliar para armazenar o destino e
                                                                             // a distancia
                arrId = 0;// reseta o id da cidade destino

                for (String s : aux) {// adiciona no mapa auxiliar o destino e a distancia
                    auxMap.put(dest.get(arrId), Integer.parseInt(s));

                    arrId++;// incrementa o id da cidade destino
                }
                routes.put(dest.get(depId), auxMap);// adiciona no mapa de rotas a origem e o mapa de destino e
                                                    // distancia
                depId++;// incrementa o id da cidade origem
            }

        } catch (IOException e) {// trata exceção
            System.err.format("Error", e);
        }

    }

    // le o arquivo de custos, e adiciona no map seguindo o padrão de id e custo
    // Id = 1 -> Pequeno Porte
    // Id = 2 -> Médio Porte
    // Id = 3 -> Grande Porte
    public void readCost(String file, Map<Integer, Double> costs) {
        Path path = Paths.get(file);
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                costs.put(Integer.parseInt(aux[0]), Double.parseDouble(aux[1]));// adiciona no mapa de custos o id e o
                                                                                // custo
            }

        } catch (IOException e) {// trata exceção
            System.err.format("Error", e);
        }
    }
}

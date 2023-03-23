import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadFile {

    public void readFile(String file, List<Route> routes, Destination destinations) {

        Path path = Paths.get(file);

        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line = reader.readLine();
            String[] aux = line.split(";");
            int id = 0;
            for (String s : aux) {
                destinations.setDest(destinations.getDest(), id, s);
                id++;
            }
            int depId = 0;
            int arrId = 0;

            Route auxRoute = new Route("", 0, 0);
            while ((line = reader.readLine()) != null) {

                aux = line.split(";");

                arrId = 0;
                for (String s : aux) {
                    auxRoute = new Route(destinations.getDest().get(depId), arrId, Integer.parseInt(s));
                    arrId++;
                    routes.add(auxRoute);
                }
                depId++;
            }

        } catch (IOException e) {
            System.err.format("Error", e);
        }

    }
}

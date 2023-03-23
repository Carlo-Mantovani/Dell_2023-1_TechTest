import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileRead {

    public void readFile(String file, Route r, Destination d) {

        Path path = Paths.get(file);
       
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line = reader.readLine();
            String[] aux = line.split(line);
            int id = 0;
            for (String s : aux) {
                d.setDest(d.getDest(), id, s);
                id++;
            }
            while ((line = reader.readLine()) != null) {
                aux = line.split(";");
                //for (String s : aux) {
                //    System.out.println(s);
                //}


            }
            System.out.println("Destinations:");
            for (Map.Entry<Integer, String> entry : d.getDest().entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

        } catch (IOException e) {
            System.err.format("Error", e);
        }

    }
}

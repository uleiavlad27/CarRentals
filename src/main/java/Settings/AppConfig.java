package Settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AppConfig {
    private static final String CONFIG_FILE = "C:\\Users\\vlads\\OneDrive\\Desktop\\MAP\\Lab4\\src\\main\\java\\Settings\\settings.properties";
    private Properties properties = new Properties();

    public Map<String, String> config() {
        Map<String, String> elements = new HashMap<>();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);

            String repositoryType = properties.getProperty("Repository");
            String carsFile = properties.getProperty("Cars");
            String rentalsFile = properties.getProperty("Rentals");

            elements.put("Repository", repositoryType);
            elements.put("Cars", carsFile);
            elements.put("Rentals", rentalsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elements;
    }
}


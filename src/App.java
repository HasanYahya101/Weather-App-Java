import config.API_Key;

public class App {
    public static void main(String[] args) throws Exception {
        // testing if it can get the api key from the config file.
        String apiKey = API_Key.getAPIKey();
        System.out.println("API Key: " + apiKey);
    }
}

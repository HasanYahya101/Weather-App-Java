package functional_layer.source;

import functional_layer.Location_Interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import config.API_Key;
/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;*/
import java.net.URL;

class locations_query implements Location_Interfaces {

    public boolean addLocation_Coordinates(String lati, String longi) {
        // String apiKey = config.API_Key.getAPIKey();
        String geoKey = config.API_Key.getGeoLocAPIKey();
        boolean flag = false;
        // API call to add location by coordinates.
        // https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API
        // key}

        /*
         * try {
         * // Construct the URL for the API call
         * String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=" +
         * lati + "&lon=" + longi + "&appid=" + apiKey;
         * URL url = new URL(urlString);
         * 
         * // Open a connection
         * HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         * 
         * // Set request method
         * connection.setRequestMethod("GET");
         * 
         * // Get the response
         * BufferedReader in = new BufferedReader(new
         * InputStreamReader(connection.getInputStream()));
         * String inputLine;
         * StringBuilder response = new StringBuilder();
         * while ((inputLine = in.readLine()) != null)
         * {
         * response.append(inputLine);
         * }
         * in.close();
         * // Process response here, set flag accordingly
         * System.out.println(response.toString());
         * // Close the connection
         * connection.disconnect();
         * 
         * } catch (IOException e) {
         * e.printStackTrace();
         * // Handle other exceptions here
         * }
         */

        try {
            URL url = new URL(
                    "https://api.geoapify.com/v1/geocode/reverse?lat=" + lati + "&lon=" + longi + "&apiKey=" + geoKey);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Accept", "application/json");

            int responseCode = http.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response body
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Print the response body for testing purposes
                System.out.println("Response Body: " + response.toString());
            } else {
                // Internet connection error
                flag = false;
            }
            http.disconnect();
        } catch (IOException e) {
            flag = false;
        }

        return flag;
    }

    public boolean addLocation_Names(String city, String country) {
        // String apiKey = config.API_Key.getAPIKey();
        boolean flag = false;
        return flag;
    }

    // main method for testing only
    public static void main(String[] args) {
        locations_query lq = new locations_query();
        lq.addLocation_Coordinates("40.7128", "-74.0060");
    }
}
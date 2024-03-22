package functional_layer.source;

import functional_layer.Location_Interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.BufferedWriter;
import java.io.FileWriter;

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
                // System.out.println("Response Body: " + response.toString());

                // extract country, country code, city, latitude and longitude from the response
                String country = response.toString().split("country\":\"")[1].split("\",\"")[0];
                String country_code = response.toString().split("country_code\":\"")[1].split("\",\"")[0];
                String city = response.toString().split("city\":\"")[1].split("\",\"")[0];
                String latitude = response.toString().split("lat\":")[1].split(",\"")[0];
                String longitude = response.toString().split("lon\":")[1].split(",\"")[0];

                // print them for testing purposes
                /*
                 * System.out.println("Country: " + country);
                 * System.out.println("Country Code: " + country_code);
                 * System.out.println("City: " + city);
                 * System.out.println("Latitude: " + latitude);
                 * System.out.println("Longitude: " + longitude);
                 */

                // add the location to the Locations.txt file in next line
                flag = saveLocation_Names(city, country, country_code, latitude, longitude);

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

    public boolean saveLocation_Names(String city, String country, String country_code, String latitude,
            String longitude) {
        // save into Locations.txt file
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("./../../../assets/text_database_module/Locations.txt", true));
            writer.write(city + ", " + country + ", " + country_code + ", " + latitude + ", " + longitude);
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
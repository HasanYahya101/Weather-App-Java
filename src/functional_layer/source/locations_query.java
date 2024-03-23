package functional_layer.source;

import functional_layer.Location_Interfaces;
import database_layer.textfile_module.location_save_interface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

import config.API_Key;
/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;*/
import java.net.URL;
import java.net.URLEncoder;

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

                // extract country, country code, city, latitude and longitude from the response
                String country = response.toString().split("country\":\"")[1].split("\",\"")[0];
                String country_code = response.toString().split("country_code\":\"")[1].split("\",\"")[0];
                String city = response.toString().split("city\":\"")[1].split("\",\"")[0];
                String latitude = response.toString().split("lat\":")[1].split(",\"")[0];
                String longitude = response.toString().split("lon\":")[1].split(",\"")[0];

                // add the location to the Locations.txt file in next line
                location_save_interface db_text_layer = new database_layer.textfile_module.source.location_save();
                flag = db_text_layer.saveLocation_Names(city, country,
                        country_code, latitude, longitude);

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
        String geoKey = config.API_Key.getGeoLocAPIKey();
        String encodedQuery = null;
        try {
            encodedQuery = URLEncoder.encode(city + ", " + country, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        String apiUrl = "https://api.geoapify.com/v1/geocode/search?text=" + encodedQuery + "&apiKey=" + geoKey;
        URL url = null;
        try {
            url = new URL(apiUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }

        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Accept", "application/json");

            int responseCode = http.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String country_code = response.toString().split("country_code\":\"")[1].split("\",\"")[0];
                String latitude = response.toString().split("lat\":")[1].split(",\"")[0];
                String longitude = response.toString().split("lon\":")[1].split(",\"")[0];

                // add the location to the Locations.txt file in next line
                location_save_interface db_text_layer = new database_layer.textfile_module.source.location_save();
                return db_text_layer.saveLocation_Names(city, country, country_code, latitude, longitude);
            } else {
                System.out.println("Error: " + http.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (http != null) {
                http.disconnect();
            }
        }
        return true;
    }

    // main method for testing only
    public static void main(String[] args) {
        locations_query lq = new locations_query();
        // lq.addLocation_Coordinates("35", "139");
        lq.addLocation_Names("Lahore", "Pakistan");
    }
}
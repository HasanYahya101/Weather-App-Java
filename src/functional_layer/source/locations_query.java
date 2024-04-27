package functional_layer.source;

import functional_layer.Location_Interfaces;
import database_layer.textfile_module.location_save_interface;
import database_layer.textfile_module.location_save_interface.Locations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;

import java.net.URL;
import java.net.URLEncoder;

public class locations_query implements Location_Interfaces {

    public boolean addLocation_Coordinates(String lati, String longi) {
        // String apiKey = config.API_Key.getAPIKey();
        String geoKey = config.API_Key.getGeoLocAPIKey();
        boolean flag = false;
        // get locs and check if lat and lon already exists
        location_save_interface dbl = new database_layer.textfile_module.source.location_save();
        java.util.List<location_save_interface.Locations> locations = dbl.getLocations();
        /*
         * for (location_save_interface.Locations loc : locations) {
         * if (loc.latitude.equals(lati) && loc.longitude.equals(longi)) {
         * // print error
         * System.out.println("Latitude and Longitude already exists.");
         * return false;
         * }
         * }
         */
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

                // print the result in string
                // System.out.println(response.toString());
                // System.exit(0);

                // extract country, country code, city, latitude and longitude from the response
                String country_code = response.toString().split("country_code\":\"")[1].split("\",\"")[0];
                String country = response.toString().split("country\":\"")[1].split("\",\"")[0];
                String city = response.toString().split("city\":\"")[1].split("\",\"")[0];
                String latitude = response.toString().split("lat\":")[1].split(",\"")[0];
                String longitude = response.toString().split("lon\":")[1].split(",\"")[0];

                // check for duplicates
                String new_lat = latitude;
                String new_lon = longitude;
                for (location_save_interface.Locations loc : locations) {
                    if (loc.latitude.equals(new_lat) && loc.longitude.equals(new_lon)) {
                        // print error
                        System.out.println("Latitude and Longitude already exist.");
                        return false;
                    }
                }
                String current_Date = java.time.LocalDate.now().toString();
                String current_Time = java.time.LocalTime.now().toString();
                String date = current_Date.split("-")[2];
                String month = current_Date.split("-")[1];
                String year = current_Date.split("-")[0];
                String hour = current_Time.split(":")[0];
                String minutes = current_Time.split(":")[1];
                // add the location to the Locations.txt file in next line
                location_save_interface db_text_layer = new database_layer.textfile_module.source.location_save();
                flag = db_text_layer.saveLocation_Names(city, country,
                        country_code, latitude, longitude, date, month, year, hour, minutes);

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
        // get all locs and check if it already exists
        location_save_interface dbl = new database_layer.textfile_module.source.location_save();
        java.util.List<location_save_interface.Locations> locations = dbl.getLocations();
        boolean flag1 = true;
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).city.equals(city) && locations.get(i).country.equals(country)) {
                // print error
                System.out.println("Location (City and Country) already exists.");
                flag1 = false;
                break;
            }
        }
        if (flag1 == false) {
            return false;
        }
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

                // print response in string
                // System.out.println(response.toString());
                // System.exit(0);

                String country_code = response.toString().split("country_code\":\"")[1].split("\",\"")[0];
                String latitude = response.toString().split("lat\":")[1].split(",\"")[0];
                String longitude = response.toString().split("lon\":")[1].split(",\"")[0];

                // get time and date
                String current_Date = java.time.LocalDate.now().toString();
                String current_Time = java.time.LocalTime.now().toString();

                String date = current_Date.split("-")[2];
                String month = current_Date.split("-")[1];
                String year = current_Date.split("-")[0];
                String hour = current_Time.split(":")[0];
                String minutes = current_Time.split(":")[1];

                // add the location to the Locations.txt file in next line
                location_save_interface db_text_layer = new database_layer.textfile_module.source.location_save();
                return db_text_layer.saveLocation_Names(city, country, country_code, latitude, longitude, date, month,
                        year, hour, minutes);
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

    public List<Locations> displayLocs() {
        location_save_interface db_text_layer = new database_layer.textfile_module.source.location_save();
        java.util.List<location_save_interface.Locations> locations = db_text_layer.getLocations();

        return locations;
    }

    // main method for testing only
    public static void main(String[] args) {
        locations_query lq = new locations_query();
        lq.addLocation_Coordinates("37.5497", "75.3436");
        // lq.addLocation_Names("Karachi", "Pakistan");
        // lq.displayLocs();
    }
}
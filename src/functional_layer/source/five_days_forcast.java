package functional_layer.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import functional_layer.*;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import functional_layer.current_weather_interface.Current_Conditions;
import functional_layer.five_days_forcast_interface;

import database_layer.textfile_module.source.five_days_save;

public class five_days_forcast implements five_days_forcast_interface {
    /*
     * private static String extractValue(String text, String start, String end) {
     * int startIndex = text.indexOf(start) + start.length();
     * int endIndex = text.indexOf(end, startIndex);
     * return text.substring(startIndex, endIndex);
     * }
     */

    private static String extractValue(String text, String start, String end) {
        int startIndex = text.indexOf(start);
        if (startIndex == -1) {
            return ""; // Start delimiter not found
        }
        startIndex += start.length();

        int endIndex = text.indexOf(end, startIndex);
        if (endIndex == -1) {
            return ""; // End delimiter not found
        }

        return text.substring(startIndex, endIndex);
    }

    @Override
    public five_days_data get5DaysForcast(String longi, String lati) {
        String apiKey = config.API_Key.getAPIKey();
        five_days_data fdd = null;

        five_days_save fdss = new five_days_save();
        fdss.delete_cache(); // delete the yesterday's cache

        List<five_days_data> fdd_temp = fdss.read_Five_Days();

        for (int i = 0; i < fdd_temp.size(); i++) {
            if (fdd_temp.get(i).lat.equals(lati) && fdd_temp.get(i).lon.equals(longi)) {
                fdd = fdd_temp.get(i);
                return fdd;
                // no need to call the API again as today data is still in cache
            }
        }

        // https://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={APIkey}
        try {
            // Construct the URL for the API call
            String urlString = "https://api.openweathermap.org/data/2.5/forecast?lat=" +
                    lati + "&lon=" + longi + "&appid=" + apiKey;
            URL url = new URL(urlString);

            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // get the current date, month and year
            String dateday = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
            String date = dateday.substring(0, 2);
            String month = dateday.substring(3, 5);
            String year = dateday.substring(6, 10);

            fdd = new five_days_data();
            fdd.lat = lati;
            fdd.lon = longi;
            fdd.date = date;
            fdd.month = month;
            fdd.year = year;

            // Print to test response
            // System.out.println(response.toString());
            // System.exit(0);

            // Use json parser to parse the response
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.toString());

            // Get the list of forecasts
            JSONArray list = (JSONArray) json.get("list");

            // Loop through the forecasts
            for (int i = 0; i < list.size(); i++) {
                JSONObject forecast = (JSONObject) list.get(i);

                String dt = forecast.get("dt").toString();
                // first get main then individually extract values from it
                JSONObject main_given = ((JSONObject) forecast.get("main"));
                String temp = main_given.get("temp").toString();
                String feels_like = main_given.get("feels_like").toString();
                String temp_min = main_given.get("temp_min").toString();
                String temp_max = main_given.get("temp_max").toString();
                String pressure = main_given.get("pressure").toString();
                String humidity = main_given.get("humidity").toString();
                // first get weather then individually extract values from it
                JSONArray weather_given = (JSONArray) forecast.get("weather");
                // get the first element of the array
                JSONObject weather_given_0 = (JSONObject) weather_given.get(0);
                String weather = weather_given_0.get("main").toString();
                String icon = weather_given_0.get("icon").toString();
                String visibility = forecast.get("visibility").toString();
                // first get wind then individually extract values from it
                JSONObject wind_given = ((JSONObject) forecast.get("wind"));
                String wind_speed = wind_given.get("speed").toString();
                String wind_deg = wind_given.get("deg").toString();
                String gust = wind_given.get("gust").toString();
                // first get clouds then individually extract values from it
                JSONObject clouds_given = ((JSONObject) forecast.get("clouds"));
                String clouds_all = clouds_given.get("all").toString();
                String sunrise = forecast.get("sunrise").toString();
                String sunset = forecast.get("sunset").toString();
                String dt_text = forecast.get("dt_txt").toString();

                five_days_struct fds = new five_days_struct();
                fds.dt = dt;
                fds.temp = temp;
                fds.feels_like = feels_like;
                fds.temp_min = temp_min;
                fds.temp_max = temp_max;
                fds.pressure = pressure;
                fds.humidity = humidity;
                fds.weather = weather;
                fds.icon = icon;
                fds.visibility = visibility;
                fds.wind_speed = wind_speed;
                fds.wind_deg = wind_deg;
                fds.gust = gust;
                fds.clouds_all = clouds_all;
                fds.sunrise = sunrise;
                fds.sunset = sunset;
                fds.dt_text = dt_text;

                // Add the extracted data to the list in the data object
                fdd.list.add(fds);

            }

            // Process extracted data as needed
            /*
             * System.out
             * 
             * /*
             * for (int i = 1; i < forecasts.length; i++) {
             * String forecast = forecasts[i];
             * 
             * String dt = extractValue(forecast, "", ",");
             * String temp = extractValue(forecast, "\"temp\":", ",");
             * String feels_like = extractValue(forecast, "\"feels_like\":", ",");
             * String temp_min = extractValue(forecast, "\"temp_min\":", ",");
             * String temp_max = extractValue(forecast, "\"temp_max\":", ",");
             * String pressure = extractValue(forecast, "\"pressure\":", ",");
             * String humidity = extractValue(forecast, "\"humidity\":", ",");
             * String weather = extractValue(forecast, "\"main\":\"", "\",");
             * String icon = extractValue(forecast, "\"icon\":\"", "\"}");
             * String visibility = extractValue(forecast, "\"visibility\":", ",");
             * String wind_speed = extractValue(forecast, "\"speed\":", ",");
             * String wind_deg = extractValue(forecast, "\"deg\":", ",");
             * String gust = extractValue(forecast, "\"gust\":", "}");
             * String clouds_all = extractValue(forecast, "\"all\":", "}");
             * String sunrise = extractValue(forecast, "\"sunrise\":", ",");
             * String sunset = extractValue(forecast, "\"sunset\":", "}");
             * String dt_text = extractValue(forecast, "\"dt_txt\":\"", "\"}");
             * 
             * five_days_struct fds = new five_days_struct();
             * fds.dt = dt;
             * fds.temp = temp;
             * fds.feels_like = feels_like;
             * fds.temp_min = temp_min;
             * fds.temp_max = temp_max;
             * fds.pressure = pressure;
             * fds.humidity = humidity;
             * fds.weather = weather;
             * fds.icon = icon;
             * fds.visibility = visibility;
             * fds.wind_speed = wind_speed;
             * fds.wind_deg = wind_deg;
             * fds.gust = gust;
             * fds.clouds_all = clouds_all;
             * fds.sunrise = sunrise;
             * fds.sunset = sunset;
             * fds.dt_text = dt_text;
             * 
             * // Add the extracted data to the list in the data object
             * fdd.list.add(fds);
             * 
             * }
             */
            // Close the connection
            connection.disconnect();
            // save the data to cache
            fdss.save_Five_Days(fdd);
            return fdd;

        } catch (IOException e) {
            e.printStackTrace();
            // return null if an error occurs
            // return null;
        } catch (ParseException e) {
            e.printStackTrace();
            // return null if an error occurs
            // return null;
        }
        return fdd;
    }

    // main function to test functionality
    public static void main(String[] args) {
        five_days_forcast fdf = new five_days_forcast();
        fdf.get5DaysForcast("31.5656822", "74.3141829");
    }
}

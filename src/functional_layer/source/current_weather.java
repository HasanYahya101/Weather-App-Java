package functional_layer.source;

import functional_layer.current_weather_interface;
import config.API_Key;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class current_weather implements current_weather_interface {
    private static String extractValue(String input, String start, String end) {
        int startIndex = input.indexOf(start) + start.length();
        int endIndex = input.indexOf(end, startIndex);
        return input.substring(startIndex, endIndex);
    }

    public Current_Conditions getCurrentWeather(String lati, String longi) {
        String apiKey = config.API_Key.getAPIKey();
        Current_Conditions cc = null;

        try {
            // Construct the URL for the API call
            String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=" +
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

            // Print to test response
            // System.out.println(response.toString());

            String responseString = response.toString();
            String lon = extractValue(responseString, "\"lon\":", ",");
            String lat = extractValue(responseString, "\"lat\":", "}");
            String id = extractValue(responseString, "\"id\":", ",");
            String main = extractValue(responseString, "\"main\":\"", "\",");
            String description = extractValue(responseString, "\"description\":\"", "\",");
            String icon = extractValue(responseString, "\"icon\":\"", "\"}");
            String temp = extractValue(responseString, "\"temp\":", ",");
            String feels_like = extractValue(responseString, "\"feels_like\":", ",");
            String temp_min = extractValue(responseString, "\"temp_min\":", ",");
            String temp_max = extractValue(responseString, "\"temp_max\":", ",");
            String pressure = extractValue(responseString, "\"pressure\":", ",");
            String humidity = extractValue(responseString, "\"humidity\":", "}");
            String visibility = extractValue(responseString, "\"visibility\":", ",");
            String wind_speed = extractValue(responseString, "\"speed\":", ",");
            String wind_deg = extractValue(responseString, "\"deg\":", ",");
            String gust = extractValue(responseString, "\"gust\":", "}");
            String rain_1hr = extractValue(responseString, "\"1h\":", "}");
            String clouds_all = extractValue(responseString, "\"all\":", "}");
            String sunrise = extractValue(responseString, "\"sunrise\":", ",");
            String sunset = extractValue(responseString, "\"sunset\":", "}");
            String timezone = extractValue(responseString, "\"timezone\":", ",");

            // get the current date, month and year
            String dateday = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
            String date = dateday.substring(0, 2);
            String month = dateday.substring(3, 5);
            String year = dateday.substring(6, 10);

            // save data in its object
            cc = new Current_Conditions();
            cc.lon = lon;
            cc.lat = lat;
            cc.id = id;
            cc.main = main;
            cc.description = description;
            cc.icon = icon;
            cc.temp = temp;
            cc.feels_like = feels_like;
            cc.temp_min = temp_min;
            cc.temp_max = temp_max;
            cc.pressure = pressure;
            cc.humidity = humidity;
            cc.visibility = visibility;
            cc.wind_speed = wind_speed;
            cc.wind_deg = wind_deg;
            cc.gust = gust;
            cc.rain_1hr = rain_1hr;
            cc.clouds_all = clouds_all;
            cc.sunrise = sunrise;
            cc.sunset = sunset;
            cc.timezone = timezone;
            cc.date = date;
            cc.month = month;
            cc.year = year;

            // print all for testing
            // System.out.println("lon: " + lon);
            // System.out.println("lat: " + lat);
            // System.out.println("id: " + id);
            // System.out.println("main: " + main);
            // System.out.println("description: " + description);
            // System.out.println("icon: " + icon);
            // System.out.println("temp: " + temp);
            // System.out.println("feels_like: " + feels_like);
            // System.out.println("temp_min: " + temp_min);
            // System.out.println("temp_max: " + temp_max);
            // System.out.println("pressure: " + pressure);
            // System.out.println("humidity: " + humidity);
            // System.out.println("visibility: " + visibility);
            // System.out.println("wind_speed: " + wind_speed);
            // System.out.println("wind_deg: " + wind_deg);
            // System.out.println("gust: " + gust);
            // System.out.println("rain_1hr: " + rain_1hr);
            // System.out.println("clouds_all: " + clouds_all);
            // System.out.println("sunrise: " + sunrise);
            // System.out.println("sunset: " + sunset);
            // System.out.println("timezone: " + timezone);

            // Close the connection
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle other exceptions here
        }

        return cc;
    }

    // main for testing
    public static void main(String[] args) {
        current_weather cw = new current_weather();
        cw.getCurrentWeather("35", "139");
    }
}

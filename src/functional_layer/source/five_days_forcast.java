package functional_layer.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import functional_layer.*;

import functional_layer.current_weather_interface.Current_Conditions;
import functional_layer.five_days_forcast_interface;

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
            System.out.println(response.toString());

            String[] forecasts = response.toString().split("\"dt\":");

            for (int i = 1; i < forecasts.length; i++) {
                String forecast = forecasts[i];

                String dt = extractValue(forecast, "", ",");
                String temp = extractValue(forecast, "\"temp\":", ",");
                String feels_like = extractValue(forecast, "\"feels_like\":", ",");
                String temp_min = extractValue(forecast, "\"temp_min\":", ",");
                String temp_max = extractValue(forecast, "\"temp_max\":", ",");
                String pressure = extractValue(forecast, "\"pressure\":", ",");
                String humidity = extractValue(forecast, "\"humidity\":", ",");
                String weather = extractValue(forecast, "\"main\":\"", "\",");
                String icon = extractValue(forecast, "\"icon\":\"", "\"}");
                String visibility = extractValue(forecast, "\"visibility\":", ",");
                String wind_speed = extractValue(forecast, "\"speed\":", ",");
                String wind_deg = extractValue(forecast, "\"deg\":", ",");
                String gust = extractValue(forecast, "\"gust\":", "}");
                String clouds_all = extractValue(forecast, "\"all\":", "}");
                String sunrise = extractValue(forecast, "\"sunrise\":", ",");
                String sunset = extractValue(forecast, "\"sunset\":", "}");
                String dt_text = extractValue(forecast, "\"dt_txt\":\"", "\"}");

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

                // Process extracted data as needed
                /*
                 * System.out.println("Date Time: " + dt);
                 * System.out.println("Temperature: " + temp);
                 * System.out.println("Feels Like: " + feels_like);
                 * System.out.println("Min Temperature: " + temp_min);
                 * System.out.println("Max Temperature: " + temp_max);
                 * System.out.println("Pressure: " + pressure);
                 * System.out.println("Humidity: " + humidity);
                 * System.out.println("Weather: " + weather);
                 * System.out.println("Icon: " + icon);
                 * System.out.println("Visibility: " + visibility);
                 * System.out.println("Wind Speed: " + wind_speed);
                 * System.out.println("Wind Degree: " + wind_deg);
                 * System.out.println("Gust: " + gust);
                 * System.out.println("Clouds: " + clouds_all);
                 * System.out.println("Sunrise: " + sunrise);
                 * System.out.println("Sunset: " + sunset);
                 * System.out.println("--------------------------------------");
                 */
            }
            // Close the connection
            connection.disconnect();
            return fdd;

        } catch (IOException e) {
            e.printStackTrace();
            // return null if an error occurs
            // return null;
        }
        return fdd;
    }

    // main function to test functionality
    public static void main(String[] args) {
        five_days_forcast fdf = new five_days_forcast();
        fdf.get5DaysForcast("40.7128", "-74.0060");
    }
}

package functional_layer.source;

import functional_layer.current_weather_interface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

public class current_weather implements current_weather_interface {

    public Current_Conditions getCurrentWeather(String lati, String longi) {
        String apiKey = config.API_Key.getAPIKey();
        Current_Conditions cc = null;
        database_layer.textfile_module.source.current_conditions cc_db = new database_layer.textfile_module.source.current_conditions();
        cc_db.remove_prev_cache();
        List<Current_Conditions> temp_conditions = cc_db.return_current_conditions();
        boolean flag = false;
        String given_lati_before_decimal = lati.substring(0, lati.indexOf("."));
        String given_longi_before_decimal = longi.substring(0, longi.indexOf("."));
        String given_lati_2_digits_after_decimal = lati.substring(lati.indexOf(".") + 1, lati.indexOf(".") + 3);
        String given_longi_2_digits_after_decimal = longi.substring(longi.indexOf(".") + 1, longi.indexOf(".") + 3);
        if (temp_conditions != null && temp_conditions.size() > 0) {
            // check if the data is already in the cache
            // System.out.println("Taken: " + lati + " " + longi);
            for (Current_Conditions temp : temp_conditions) {
                // System.out.println("Database: " + temp.lat + " " + temp.lon);
                String database_lati_before_decimal = temp.lat.substring(0, temp.lat.indexOf("."));
                String database_longi_before_decimal = temp.lon.substring(0, temp.lon.indexOf("."));
                String database_lati_2_digits_after_decimal = temp.lat.substring(temp.lat.indexOf(".") + 1,
                        temp.lat.indexOf(".") + 3);
                String database_longi_2_digits_after_decimal = temp.lon.substring(temp.lon.indexOf(".") + 1,
                        temp.lon.indexOf(".") + 3);
                if (given_lati_before_decimal.equals(database_lati_before_decimal)
                        && given_longi_before_decimal.equals(database_longi_before_decimal)
                        && given_lati_2_digits_after_decimal.equals(database_lati_2_digits_after_decimal)
                        && given_longi_2_digits_after_decimal.equals(database_longi_2_digits_after_decimal)) {
                    cc = temp;
                    flag = true;
                    break;
                }
            }
        }

        if (flag == true) {
            return cc;
        } else {

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
                // System.exit(0);

                String responseString = response.toString();
                String lon;
                String lat;
                String id;
                String main;
                String description;
                String icon;
                String temp;
                String feels_like;
                String temp_min;
                String temp_max;
                String pressure;
                String humidity;
                String visibility;
                String wind_speed;
                String wind_deg;
                String gust;
                String clouds_all;
                String sunrise;
                String sunset;
                String timezone;

                JSONParser parser;
                JSONObject json;
                // using json library to parse the response
                parser = new JSONParser();
                json = (JSONObject) parser.parse(responseString);
                JSONObject coord = (JSONObject) json.get("coord");
                lon = coord.get("lon").toString();
                lat = coord.get("lat").toString();
                JSONArray weather = (JSONArray) json.get("weather");
                JSONObject weather0 = (JSONObject) weather.get(0);
                if (weather0.get("id") == null) {
                    id = "null";
                } else {
                    id = weather0.get("id").toString();
                }
                if (weather0.get("main") == null) {
                    main = "null";
                } else {
                    main = weather0.get("main").toString();
                }
                if (weather0.get("description") == null) {
                    description = "null";
                } else {
                    description = weather0.get("description").toString();
                }
                if (weather0.get("icon") == null) {
                    icon = "null";
                } else {
                    icon = weather0.get("icon").toString();
                }
                JSONObject main_obj = (JSONObject) json.get("main");
                if (main_obj.get("temp") == null) {
                    temp = "null";
                } else {
                    temp = main_obj.get("temp").toString();
                }
                if (main_obj.get("feels_like") == null) {
                    feels_like = "null";
                } else {
                    feels_like = main_obj.get("feels_like").toString();
                }
                if (main_obj.get("temp_min") == null) {
                    temp_min = "null";
                } else {
                    temp_min = main_obj.get("temp_min").toString();
                }
                if (main_obj.get("temp_max") == null) {
                    temp_max = "null";
                } else {
                    temp_max = main_obj.get("temp_max").toString();
                }
                if (main_obj.get("pressure") == null) {
                    pressure = "null";
                } else {
                    pressure = main_obj.get("pressure").toString();
                }
                if (main_obj.get("humidity") == null) {
                    humidity = "null";
                } else {
                    humidity = main_obj.get("humidity").toString();
                }
                if (json.get("visibility") == null) {
                    visibility = "null";
                } else {
                    visibility = json.get("visibility").toString();
                }
                JSONObject wind = (JSONObject) json.get("wind");
                if (wind.get("speed") == null) {
                    wind_speed = "0";
                } else {
                    wind_speed = wind.get("speed").toString();
                }
                if (wind.get("deg") == null) {
                    wind_deg = "0";
                } else {
                    wind_deg = wind.get("deg").toString();
                }
                if (wind.get("gust") == null) {
                    gust = "0";
                } else {
                    gust = wind.get("gust").toString();
                }
                JSONObject clouds = (JSONObject) json.get("clouds");
                if (clouds.get("all") == null) {
                    clouds_all = "0";
                } else {
                    clouds_all = clouds.get("all").toString();
                }
                JSONObject sys = (JSONObject) json.get("sys");
                if (sys.get("sunrise") == null) {
                    sunrise = "null";
                } else {
                    sunrise = sys.get("sunrise").toString();
                }
                if (sys.get("sunset") == null) {
                    sunset = "null";
                } else {
                    sunset = sys.get("sunset").toString();
                }
                if (json.get("timezone") == null) {
                    timezone = "null";
                } else {
                    timezone = json.get("timezone").toString();
                }

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
                // System.out.println("clouds_all: " + clouds_all);
                // System.out.println("sunrise: " + sunrise);
                // System.out.println("sunset: " + sunset);
                // System.out.println("timezone: " + timezone);

                boolean flag2 = cc_db.saveCurrent_Conditions(cc);

                if (flag2 == false) {
                    System.out.println("Error in saving the data");
                }

                // Close the connection
                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
                // Handle other exceptions here
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return cc;

        }
    }

    public List<Current_Conditions> return_current_conditions() {
        database_layer.textfile_module.source.current_conditions cc_db = new database_layer.textfile_module.source.current_conditions();
        return cc_db.return_current_conditions();
    }

    // main for testing
    public static void main(String[] args) {
        current_weather cw = new current_weather();
        cw.getCurrentWeather("31.5656822",
                "74.3141829");
    }
}

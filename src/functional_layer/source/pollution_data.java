package functional_layer.source;

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

import functional_layer.pollution_data_interface;
import database_layer.textfile_module.source.pollution_data_save;

public class pollution_data implements pollution_data_interface {

    public polution_data_struct getPollutionData(String longi, String lati, String db_type) {
        // save pollution data
        pollution_data_save save_data = new pollution_data_save();
        save_data.remove_pollution_cache();
        List<polution_data_struct> data = save_data.get_all_data();
        // check if data is already saved for the location
        for (polution_data_struct d : data) {
            if (d.lat.equals(lati) && d.lon.equals(longi)) {
                return d;
            }
        }
        String apiKey = config.API_Key.getAPIKey();
        try {
            // Construct the URL for the API call
            String urlString = "https://api.openweathermap.org/data/2.5/air_pollution?lat=" +
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

            // get current time
            String time = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
            String hour = time.substring(0, 2);
            String minutes = time.substring(3, 5);

            // Print to test response
            // System.out.println(response.toString());
            // System.exit(0);

            // Extract the values from the response using json parser
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.toString());
            // Extract the values from the response

            String lat = lati;
            String lon = longi;
            String aqi;
            // Extract the aqi value
            JSONArray list = (JSONArray) json.get("list");
            JSONObject main = (JSONObject) list.get(0);
            JSONObject components = (JSONObject) main.get("components");
            if (components.get("aqi") == null) {
                aqi = "null";
            } else {
                aqi = components.get("aqi").toString();
            }
            // gases
            String co;
            if (components.get("co") == null) {
                co = "null";
            } else {
                co = components.get("co").toString();
            }
            String no;
            if (components.get("no") == null) {
                no = "null";
            } else {
                no = components.get("no").toString();
            }
            String no2;
            if (components.get("no2") == null) {
                no2 = "null";
            } else {
                no2 = components.get("no2").toString();
            }
            String o3;
            if (components.get("o3") == null) {
                o3 = "null";
            } else {
                o3 = components.get("o3").toString();
            }
            String so2;
            if (components.get("so2") == null) {
                so2 = "null";
            } else {
                so2 = components.get("so2").toString();
            }
            String pm2_5;
            if (components.get("pm2_5") == null) {
                pm2_5 = "null";
            } else {
                pm2_5 = components.get("pm2_5").toString();
            }
            String pm10;
            if (components.get("pm10") == null) {
                pm10 = "null";
            } else {
                pm10 = components.get("pm10").toString();
            }
            String nh3;
            if (components.get("nh3") == null) {
                nh3 = "null";
            } else {
                nh3 = components.get("nh3").toString();
            }
            // dt
            String dt;
            if (main.get("dt") == null) {
                dt = "null";
            } else {
                dt = main.get("dt").toString();
            }

            // print all values for testing
            /*
             * System.out.println("lat: " + lat);
             * System.out.println("lon: " + lon);
             * System.out.println("aqi: " + aqi);
             * System.out.println("co: " + co);
             * System.out.println("no: " + no);
             * System.out.println("no2: " + no2);
             * System.out.println("o3: " + o3);
             * System.out.println("so2: " + so2);
             * System.out.println("pm2_5: " + pm2_5);
             * System.out.println("pm10: " + pm10);
             * System.out.println("nh3: " + nh3);
             * System.out.println("dt: " + dt);
             */
            polution_data_struct pds = new polution_data_struct();
            pds.date = date;
            pds.month = month;
            pds.year = year;
            // location
            pds.lat = lat;
            pds.lon = lon;
            pds.aqi = aqi;
            // gases
            pds.co = co;
            pds.no = no;
            pds.no2 = no2;
            pds.o3 = o3;
            pds.so2 = so2;
            pds.pm2_5 = pm2_5;
            pds.pm10 = pm10;
            pds.nh3 = nh3;
            // dt
            pds.dt = dt;

            pds.hour = hour;
            pds.minutes = minutes;

            // Close the connection
            connection.disconnect();
            // save the data
            save_data.savePollutionData(pds);

            return pds;

        } catch (IOException e) {
            e.printStackTrace();
            // return null if an error occurs
            // return null;
        } catch (ParseException e) {
            e.printStackTrace();
            // return null if an error occurs
            // return null;
        }
        return null;
    }

    public List<polution_data_struct> return_stored_pop_data(String db_type) {
        pollution_data_save save_data = new pollution_data_save();
        return save_data.get_all_data();
    }

    // main for testing only
    public static void main(String[] args) {
        pollution_data pd = new pollution_data();
        pd.getPollutionData("31.5656822", "74.3141829", "sql");
    }
}

package functional_layer.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import functional_layer.pollution_data_interface;
import database_layer.textfile_module.source.pollution_data_save;

public class pollution_data implements pollution_data_interface {
    private static String extractValue(String input, String start, String end) {
        int startIndex = input.indexOf(start) + start.length();
        int endIndex = input.indexOf(end, startIndex);
        return input.substring(startIndex, endIndex);
    }

    public polution_data_struct getPollutionData(String longi, String lati) {
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

            // Print to test response
            // System.out.println(response.toString());

            String lat = lati;
            String lon = longi;
            String aqi = extractValue(response.toString(), "\"aqi\":", "}");
            // gases
            String co = extractValue(response.toString(), "\"co\":", ",");
            String no = extractValue(response.toString(), "\"no\":", ",");
            String no2 = extractValue(response.toString(), "\"no2\":", ",");
            String o3 = extractValue(response.toString(), "\"o3\":", ",");
            String so2 = extractValue(response.toString(), "\"so2\":", ",");
            String pm2_5 = extractValue(response.toString(), "\"pm2_5\":", ",");
            String pm10 = extractValue(response.toString(), "\"pm10\":", ",");
            String nh3 = extractValue(response.toString(), "\"nh3\":", "}");
            // dt
            String dt = extractValue(response.toString(), "\"dt\":", "}");

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

            // Close the connection
            connection.disconnect();
            // save the data
            save_data.savePollutionData(pds);

            return pds;

        } catch (IOException e) {
            e.printStackTrace();
            // return null if an error occurs
            // return null;
        }
        return null;
    }

    // main for testing only
    public static void main(String[] args) {
        pollution_data pd = new pollution_data();
        pd.getPollutionData("130", "21");
    }
}

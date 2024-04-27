package database_layer.textfile_module.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import database_layer.textfile_module.pollution_data_save_interface;
import functional_layer.pollution_data_interface.polution_data_struct;

public class pollution_data_save implements pollution_data_save_interface {

    public boolean savePollutionData(polution_data_struct struct) {
        // save into Pollution.txt file
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(
                            "assets\\\\text_database\\\\Pollution.txt",
                            true));
            writer.write(struct.date + ", " + struct.month + ", " + struct.year + ", " + struct.lat + ", " + struct.lon
                    + ", " + struct.aqi + ", " + struct.dt + ", " + struct.nh3
                    + ", " + struct.co
                    + ", " + struct.no + ", " + struct.no2 + ", " + struct.o3 + ", " + struct.so2 + ", " + struct.pm2_5
                    + ", " + struct.pm10
                    + ", " + struct.hour + ", " + struct.minutes);
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<polution_data_struct> get_all_data() {
        List<polution_data_struct> return_data = new java.util.ArrayList<polution_data_struct>();
        // read file and return all data just as it was saved
        String file_path = "assets\\\\text_database\\\\Pollution.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by comma to extract individual fields
                String[] parts = line.split(", ");
                // Create a new pollution data structure and populate it
                polution_data_struct data = new polution_data_struct();
                data.date = parts[0];
                data.month = parts[1];
                data.year = parts[2];
                data.lat = parts[3];
                data.lon = parts[4];
                data.aqi = parts[5];
                data.dt = parts[6];
                data.nh3 = parts[7];
                data.co = parts[8];
                data.no = parts[9];
                data.no2 = parts[10];
                data.o3 = parts[11];
                data.so2 = parts[12];
                data.pm2_5 = parts[13];
                data.pm10 = parts[14];
                data.hour = parts[15];
                data.minutes = parts[16];

                // Add the populated data to the return list
                return_data.add(data);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return return_data;
    }

    public void remove_pollution_cache() {
        // get current date, month and year
        String dateday = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
        String current_date = dateday.substring(0, 2);
        String current_month = dateday.substring(3, 5);
        String current_year = dateday.substring(6, 10);
        List<polution_data_struct> poll_data = get_all_data();
        // check if date, month or year are for yesterday or earlier
        for (int i = 0; i < poll_data.size(); i++) {
            if (Integer.parseInt(poll_data.get(i).year) < Integer.parseInt(current_year)) {
                poll_data.remove(i);
            } else if (Integer.parseInt(poll_data.get(i).month) < Integer.parseInt(current_month)) {
                poll_data.remove(i);
            } else if (Integer.parseInt(poll_data.get(i).date) < Integer.parseInt(current_date)) {
                poll_data.remove(i);
            }
        }
        // save the remaining data by removing all data in file first
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(
                            "assets\\\\text_database\\\\Pollution.txt",
                            false));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // save the remaining data
        for (int i = 0; i < poll_data.size(); i++) {
            savePollutionData(poll_data.get(i));
        }
    }

}

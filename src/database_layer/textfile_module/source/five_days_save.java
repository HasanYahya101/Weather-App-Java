package database_layer.textfile_module.source;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

import database_layer.textfile_module.five_days_interface_save;
import functional_layer.five_days_forcast_interface.five_days_data;
import functional_layer.five_days_forcast_interface.five_days_struct;

import java.util.List;

public class five_days_save implements five_days_interface_save {
    public boolean save_Five_Days(five_days_data fdd) {
        boolean flag = false;
        // save into Current_Conditions.txt file
        String data_to_be_inserted = "";
        data_to_be_inserted += fdd.date + ",";
        data_to_be_inserted += fdd.month + ",";
        data_to_be_inserted += fdd.year + ",";
        data_to_be_inserted += fdd.lat + ",";
        data_to_be_inserted += fdd.lon + ","; // all data outside list
        // adding list data for all lists
        for (int i = 0; i < fdd.list.size(); i++) {
            data_to_be_inserted += fdd.list.get(i).dt + ",";
            data_to_be_inserted += fdd.list.get(i).temp + ",";
            data_to_be_inserted += fdd.list.get(i).feels_like + ",";
            data_to_be_inserted += fdd.list.get(i).temp_min + ",";
            data_to_be_inserted += fdd.list.get(i).temp_max + ",";
            data_to_be_inserted += fdd.list.get(i).pressure + ",";
            data_to_be_inserted += fdd.list.get(i).humidity + ",";
            data_to_be_inserted += fdd.list.get(i).weather + ",";
            data_to_be_inserted += fdd.list.get(i).icon + ",";
            data_to_be_inserted += fdd.list.get(i).visibility + ",";
            data_to_be_inserted += fdd.list.get(i).wind_speed + ",";
            data_to_be_inserted += fdd.list.get(i).wind_deg + ",";
            data_to_be_inserted += fdd.list.get(i).gust + ",";
            data_to_be_inserted += fdd.list.get(i).clouds_all + ",";
            data_to_be_inserted += fdd.list.get(i).sunrise + ",";
            data_to_be_inserted += fdd.list.get(i).sunset + ",";
            data_to_be_inserted += fdd.list.get(i).dt_text + ",";
        }

        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(
                            "assets\\\\text_database\\\\Five_Days.txt",
                            true));
            writer.write(data_to_be_inserted);
            writer.newLine();
            writer.close();
            flag = true;

            return flag;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean save_Five_Days(List<five_days_data> fdd) {
        boolean flag = false;
        for (int i = 0; i < fdd.size(); i++) {
            flag = save_Five_Days(fdd.get(i));
        }
        return flag;
    }

    public List<five_days_data> read_Five_Days() {
        List<five_days_data> fdd = new java.util.ArrayList<five_days_data>();
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(
                            "assets\\\\text_database\\\\Five_Days.txt"));
            String line = reader.readLine();
            // five_days_struct fdd_struct = null;
            while (line != null && line.length() > 0) {
                String[] data = line.split(",");
                five_days_data fdd_temp = new five_days_data();
                fdd_temp.date = data[0];
                fdd_temp.month = data[1];
                fdd_temp.year = data[2];
                fdd_temp.lat = data[3];
                fdd_temp.lon = data[4];
                fdd_temp.list = new java.util.ArrayList<five_days_struct>();
                for (int i = 5; i < data.length; i += 17) {
                    // List<five_days_struct> list;
                    five_days_struct fdd_struct = new five_days_struct();
                    fdd_struct.dt = data[i];
                    fdd_struct.temp = data[i + 1];
                    fdd_struct.feels_like = data[i + 2];
                    fdd_struct.temp_min = data[i + 3];
                    fdd_struct.temp_max = data[i + 4];
                    fdd_struct.pressure = data[i + 5];
                    fdd_struct.humidity = data[i + 6];
                    fdd_struct.weather = data[i + 7];
                    fdd_struct.icon = data[i + 8];
                    fdd_struct.visibility = data[i + 9];
                    fdd_struct.wind_speed = data[i + 10];
                    fdd_struct.wind_deg = data[i + 11];
                    fdd_struct.gust = data[i + 12];
                    fdd_struct.clouds_all = data[i + 13];
                    fdd_struct.sunrise = data[i + 14];
                    fdd_struct.sunset = data[i + 15];
                    fdd_struct.dt_text = data[i + 16];
                    fdd_temp.list.add(fdd_struct);
                }
                fdd.add(fdd_temp);
                // print fdd size
                System.out.println("fdd size: " + fdd.size());
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fdd;
    }

    public void delete_cache() {
        List<five_days_data> fdd = read_Five_Days();
        // get current info
        int currentdate = java.time.LocalDate.now().getDayOfMonth();
        int currentmonth = java.time.LocalDate.now().getMonthValue();
        int currentyear = java.time.LocalDate.now().getYear();
        // remove all data with date less than current date
        for (int i = fdd.size() - 1; i >= 0; i--) {
            int date = Integer.parseInt(fdd.get(i).date);
            int month = Integer.parseInt(fdd.get(i).month);
            int year = Integer.parseInt(fdd.get(i).year);
            if (year < currentyear || (year == currentyear
                    && (month < currentmonth || (month == currentmonth && date < currentdate)))) {
                fdd.remove(i);
            }
        }
        // save the new data but clear the file first
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(
                            "assets\\\\text_database\\\\Five_Days.txt"));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        save_Five_Days(fdd);
    }

    // main just for testing
    public static void main(String[] args) {
        five_days_save fdd = new five_days_save();
        // get save data and print it
        List<five_days_data> fdd_data = fdd.read_Five_Days();
        // print sie of list
        System.out.println("list data size: " + fdd_data.size());
        for (int i = 0; i < fdd_data.size(); i++) {
            five_days_data current_list = fdd_data.get(i);
            System.out.println(current_list.date);
            System.out.println(current_list.month);
            System.out.println(current_list.year);
            System.out.println(current_list.lat);
            System.out.println(current_list.lon);
            // print size of list
            System.out.println("list size:" + current_list.list.size());
            for (int j = 0; j < current_list.list.size(); j++) {
                five_days_struct current_struct = current_list.list.get(j);
                System.out.println(current_struct.dt);
                System.out.println(current_struct.temp);
                System.out.println(current_struct.feels_like);
                System.out.println(current_struct.temp_min);
                System.out.println(current_struct.temp_max);
                System.out.println(current_struct.pressure);
                System.out.println(current_struct.humidity);
                System.out.println(current_struct.weather);
                System.out.println(current_struct.icon);
                System.out.println(current_struct.visibility);
                System.out.println(current_struct.wind_speed);
                System.out.println(current_struct.wind_deg);
                System.out.println(current_struct.gust);
                System.out.println(current_struct.clouds_all);
                System.out.println(current_struct.sunrise);
                System.out.println(current_struct.sunset);
                System.out.println(current_struct.dt_text);
            }
        }
    }
}
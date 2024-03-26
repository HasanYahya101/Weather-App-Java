package database_layer.textfile_module.source;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import database_layer.textfile_module.current_conditions_interface;
import functional_layer.current_weather_interface.Current_Conditions;

public class current_conditions implements current_conditions_interface {
    public boolean saveCurrent_Conditions(Current_Conditions cc) {
        String lon = cc.lon;
        String lat = cc.lat;
        String id = cc.id;
        String main = cc.main;
        String description = cc.description;
        String icon = cc.icon;
        String temp = cc.temp;
        String feels_like = cc.feels_like;
        String temp_min = cc.temp_min;
        String temp_max = cc.temp_max;
        String pressure = cc.pressure;
        String humidity = cc.humidity;
        String visibility = cc.visibility;
        String wind_speed = cc.wind_speed;
        String wind_deg = cc.wind_deg;
        String gust = cc.gust;
        String rain_1hr = cc.rain_1hr;
        String clouds_all = cc.clouds_all;
        String sunrise = cc.sunrise;
        String sunset = cc.sunset;
        String timezone = cc.timezone;
        String date = cc.date;
        String month = cc.month;
        String year = cc.year;
        // save into Current_Conditions.txt file
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(
                            "assets\\\\text_database\\\\Current_Conditions.txt",
                            true));
            writer.write(lon + "," + lat + "," + id + "," + main + "," + description + "," + icon + "," + temp + ","
                    + feels_like + "," + temp_min + "," + temp_max + "," + pressure + "," + humidity + "," + visibility
                    + "," + wind_speed + "," + wind_deg + "," + gust + "," + rain_1hr + "," + clouds_all + "," + sunrise
                    + "," + sunset + "," + timezone + "," + date + "," + month + "," + year);
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Current_Conditions> return_current_conditions() {
        Current_Conditions cc = null;
        List<Current_Conditions> cc_list = null;
        // if Current_Conditions.txt file is empty
        if (new java.io.File("assets\\\\text_database\\\\Current_Conditions.txt").length() == 0) {
            return cc_list;
        } else {
            cc_list = new java.util.ArrayList<Current_Conditions>();
            // read from Current_Conditions.txt file
            try {
                java.io.File file = new java.io.File("assets\\\\text_database\\\\Current_Conditions.txt");
                java.util.Scanner input = new java.util.Scanner(file);
                while (input.hasNext()) {
                    String line = input.nextLine();
                    String[] data = line.split(",");
                    cc = new Current_Conditions();
                    cc.lon = data[0];
                    cc.lat = data[1];
                    cc.id = data[2];
                    cc.main = data[3];
                    cc.description = data[4];
                    cc.icon = data[5];
                    cc.temp = data[6];
                    cc.feels_like = data[7];
                    cc.temp_min = data[8];
                    cc.temp_max = data[9];
                    cc.pressure = data[10];
                    cc.humidity = data[11];
                    cc.visibility = data[12];
                    cc.wind_speed = data[13];
                    cc.wind_deg = data[14];
                    cc.gust = data[15];
                    cc.rain_1hr = data[16];
                    cc.clouds_all = data[17];
                    cc.sunrise = data[18];
                    cc.sunset = data[19];
                    cc.timezone = data[20];
                    cc.date = data[21];
                    cc.month = data[22];
                    cc.year = data[23];

                    cc_list.add(cc);
                }
                input.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return cc_list;
        }
    }

    public void remove_prev_cache() {
        List<Current_Conditions> cc_list = return_current_conditions();
        if (cc_list.size() == 0 || cc_list == null) {
            return;
        }
        // remove all dates data from cc_list
        for (int i = 0; i < cc_list.size(); i++) {
            int date = Integer.parseInt(cc_list.get(i).date);
            int month = Integer.parseInt(cc_list.get(i).month);
            int year = Integer.parseInt(cc_list.get(i).year);
            // get current date
            int currentdate = java.time.LocalDate.now().getDayOfMonth();
            int currentmonth = java.time.LocalDate.now().getMonthValue();
            int currentyear = java.time.LocalDate.now().getYear();
            if (year < currentyear) {
                cc_list.remove(i);
                i--;
            } else if (year == currentyear && month < currentmonth) {
                cc_list.remove(i);
                i--;
            } else if (year == currentyear && month == currentmonth && date < currentdate) {
                cc_list.remove(i);
                i--;
            }
        }
        // clear the Current_Conditions.txt file
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(
                            "assets\\\\text_database\\\\Current_Conditions.txt"));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // save the remaining data into Current_Conditions.txt file
        for (int i = 0; i < cc_list.size(); i++) {
            saveCurrent_Conditions(cc_list.get(i));
        }
    }
}
package database_layer.textfile_module.source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import database_layer.textfile_module.location_save_interface;

public class location_save implements location_save_interface {
    public boolean saveLocation_Names(String city, String country, String country_code, String latitude,
            String longitude, String date, String month, String year, String hour, String minutes) {
        // save into Locations.txt file
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(
                            "databases\\\\text_database\\\\Locations.txt",
                            true));
            writer.write(city + ", " + country + ", " + country_code + ", " + latitude + ", " + longitude + ", "
                    + date + ", " + month + ", " + year + ", " + hour + ", " + minutes);
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Locations> getLocations() {
        List<Locations> locations = new java.util.ArrayList<Locations>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader("databases\\\\text_database\\\\Locations.txt"))) {
            String line;
            Locations loc = null;
            while ((line = reader.readLine()) != null) {
                String city = line.split(", ")[0];
                String country = line.split(", ")[1];
                String country_code = line.split(", ")[2];
                String latitude = line.split(", ")[3];
                String longitude = line.split(", ")[4];
                String date = line.split(", ")[5];
                String month = line.split(", ")[6];
                String year = line.split(", ")[7];
                String hour = line.split(", ")[8];
                String minutes = line.split(", ")[9];
                loc = new Locations();
                loc.city = city;
                loc.country = country;
                loc.country_code = country_code;
                loc.latitude = latitude;
                loc.longitude = longitude;
                loc.Day = date;
                loc.Month = month;
                loc.Year = year;
                loc.Hour = hour;
                loc.Minutes = minutes;
                // add data to list
                locations.add(loc);
                // print all locations
                // System.out.println(city + ", " + country + ", " + country_code + ", " +
                // latitude + ", " + longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locations;
    }

    // main for testing only
    public static void main(String[] args) {
        location_save db_text_layer = new location_save();
        List<Locations> locations = db_text_layer.getLocations();
        // get locations and print them
        for (Locations loc : locations) {
            System.out.println(loc.city + ", " + loc.country + ", " + loc.country_code + ", " + loc.latitude + ", "
                    + loc.longitude);
        }
    }
}

package database_layer.textfile_module.source;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import database_layer.textfile_module.location_save_interface;

public class location_save implements location_save_interface {
    public boolean saveLocation_Names(String city, String country, String country_code, String latitude,
            String longitude) {
        // save into Locations.txt file
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(
                            "assets\\\\text_database\\\\Locations.txt",
                            true));
            writer.write(city + ", " + country + ", " + country_code + ", " + latitude + ", " + longitude);
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

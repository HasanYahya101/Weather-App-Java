package database_layer.sql_module;

import java.util.List;

import database_layer.textfile_module.location_save_interface.Locations;

public interface location_save_interface {
    public abstract boolean saveLocation_Names(String city, String country, String country_code, String latitude,
            String longitude, String date, String month, String year, String hour, String minutes);

    public abstract List<Locations> getLocations();
}

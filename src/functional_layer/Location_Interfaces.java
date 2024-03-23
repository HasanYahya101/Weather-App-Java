package functional_layer;

import database_layer.textfile_module.location_save_interface;

class Locations {
    public String city;
    public String country;
    public String country_code;
    public String latitude;
    public String longitude;
}

public interface Location_Interfaces {
    // lati: latitude, longi: longitude which are parameters in API call.
    public boolean addLocation_Coordinates(String lati, String longi);

    public boolean addLocation_Names(String city, String country);

    public java.util.List<location_save_interface.Locations> displayLocs();
}
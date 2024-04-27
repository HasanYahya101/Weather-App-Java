package functional_layer;

import database_layer.textfile_module.location_save_interface;

class Locations {
    public String city;
    public String country;
    public String country_code;
    public String latitude;
    public String longitude;
    public String Day;
    public String Month;
    public String Year;
    public String Hour;
    public String Minutes;
}

public interface Location_Interfaces {
    // lati: latitude, longi: longitude which are parameters in API call.
    public abstract boolean addLocation_Coordinates(String lati, String longi);

    public abstract boolean addLocation_Names(String city, String country);

    public abstract java.util.List<location_save_interface.Locations> displayLocs();
}
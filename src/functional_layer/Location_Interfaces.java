package functional_layer;

import java.util.List;

public interface Location_Interfaces {
    // lati: latitude, longi: longitude which are parameters in API call.
    public boolean addLocation_Coordinates(String lati, String longi);

    public boolean addLocation_Names(String city, String country);

    public List<String> displayLocs();
}
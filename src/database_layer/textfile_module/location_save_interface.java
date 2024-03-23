package database_layer.textfile_module;

import java.util.List;

public interface location_save_interface {

    class Locations {
        public String city;
        public String country;
        public String country_code;
        public String latitude;
        public String longitude;
    }

    public abstract boolean saveLocation_Names(String city, String country, String country_code, String latitude,
            String longitude);

    public abstract List<Locations> getLocations();
}

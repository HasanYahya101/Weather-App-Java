package database_layer.textfile_module;

import java.util.List;

public interface location_save_interface {

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

    public abstract boolean saveLocation_Names(String city, String country, String country_code, String latitude,
            String longitude, String date, String month, String year, String hour, String minutes);

    public abstract List<Locations> getLocations();
}

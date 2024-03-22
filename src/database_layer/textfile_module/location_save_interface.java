package database_layer.textfile_module;

public interface location_save_interface {
    public abstract boolean saveLocation_Names(String city, String country, String country_code, String latitude,
            String longitude);
}

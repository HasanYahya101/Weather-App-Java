package database_layer.sql_module;

import java.util.List;

import functional_layer.five_days_forcast_interface.five_days_data;

public interface five_days_interface_save {
    public abstract boolean save_Five_Days(five_days_data fdd);

    public abstract boolean save_Five_Days(List<five_days_data> fdd);

    public abstract List<five_days_data> read_Five_Days();

    public abstract void delete_cache();
}

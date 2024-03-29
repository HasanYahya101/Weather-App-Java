package database_layer.textfile_module;

import java.util.List;

import functional_layer.pollution_data_interface.polution_data_struct;;

public interface pollution_data_save_interface {
    public abstract boolean savePollutionData(polution_data_struct struct);

    public abstract List<polution_data_struct> get_all_data();
}

package database_layer.sql_module;

import java.util.List;

import functional_layer.current_weather_interface.Current_Conditions;

public interface current_conditions_interface {
    public abstract boolean saveCurrent_Conditions(Current_Conditions cc);

    public abstract List<Current_Conditions> return_current_conditions();

    public abstract void remove_prev_cache();
}

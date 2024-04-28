package functional_layer;

import java.util.List;

public interface current_weather_interface {

    public class Current_Conditions {
        public String lon;
        public String lat;
        public String id;
        public String main;
        public String description;
        public String icon;
        public String temp;
        public String feels_like;
        public String temp_min;
        public String temp_max;
        public String pressure;
        public String humidity;
        public String visibility;
        public String wind_speed;
        public String wind_deg;
        public String gust;
        public String clouds_all;
        public String sunrise;
        public String sunset;
        public String timezone;
        public String date;
        public String month;
        public String year;
        public String hour;
        public String minutes;
    }

    public abstract Current_Conditions getCurrentWeather(String lati, String longi, String db_type);

    public abstract List<Current_Conditions> return_current_conditions(String db_type);
}

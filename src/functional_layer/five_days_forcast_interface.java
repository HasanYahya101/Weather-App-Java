package functional_layer;

import java.util.List;

public interface five_days_forcast_interface {
    class five_days_data {
        public String lat;
        public String lon;
        public String date;
        public String month;
        public String year;
        public String hour;
        public String minutes;
        public List<five_days_struct> list;
    }

    class five_days_struct {
        public String dt;
        public String temp;
        public String feels_like;
        public String temp_min;
        public String temp_max;
        public String pressure;
        public String humidity;
        public String weather;
        public String icon;
        public String visibility;
        public String wind_speed;
        public String wind_deg;
        public String gust;
        public String clouds_all;
        public String sunrise;
        public String sunset;
        public String dt_text;
    }

    public abstract List<five_days_data> get5DaysstoredData(String db_type);

    public abstract five_days_data get5DaysForcast(String lati, String longi, String db_type);
}

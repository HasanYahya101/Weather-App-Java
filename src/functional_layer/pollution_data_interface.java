package functional_layer;

import java.util.List;

public interface pollution_data_interface {

    public class polution_data_struct {
        public String date;
        public String month;
        public String year;
        // location
        public String lat;
        public String lon;
        public String aqi;
        // gases
        public String co;
        public String no;
        public String no2;
        public String o3;
        public String so2;
        public String pm2_5;
        public String pm10;
        public String nh3;
        // dt
        public String dt;
    }

    public abstract List<polution_data_struct> return_stored_pop_data();

    public abstract polution_data_struct getPollutionData(String longi, String lati);

}

package database_layer.sql_module.source;

import java.util.List;

// Sqlite driver imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import functional_layer.five_days_forcast_interface.five_days_data;
import functional_layer.five_days_forcast_interface.five_days_struct;

public class five_days_save implements database_layer.sql_module.five_days_interface_save {
    public boolean save_Five_Days(five_days_data fdd) {
        boolean flag = true;
        // connect to sqlite db
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:databases\\\\sql_database\\\\Weather.db");
            if (conn != null) {
                // System.out.println("Connection to Weather.db has been established.");
                // Initialise tables
                table_initialization table = new table_initialization();
                table.create_tables();
                // initialize stmt
                Statement stmt = conn.createStatement();
                String date = fdd.date;
                String month = fdd.month;
                String year = fdd.year;
                String hour = fdd.hour;
                String minutes = fdd.minutes;
                String lat = fdd.lat;
                String lon = fdd.lon;

                // get max id
                String sql = "SELECT MAX(ID) FROM Forecast;";
                stmt.execute(sql);
                int id = stmt.getResultSet().getInt(1) + 1;
                for (int j = 0; j < fdd.list.size(); j++) {
                    // get the list
                    five_days_struct list = fdd.list.get(j);
                    // get the values
                    String dt = list.dt;
                    String temp = list.temp;
                    String feels_like = list.feels_like;
                    String temp_min = list.temp_min;
                    String temp_max = list.temp_max;
                    String pressure = list.pressure;
                    String humidity = list.humidity;
                    String weather = list.weather;
                    String icon = list.icon;
                    String visibility = list.visibility;
                    String wind_speed = list.wind_speed;
                    String wind_deg = list.wind_deg;
                    String gust = list.gust;
                    String clouds_all = list.clouds_all;
                    String sunrise = list.sunrise;
                    String sunset = list.sunset;
                    String dt_text = list.dt_text;

                    // insert into table
                    sql = "INSERT INTO Forecast (id, lon, lat, date, month, year, hour, minutes, dt, temp, feels_like, temp_min, temp_max, pressure, humidity, weather, icon, visibility, wind_speed, wind_deg, gust, clouds_all, sunrise, sunset, dt_text) VALUES ("
                            + id + ", '" + lon + "', '" + lat + "', '" + date + "', '" + month + "', '" + year + "', '"
                            + hour + "', '" + minutes + "', '" + dt + "', '" + temp + "', '" + feels_like + "', '"
                            + temp_min
                            + "', '" + temp_max + "', '" + pressure + "', '" + humidity + "', '" + weather + "', '"
                            + icon
                            + "', '" + visibility + "', '" + wind_speed + "', '" + wind_deg + "', '" + gust + "', '"
                            + clouds_all + "', '" + sunrise + "', '" + sunset + "', '" + dt_text + "');";
                    stmt.execute(sql);
                }
                // close connection
                conn.close();

            }
        } catch (SQLException e) {
            flag = false;
            // close connection if open
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
            }
        }
        return flag;
    }

    public boolean save_Five_Days(List<five_days_data> fdt) {
        boolean flag = true;
        // connect to sqlite db
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:databases\\\\sql_database\\\\Weather.db");
            if (conn != null) {
                // System.out.println("Connection to Weather.db has been established.");
                // Initialise tables
                table_initialization table = new table_initialization();
                table.create_tables();
                // initialize stmt
                Statement stmt = conn.createStatement();
                for (int i = 0; i < fdt.size(); i++) {
                    // get the data
                    five_days_data fdd = fdt.get(i);
                    String date = fdd.date;
                    String month = fdd.month;
                    String year = fdd.year;
                    String hour = fdd.hour;
                    String minutes = fdd.minutes;
                    String lat = fdd.lat;
                    String lon = fdd.lon;

                    // get max id
                    String sql = "SELECT MAX(ID) FROM Forecast;";
                    stmt.execute(sql);
                    int id = stmt.getResultSet().getInt(1) + 1;
                    for (int j = 0; j < fdd.list.size(); j++) {
                        // get the list
                        five_days_struct list = fdd.list.get(j);
                        // get the values
                        String dt = list.dt;
                        String temp = list.temp;
                        String feels_like = list.feels_like;
                        String temp_min = list.temp_min;
                        String temp_max = list.temp_max;
                        String pressure = list.pressure;
                        String humidity = list.humidity;
                        String weather = list.weather;
                        String icon = list.icon;
                        String visibility = list.visibility;
                        String wind_speed = list.wind_speed;
                        String wind_deg = list.wind_deg;
                        String gust = list.gust;
                        String clouds_all = list.clouds_all;
                        String sunrise = list.sunrise;
                        String sunset = list.sunset;
                        String dt_text = list.dt_text;

                        // insert into table
                        sql = "INSERT INTO Forecast (id, lon, lat, date, month, year, hour, minutes, dt, temp, feels_like, temp_min, temp_max, pressure, humidity, weather, icon, visibility, wind_speed, wind_deg, gust, clouds_all, sunrise, sunset, dt_text) VALUES ("
                                + id + ", '" + lon + "', '" + lat + "', '" + date + "', '" + month + "', '" + year
                                + "', '"
                                + hour + "', '" + minutes + "', '" + dt + "', '" + temp + "', '" + feels_like + "', '"
                                + temp_min
                                + "', '" + temp_max + "', '" + pressure + "', '" + humidity + "', '" + weather + "', '"
                                + icon
                                + "', '" + visibility + "', '" + wind_speed + "', '" + wind_deg + "', '" + gust + "', '"
                                + clouds_all + "', '" + sunrise + "', '" + sunset + "', '" + dt_text + "');";
                        stmt.execute(sql);
                    }
                }
                // close connection
                conn.close();

            }
        } catch (SQLException e) {
            flag = false;
            // close connection if open
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
            }
        }
        return flag;
    }

    public List<five_days_data> read_Five_Days() {
        List<five_days_data> fdl = new java.util.ArrayList<five_days_data>();
        // get max id from table
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:databases\\\\sql_database\\\\Weather.db");
            if (conn != null) {
                // System.out.println("Connection to Weather.db has been established.");
                // Initialise tables
                table_initialization table = new table_initialization();
                table.create_tables();
                // initialize stmt
                Statement stmt = conn.createStatement();
                // get max id
                String sql = "SELECT MAX(ID) FROM Forecast;";
                stmt.execute(sql);
                int id = stmt.getResultSet().getInt(1);
                // Print id
                // System.out.println(id);
                if (id == 0) {
                    return fdl;
                } else {

                    // get the data
                    for (int i = 1; i <= id; i++) {
                        // check if given id exists
                        boolean exists = true;
                        Statement stmt1 = conn.createStatement();
                        sql = "SELECT * FROM Forecast WHERE ID = " + (i) + ";";
                        stmt1.execute(sql);
                        if (!stmt1.getResultSet().next()) {
                            exists = false;
                        }
                        if (!exists) {
                            continue;
                        }
                        // get the data
                        five_days_data fdd = new five_days_data();
                        fdd.list = new java.util.ArrayList<five_days_struct>();
                        // get the values
                        sql = "SELECT * FROM Forecast WHERE ID = " + (i) + ";";
                        Statement stmt3 = conn.createStatement();
                        stmt3.execute(sql);
                        String lat = null;
                        String lon = null;
                        String date = null;
                        String month = null;
                        String year = null;
                        String hour = null;
                        String minutes = null;
                        // loop over each tuple
                        while (stmt3.getResultSet().next() != false) {
                            fdd.lat = stmt3.getResultSet().getString("lat");
                            lat = fdd.lat;
                            fdd.lon = stmt3.getResultSet().getString("lon");
                            lon = fdd.lon;
                            fdd.date = stmt3.getResultSet().getString("date");
                            date = fdd.date;
                            fdd.month = stmt3.getResultSet().getString("month");
                            month = fdd.month;
                            fdd.year = stmt3.getResultSet().getString("year");
                            year = fdd.year;
                            fdd.hour = stmt3.getResultSet().getString("hour");
                            hour = fdd.hour;
                            fdd.minutes = stmt3.getResultSet().getString("minutes");
                            minutes = fdd.minutes;
                            // get the list
                            five_days_struct list = new five_days_struct();
                            list.dt = stmt3.getResultSet().getString("dt");
                            list.temp = stmt3.getResultSet().getString("temp");
                            list.feels_like = stmt3.getResultSet().getString("feels_like");
                            list.temp_min = stmt3.getResultSet().getString("temp_min");
                            list.temp_max = stmt3.getResultSet().getString("temp_max");
                            list.pressure = stmt3.getResultSet().getString("pressure");
                            list.humidity = stmt3.getResultSet().getString("humidity");
                            list.weather = stmt3.getResultSet().getString("weather");
                            list.icon = stmt3.getResultSet().getString("icon");
                            list.visibility = stmt3.getResultSet().getString("visibility");
                            list.wind_speed = stmt3.getResultSet().getString("wind_speed");
                            list.wind_deg = stmt3.getResultSet().getString("wind_deg");
                            list.gust = stmt3.getResultSet().getString("gust");
                            list.clouds_all = stmt3.getResultSet().getString("clouds_all");
                            list.sunrise = stmt3.getResultSet().getString("sunrise");
                            list.sunset = stmt3.getResultSet().getString("sunset");
                            list.dt_text = stmt3.getResultSet().getString("dt_text");
                            fdd.list.add(list);

                            // print all these values
                            System.out.println(lat + " " + lon + " " + date + " " + month + " " + year + " " + hour
                                    + " " + minutes + " " + list.dt + " " + list.temp + " " + list.feels_like + " "
                                    + list.temp_min + " " + list.temp_max + " " + list.pressure + " " + list.humidity
                                    + " "
                                    + list.weather + " " + list.icon + " " + list.visibility + " " + list.wind_speed
                                    + " "
                                    + list.wind_deg + " " + list.gust + " " + list.clouds_all + " " + list.sunrise + " "
                                    + list.sunset + " " + list.dt_text);
                        }

                        fdl.add(fdd);

                    }
                    // close connection
                    conn.close();
                }
            }
        } catch (SQLException e) {
            // close connection if open
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
            }
            return fdl;
        }
        return fdl;
    }

    public void delete_cache() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:databases\\\\sql_database\\\\Weather.db");
            if (conn != null) {
                // System.out.println("Connection to Weather.db has been established.");
                // Initialise tables
                table_initialization table = new table_initialization();
                table.create_tables();
                // initialize stmt
                Statement stmt = conn.createStatement();
                String current_date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
                String curr_date = current_date.substring(8, 10);
                String curr_month = current_date.substring(5, 7);
                String curr_year = current_date.substring(0, 4);
                // delete old tuples
                String sql = "DELETE FROM Forecast WHERE (year < " + curr_year + ") OR (year = " + curr_year
                        + " AND month < " + curr_month + ") OR (year = " + curr_year + " AND month = " + curr_month
                        + " AND date < " + curr_date + ");";
                stmt.execute(sql);
                // close connection
                conn.close();
            }
        } catch (SQLException e) {
            // close connection if open
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
            }
            return;
        }
    }

    // main for testing only
    public static void main(String[] args) {
        // save random data for testing
        five_days_data fdd = new five_days_data();
        fdd.list = new java.util.ArrayList<five_days_struct>();

        fdd.date = "01";
        fdd.month = "01";
        fdd.year = "2021";
        fdd.hour = "00";
        fdd.minutes = "00";
        fdd.lat = "0";
        fdd.lon = "0";
        fdd.list = new java.util.ArrayList<five_days_struct>();
        for (int i = 0; i < 5; i++) {
            five_days_struct list = new five_days_struct();
            list.dt = "1612136400";
            list.temp = "0";
            list.feels_like = "0";
            list.temp_min = "0";
            list.temp_max = "0";
            list.pressure = "0";
            list.humidity = "0";
            list.weather = "0";
            list.icon = "0";
            list.visibility = "0";
            list.wind_speed = "0";
            list.wind_deg = "0";
            list.gust = "0";
            list.clouds_all = "0";
            list.sunrise = "0";
            list.sunset = "0";
            list.dt_text = "0";
            fdd.list.add(list);
        }
        five_days_save f = new five_days_save();
        f.save_Five_Days(fdd);
        // now read data and print
        List<five_days_data> fdl = f.read_Five_Days();
        System.out.println(fdl.size());
        for (int i = 0; i < fdl.size(); i++) {
            five_days_data fdd1 = fdl.get(i);
            System.out.println(fdd1.date + " " + fdd1.month + " " + fdd1.year + " " +
                    fdd1.hour + " " + fdd1.minutes
                    + " " + fdd1.lat + " " + fdd1.lon);
            for (int j = 0; j < fdd1.list.size(); j++) {
                five_days_struct list = fdd1.list.get(j);
                System.out.println(list.dt + " " + list.temp + " " + list.feels_like + " " +
                        list.temp_min + " "
                        + list.temp_max + " " + list.pressure + " " + list.humidity + " " +
                        list.weather + " "
                        + list.icon + " " + list.visibility + " " + list.wind_speed + " " +
                        list.wind_deg + " "
                        + list.gust + " " + list.clouds_all + " " + list.sunrise + " " + list.sunset
                        + " "
                        + list.dt_text);
            }
        }

        // delete cache
    }
}

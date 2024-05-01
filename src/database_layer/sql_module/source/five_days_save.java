package database_layer.sql_module.source;

import java.util.ArrayList;
import java.util.List;

// Sqlite driver imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

    public List<five_days_data> read_Five_Days() {
        List<five_days_data> fdl = new java.util.ArrayList<five_days_data>();
        List<Integer> idList = new ArrayList<>();
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
                // get all distinct ids in Ascending order
                String sql = "SELECT DISTINCT ID FROM Forecast ORDER BY ID ASC;";
                stmt.execute(sql);
                ResultSet rs = stmt.executeQuery(sql);
                // iterate through the result set and count the rows
                int rowCount = 0;
                while (rs.next()) {
                    rowCount++;
                    int id = rs.getInt("ID");
                    // print id
                    // System.out.println("ID: " + id);
                    idList.add(id);

                }
                // print the number of rows
                // System.out.println("Rows: " + rowCount);
                if (rowCount == 0) {
                    // no values in the database
                    return fdl;
                }
                for (int i = 0; i < idList.size(); i++) {
                    int curr_id = idList.get(i);
                    five_days_data fdd = new five_days_data();
                    fdd.list = new ArrayList<five_days_struct>();

                    String sql_fetch_by_id = "SELECT lon, lat, date, month, " +
                            "year, hour, minutes, dt, temp, feels_like, temp_min, temp_max, pressure, humidity, weather, icon, visibility, wind_speed, wind_deg, gust, clouds_all, sunrise, sunset, dt_text FROM Forecast WHERE ID = "
                            + curr_id + ";";
                    // create a new statement
                    Statement smtt2 = conn.createStatement();
                    ResultSet rsData = smtt2.executeQuery(sql_fetch_by_id);

                    // System.out.println("Rows: " + rowCountData);
                    while (rsData.next()) {
                        five_days_struct fds = new five_days_struct();
                        // get fdd values
                        fdd.lon = rsData.getString("lon");
                        fdd.lat = rsData.getString("lat");
                        fdd.date = rsData.getString("date");
                        fdd.month = rsData.getString("month");
                        fdd.year = rsData.getString("year");
                        fdd.hour = rsData.getString("hour");
                        fdd.minutes = rsData.getString("minutes");
                        // get struct data
                        fds.dt = rsData.getString("dt");
                        fds.temp = rsData.getString("temp");
                        fds.feels_like = rsData.getString("feels_like");
                        fds.temp_min = rsData.getString("temp_min");
                        fds.temp_max = rsData.getString("temp_max");
                        fds.pressure = rsData.getString("pressure");
                        fds.humidity = rsData.getString("humidity");
                        fds.weather = rsData.getString("weather");
                        fds.icon = rsData.getString("icon");
                        fds.visibility = rsData.getString("visibility");
                        fds.wind_speed = rsData.getString("wind_speed");
                        fds.wind_deg = rsData.getString("wind_deg");
                        fds.gust = rsData.getString("gust");
                        fds.clouds_all = rsData.getString("clouds_all");
                        fds.sunrise = rsData.getString("sunrise");
                        fds.sunset = rsData.getString("sunset");
                        fds.dt_text = rsData.getString("dt_text");

                        // add to list
                        fdd.list.add(fds);
                    }
                    // add to list
                    fdl.add(fdd);
                }

                // close connection
                conn.close();
                return fdl;
            }
        } catch (SQLException e) {
            // print e
            System.out.println(e);
            // close connection if open
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                // print ex
                System.out.println(ex);
                return fdl;
            }
        } catch (Exception e) {
            // print e
            System.out.println(e);
            // close connection if open
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                // print ex
                System.out.println(ex);
                return fdl;
            }
        }
        return fdl;
    }

    // main for testing only
    public static void main(String[] args) {
        five_days_save f = new five_days_save();
        f.delete_cache();
    }
}

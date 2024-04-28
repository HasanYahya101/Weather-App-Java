package database_layer.sql_module.source;

import java.util.List;

// Sqlite driver imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import functional_layer.current_weather_interface.Current_Conditions;

public class current_conditions_save implements database_layer.sql_module.current_conditions_interface {
    public boolean saveCurrent_Conditions(Current_Conditions cc) {
        boolean flag = true;
        // connect to sqlite
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:assets\\\\sql_database\\\\Weather.db");
            if (conn != null) {
                // System.out.println("Connection to Weather.db has been established.");
                // Initialise tables
                table_initialization table = new table_initialization();
                table.create_tables();
                // initialize stmt
                Statement stmt = conn.createStatement();

                // Insert data into Current_Conditions
                String sql = "INSERT INTO Current_Conditions (id, lon, lat, main, description, icon, temp, feels_like, temp_min, temp_max, pressure, humidity, visibility, wind_speed, wind_deg, gust, clouds_all, sunrise, sunset, timezone, date, month, year, hour, minutes) VALUES ('"
                        + cc.id + "', '" + cc.lon + "', '" + cc.lat + "', '" + cc.main + "', '" + cc.description
                        + "', '"
                        + cc.icon + "', '" + cc.temp + "', '" + cc.feels_like + "', '" + cc.temp_min + "', '"
                        + cc.temp_max
                        + "', '" + cc.pressure + "', '" + cc.humidity + "', '" + cc.visibility + "', '" + cc.wind_speed
                        + "', '" + cc.wind_deg + "', '" + cc.gust + "', '" + cc.clouds_all + "', '" + cc.sunrise
                        + "', '"
                        + cc.sunset + "', '" + cc.timezone + "', '" + cc.date + "', '" + cc.month + "', '" + cc.year
                        + "', '"
                        + cc.hour + "', '" + cc.minutes + "');";
                stmt.execute(sql);

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

    public List<Current_Conditions> return_current_conditions() {
        List<Current_Conditions> return_data = new java.util.ArrayList<Current_Conditions>();
        // Connect to Weather.db on path assets\\\\sql_database\\\\Weather.db
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:assets\\\\sql_database\\\\Weather.db");
            if (conn != null) {
                // System.out.println("Connection to Weather.db has been established.");
                // Initialise tables
                table_initialization table = new table_initialization();
                table.create_tables();
                // initialize stmt
                Statement stmt = conn.createStatement();

                // Select all data from Current_Conditions
                String sql = "SELECT * FROM Current_Conditions;";
                java.sql.ResultSet rs = stmt.executeQuery(sql);

                // loop through the result set
                while (rs.next()) {
                    Current_Conditions cc = new Current_Conditions();
                    cc.id = rs.getString("id");
                    cc.lon = rs.getString("lon");
                    cc.lat = rs.getString("lat");
                    cc.main = rs.getString("main");
                    cc.description = rs.getString("description");
                    cc.icon = rs.getString("icon");
                    cc.temp = rs.getString("temp");
                    cc.feels_like = rs.getString("feels_like");
                    cc.temp_min = rs.getString("temp_min");
                    cc.temp_max = rs.getString("temp_max");
                    cc.pressure = rs.getString("pressure");
                    cc.humidity = rs.getString("humidity");
                    cc.visibility = rs.getString("visibility");
                    cc.wind_speed = rs.getString("wind_speed");
                    cc.wind_deg = rs.getString("wind_deg");
                    cc.gust = rs.getString("gust");
                    cc.clouds_all = rs.getString("clouds_all");
                    cc.sunrise = rs.getString("sunrise");
                    cc.sunset = rs.getString("sunset");
                    cc.timezone = rs.getString("timezone");
                    cc.date = rs.getString("date");
                    cc.month = rs.getString("month");
                    cc.year = rs.getString("year");
                    cc.hour = rs.getString("hour");
                    cc.minutes = rs.getString("minutes");
                    return_data.add(cc);
                }

                stmt.execute(sql);
                // close connection
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // close connection if open
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
            }
        }

        return return_data;
    }

    public void remove_prev_cache() {
        // Connect to Weather.db on path assets\\\\sql_database\\\\Weather.db
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:assets\\\\sql_database\\\\Weather.db");
            if (conn != null) {
                // System.out.println("Connection to Weather.db has been established.");
                // Initialise tables
                table_initialization table = new table_initialization();
                table.create_tables();
                // initialize stmt
                Statement stmt = conn.createStatement();

                /*
                 * remove data that has this (curr_year < year || (curr_year == year &&
                 * curr_month < month) || (curr_year == year && curr_month == month && curr_date
                 * < date))
                 */
                // Note that all these 3 vars are TEXT in SQLite tables
                String current_date = java.time.LocalDate.now().toString();
                String curr_date = current_date.split("-")[2];
                String curr_month = current_date.split("-")[1];
                String curr_year = current_date.split("-")[0];

                String sql = "DELETE FROM Current_Conditions WHERE (year < '" + curr_year + "') OR (year = '"
                        + curr_year
                        + "' AND month < '" + curr_month + "') OR (year = '" + curr_year + "' AND month = '"
                        + curr_month + "' AND date < '" + curr_date + "');";
                stmt.execute(sql);
                // close connection
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // close connection if open
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
            }
        }

    }

    // main for testing only
    public static void main(String[] args) {
        current_conditions_save ccs = new current_conditions_save();
        // Current_Conditions cc = new Current_Conditions();

        ccs.remove_prev_cache();

    }
}

package database_layer.sql_module.source;

import java.util.List;

import database_layer.sql_module.pollution_data_save_interface;
import functional_layer.pollution_data_interface.polution_data_struct;

// Sqlite driver imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class pollution_data_save implements pollution_data_save_interface {
    public boolean savePollutionData(polution_data_struct struct) {
        boolean flag = true;
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

                // Insert data into Pollution_Data
                String sql = "INSERT INTO Pollution_Data (date, month, year, lat, lon, aqi, dt, nh3, co, no, no2, o3, so2, pm2_5, pm10, hour, minutes) VALUES ('"
                        + struct.date + "', '" + struct.month + "', '" + struct.year + "', '" + struct.lat + "', '"
                        + struct.lon + "', '" + struct.aqi + "', '" + struct.dt + "', '" + struct.nh3 + "', '"
                        + struct.co + "', '" + struct.no + "', '" + struct.no2 + "', '" + struct.o3 + "', '"
                        + struct.so2
                        + "', '" + struct.pm2_5 + "', '" + struct.pm10 + "', '" + struct.hour + "', '" + struct.minutes
                        + "');";
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

    public List<polution_data_struct> get_all_data() {
        List<polution_data_struct> return_data = new java.util.ArrayList<polution_data_struct>();
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

                // Select all data from Pollution_Data
                String sql = "SELECT * FROM Pollution_Data;";
                java.sql.ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    // Create a new pollution data structure and populate it
                    polution_data_struct data = new polution_data_struct();
                    data.date = rs.getString("date");
                    data.month = rs.getString("month");
                    data.year = rs.getString("year");
                    data.lat = rs.getString("lat");
                    data.lon = rs.getString("lon");
                    data.aqi = rs.getString("aqi");
                    data.dt = rs.getString("dt");
                    data.nh3 = rs.getString("nh3");
                    data.co = rs.getString("co");
                    data.no = rs.getString("no");
                    data.no2 = rs.getString("no2");
                    data.o3 = rs.getString("o3");
                    data.so2 = rs.getString("so2");
                    data.pm2_5 = rs.getString("pm2_5");
                    data.pm10 = rs.getString("pm10");
                    data.hour = rs.getString("hour");
                    data.minutes = rs.getString("minutes");
                    return_data.add(data);
                }
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
                // deal with error
            }
        }
        return return_data;
    }

    public void remove_pollution_cache() {
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

                // get current date
                String current_date = java.time.LocalDate.now().toString();
                String curr_date = current_date.split("-")[2];
                String curr_month = current_date.split("-")[1];
                String curr_year = current_date.split("-")[0];

                /*
                 * remove data that has this (curr_year < year || (curr_year == year &&
                 * curr_month < month) || (curr_year == year && curr_month == month && curr_date
                 * < date))
                 */
                // Note that all these 3 vars are TEXT in SQLite tables
                String sql = "DELETE FROM Pollution_Data WHERE (year < '" + curr_year + "') OR (year = '" + curr_year
                        + "' AND month < '" + curr_month + "') OR (year = '" + curr_year + "' AND month = '"
                        + curr_month
                        + "' AND date < '" + curr_date + "');";

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
                // deal with error
            }
        }
    }

    // main for testing
    public static void main(String[] args) {
        pollution_data_save obj = new pollution_data_save();
        polution_data_struct data = new polution_data_struct();
        data.date = "1";
        data.month = "1";
        data.year = "2021";
        data.lat = "1";
        data.lon = "1";
        data.aqi = "1";
        data.dt = "1";
        data.nh3 = "1";
        data.co = "1";
        data.no = "1";
        data.no2 = "1";
        data.o3 = "1";
        data.so2 = "1";
        data.pm2_5 = "1";
        data.pm10 = "1";
        data.hour = "1";
        data.minutes = "1";
        obj.savePollutionData(data);
        // get all data and print it
        List<polution_data_struct> all_data = obj.get_all_data();
        for (polution_data_struct d : all_data) {
            System.out.println(d.date + " " + d.month + " " + d.year + " " + d.lat + " " + d.lon + " " + d.aqi + " "
                    + d.dt + " " + d.nh3 + " " + d.co + " " + d.no + " " + d.no2 + " " + d.o3 + " " + d.so2 + " "
                    + d.pm2_5 + " " + d.pm10 + " " + d.hour + " " + d.minutes);
        }
        // obj.remove_pollution_cache();
    }
}

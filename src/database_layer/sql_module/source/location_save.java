package database_layer.sql_module.source;

import java.util.List;

// Sqlite driver imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import database_layer.textfile_module.location_save_interface.Locations;

public class location_save implements database_layer.sql_module.location_save_interface {
    public boolean saveLocation_Names(String city, String country, String country_code, String latitude,
            String longitude, String date, String month, String year, String hour, String minutes) {
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

                // Insert data into Locations
                String sql = "INSERT INTO Locations (city, country, country_code, latitude, longitude, date, month, year, hour, minutes) VALUES ('"
                        + city + "', '" + country + "', '" + country_code + "', '" + latitude + "', '" + longitude
                        + "', '"
                        + date + "', '" + month + "', '" + year + "', '" + hour + "', '" + minutes + "');";
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            flag = false;
        }

        return flag;
    }

    public List<Locations> getLocations() {
        List<Locations> return_data = new java.util.ArrayList<Locations>();

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

                // Select data from Locations
                String sql = "SELECT * FROM Locations;";
                java.sql.ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Locations loc = new Locations();
                    loc.city = rs.getString("city");
                    loc.country = rs.getString("country");
                    loc.country_code = rs.getString("country_code");
                    loc.latitude = rs.getString("latitude");
                    loc.longitude = rs.getString("longitude");
                    loc.Day = rs.getString("date");
                    loc.Month = rs.getString("month");
                    loc.Year = rs.getString("year");
                    loc.Hour = rs.getString("hour");
                    loc.Minutes = rs.getString("minutes");
                    return_data.add(loc);
                }
            }
        } catch (SQLException e) {
            // System.out.println(e.getMessage());
        }

        return return_data;
    }
}

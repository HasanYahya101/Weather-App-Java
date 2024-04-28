package database_layer.sql_module.source;

import database_layer.sql_module.table_interface;

// Sqlite driver imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class table_initialization implements table_interface {
        public void create_tables() {
                // Connect to Weather.db on path assets\\\\sql_database\\\\Weather.db
                Connection conn = null;
                try {
                        conn = DriverManager.getConnection("jdbc:sqlite:assets\\\\sql_database\\\\Weather.db");
                        if (conn != null) {
                                // System.out.println("Connection to Weather.db has been established.");
                                // Create tables
                                Statement stmt = conn.createStatement();
                                // checf if all 4 tables already exist
                                ResultSet rs1 = stmt.executeQuery(
                                                "SELECT name FROM sqlite_master WHERE type='table' AND name='Current_Conditions';");
                                ResultSet rs2 = stmt
                                                .executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Locations';");
                                ResultSet rs3 = stmt
                                                .executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Pollution_Data';");
                                ResultSet rs4 = stmt
                                                .executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Forecast';");

                                if (rs1.next() && rs2.next() && rs3.next() && rs4.next()) {
                                        // All tables already exist.
                                        return;
                                }
                                // Create table Current_Conditions
                                String sql = "CREATE TABLE IF NOT EXISTS Current_Conditions (\n"
                                                + "    id INTEGER NOT NULL,\n"
                                                + "    lon TEXT NOT NULL,\n"
                                                + "    lat TEXT NOT NULL,\n"
                                                + "    main TEXT NOT NULL,\n"
                                                + "    description TEXT NOT NULL,\n"
                                                + "    icon TEXT NOT NULL,\n"
                                                + "    temp TEXT NOT NULL,\n"
                                                + "    feels_like TEXT NOT NULL,\n"
                                                + "    temp_min TEXT NOT NULL,\n"
                                                + "    temp_max TEXT NOT NULL,\n"
                                                + "    pressure TEXT NOT NULL,\n"
                                                + "    humidity TEXT NOT NULL,\n"
                                                + "    visibility TEXT NOT NULL,\n"
                                                + "    wind_speed TEXT NOT NULL,\n"
                                                + "    wind_deg TEXT NOT NULL,\n"
                                                + "    gust TEXT NOT NULL,\n"
                                                + "    clouds_all TEXT NOT NULL,\n"
                                                + "    sunrise TEXT NOT NULL,\n"
                                                + "    sunset TEXT NOT NULL,\n"
                                                + "    timezone TEXT NOT NULL,\n"
                                                + "    date TEXT NOT NULL,\n"
                                                + "    month TEXT NOT NULL,\n"
                                                + "    year TEXT NOT NULL,\n"
                                                + "    hour TEXT NOT NULL,\n"
                                                + "    minutes TEXT NOT NULL\n"
                                                + ");";
                                stmt.execute(sql);
                                // Create table Locations
                                sql = "CREATE TABLE IF NOT EXISTS Locations (\n"
                                                + "    city TEXT NOT NULL,\n"
                                                + "    country TEXT NOT NULL,\n"
                                                + "    lon TEXT NOT NULL,\n"
                                                + "    lat TEXT NOT NULL,\n"
                                                + "    country_code TEXT NOT NULL,\n"
                                                + "    Date Text NOT NULL,\n"
                                                + "    Month Text NOT NULL,\n"
                                                + "    Year Text NOT NULL,\n"
                                                + "    Hour Text NOT NULL,\n"
                                                + "    Minutes Text NOT NULL\n"
                                                + ");";
                                stmt.execute(sql);
                                // Create table Pollution Data
                                sql = "CREATE TABLE IF NOT EXISTS Pollution_Data (\n"
                                                + "    date TEXT NOT NULL,\n"
                                                + "    month TEXT NOT NULL,\n"
                                                + "    year TEXT NOT NULL,\n"
                                                + "    lon TEXT NOT NULL,\n"
                                                + "    lat TEXT NOT NULL,\n"
                                                + "    aqi TEXT NOT NULL,\n"
                                                + "    dt TEXT NOT NULL,\n"
                                                + "    co TEXT NOT NULL,\n"
                                                + "    no TEXT NOT NULL,\n"
                                                + "    no2 TEXT NOT NULL,\n"
                                                + "    o3 TEXT NOT NULL,\n"
                                                + "    so2 TEXT NOT NULL,\n"
                                                + "    pm2_5 TEXT NOT NULL,\n"
                                                + "    pm10 TEXT NOT NULL,\n"
                                                + "    nh3 TEXT NOT NULL,\n"
                                                + "    hour TEXT NOT NULL,\n"
                                                + "    minutes TEXT NOT NULL\n"
                                                + ");";
                                stmt.execute(sql);
                                // Create table Forecast
                                sql = "CREATE TABLE IF NOT EXISTS Forecast (\n"
                                                + "    ID INTEGER NOT NULL,\n"
                                                + "    lon TEXT NOT NULL,\n"
                                                + "    lat TEXT NOT NULL,\n"
                                                + "    date TEXT NOT NULL,\n"
                                                + "    month TEXT NOT NULL,\n"
                                                + "    year TEXT NOT NULL,\n"
                                                + "    hour TEXT NOT NULL,\n"
                                                + "    minutes TEXT NOT NULL,\n"
                                                + "    dt TEXT NOT NULL,\n"
                                                + "    temp TEXT NOT NULL,\n"
                                                + "    feels_like TEXT NOT NULL,\n"
                                                + "    temp_min TEXT NOT NULL,\n"
                                                + "    temp_max TEXT NOT NULL,\n"
                                                + "    pressure TEXT NOT NULL,\n"
                                                + "    humidity TEXT NOT NULL,\n"
                                                + "    weather TEXT NOT NULL,\n"
                                                + "    icon TEXT NOT NULL,\n"
                                                + "    visibility TEXT NOT NULL,\n"
                                                + "    wind_speed TEXT NOT NULL,\n"
                                                + "    wind_deg TEXT NOT NULL,\n"
                                                + "    gust TEXT NOT NULL,\n"
                                                + "    clouds_all TEXT NOT NULL,\n"
                                                + "    sunrise TEXT NOT NULL,\n"
                                                + "    sunset TEXT NOT NULL,\n"
                                                + "    dt_text TEXT NOT NULL\n"
                                                + ");";
                                stmt.execute(sql);
                                // close connection
                                conn.close();
                        }
                } catch (SQLException e) {
                        System.out.println(e.getMessage());
                        // close connection if open
                        try {
                                if (conn != null) {
                                        conn.close();
                                }
                        } catch (SQLException ex) {
                                System.out.println(ex.getMessage());
                        }
                }

        }

        // main for testing only
        public static void main(String[] args) {
                table_initialization ti = new table_initialization();
                ti.create_tables();
        }
}

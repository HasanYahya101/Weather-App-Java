package ui_layer.terminal;

import java.util.Scanner;
import functional_layer.*;
import database_layer.textfile_module.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class terminal {
    private static LocalDateTime unixTimestampToLocalDateTime(long unixTimestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp), ZoneId.of("UTC"));
    }

    public void run() {
        System.out.print("\033[H\033[2J"); // clear the terminal
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("Welcome to the Weather App Terminal:");
        System.out.println("1. Add Location by City and Country Name.");
        System.out.println("2. Add Location by Latitude and Longitude.");
        System.out.println("3. Show Current Weather Conditions.");
        System.out.println("4. Show \"Feels like, minimum and maximum temperature\".");
        System.out.println("5. Show Sunrise and Sunset time.");
        System.out.println("6. Show weather forecast for 5 days.");
        System.out.println("7. Display timestamps for weather record.");
        System.out.println("8. Generate Notification for poor weather conditions.");
        System.out.println("9. Show Air Pollution data.");
        System.out.println("10. Generate Notification for poor air quality.");
        System.out.println("11. Show pollution Gases Information.");
        System.out.println("12. View all Locations.");
        System.out.println("13. Exit.");
        System.out.println("");
        System.out.println("Enter your choice: ");
        choice = scanner.nextInt();
        if (choice > 13 || choice < 1) {
            System.out.println("Invalid choice. Please enter a valid choice.");
            System.out.println("");
            run();
        } else {
            if (choice == 1) {
                addLocationByCityAndCountryName();
            } else if (choice == 2) {
                addLocationbyCoordinates();
            } else if (choice == 3) {
                show_curr_conditions();
            } else if (choice == 4) {
                show_feels_like();
            } else if (choice == 5) {
                show_sunset_sunrise();
            } else if (choice == 6) {
                show_weather_5days();
            } else if (choice == 7) {
                System.out.println("Display timestamps for weather record.");
            } else if (choice == 8) {
                generate_noti_poor_weather();
            } else if (choice == 9) {
                System.out.println("Show Air Pollution data.");
            } else if (choice == 10) {
                System.out.println("Generate Notification for poor air quality.");
            } else if (choice == 11) {
                System.out.println("Show pollution Gases Information.");
            } else if (choice == 12) {
                display_locations();
            } else if (choice == 13) {
                System.out.println("Exiting the program.");
                System.exit(0);
            }
        }
        scanner.close();
    }

    public void generate_noti_poor_weather() {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs();
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run();
        } else {
            System.out.println("Locations:");
            int i = 1;
            for (location_save_interface.Locations location : locations) {
                System.out
                        .println(i + ". City: " + location.city + ", Country: " + location.country + ", Country Code: "
                                + location.country_code + ", Latitude: " + location.latitude + ", Longitude: "
                                + location.longitude);
                i++;
            }
            System.out.println("Enter the index of Location: ");
            Scanner scanner = new Scanner(System.in);
            int index = scanner.nextInt();
            scanner.nextLine();
            while (index > locations.size() || index < 1) {
                System.out.println("Invalid index. Please enter a valid index.");
                index = scanner.nextInt();
                scanner.nextLine();
            }
            String latitude = locations.get(index - 1).latitude;
            String longitude = locations.get(index - 1).longitude;
            current_weather_interface cw = new functional_layer.source.current_weather();
            functional_layer.current_weather_interface.Current_Conditions cc = cw.getCurrentWeather(latitude,
                    longitude);
            System.out.println("Weather:");
            // print in separate lines
            System.out.println("Longitude: " + cc.lon);
            System.out.println("Latitude: " + cc.lat);
            System.out.println("Main: " + cc.main);
            System.out.println("Description: " + cc.description);
            System.out.println("Temperature: " + cc.temp);
            System.out.println("Pressure: " + cc.pressure);
            System.out.println("Humidity: " + cc.humidity);
            System.out.println("Visibility: " + cc.visibility);
            System.out.println("Wind Speed: " + cc.wind_speed);
            System.out.println("Wind Degree: " + cc.wind_deg);
            System.out.println("Gust: " + cc.gust);
            System.out.println("Clouds: " + cc.clouds_all);
            System.out.println("Press any key to continue.");
            float temp = Float.parseFloat(cc.temp);
            float wind_speed = Float.parseFloat(cc.wind_speed);
            float humidity = Float.parseFloat(cc.humidity);
            cc.main = cc.main.toLowerCase();
            if (temp < 10 || humidity > 80 || temp > 30 || wind_speed > 30 || humidity > 90 || cc.main.contains("rain")
                    || cc.main.contains("storm") || cc.main.contains("snow") || cc.main.contains("hail")
                    || cc.main.contains("thunderstorm") || cc.main.contains("tornado") || cc.main.contains("hurricane")
                    || cc.main.contains("tropical storm") || cc.main.contains("cyclone") || cc.main.contains("blizzard")
                    || cc.main.contains("dust") || cc.main.contains("smoke") || cc.main.contains("fog")
                    || cc.main.contains("mist") || cc.main.contains("haze") || cc.main.contains("sand")
                    || cc.main.contains("ash") || cc.main.contains("squall") || cc.main.contains("tornado")) {
                System.out.println("Poor Weather Conditions. Notification generated.");
            } else {
                System.out.println("Weather Conditions are normal.");
            }
            // create a system command
            scanner.nextLine();
            run();
        }
    }

    public void show_weather_5days() {
        functional_layer.five_days_forcast_interface fdf = null;
        fdf = new functional_layer.source.five_days_forcast();

        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs();
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run();
        } else {
            System.out.println("Locations:");
            int i = 1;
            for (location_save_interface.Locations location : locations) {
                System.out
                        .println(i + ". City: " + location.city + ", Country: " + location.country + ", Country Code: "
                                + location.country_code + ", Latitude: " + location.latitude + ", Longitude: "
                                + location.longitude);
                i++;
            }
            System.out.println("Enter the index of Location: ");
            Scanner scanner = new Scanner(System.in);
            int index = scanner.nextInt();
            scanner.nextLine();
            while (index > locations.size() || index < 1) {
                System.out.println("Invalid index. Please enter a valid index.");
                index = scanner.nextInt();
                scanner.nextLine();
            }
            String latitude = locations.get(index - 1).latitude;
            String longitude = locations.get(index - 1).longitude;
            functional_layer.five_days_forcast_interface.five_days_data fdd = fdf.get5DaysForcast(latitude, longitude);
            System.out.println("Data:");
            System.out.println("Latitude: " + fdd.lat);
            System.out.println("Longitude: " + fdd.lon);
            System.out.println("Date: " + fdd.date + "-" + fdd.month + "-" + fdd.year);
            System.out.println("List:");
            for (functional_layer.five_days_forcast_interface.five_days_struct fds : fdd.list) {
                System.out.println("-----------------------------------------------------");
                System.out.println("Date Time: " + fds.dt);
                System.out.println("Temperature: " + fds.temp);
                System.out.println("Feels Like: " + fds.feels_like);
                System.out.println("Minimum Temperature: " + fds.temp_min);
                System.out.println("Maximum Temperature: " + fds.temp_max);
                System.out.println("Pressure: " + fds.pressure);
                System.out.println("Humidity: " + fds.humidity);
                System.out.println("Weather: " + fds.weather);
                System.out.println("Icon: " + fds.icon);
                System.out.println("Visibility: " + fds.visibility);
                System.out.println("Wind Speed: " + fds.wind_speed
                        + " (Speed of the wind at the moment of calculation. Unit Default: meter/sec)");
                System.out.println("Wind Degree: " + fds.wind_deg
                        + " (Wind direction, degrees (meteorological))");
                System.out.println("Gust: " + fds.gust + " (Wind gust. Unit Default: meter/sec)");
                System.out.println("Clouds: " + fds.clouds_all + " (Cloudiness, %)");
                System.out.println("Sunrise: " + fds.sunrise + " (Sunrise time)");
                System.out.println("Sunset: " + fds.sunset + " (Sunset time)");
                System.out.println("Date Time Text: " + fds.dt_text);
                System.out.println("");
                System.out.println("-----------------------------------------------------");

            }
            System.out.println("Press any key to continue.");
            // create a system command
            scanner.nextLine();
            run();

        }

    }

    public void show_sunset_sunrise() {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs();
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run();
        } else {
            System.out.println("Locations:");
            int i = 1;
            for (location_save_interface.Locations location : locations) {
                System.out
                        .println(i + ". City: " + location.city + ", Country: " + location.country + ", Country Code: "
                                + location.country_code + ", Latitude: " + location.latitude + ", Longitude: "
                                + location.longitude);
                i++;
            }
            System.out.println("Enter the index of Location: ");
            Scanner scanner = new Scanner(System.in);
            int index = scanner.nextInt();
            scanner.nextLine();
            while (index > locations.size() || index < 1) {
                System.out.println("Invalid index. Please enter a valid index.");
                index = scanner.nextInt();
                scanner.nextLine();
            }
            String latitude = locations.get(index - 1).latitude;
            String longitude = locations.get(index - 1).longitude;
            current_weather_interface cw = new functional_layer.source.current_weather();
            functional_layer.current_weather_interface.Current_Conditions cc = cw.getCurrentWeather(latitude,
                    longitude);
            // convert from string to long
            long sunriseUnixTimestamp = Long.parseLong(cc.sunrise);
            long sunsetUnixTimestamp = Long.parseLong(cc.sunset);
            // Convert Unix timestamps to LocalDateTime objects
            LocalDateTime sunriseDateTime = unixTimestampToLocalDateTime(sunriseUnixTimestamp);
            LocalDateTime sunsetDateTime = unixTimestampToLocalDateTime(sunsetUnixTimestamp);
            // Format the LocalDateTime objects
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedSunrise = sunriseDateTime.format(formatter);
            String formattedSunset = sunsetDateTime.format(formatter);
            System.out.println("Data (Format: yyyy-MM-dd HH:mm:ss):");
            // print in separate lines
            System.out.println("Sunrise: " + formattedSunrise + " (Unix Timestamp: " + sunriseUnixTimestamp + ")");
            System.out.println("Sunset: " + formattedSunset + " (Unix Timestamp: " + sunsetUnixTimestamp + ")");
            System.out.println("Press any key to continue.");
            // create a system command
            scanner.nextLine();
            run();
        }
    }

    public void show_feels_like() {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs();
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run();
        } else {
            System.out.println("Locations:");
            int i = 1;
            for (location_save_interface.Locations location : locations) {
                System.out
                        .println(i + ". City: " + location.city + ", Country: " + location.country + ", Country Code: "
                                + location.country_code + ", Latitude: " + location.latitude + ", Longitude: "
                                + location.longitude);
                i++;
            }
            System.out.println("Enter the index of Location: ");
            Scanner scanner = new Scanner(System.in);
            int index = scanner.nextInt();
            scanner.nextLine();
            while (index > locations.size() || index < 1) {
                System.out.println("Invalid index. Please enter a valid index.");
                index = scanner.nextInt();
                scanner.nextLine();
            }
            String latitude = locations.get(index - 1).latitude;
            String longitude = locations.get(index - 1).longitude;
            current_weather_interface cw = new functional_layer.source.current_weather();
            functional_layer.current_weather_interface.Current_Conditions cc = cw.getCurrentWeather(latitude,
                    longitude);
            System.out.println("Data:");
            // print in separate lines
            System.out.println("Feels Like: " + cc.feels_like);
            System.out.println("Minimum Temperature: " + cc.temp_min);
            System.out.println("Maximum Temperature: " + cc.temp_max);
            System.out.println("Press any key to continue.");
            // create a system command
            scanner.nextLine();
            run();
        }
    }

    public void show_curr_conditions() {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs();
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run();
        } else {
            System.out.println("Locations:");
            int i = 1;
            for (location_save_interface.Locations location : locations) {
                System.out
                        .println(i + ". City: " + location.city + ", Country: " + location.country + ", Country Code: "
                                + location.country_code + ", Latitude: " + location.latitude + ", Longitude: "
                                + location.longitude);
                i++;
            }
            System.out.println("Enter the index of Location: ");
            Scanner scanner = new Scanner(System.in);
            int index = scanner.nextInt();
            scanner.nextLine();
            while (index > locations.size() || index < 1) {
                System.out.println("Invalid index. Please enter a valid index.");
                index = scanner.nextInt();
                scanner.nextLine();
            }
            String latitude = locations.get(index - 1).latitude;
            String longitude = locations.get(index - 1).longitude;
            current_weather_interface cw = new functional_layer.source.current_weather();
            functional_layer.current_weather_interface.Current_Conditions cc = cw.getCurrentWeather(latitude,
                    longitude);
            System.out.println("Current Weather Conditions:");
            // print in separate lines
            System.out.println("Longitude: " + cc.lon);
            System.out.println("Latitude: " + cc.lat);
            System.out.println("Main: " + cc.main);
            System.out.println("Description: " + cc.description);
            System.out.println("Temperature: " + cc.temp);
            System.out.println("Pressure: " + cc.pressure);
            System.out.println("Humidity: " + cc.humidity);
            System.out.println("Visibility: " + cc.visibility);
            System.out.println("Wind Speed: " + cc.wind_speed);
            System.out.println("Wind Degree: " + cc.wind_deg);
            System.out.println("Gust: " + cc.gust);
            System.out.println("Clouds: " + cc.clouds_all);
            System.out.println("Press any key to continue.");
            // create a system command
            scanner.nextLine();
            run();
        }
    }

    public void addLocationByCityAndCountryName() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter City Name: ");
        String city = scn.nextLine();
        System.out.println("Enter Country Name: ");
        String country = scn.nextLine();
        Location_Interfaces lq = new functional_layer.source.locations_query();
        boolean flag = false;
        flag = lq.addLocation_Names(city, country);
        if (!flag) {
            System.out.println("Error in adding Location, please try again.");
        } else {
            System.out.println("Location added successfully.");
        }
        System.out.println("Press any key to continue.");
        scn.nextLine();
        run();
    }

    public void addLocationbyCoordinates() {
        // add location by coordinates
        String lati;
        String lon;
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter Latitude: ");
        lati = scn.nextLine();
        System.out.println("Enter Longitude: ");
        lon = scn.nextLine();
        // scn.close();
        Location_Interfaces lq = new functional_layer.source.locations_query();
        boolean flag = lq.addLocation_Coordinates(lati, lon);
        if (flag == false) {
            System.out.println("Error in adding Location, please try again.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            // scanner.close();
            run();
        } else {
            System.out.println("Location added successfully.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            // scanner.close();
            run();
        }
    }

    public void display_locations() {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs();
        if (locations.size() == 0) {
            System.out.println("No locations found.");
        } else {
            System.out.println("Locations:");
            for (location_save_interface.Locations location : locations) {
                System.out.println("City: " + location.city + ", Country: " + location.country + ", Country Code: "
                        + location.country_code + ", Latitude: " + location.latitude + ", Longitude: "
                        + location.longitude);
            }
        }
        System.out.println("Press any key to continue.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        run();
    }

    // main for testing only
    public static void main(String[] args) {
        // call run method
        terminal terminal = new terminal();
        terminal.run();
    }
}

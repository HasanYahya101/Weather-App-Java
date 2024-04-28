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

    public void run(String db_type) {
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
            run(db_type);
        } else {
            if (choice == 1) {
                addLocationByCityAndCountryName(db_type);
            } else if (choice == 2) {
                addLocationbyCoordinates(db_type);
            } else if (choice == 3) {
                show_curr_conditions(db_type);
            } else if (choice == 4) {
                show_feels_like(db_type);
            } else if (choice == 5) {
                show_sunset_sunrise(db_type);
            } else if (choice == 6) {
                show_weather_5days(db_type);
            } else if (choice == 7) {
                display_timestamps(db_type);
            } else if (choice == 8) {
                generate_noti_poor_weather(db_type);
            } else if (choice == 9) {
                show_air_pollution(db_type);
            } else if (choice == 10) {
                generate_notification_air_quality(db_type);
            } else if (choice == 11) {
                show_gases_info(db_type);
            } else if (choice == 12) {
                display_locations(db_type);
            } else if (choice == 13) {
                System.out.println("Exiting the program.");
                System.exit(0);
            }
        }
        scanner.close();
    }

    public void display_timestamps(String db_type) {
        // creating objects of all classes
        functional_layer.Location_Interfaces lq = new functional_layer.source.locations_query();
        functional_layer.current_weather_interface cw = new functional_layer.source.current_weather();
        functional_layer.five_days_forcast_interface fdf = new functional_layer.source.five_days_forcast();
        functional_layer.pollution_data_interface pd = new functional_layer.source.pollution_data();
        // creating data classes
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs(db_type);
        java.util.List<functional_layer.current_weather_interface.Current_Conditions> cc = new java.util.ArrayList<functional_layer.current_weather_interface.Current_Conditions>();
        java.util.List<functional_layer.five_days_forcast_interface.five_days_data> fdd = new java.util.ArrayList<functional_layer.five_days_forcast_interface.five_days_data>();
        java.util.List<functional_layer.pollution_data_interface.polution_data_struct> apd = new java.util.ArrayList<functional_layer.pollution_data_interface.polution_data_struct>();
        // getting values from the database
        cc = cw.return_current_conditions(db_type);
        fdd = fdf.get5DaysstoredData(db_type);
        apd = pd.return_stored_pop_data(db_type);
        System.out.println("Timestamps (Locations):");
        int i = 1;
        if (locations != null && locations.size() > 0) {
            for (location_save_interface.Locations location : locations) {
                System.out.println(i + ". " + "Date: " + location.Day + "-" + location.Month + "-" + location.Year
                        + ", Time: " + location.Hour + ":" + location.Minutes + ", City: " + location.city
                        + ", Country: "
                        + location.country);
                i++;
            }
        } else {
            System.out.println("No locations found.");
        }
        i = 1;
        System.out.println("------------------------------------------------------------------");
        System.out.println("Timestamps (Weather Data):");
        if (cc != null && cc.size() > 0) {

            for (functional_layer.current_weather_interface.Current_Conditions c : cc) {
                System.out.println(i + ". Date: " + c.date + "-" + c.month + "-" + c.year + ", Time: " + c.hour + ":"
                        + c.minutes + ", Latitude: " + c.lat + ", Longitude: " + c.lon);
                i++;
            }
        } else {
            System.out.println("No weather data found.");
        }
        System.out.println("------------------------------------------------------------------");
        i = 1;
        System.out.println("Timestamps (5 Days Forecast):");
        if (fdd != null && fdd.size() > 0) {

            for (functional_layer.five_days_forcast_interface.five_days_data f : fdd) {
                System.out.println(i + ". Date: " + f.date + "-" + f.month + "-" + f.year + ", Time: " + f.hour + ":"
                        + f.minutes + ", Latitude: " + f.lat + ", Longitude: " + f.lon);
                i++;
            }
        } else {
            System.out.println("No 5 days forecast data found.");
        }
        System.out.println("------------------------------------------------------------------");
        i = 1;
        System.out.println("Timestamps (Pollution Data):");
        if (apd != null && apd.size() > 0) {
            for (functional_layer.pollution_data_interface.polution_data_struct a : apd) {
                System.out.println(i + ". Date: " + a.date + "-" + a.month + "-" + a.year + ", Time: " + a.hour + ":"
                        + a.minutes + ", Latitude: " + a.lat + ", Longitude: " + a.lon);
                i++;
            }
        } else {
            System.out.println("No pollution data found.");
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("Press any key to continue.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        run(db_type);
    }

    public void generate_notification_air_quality(String db_type) {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs(db_type);
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run(db_type);
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
            functional_layer.pollution_data_interface pollution_data = new functional_layer.source.pollution_data();
            functional_layer.pollution_data_interface.polution_data_struct apd = pollution_data.getPollutionData(
                    latitude, longitude, db_type);
            System.out.println("Data:");
            System.out.println("Latitude: " + apd.lat);
            System.out.println("Longitude: " + apd.lon);
            // convert dt unix to local date time
            long dt = Long.parseLong(apd.dt);
            LocalDateTime dateTime = unixTimestampToLocalDateTime(dt);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = dateTime.format(formatter);
            System.out.println("Date Time: " + formattedDateTime);
            // System.out.println("Date Time: " + apd.dt);
            System.out.println("AQI: " + apd.aqi);
            System.out.println("CO: " + apd.co);
            System.out.println("NO: " + apd.no);
            System.out.println("NO2: " + apd.no2);
            System.out.println("O3: " + apd.o3);
            System.out.println("SO2: " + apd.so2);
            System.out.println("PM2.5: " + apd.pm2_5);
            System.out.println("PM10: " + apd.pm10);
            System.out.println("NH3: " + apd.nh3);
            System.out.println("Press any key to continue.");
            boolean flag = false;
            if (apd.aqi.equals("null") != true) {
                int aqi = Integer.parseInt(apd.aqi);
                if (aqi > 300) {
                    System.out.println("Hazardous air quality. Notification generated.");
                    flag = true;
                } else if (aqi > 200) {
                    System.out.println("Very unhealthy air quality. Notification generated.");
                    flag = true;
                } else if (aqi > 150) {
                    System.out.println("Unhealthy air quality. Notification generated.");
                    flag = true;
                } else if (aqi > 100) {
                    System.out.println("Unhealthy for sensitive groups. Notification generated.");
                    flag = true;
                } else if (aqi > 50) {
                    System.out.println("Moderate air quality. Notification generated.");
                    flag = true;
                } else {
                    System.out.println("Good air quality. Notification generated.");
                    flag = true;
                }
            } else {
                System.out.println("No AQI data found.");
            }
            if (apd.co.equals("null") != true) {
                float co = Float.parseFloat(apd.co);
                if (co >= 500) {
                    System.out.println("Very high CO levels. Notification generated.");
                    flag = true;
                }
            }
            if (apd.no.equals("null") != true) {
                float no = Float.parseFloat(apd.no);
                if (no >= 0.2) {
                    System.out.println("Very high NO levels. Notification generated.");
                    flag = true;
                }
            }
            if (apd.no2.equals("null") != true) {
                float no2 = Float.parseFloat(apd.no2);
                if (no2 >= 0.09) {
                    System.out.println("Very high NO2 levels. Notification generated.");
                    flag = true;
                }
            }
            if (apd.o3.equals("null") != true) {
                float o3 = Float.parseFloat(apd.o3);
                if (o3 >= 130) {
                    System.out.println("Very high O3 levels. Notification generated.");
                    flag = true;
                }
            }
            if (apd.so2.equals("null") != true) {
                float so2 = Float.parseFloat(apd.so2);
                if (so2 > 0.3) {
                    System.out.println("Very high SO2 levels. Notification generated.");
                    flag = true;
                }
            }
            if (apd.pm2_5.equals("null") != true) {
                float pm2_5 = Float.parseFloat(apd.pm2_5);
                if (pm2_5 >= 1.2) {
                    System.out.println("Very high PM2.5 levels. Notification generated.");
                    flag = true;
                }
            }
            if (apd.pm10.equals("null") != true) {
                float pm10 = Float.parseFloat(apd.pm10);
                if (pm10 >= 1.5) {
                    System.out.println("Very high PM10 levels. Notification generated.");
                    flag = true;
                }
            }
            if (apd.nh3.equals("null") != true) {
                float nh3 = Float.parseFloat(apd.nh3);
                if (nh3 >= 10) {
                    System.out.println("Very high NH3 levels. Notification generated.");
                    flag = true;
                }
            }
            if (flag == false) {
                System.out.println("No poor air quality detected.");
            }
            // create a system command
            System.out.println("Press any key to continue.");
            scanner.nextLine();
            run(db_type);
        }
    }

    public void show_gases_info(String db_type) {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs(db_type);
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run(db_type);
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
            functional_layer.pollution_data_interface pollution_data = new functional_layer.source.pollution_data();
            functional_layer.pollution_data_interface.polution_data_struct apd = pollution_data.getPollutionData(
                    latitude, longitude, db_type);
            System.out.println("Data:");
            System.out.println("Latitude: " + apd.lat);
            System.out.println("Longitude: " + apd.lon);
            // convert dt unix to local date time
            long dt = Long.parseLong(apd.dt);
            LocalDateTime dateTime = unixTimestampToLocalDateTime(dt);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = dateTime.format(formatter);
            System.out.println("Date Time: " + formattedDateTime);
            // System.out.println("Date Time: " + apd.dt);
            System.out.println("AQI: " + apd.aqi);
            System.out.println("CO: " + apd.co);
            System.out.println("NO: " + apd.no);
            System.out.println("NO2: " + apd.no2);
            System.out.println("O3: " + apd.o3);
            System.out.println("SO2: " + apd.so2);
            System.out.println("PM2.5: " + apd.pm2_5);
            System.out.println("PM10: " + apd.pm10);
            System.out.println("NH3: " + apd.nh3);
            System.out.println("Press any key to continue.");
            // create a system command
            scanner.nextLine();
            run(db_type);
        }
    }

    public void show_air_pollution(String db_type) {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs(db_type);
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run(db_type);
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
            functional_layer.pollution_data_interface pollution_data = new functional_layer.source.pollution_data();
            functional_layer.pollution_data_interface.polution_data_struct apd = pollution_data.getPollutionData(
                    latitude, longitude, db_type);
            System.out.println("Data:");
            System.out.println("Latitude: " + apd.lat);
            System.out.println("Longitude: " + apd.lon);
            // convert dt unix to local date time
            long dt = Long.parseLong(apd.dt);
            LocalDateTime dateTime = unixTimestampToLocalDateTime(dt);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = dateTime.format(formatter);
            System.out.println("Date Time: " + formattedDateTime);
            // System.out.println("Date Time: " + apd.dt);
            System.out.println("AQI: " + apd.aqi);
            System.out.println("CO: " + apd.co);
            System.out.println("NO: " + apd.no);
            System.out.println("NO2: " + apd.no2);
            System.out.println("O3: " + apd.o3);
            System.out.println("SO2: " + apd.so2);
            System.out.println("PM2.5: " + apd.pm2_5);
            System.out.println("PM10: " + apd.pm10);
            System.out.println("NH3: " + apd.nh3);
            System.out.println("Press any key to continue.");
            // create a system command
            scanner.nextLine();
            run(db_type);
        }
    }

    public void generate_noti_poor_weather(String db_type) {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs(db_type);
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run(db_type);
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
                    longitude, db_type);
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
            System.out.println("Press any key to continue.");
            scanner.nextLine();
            run(db_type);
        }
    }

    public void show_weather_5days(String db_type) {
        functional_layer.five_days_forcast_interface fdf = null;
        fdf = new functional_layer.source.five_days_forcast();

        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs(db_type);
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run(db_type);
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
            functional_layer.five_days_forcast_interface.five_days_data fdd = fdf.get5DaysForcast(latitude, longitude,
                    db_type);
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
            run(db_type);

        }

    }

    public void show_sunset_sunrise(String db_type) {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs(db_type);
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run(db_type);
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
                    longitude, db_type);
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
            run(db_type);
        }
    }

    public void show_feels_like(String db_type) {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs(db_type);
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run(db_type);
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
                    longitude, db_type);
            System.out.println("Data:");
            // print in separate lines
            System.out.println("Feels Like: " + cc.feels_like);
            System.out.println("Minimum Temperature: " + cc.temp_min);
            System.out.println("Maximum Temperature: " + cc.temp_max);
            System.out.println("Press any key to continue.");
            // create a system command
            scanner.nextLine();
            run(db_type);
        }
    }

    public void show_curr_conditions(String db_type) {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs(db_type);
        if (locations.size() == 0) {
            System.out.println("No locations found. Please add a location first.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            run(db_type);
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
                    longitude, db_type);
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
            run(db_type);
        }
    }

    public void addLocationByCityAndCountryName(String db_type) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter City Name: ");
        String city = scn.nextLine();
        System.out.println("Enter Country Name: ");
        String country = scn.nextLine();
        Location_Interfaces lq = new functional_layer.source.locations_query();
        boolean flag = false;
        flag = lq.addLocation_Names(city, country, db_type);
        if (!flag) {
            System.out.println("Error in adding Location, please try again.");
        } else {
            System.out.println("Location added successfully.");
        }
        System.out.println("Press any key to continue.");
        scn.nextLine();
        run(db_type);
    }

    public void addLocationbyCoordinates(String db_type) {
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
        boolean flag = lq.addLocation_Coordinates(lati, lon, db_type);
        if (flag == false) {
            System.out.println("Error in adding Location, please try again.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            // scanner.close();
            run(db_type);
        } else {
            System.out.println("Location added successfully.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            // scanner.close();
            run(db_type);
        }
    }

    public void display_locations(String db_type) {
        Location_Interfaces lq = new functional_layer.source.locations_query();
        java.util.List<location_save_interface.Locations> locations = lq.displayLocs(db_type);
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
        run(db_type);
    }

    // main for testing only
    public static void main(String[] args) {
        // call run method
        terminal terminal = new terminal();
        String db_type;
        // enter value
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the database type (txt or sql): ");
        db_type = scanner.nextLine();
        scanner.close();
        terminal.run(db_type);
    }
}

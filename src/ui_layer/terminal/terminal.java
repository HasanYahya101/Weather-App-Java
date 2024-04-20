package ui_layer.terminal;

import java.util.Scanner;
import functional_layer.*;
import functional_layer.source.*;
import database_layer.textfile_module.*;

public class terminal {

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
                System.out.println("Show Current Weather Conditions.");
            } else if (choice == 4) {
                System.out.println("Show \"Feels like, minimum and maximum temperature\".");
            } else if (choice == 5) {
                System.out.println("Show Sunrise and Sunset time.");
            } else if (choice == 6) {
                System.out.println("Show weather forecast for 5 days.");
            } else if (choice == 7) {
                System.out.println("Display timestamps for weather record.");
            } else if (choice == 8) {
                System.out.println("Generate Notification for poor weather conditions.");
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
        scn.close();
        Location_Interfaces lq = new functional_layer.source.locations_query();
        boolean flag = lq.addLocation_Coordinates(lati, lon);
        if (flag == false) {
            System.out.println("Error in adding Location, please try again.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            scanner.close();
            run();
        } else {
            System.out.println("Location added successfully.");
            System.out.println("Press any key to continue.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            scanner.close();
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
        // scanner.close(); // not closing because it will close the System.in (a leak
        // shoudn't be a problem)
        run();
    }

    // main for testing only
    public static void main(String[] args) {
        // call run method
        terminal terminal = new terminal();
        terminal.run();
    }
}

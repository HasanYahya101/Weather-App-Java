package ui_layer.desktop;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.List;
import java.util.ArrayList;

// import javafx libraries
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.*;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.awt.*;
import javax.swing.*;
// import mouse event
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// import functional layer
import functional_layer.Location_Interfaces;
import functional_layer.source.locations_query;;

public class desktop extends Application {
    public class Location {
        String city;
        String country;
        String latitude;
        String longitude;

        public Location(String city, String country, String latitude, String longitude) {
            this.city = city;
            this.country = country;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return city + ", " + country + " (" + latitude + ", " + longitude + ")";
        }
    }

    @Override
    public void start(Stage primaryStage) {
        run(primaryStage, "sql");
    }

    public void run(Stage primaryStage, String db_type) {
        String db = db_type;
        System.out.println("Database Type: " + db);
        int width = 800;
        int height = 600;

        String title = " Weather Desktop Application";

        Image icon = new Image(getClass().getResourceAsStream("assets\\\\title_bar_icon.png"));
        primaryStage.getIcons().add(icon);
        Button button_names = new Button("Add Location by Names");
        button_names.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Add Location by City and Country Name");
                showAddLocationPopup_Names(primaryStage, db);
            }
        });
        Button button_coord = new Button("Add Location by Coordinates");
        button_coord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Add Location by Coordinates");
                showAddLocationPopup_Coord(primaryStage, db);
            }
        });
        Button button_search = new Button("Check Weather Data");
        button_search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Search Weather Data");
                Location loc = showLocationsList(primaryStage, db);
                showCurrent_Conditions(primaryStage, loc, db);
            }
        });
        Button forcasts = new Button("Check Weather Forcasts");
        forcasts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Search Weather Forcasts");
                Location loc = showLocationsList(primaryStage, db);
            }
        });
        Button pollution_data = new Button("Check Pollution Data");
        pollution_data.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Search Pollution Data");
                Location loc = showLocationsList(primaryStage, db);
                showPop_data(primaryStage, loc, db);
            }
        });
        // set location on screen in the middle of the screen
        button_names.setLayoutX(width / 2 - 158);
        button_names.setLayoutY(height / 2 - 50 - 100 + 30);
        button_coord.setLayoutX(width / 2 - 158);
        button_coord.setLayoutY(height / 2 + 20 - 100 + 30);
        button_search.setLayoutX(width / 2 - 158);
        button_search.setLayoutY(height / 2 + 90 - 100 + 30);
        forcasts.setLayoutX(width / 2 - 158);
        forcasts.setLayoutY(height / 2 + 160 - 100 + 30);
        pollution_data.setLayoutX(width / 2 - 158);
        pollution_data.setLayoutY(height / 2 + 230 - 100 + 30);

        button_names.setPrefHeight(47);
        button_names.setPrefWidth(316);
        button_coord.setPrefHeight(47);
        button_coord.setPrefWidth(316);
        button_search.setPrefHeight(47);
        button_search.setPrefWidth(316);
        forcasts.setPrefHeight(47);
        forcasts.setPrefWidth(316);
        pollution_data.setPrefHeight(47);
        pollution_data.setPrefWidth(316);

        button_names.setFont(javafx.scene.text.Font.font("Cambria", 18));
        button_coord.setFont(javafx.scene.text.Font.font("Cambria", 18));
        button_search.setFont(javafx.scene.text.Font.font("Cambria", 18));
        forcasts.setFont(javafx.scene.text.Font.font("Cambria", 18));
        pollution_data.setFont(javafx.scene.text.Font.font("Cambria", 18));

        Text text = new Text();
        text.setText("Weather Application");
        text.setFont(javafx.scene.text.Font.font("Cambria", 30));
        text.setLayoutX(width / 2 - 150 + 20);
        text.setLayoutY(125 - 30);

        Pane root = new Pane();
        Scene scene = new Scene(root, width, height);

        // add background photo
        Image image = new Image(getClass().getResourceAsStream("assets\\\\Menu_Background.jpg"));
        ImageView imageView = new ImageView(image);
        // zoom out of the image
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        root.getChildren().add(imageView);
        root.getChildren().add(button_names);
        root.getChildren().add(button_coord);
        root.getChildren().add(button_search);
        root.getChildren().add(text);
        root.getChildren().add(forcasts);
        root.getChildren().add(pollution_data);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void showAddLocationPopup_Names(Stage primaryStage, String db_type_given) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(primaryStage);
        popupStage.setTitle(" Add Location Names");

        // add the title page icon
        Image icon = new Image(getClass().getResourceAsStream("assets\\\\title_bar_icon.png"));
        popupStage.getIcons().add(icon);

        Pane root = new Pane();

        Text cityText = new Text("City: ");
        cityText.setLayoutX(50);
        cityText.setLayoutY(50 + 17 - 13);
        // bolden it
        cityText.setStyle("-fx-font-weight: bold;");

        Text countryText = new Text("Country: ");
        countryText.setLayoutX(50);
        countryText.setLayoutY(100 + 17 - 13);
        // bolden it
        countryText.setStyle("-fx-font-weight: bold;");

        TextField city = new TextField();
        city.setPromptText("Enter City Name");
        city.setLayoutX(115);
        city.setLayoutY(50 - 13);
        city.setPrefWidth(230);

        TextField country = new TextField();
        country.setPromptText("Enter Country Name");
        country.setLayoutX(115);
        country.setLayoutY(100 - 13);
        country.setPrefWidth(230);

        Button add = new Button("Add Location");
        add.setLayoutX(50 + 160);
        add.setLayoutY(150);

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(150 + 160);
        cancel.setLayoutY(150);

        Text errorText = new Text();
        errorText.setLayoutX(50);
        errorText.setLayoutY(135);
        errorText.setStyle("-fx-font-weight: bold;");
        errorText.setStyle("-fx-fill: red;");

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String city__ = city.getText();
                String country__ = country.getText();
                System.out.println("City: " + city__);
                System.out.println("Country: " + country__);
                String error;
                functional_layer.Location_Interfaces location = new locations_query();
                boolean flag = true;
                if (city__.length() == 0 || country__.length() == 0) {
                    System.out.println("City or Country Name is empty");
                    error = "City or Country Name is empty";
                    errorText.setText(error);
                    return;
                } else {
                    errorText.setText("");
                    flag = location.addLocation_Names(city__, country__, db_type_given);
                    if (flag == false) {
                        System.out.println("Error, adding location to Database.");
                        error = "Error, adding location to Database.";
                        errorText.setText(error);
                        return;
                    }
                }
                popupStage.close();
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popupStage.close();
            }
        });

        root.getChildren().add(city);
        root.getChildren().add(country);
        root.getChildren().add(cityText);
        root.getChildren().add(countryText);
        root.getChildren().add(add);
        root.getChildren().add(cancel);
        root.getChildren().add(errorText);

        int width = 400;
        int height = 200;

        Scene popupScene = new Scene(root, width, height);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    private void showAddLocationPopup_Coord(Stage primaryStage, String db_type_given) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(primaryStage);
        popupStage.setTitle(" Add Location Names");

        // add the title page icon
        Image icon = new Image(getClass().getResourceAsStream("assets\\\\title_bar_icon.png"));
        popupStage.getIcons().add(icon);

        Pane root = new Pane();

        Text latitudetText = new Text("Latitude: ");
        latitudetText.setLayoutX(50);
        latitudetText.setLayoutY(50 + 17 - 13);
        // bolden it
        latitudetText.setStyle("-fx-font-weight: bold;");

        Text longiText = new Text("Longitude: ");
        longiText.setLayoutX(50);
        longiText.setLayoutY(100 + 17 - 13);
        // bolden it
        longiText.setStyle("-fx-font-weight: bold;");

        TextField latitude = new TextField();
        latitude.setPromptText("Enter Latitude value");
        latitude.setLayoutX(115);
        latitude.setLayoutY(50 - 13);
        latitude.setPrefWidth(230);

        TextField longitude = new TextField();
        longitude.setPromptText("Enter Longitude value");
        longitude.setLayoutX(115);
        longitude.setLayoutY(100 - 13);
        longitude.setPrefWidth(230);

        Button add = new Button("Add Location");
        add.setLayoutX(50 + 160);
        add.setLayoutY(150);

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(150 + 160);
        cancel.setLayoutY(150);

        Text errorText = new Text();
        errorText.setLayoutX(50);
        errorText.setLayoutY(135);
        errorText.setStyle("-fx-font-weight: bold;");
        errorText.setStyle("-fx-fill: red;");

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String lat__ = latitude.getText();
                String long__ = longitude.getText();
                System.out.println("City: " + lat__);
                System.out.println("Country: " + long__);
                String error;
                functional_layer.Location_Interfaces location = new locations_query();
                boolean flag = true;
                if (lat__.length() == 0 || long__.length() == 0) {
                    System.out.println("One or both fields are empty");
                    error = "City or Country Name is empty";
                    errorText.setText(error);
                    return;
                } else if (lat__.matches(".*[a-zA-Z]+.*") || long__.matches(".*[a-zA-Z]+.*")) {
                    // if it contains any alphabet
                    System.out.println("Invalid Latitude or Longitude value");
                    error = "Invalid Latitude or Longitude value";
                    errorText.setText(error);
                    return;

                } else {
                    errorText.setText("");
                    flag = location.addLocation_Names(lat__, long__, db_type_given);
                    if (flag == false) {
                        System.out.println("Error, adding location to Database.");
                        error = "Error, adding location to Database.";
                        errorText.setText(error);
                        return;
                    }
                }
                popupStage.close();
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                popupStage.close();
            }
        });

        root.getChildren().add(latitude);
        root.getChildren().add(longitude);
        root.getChildren().add(latitudetText);
        root.getChildren().add(longiText);
        root.getChildren().add(add);
        root.getChildren().add(cancel);
        root.getChildren().add(errorText);

        int width = 400;
        int height = 200;

        Scene popupScene = new Scene(root, width, height);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    public Location showLocationsList(Stage primaryStage, String db_type) {
        functional_layer.Location_Interfaces location = new locations_query();
        List<database_layer.textfile_module.location_save_interface.Locations> locations = location
                .displayLocs(db_type);

        if (locations.size() == 0) {
            JOptionPane.showMessageDialog(null, "No locations added, please add locations first.");
            return null;
        }

        DefaultListModel<Location> listModel = new DefaultListModel<>();
        for (database_layer.textfile_module.location_save_interface.Locations loc : locations) {
            listModel.addElement(new Location(loc.city, loc.country, loc.latitude, loc.longitude));
        }

        JList<Location> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);

        JScrollPane scrollPane = new JScrollPane(list);

        // Create a semaphore to hold the selected location
        Semaphore semaphore = new Semaphore(0);

        // Listener for mouse click on the list
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    Location selectedLocation = listModel.getElementAt(index);
                    semaphore.release(); // Release the semaphore
                }
            }
        });

        JFrame frame = new JFrame("Locations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setSize(460, 200);
        frame.setVisible(true);

        try {
            semaphore.acquire(); // Wait for user selection
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // print the selected location
        System.out.println("Selected Location: " + list.getSelectedValue());
        frame.dispose();
        return list.getSelectedValue();
    }

    public void showCurrent_Conditions(Stage primaryStage, Location loc, String db_type) {
        // make a window
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(primaryStage);
        popupStage.setTitle(" Current Weather Conditions");

        // add the title page icon
        Image img_icon = new Image(getClass().getResourceAsStream("assets\\\\title_bar_icon.png"));
        popupStage.getIcons().add(img_icon);

        Pane root = new Pane();

        functional_layer.current_weather_interface current = new functional_layer.source.current_weather();
        functional_layer.current_weather_interface.Current_Conditions current_conditions = current
                .getCurrentWeather(loc.latitude, loc.longitude, db_type);

        String main = current_conditions.main;
        String description = current_conditions.description;
        String temp = current_conditions.temp;
        String feels_like = current_conditions.feels_like;
        String temp_min = current_conditions.temp_min;
        String temp_max = current_conditions.temp_max;
        String pressure = current_conditions.pressure;
        String humidity = current_conditions.humidity;
        String visibility = current_conditions.visibility;
        String wind_speed = current_conditions.wind_speed;
        String wind_deg = current_conditions.wind_deg;
        String gust = current_conditions.gust;
        String sunrise = current_conditions.sunrise;
        String sunset = current_conditions.sunset;

        // test if warning works
        // float tempe = 40;
        float tempe = Float.parseFloat(current_conditions.temp);
        float humid = Float.parseFloat(current_conditions.humidity);
        float wind_speed_ = Float.parseFloat(current_conditions.wind_speed);

        String main_lower_case = main.toLowerCase();

        boolean flag = false;

        if (tempe < 10 || humid > 80 || tempe > 30 || wind_speed_ > 30 || humid > 90
                || main_lower_case.contains("rain")
                || main_lower_case.contains("storm") || main_lower_case.contains("snow")
                || main_lower_case.contains("hail")
                || main_lower_case.contains("thunderstorm") || main_lower_case.contains("tornado")
                || main_lower_case.contains("hurricane")
                || main_lower_case.contains("tropical storm") || main_lower_case.contains("cyclone")
                || main_lower_case.contains("blizzard")
                || main_lower_case.contains("dust") || main_lower_case.contains("smoke")
                || main_lower_case.contains("fog")
                || main_lower_case.contains("mist") || main_lower_case.contains("haze")
                || main_lower_case.contains("sand")
                || main_lower_case.contains("ash") || main_lower_case.contains("squall")
                || main_lower_case.contains("tornado")) {
            System.out.println("Poor Weather Conditions. Notification generated.");
            flag = true;
        }

        int yOffset = 50; // Initial Y offset

        Text mainText = new Text(main + " (" + description + ")");
        mainText.setLayoutX(50);
        mainText.setLayoutY(yOffset + 17 - 20);
        mainText.setStyle("-fx-font-weight: bold;");
        yOffset += 30;

        Text tempText = new Text("Temperature: " + temp + "°C");
        tempText.setLayoutX(50);
        tempText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text feels_likeText = new Text("Feels Like: " + feels_like + "°C");
        feels_likeText.setLayoutX(50);
        feels_likeText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text temp_minText = new Text("Min Temperature: " + temp_min + "°C");
        temp_minText.setLayoutX(50);
        temp_minText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text temp_maxText = new Text("Max Temperature: " + temp_max + "°C");
        temp_maxText.setLayoutX(50);
        temp_maxText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text pressureText = new Text("Pressure: " + pressure + " hPa");
        pressureText.setLayoutX(50);
        pressureText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text humidityText = new Text("Humidity: " + humidity + "%");
        humidityText.setLayoutX(50);
        humidityText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text visibilityText = new Text("Visibility: " + visibility + " m");
        visibilityText.setLayoutX(50);
        visibilityText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text wind_speedText = new Text("Wind Speed: " + wind_speed + " m/s");
        wind_speedText.setLayoutX(50);
        wind_speedText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text wind_degText = new Text("Wind Degree: " + wind_deg + "°");
        wind_degText.setLayoutX(50);
        wind_degText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text gustText = new Text("Gust: " + gust + " m/s");
        gustText.setLayoutX(50);
        gustText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text sunriseText = new Text("Sunrise: " + sunrise);
        sunriseText.setLayoutX(50);
        sunriseText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text sunsetText = new Text("Sunset: " + sunset);
        sunsetText.setLayoutX(50);
        sunsetText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        root.getChildren().add(mainText);
        root.getChildren().add(tempText);
        root.getChildren().add(feels_likeText);
        root.getChildren().add(temp_minText);
        root.getChildren().add(temp_maxText);
        root.getChildren().add(pressureText);
        root.getChildren().add(humidityText);
        root.getChildren().add(visibilityText);
        root.getChildren().add(wind_speedText);
        root.getChildren().add(wind_degText);
        root.getChildren().add(gustText);
        root.getChildren().add(sunriseText);
        root.getChildren().add(sunsetText);

        int width = 400;
        int height = 450;

        Scene popupScene = new Scene(root, width, height);
        popupStage.setScene(popupScene);
        popupStage.show();

        // create a warning popup if flag is true
        if (flag) {
            Stage warningStage = new Stage();
            warningStage.initModality(Modality.APPLICATION_MODAL);
            warningStage.initOwner(primaryStage);
            warningStage.setTitle(" Warning");

            // add the title page icon
            Image img_icon_warning = new Image(getClass().getResourceAsStream("assets\\\\title_bar_icon.png"));
            warningStage.getIcons().add(img_icon_warning);

            Pane root_warning = new Pane();

            Text warningText = new Text("Poor Weather Conditions. Please take necessary precautions.");
            // centre text
            warningText.setLayoutX(50);
            warningText.setLayoutY(50 + 17 - 20);
            warningText.setStyle("-fx-font-weight: bold;");

            Button close = new Button("Close");
            close.setLayoutX(50 + 160);
            close.setLayoutY(100);

            close.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    warningStage.close();
                }
            });

            root_warning.getChildren().add(warningText);
            root_warning.getChildren().add(close);

            int width_warning = 500;
            int height_warning = 100;

            Scene popupScene_warning = new Scene(root_warning, width_warning, height_warning);
            warningStage.setScene(popupScene_warning);
            warningStage.show();
        }
    }

    public void showPop_data(Stage primaryStage, Location loc, String db_type) {
        // make a window
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(primaryStage);
        popupStage.setTitle(" Pollution Data");

        // add the title page icon
        Image img_icon = new Image(getClass().getResourceAsStream("assets\\\\title_bar_icon.png"));
        popupStage.getIcons().add(img_icon);

        Pane root = new Pane();

        functional_layer.pollution_data_interface pollution = new functional_layer.source.pollution_data();
        functional_layer.pollution_data_interface.polution_data_struct pollution_data = pollution.getPollutionData(
                loc.latitude,
                loc.longitude, db_type);

        String aqi = pollution_data.aqi;
        String co = pollution_data.co;
        String no = pollution_data.no;
        String no2 = pollution_data.no2;
        String o3 = pollution_data.o3;
        String so2 = pollution_data.so2;
        String pm2_5 = pollution_data.pm2_5;
        String pm10 = pollution_data.pm10;
        String nh3 = pollution_data.nh3;

        int yOffset = 50; // Initial Y offset

        Text aqiText = new Text("AQI: " + aqi);
        aqiText.setLayoutX(50);
        aqiText.setLayoutY(yOffset + 17 - 20);
        aqiText.setStyle("-fx-font-weight: bold;");
        yOffset += 30;

        Text coText = new Text("CO: " + co + " µg/m³");
        coText.setLayoutX(50);
        coText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text noText = new Text("NO: " + no + " µg/m³");
        noText.setLayoutX(50);
        noText.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text no2Text = new Text("NO2: " + no2 + " µg/m³");
        no2Text.setLayoutX(50);
        no2Text.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text o3Text = new Text("O3: " + o3 + " µg/m³");
        o3Text.setLayoutX(50);
        o3Text.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text so2Text = new Text("SO2: " + so2 + " µg/m³");
        so2Text.setLayoutX(50);
        so2Text.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text pm2_5Text = new Text("PM2.5: " + pm2_5 + " µg/m³");
        pm2_5Text.setLayoutX(50);
        pm2_5Text.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text pm10Text = new Text("PM10: " + pm10 + " µg/m³");
        pm10Text.setLayoutX(50);
        pm10Text.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        Text nh3Text = new Text("NH3: " + nh3 + " µg/m³");
        nh3Text.setLayoutX(50);
        nh3Text.setLayoutY(yOffset + 17 - 20);
        yOffset += 30;

        root.getChildren().add(aqiText);
        root.getChildren().add(coText);
        root.getChildren().add(noText);
        root.getChildren().add(no2Text);
        root.getChildren().add(o3Text);
        root.getChildren().add(so2Text);
        root.getChildren().add(pm2_5Text);
        root.getChildren().add(pm10Text);
        root.getChildren().add(nh3Text);

        int width = 200;
        int height = 340;

        Scene popupScene = new Scene(root, width, height);
        popupStage.setScene(popupScene);
        popupStage.show();

        if (!aqi.equals("null")) {

            boolean flag = false;

            float aqi_val = Float.parseFloat(aqi);

            if (aqi_val > 300) {
                System.out.println("Poor Air Quality. Notification generated.");
                flag = true;
            }

            // create a warning popup if flag is true
            String warning_message = "Poor Air Quality (AQI = " + Float.toString(aqi_val)
                    + "). Please take necessary precautions.";

            if (flag) {
                Stage warningStage = new Stage();
                warningStage.initModality(Modality.APPLICATION_MODAL);
                warningStage.initOwner(primaryStage);
                warningStage.setTitle(" Warning");

                // add the title page icon
                Image img_icon_warning = new Image(getClass().getResourceAsStream("assets\\\\title_bar_icon.png"));
                warningStage.getIcons().add(img_icon_warning);

                Pane root_warning = new Pane();

                Text warningText = new Text(warning_message);
                // centre text
                warningText.setLayoutX(50);
                warningText.setLayoutY(50 + 17 - 20);
                warningText.setStyle("-fx-font-weight: bold;");

                Button close = new Button("Close");
                close.setLayoutX(50 + 160);
                close.setLayoutY(100);

                close.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        warningStage.close();
                    }
                });

                root_warning.getChildren().add(warningText);
                root_warning.getChildren().add(close);

                int width_warning = 500;
                int height_warning = 100;

                Scene popupScene_warning = new Scene(root_warning, width_warning, height_warning);
                warningStage.setScene(popupScene_warning);
                warningStage.show();
            }
        }

    }

    public static void main(String[] args) {
        String db;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the database type: ");
        db = sc.nextLine();
        while (!db.equals("sql") && !db.equals("txt")) {
            System.out.println("Invalid Database Type. Please enter again: ");
            db = sc.nextLine();
        }
        sc.close();

        launch(args);
    }
}

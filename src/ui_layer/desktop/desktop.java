package ui_layer.desktop;

import java.util.Scanner;

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

// import functional layer
import functional_layer.Location_Interfaces;
import functional_layer.source.locations_query;;

public class desktop extends Application {

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
        Button button_search = new Button("Search Weather Data");
        button_search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Search Weather Data");
            }
        });
        // set location on screen in the middle of the screen
        button_names.setLayoutX(width / 2 - 158);
        button_names.setLayoutY(height / 2 - 50);
        button_coord.setLayoutX(width / 2 - 158);
        button_coord.setLayoutY(height / 2 + 20);
        button_search.setLayoutX(width / 2 - 158);
        button_search.setLayoutY(height / 2 + 90);

        button_names.setPrefHeight(47);
        button_names.setPrefWidth(316);
        button_coord.setPrefHeight(47);
        button_coord.setPrefWidth(316);
        button_search.setPrefHeight(47);
        button_search.setPrefWidth(316);

        button_names.setFont(javafx.scene.text.Font.font("Cambria", 18));
        button_coord.setFont(javafx.scene.text.Font.font("Cambria", 18));
        button_search.setFont(javafx.scene.text.Font.font("Cambria", 18));

        Text text = new Text();
        text.setText("Weather Application");
        text.setFont(javafx.scene.text.Font.font("Cambria", 30));
        text.setLayoutX(width / 2 - 150 + 20);
        text.setLayoutY(125);

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

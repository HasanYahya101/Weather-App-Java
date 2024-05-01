package ui_layer.desktop;

import java.util.Random;

// import javafx libraries
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class desktop extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Generate random dimensions
        Random random = new Random();
        int width = random.nextInt(800) + 200; // Width between 200 and 1000
        int height = random.nextInt(600) + 200; // Height between 200 and 800

        // Generate random title
        String[] titles = { "Hello World!", "Random Window", "JavaFX Demo", "My App", "Custom Title" };
        String title = titles[random.nextInt(titles.length)];

        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> System.out.println("Hello World!"));

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, width, height);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

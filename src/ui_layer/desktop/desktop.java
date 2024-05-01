package ui_layer.desktop;

// import javafx libraries
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.*;

public class desktop extends Application {
    @Override
    public void start(Stage primaryStage) {
        int width = 1000;
        int height = 600;

        String title = " Weather Desktop Application";

        Image icon = new Image(getClass().getResourceAsStream("assets\\\\title_bar_icon.png"));
        primaryStage.getIcons().add(icon);
        StackPane root = new StackPane();
        Scene scene = new Scene(root, width, height);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

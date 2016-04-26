package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window = new Stage();
    Double grafHeight;
    Double grafWidth;

    public void start(Stage primaryStage){
        window = primaryStage;
        Group root = new Group();
        grafHeight = 350.0;
        grafWidth = 350.0;
        drawClass drw = new drawClass(grafHeight, grafWidth);
        drw.setAxisis();
        drw.setGraphic();
        root.getChildren().addAll(drw.getLayer1(), drw.getLayer2());
        drw.getLayer1().toFront();
        window.setTitle("Begin to draw");
        window.setScene(new Scene(root, 800, 600));
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

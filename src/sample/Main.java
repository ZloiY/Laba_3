package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window = new Stage();
    Double grafHeight;
    Double grafWidth;

    public void start(Stage primaryStage){
        window = primaryStage;
        HBox mainLayout = new HBox(10);
        VBox btnLayout = new VBox(10);
        Button scale = new Button("Scale");
        TextField zoomTF = new TextField();
        GraphicView graphic = new GraphicView();
        graphic.setTable();
        Group root = new Group();
        grafHeight = 350.0;
        grafWidth = 350.0;
        GraphicDraw drw = new GraphicDraw(grafHeight, grafWidth);
        drw.setScale(1.0);
        drw.setAxisis(graphic.getTable());
        drw.setGrid();
        root.getChildren().addAll(drw.getAxisis(), drw.getGraphic(), drw.getGrid(), drw.getCordinates());
        btnLayout.getChildren().addAll(scale, zoomTF);
        drw.getAxisis().toFront();
        drw.getGrid().toBack();
        scale.setOnAction(e -> {
            drw.setScale(Double.parseDouble(zoomTF.getText()));
            drw.zoom(graphic.getTable());
        });
        mainLayout.getChildren().addAll(root, btnLayout);
        window.setTitle("Begin to draw");
        window.setScene(new Scene(mainLayout, 800, 600));
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

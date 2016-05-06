package sample;

import com.sun.deploy.panel.ControlPanel;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oracle.jrockit.jfr.events.EventControl;

import java.util.EventListener;

public class Main extends Application {

    Stage window = new Stage();
    Double grafHeight;
    Double grafWidth;
    Double scale;
    Double frstX;
    Double frstY;
    Double scndX;
    Double scndY;
    GraphicDraw drw;
    GraphicView graphic;

    public void start(Stage primaryStage){
        window = primaryStage;
        Scene wndScene;
        HBox mainLayout = new HBox(10);
        VBox btnLayout = new VBox(10);
        graphic = new GraphicView();
        graphic.setTable();
        Group root = new Group();
        grafHeight = 350.0;
        grafWidth = 350.0;
        scale = 1.0;
        drw = new GraphicDraw(grafHeight, grafWidth);
        drw.setScale(1.0);
        drw.setAxisis(graphic.getTable());
        drw.setGrid();
        root.getChildren().addAll(drw.getAxisis(), drw.getGraphic(), drw.getGrid(), drw.getCordinates());
        btnLayout.getChildren().addAll();
        drw.getAxisis().toFront();
        drw.getGrid().toBack();
        root.setOnScroll(onScrollEventHandler);
        root.setOnMouseClicked(mouseBtnEventHandler);
        root.setOnDragDetected(mouseDrgEventHandler);
        mainLayout.getChildren().addAll(root, btnLayout);
        window.setTitle("Begin to draw");
        wndScene = new Scene(mainLayout, 800, 600);
        window.setScene(wndScene);
        window.show();
    }

    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>(){

        public void handle(ScrollEvent event){
            if (event.isControlDown() && event.getDeltaY() > 0){
                scale += 0.1;
                drw.setScale(scale);
                drw.zoom(graphic.getTable());
            }
            if (event.isControlDown() && event.getDeltaY() < 0 && scale > 0.1){
                scale -= 0.1;
                drw.setScale(scale);
                drw.zoom(graphic.getTable());
            }

        }
    };

    private EventHandler<MouseEvent> mouseBtnEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
            if(event.isPrimaryButtonDown()){
                frstX = event.getSceneX();
                frstY = event.getSceneY();
            }
        }
    };

    private EventHandler<MouseEvent> mouseDrgEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
                scndX = event.getSceneX();
                scndY = event.getSceneY();
                drw.setDelta(scndX -frstX, scndY -frstY, graphic.getTable());
        }
    };


    public static void main(String[] args) {
        launch(args);
    }

}

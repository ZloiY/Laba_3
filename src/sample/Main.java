package sample;

import com.sun.deploy.panel.ControlPanel;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
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
    GraphicDraw drw;
    GraphicView graphic;
    Group root;
    FileMake file;

    public void start(Stage primaryStage){
        window = primaryStage;
        Scene wndScene;
        HBox mainLayout = new HBox(10);
        VBox btnLayout = new VBox(10);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);
        TextField nmbOfRowsTF = new TextField();
        TextField nmbOfSymbolsTF = new TextField();
        Label nmbOfRowsLbl = new Label("Enter number of rows in text file.");
        Label nmbOfSymbolsLbl = new Label("Enter number of symbols in string.");
        grid.add(nmbOfSymbolsLbl,0,0);
        grid.add(nmbOfSymbolsTF,0,1);
        grid.add(nmbOfRowsLbl,1,0);
        grid.add(nmbOfRowsTF,1,1);
        Button makeFileBtn = new Button("Create file");
        makeFileBtn.setOnAction(e ->{
            file = new FileMake(Integer.parseInt(nmbOfSymbolsTF.getText()),
                    Integer.parseInt(nmbOfRowsTF.getText()));
        });
        grid.add(makeFileBtn, 0,2);
        graphic = new GraphicView();
        graphic.setTable();
        root = new Group();
        grafHeight = 350.0;
        grafWidth = 350.0;
        scale = 1.0;
        drw = new GraphicDraw(grafHeight, grafWidth);
        drw.setScale(1.0);
        drw.setAxisis(graphic.getTable());
        drw.setGrid();
        root.getChildren().addAll(drw.getAxisis(), drw.getGraphic(), drw.getGrid(), drw.getCordinates());
        btnLayout.getChildren().addAll(grid);
        drw.getAxisis().toFront();
        drw.getGrid().toBack();
        root.setOnScroll(onScrollEventHandler);
        MouseLocation lastMouseLocation = new MouseLocation();
        root.addEventHandler(MouseEvent.MOUSE_PRESSED,(final MouseEvent mouseEvent) ->{
            lastMouseLocation.x = mouseEvent.getSceneX();
            lastMouseLocation.y = mouseEvent.getSceneY();
        });
        root.addEventHandler(MouseEvent.MOUSE_DRAGGED, (final MouseEvent mouseEvent) ->{
            double deltaX = mouseEvent.getSceneX() - lastMouseLocation.x;
            double deltaY = mouseEvent.getSceneY() - lastMouseLocation.y;
            drw.setDelta(deltaX, deltaY, graphic.getTable());
            lastMouseLocation.x = mouseEvent.getSceneX();
            lastMouseLocation.y = mouseEvent.getSceneY();
        });
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

    public static void main(String[] args) {
        launch(args);
    }

    private static final class MouseLocation {
        public double x, y;
    }

}

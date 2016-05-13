package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    Stage window = new Stage();
    Double grafHeight;
    Double grafWidth;
    Double scale;
    GraphicDraw drw;
    GraphicView graphic;
    Group root;
    FileMake file;
    TableView<Dot> graphicData;
    List<String> fromFileList;
    List<String> templateList;
    SettingWindow pageWindow;
    Label zoomLbl;
    SearchAlg searchAlg;
    VBox btnLayout;

    public void start(Stage primaryStage){
        window = primaryStage;
        Scene wndScene;
        HBox mainLayout = new HBox(10);
        VBox graphicLayout = new VBox(10);
        btnLayout = new VBox(10);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);
        //TextField nmbOfRowsTF = new TextField();
        TextField nmbOfSymbolsTF = new TextField();
        //Label nmbOfRowsLbl = new Label("Enter number of rows in text file.");
        Label nmbOfSymbolsLbl = new Label("Enter number of symbols in string.");
        zoomLbl = new Label();
        grafHeight = 500.0;
        grafWidth = 500.0;
        root = new Group();
        drw = new GraphicDraw(grafHeight, grafWidth);
        root.getChildren().addAll(drw.getAxisis(), drw.getGraphic(), drw.getGrid(), drw.getCordinates());
        grid.add(nmbOfSymbolsLbl,0,0);
        grid.add(nmbOfSymbolsTF,0,1);
       // grid.add(nmbOfRowsLbl,1,0);
        //grid.add(nmbOfRowsTF,1,1);
        //Button makeFileBtn = new Button("Create file");
        graphicData = new TableView<Dot>();
        graphic = new GraphicView();
        graphic.setColumnsRandTbl(graphicData);
        graphic.setTable(graphicData);
        drw.setDelta(0d,0d,graphic.getTable());
        btnLayout.getChildren().add(graphic.getTable());
        pageWindow = new SettingWindow();
        btnLayout.getChildren().add(pageWindow.view(graphic.getTable(), 1, 1));
        Button startBtn = new Button("Start");
        grid.add(startBtn, 0, 3);
        startBtn.setOnAction(e ->{
            fromFileList = new ArrayList<>();
            templateList = new ArrayList<String>();
            FileMake file = new FileMake();
            RandomString randomStr = new RandomString();
            //file = new FileMake(Integer.parseInt(nmbOfSymbolsTF.getText()), Integer.parseInt(nmbOfRowsTF.getText()));
            for (int nmbrOfRows = 1; nmbrOfRows <= Integer.parseInt(nmbOfSymbolsTF.getText()); nmbrOfRows++){
                templateList.add(randomStr.getRandString(nmbrOfRows));
            }
            fromFileList = file.readFile("graphic.txt");
            searchAlg = new SearchAlg();
            myThread.start();
        });
        //grid.add(makeFileBtn, 0,2);
        scale = 3.0;
        Double percentScale= roundResult(scale, 100);
        Double labelScale = percentScale*100;
        zoomLbl.setText(labelScale.toString() + "%");
        drw.setScale(3.0);
        btnLayout.getChildren().add(grid);
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
        graphicLayout.getChildren().addAll(root,zoomLbl);
        mainLayout.getChildren().addAll(graphicLayout, btnLayout);
        window.setTitle("Begin to draw");
        wndScene = new Scene(mainLayout, 800, 600);
        window.setScene(wndScene);
        window.show();
    }

    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>(){

        public void handle(ScrollEvent event){
            if (event.isControlDown() && event.getDeltaY() > 0){
                scale += 0.1;
                Double percentScale= roundResult(scale, 100);
                Double labelScale = percentScale*100;
                zoomLbl.setText(labelScale.toString()+"%");
                drw.setScale(scale);
                drw.zoom(graphic.getTable());
            }
            if (event.isControlDown() && event.getDeltaY() < 0 && scale > 0.1){
                scale -= 0.1;
                Double percentScale= roundResult(scale, 100);
                Double labelScale = percentScale*100;
                zoomLbl.setText(labelScale.toString()+"%");
                drw.setScale(scale);
                drw.zoom(graphic.getTable());
            }

        }
    };

    private double roundResult (double d, int precise) {

        precise = 10^precise;
        d = d*precise;
        int i = (int) Math.round(d);
        return (double) i/precise;

    }

    Thread myThread = new Thread(new Runnable() {

        public void run() {
            for (int indexOfTemplate = 0; indexOfTemplate < templateList.size()-1; indexOfTemplate++) {
                Long startTime = System.nanoTime();
                if (searchAlg.getFirstEntry(fromFileList.get(0), templateList.get(indexOfTemplate)) != -1) {
                    Long endTime = System.nanoTime();
                    Long duration = (endTime - startTime) / 10000;
                    Integer ts = templateList.get(indexOfTemplate).length();
                    graphicData.getItems().add(new Dot(ts.doubleValue(), duration.doubleValue()));
                }else{
                    Integer ts = templateList.get(indexOfTemplate).length();
                    graphicData.getItems().add(new Dot(ts.doubleValue(), 80.0));
                }
                graphic.getItems(graphicData);
                graphic.setItems();
                drw.setDelta(0.0, 0.0, graphic.getTable());
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){}
            }
        }
    });

    public static void main(String[] args) {
        launch(args);
    }

    private static final class MouseLocation {
        public double x, y;
    }

}

package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by ZloiY on 27.04.2016.
 */
public class GraphicView {
    private TableView<GraphicData> table = new TableView<>();
    private ObservableList<GraphicData> tbl = FXCollections.observableArrayList();

    public TableView<GraphicData> getTable() {
        return table;
    }

    public void setTable(){
        TableColumn<GraphicData, String> xClmn = new TableColumn<>("X");
        xClmn.setCellValueFactory(new PropertyValueFactory<>("x"));
        TableColumn<GraphicData, String> yClmn = new TableColumn<>("Y");
        yClmn.setCellValueFactory(new PropertyValueFactory<>("y"));
        table.setItems(getItems());
        table.getColumns().addAll(xClmn, yClmn);
    }

    public ObservableList<GraphicData> getItems(){
        tbl.add(new GraphicData(45.0, 45.0));
        tbl.add(new GraphicData(80.0, 150.0));
        tbl.add(new GraphicData(180.0, 230.0));
        tbl.add(new GraphicData(300.0, 300.0));
        return tbl;
    }
}

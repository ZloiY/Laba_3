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
    TableColumn<GraphicData, String> xClmn = new TableColumn<>("X");
    TableColumn<GraphicData, String> yClmn = new TableColumn<>("Y");

    public TableView<GraphicData> getTable() {
        return table;
    }

    public void setTable(TableView<GraphicData> data){
        xClmn.setCellValueFactory(new PropertyValueFactory<>("x"));
        yClmn.setCellValueFactory(new PropertyValueFactory<>("y"));
        setColumnsRandTbl(data);
        table.setItems(getItems(data));
        table.getColumns().addAll(xClmn, yClmn);
    }

    public ObservableList<GraphicData> getItems(TableView<GraphicData> data){
        tbl.addAll(data.getItems());
        return tbl;
    }

    private void setColumnsRandTbl(TableView<GraphicData> data){
        data.getColumns().addAll(xClmn, yClmn);
    }
}

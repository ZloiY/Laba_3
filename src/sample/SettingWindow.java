package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Created by NoRFolf on 05.04.2016.
 */
public class SettingWindow {

    public Integer allPages = 1;
    public ObservableList<Dot> curPage;
    public TableView<Dot> allData = new TableView<>();

    public void setAllData(TableView<Dot> table){
        allData.setItems(table.getItems());
    }

    public Integer getAllPages(){return allPages;}

    public void action(TableView<Dot> table, Double numrows, Integer numPages){
        table.setFixedCellSize(table.getHeight() / numrows);
        table.setMaxHeight(Math.ceil(table.getFixedCellSize()
                * numrows));
        table.setMinHeight(Math.ceil(table.getFixedCellSize()
                * numrows));
        pages(table, numPages, numrows.intValue());
    }

    public void pages(TableView<Dot> mainTable, Integer numPages, Integer numrows){
        allPages = mainTable.getItems().size() / numrows;
        curPage = FXCollections.observableArrayList();
        Integer i = numPages;
        if (i.equals(1)){
            mainTable.setItems(allData.getItems());
            curPage.clear();
            for(i = 0; i < numrows; i++){
                curPage.add(mainTable.getItems().get(i));
            }
            mainTable.setItems(curPage);
        }else {
            if (i > 1) {
                mainTable.setItems(allData.getItems());
                curPage.clear();
                i--;
                int j;
                for (i = i*numrows, j = numrows; i < j * (numPages)
                        && i < mainTable.getItems().size(); i++) {
                    curPage.add(mainTable.getItems().get(i));
                }
                mainTable.setItems(curPage);
            }
        }
    }

    public void firstPage(TableView<Dot> mainTable,  Integer numrows){
        mainTable.setItems(allData.getItems());
        curPage.clear();
        for(int i = 1; i-1 < numrows; i++){
            curPage.add(allData.getItems().get(i));
        }
        mainTable.setItems(curPage);
    }

    public void lastPage(TableView<Dot> mainTable,  Integer numrows){
        mainTable.setItems(allData.getItems());
        for (int a = 1; a < mainTable.getItems().size(); a++){
            curPage.clear();
            int i = a;
            int j;
            for (i = i*numrows, j = numrows; i < j * (a+1)
                    && i < mainTable.getItems().size(); i++) {
                curPage.add(mainTable.getItems().get(i));
            }
            if (i == mainTable.getItems().size()){
                mainTable.setItems(curPage);
                return;
            }
        }
    }

    public GridPane view(TableView<Dot> mainTable, Integer currPageIndex, Integer allPgs){
        GridPane mainLayout = new GridPane();
        HBox pgsBox = new HBox(10);
        HBox nextPrevBox = new HBox(10);
        HBox numrowsBox = new HBox(10);
        TextField currPg = new TextField();
        TextField numbofrows = new TextField("5");
        numbofrows.setMaxWidth(30);
        currPg.setMaxWidth(40);
        currPg.setText(currPageIndex.toString());
        Label allPg = new Label("/" + allPgs);
        Label txtFieldLbl = new Label("Enter number of rows:");
        Button nextBtn = new Button(">>");
        Button prevBtn = new Button("<<");
        Button frstPg = new Button("First Page");
        Button lstPg = new Button("Last Page");
        Button aplBtn = new Button("Apply");
        pgsBox.getChildren().addAll(currPg, allPg);
        nextPrevBox.getChildren().addAll(prevBtn, nextBtn);
        numrowsBox.getChildren().addAll(numbofrows,aplBtn);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.add(pgsBox, 0, 0);
        mainLayout.add(txtFieldLbl, 2, 0);
        mainLayout.add(numrowsBox, 2, 1);
        mainLayout.add(nextPrevBox, 1, 0);
        mainLayout.add(frstPg, 0, 1);
        mainLayout.add(lstPg, 1, 1);
        nextBtn.setOnAction(e -> {
            Integer nextPg = Integer.parseInt(currPg.getText()) + 1;
            currPg.setText(nextPg.toString());
            pages(mainTable,  nextPg, Integer.parseInt(numbofrows.getText()));
        });
        prevBtn.setOnAction(e -> {
            Integer prevPg = Integer.parseInt(currPg.getText()) - 1;
            currPg.setText(prevPg.toString());
            pages(mainTable,  prevPg, Integer.parseInt(numbofrows.getText()));
        });
        aplBtn.setOnAction(e -> {
            setAllData(mainTable);
            action(mainTable, Double.parseDouble(numbofrows.getText()), Integer.parseInt(currPg.getText()));
        });
        frstPg.setOnAction(e -> firstPage(mainTable, Integer.parseInt(numbofrows.getText())));
        lstPg.setOnAction(e -> lastPage(mainTable, Integer.parseInt(numbofrows.getText())));

        return mainLayout;
    }

}

package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

/**
 * Created by ZloiY on 26.04.2016.
 */
public class GraphicDraw {

    private Double lengthOY;
    private Double lengthOX;
    private Double offset;
    private Canvas axisis;
    private Canvas graphic;
    private Canvas grid;
    private Canvas cordinates;
    private GraphicsContext gc2;
    private GraphicsContext gc1;
    private GraphicsContext gcGrid;
    private GraphicsContext gcCordinates;
    private Double scale;
    Double wndHeight;
    Double wndWidth;
    Double range;

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }



    GraphicDraw(Double height, Double width){
        wndHeight = height;
        wndWidth = width;
        axisis = new Canvas(wndWidth, wndHeight);
        graphic = new Canvas(wndWidth, wndHeight);
        cordinates = new Canvas(wndWidth, wndHeight);
        gc1 = axisis.getGraphicsContext2D();
        gc2 = graphic.getGraphicsContext2D();
        gcCordinates = cordinates.getGraphicsContext2D();
        grid = new Canvas(wndWidth, wndHeight);
        grid.setMouseTransparent(true);
        gcGrid = grid.getGraphicsContext2D();
        gcGrid.setStroke(Color.LIGHTGREY);
        gcGrid.setLineWidth(1);
        scale = 1.0;
    }

    public Canvas getAxisis() {
        return axisis;
    }

    public Canvas getGraphic() {
        return graphic;
    }

    public Canvas getGrid() {
        return grid;
    }

    public Canvas getCordinates() {
        return cordinates;
    }

    public void setAxisis(TableView<GraphicData> table){
        gc1.setStroke(Color.BLACK);
        gc1.setFill(Color.WHITESMOKE);
        gc1.setLineWidth(3);
        offset = 45.0;
        gc1.strokeLine(offset, offset, offset, wndHeight - offset);
        gc1.strokeLine(offset, wndHeight - offset, wndWidth - offset, wndHeight - offset);
        gc1.strokeLine(offset, offset, offset - 10, offset + 20);
        gc1.strokeLine(offset, offset, offset + 10, offset + 20);
        gc1.strokeLine(wndWidth - offset, wndHeight - offset, wndWidth - offset - 20, wndHeight - offset - 10);
        gc1.strokeLine(wndWidth - offset, wndHeight - offset, wndWidth - offset - 20, wndHeight - offset + 10);
        lengthOY = wndHeight -2* offset -50;
        lengthOX = wndWidth -2* offset -50;
        gc1.fillRect(0, 0, wndWidth, offset);
        gc2.fillRect(wndWidth, 0, offset, wndHeight);
        gc1.setLineWidth(1);
        gc1.strokeText("0.0", offset - 20, wndHeight - offset + 20);
        setGraphic(table, scale);
        for (double strokesOY = 1.0; strokesOY < lengthOY/range; strokesOY++){
            gc1.setLineWidth(3);
            gc1.strokeLine(offset - 10, wndHeight - range * strokesOY - offset, offset + 10, wndHeight - range * strokesOY - offset);
        }
        for (double strokesOX = 1.0; strokesOX < lengthOX/range; strokesOX++){
            gc1.setLineWidth(3);
            gc1.strokeLine(range * strokesOX + offset, wndHeight - offset - 10, range * strokesOX + offset, wndHeight - offset + 10);
        }
    }

    public void setGrid(){
        for (double line = 1.0; line < (lengthOX+50)/range; line++){
            gcGrid.strokeLine(range *line +offset, wndHeight -offset, range *line +offset, offset);
        }
        for(double line = 1.0; line < (lengthOY+50)/range; line++){
            gcGrid.strokeLine(offset, wndHeight -range *line -offset, wndWidth -offset, wndHeight -range *line -offset);
        }
    }

    public void setGraphic(TableView<GraphicData> table, Double scale){
        gc2.setStroke(Color.DARKGREEN);
        gc2.setLineWidth(5);
        for (int index = 1; index < table.getItems().size(); index++){
            gc2.strokeLine(table.getItems().get(index - 1).getX() * scale - offset * (scale - 1) + offset,
               wndHeight - table.getItems().get(index - 1).getY() * scale + offset * (scale - 1) - offset,
               table.getItems().get(index).getX() * scale - offset * (scale - 1) + offset,
               wndHeight - table.getItems().get(index).getY() * scale + offset * (scale - 1) - offset);
        }
        setCordinates(table);
    }

    public void zoom(TableView<GraphicData> table){
        gc2.clearRect(0, 0, wndHeight, wndWidth);
        setGraphic(table, scale);
        gcCordinates.clearRect(0, 0, wndWidth, wndHeight);
        setAxisis(table);
    }

    public void setCordinates(TableView<GraphicData> table){
        int lastElem = table.getItems().size() -1;
        Double oY;
        Double oX;
        boolean outOfBoundsOY;
        boolean outOfBoundsOX;
        if (table.getItems().get(lastElem).getY() < lengthOY/scale){
            oY = (table.getItems().get(0).getY()/scale + table.getItems().get(lastElem).getY()/scale)/5*scale;
            outOfBoundsOY = false;
        }else{
            oY = (table.getItems().get(0).getY()/scale + lengthOY/scale)/5*scale;
            outOfBoundsOY = true;
        }
        if (table.getItems().get(lastElem).getX() < lengthOX/scale){
            oX = (table.getItems().get(0).getX()/scale + table.getItems().get(lastElem).getX()/scale)/5*scale;
            outOfBoundsOX = false;
        }else{
            oX = (table.getItems().get(0).getX()/scale + lengthOX/scale)/5*scale;
            outOfBoundsOX = true;
        }
        setRange(oY, oX, outOfBoundsOX, outOfBoundsOY, lastElem, table);
        setGrid();

        for (double strokesOY = 1.0; strokesOY < lengthOY/range; strokesOY++){
            gcCordinates.setLineWidth(1);
            Double tempOY = oY *strokesOY;
            tempOY = Math.floor(tempOY *100)/100;
            gcCordinates.strokeText(tempOY.toString(), 1, wndHeight - offset - range * strokesOY + 5);
        }
        for (double strokesOX = 1.0; strokesOX < lengthOX/range; strokesOX++){
            gcCordinates.setLineWidth(1);
            Double tempOX = oX *strokesOX;
            tempOX = Math.floor(tempOX *100)/100;
            gcCordinates.strokeText(tempOX.toString(), range * strokesOX - 10 + offset, wndHeight - 5);
        }

    }

    public void setRange(Double avrOY, Double avrOX, Boolean outOfBoundsOX, Boolean outOfBoundsOY,
                         Integer lastElement, TableView<GraphicData> table){
        if (outOfBoundsOX){
            Double quantity = lengthOX/avrOX;
            range = lengthOX/quantity;
        }else{
            Double quantity = table.getItems().get(lastElement).getX()/avrOX;
            range = lengthOX/quantity;
        }

        if (outOfBoundsOY){
            Double quantity = lengthOY/avrOY;
            range = lengthOY/quantity;
        }else{
            Double quantity = table.getItems().get(lastElement).getY()/avrOY;
            range = lengthOY/quantity;
        }
    }

}

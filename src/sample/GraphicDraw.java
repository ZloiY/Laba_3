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
    private GraphicsContext gcGraphic;
    private GraphicsContext gcAxisis;
    private GraphicsContext gcGrid;
    private GraphicsContext gcCordinates;
    private Double scale;
    Double deltaX;
    Double deltaY;
    Double step;
    Double wndHeight;
    Double wndWidth;
    Double rangeOX;
    Double rangeOY;

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    GraphicDraw(Double height, Double width){
        wndHeight = height;
        wndWidth = width;
        deltaX =0.0;
        deltaY =0.0;
        axisis = new Canvas(wndWidth, wndHeight);
        graphic = new Canvas(wndWidth, wndHeight);
        cordinates = new Canvas(wndWidth, wndHeight);
        gcAxisis = axisis.getGraphicsContext2D();
        gcGraphic = graphic.getGraphicsContext2D();
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
        gcAxisis.setStroke(Color.BLACK);
        gcAxisis.setFill(Color.WHITESMOKE);
        gcAxisis.setLineWidth(3);
        offset = 45.0;
        gcAxisis.strokeLine(offset, offset, offset, wndHeight - offset);
        gcAxisis.strokeLine(offset, wndHeight - offset, wndWidth - offset, wndHeight - offset);
        gcAxisis.strokeLine(offset, offset, offset - 10, offset + 20);
        gcAxisis.strokeLine(offset, offset, offset + 10, offset + 20);
        gcAxisis.strokeLine(wndWidth - offset, wndHeight - offset, wndWidth - offset - 20, wndHeight - offset - 10);
        gcAxisis.strokeLine(wndWidth - offset, wndHeight - offset, wndWidth - offset - 20, wndHeight - offset + 10);
        lengthOY = wndHeight -2* offset -50;
        lengthOX = wndWidth -2* offset -50;
        gcAxisis.fillRect(0, 0, wndWidth, offset);
        gcGraphic.fillRect(wndWidth, 0, offset, wndHeight);
        gcAxisis.setLineWidth(1);
        if(scale == 1.0){
            step = 20.0;
        }else{
            if (lengthOX/rangeOX/scale < 20.0){
                step = 20.0 +lengthOX/rangeOX/scale;
            }
        }
        setGraphic(table, scale);
        for (double strokesOY = 0.0; strokesOY < lengthOY/rangeOY; strokesOY++){
            gcAxisis.setLineWidth(3);
            gcAxisis.strokeLine(offset - 10 -deltaX, wndHeight - rangeOY * strokesOY - offset, offset + 10 -deltaX, wndHeight - rangeOY * strokesOY - offset);
        }
        for (double strokesOX = 0.0; strokesOX < lengthOX/rangeOX; strokesOX++){
            gcAxisis.setLineWidth(3);
            gcAxisis.strokeLine(rangeOX * strokesOX + offset, wndHeight - offset - 10 - deltaY, rangeOX * strokesOX + offset, wndHeight - offset + 10 - deltaY);
        }
    }

    public void setGrid(){
        for (double line = 0.0; line < (lengthOX+50)/rangeOX; line++){
            gcGrid.strokeLine(rangeOX *line +offset -deltaX, wndHeight -offset, rangeOX *line +offset -deltaX, offset);
        }
        for(double line = 0.0; line < (lengthOY+50)/rangeOY; line++){
            gcGrid.strokeLine(offset, wndHeight -rangeOY *line -offset -deltaY, wndWidth -offset, wndHeight -rangeOY *line -offset -deltaY);
        }
    }

    public void setGraphic(TableView<GraphicData> table, Double scale){
        gcGraphic.setStroke(Color.DARKGREEN);
        gcGraphic.setLineWidth(5);
        for (int index = 1; index < table.getItems().size(); index++){
            gcGraphic.strokeLine(table.getItems().get(index - 1).getX() * scale + offset +deltaX,
                    wndHeight - table.getItems().get(index - 1).getY() * scale - offset +deltaY,
                    table.getItems().get(index).getX() * scale + offset +deltaX,
                    wndHeight - table.getItems().get(index).getY() * scale - offset +deltaY);
        }
        setCordinates(table);
    }

    public void zoom(TableView<GraphicData> table){
        gcGraphic.clearRect(0, 0, wndHeight, wndWidth);
        setGraphic(table, scale);
        gcCordinates.clearRect(0, 0, wndWidth, wndHeight);
        gcAxisis.clearRect(0, 0, wndWidth, wndHeight);
        gcGrid.clearRect(0, 0, wndWidth, wndHeight);
        setAxisis(table);
    }

    public void setCordinates(TableView<GraphicData> table){
        int lastElem = table.getItems().size() -1;
        setRange(table, lastElem);
        setGrid();
        Double tempOY = 0.0;
        Double tempOX = 0.0;
        for (double strokesOY = 0.0; strokesOY < lengthOY/rangeOY; strokesOY++){
            gcCordinates.setLineWidth(1);
            tempOY = (rangeOY *strokesOY)/scale;
            gcCordinates.strokeText(String.valueOf(tempOY.intValue()), 1 +deltaX, wndHeight - offset - rangeOY * strokesOY + 5);
        }
        for (double strokesOX =0.0; strokesOX < lengthOX/rangeOX; strokesOX++){
            gcCordinates.setLineWidth(1);
            tempOX = (rangeOX *strokesOX)/scale;
            gcCordinates.strokeText(String.valueOf(tempOX.intValue()), rangeOX * strokesOX - 10 + offset, wndHeight - 5 +deltaY);
        }

    }

    public void setRange(TableView<GraphicData> table, Integer lastElem){
        if (table.getItems().get(lastElem).getY() < lengthOY/scale){
            rangeOY = (table.getItems().get(0).getY()+deltaY/scale + table.getItems().get(lastElem).getY()/scale)/5*scale;
        }else{
            rangeOY = (table.getItems().get(0).getY()/scale + lengthOY/scale)/5*scale;
        }

        if (table.getItems().get(lastElem).getX() < lengthOX/scale){
            rangeOX = (table.getItems().get(0).getX()/scale + table.getItems().get(lastElem).getX()/scale)/5*scale;
        }else{
            rangeOX = (table.getItems().get(0).getX()/scale + lengthOX/scale)/5*scale;
        }

    }

    public void setDelta(Double deltaX, Double deltaY, TableView<GraphicData> table){
        deltaX = this.deltaX;
        deltaY = this.deltaY;
        setAxisis(table);
    }

}

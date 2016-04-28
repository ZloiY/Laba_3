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
    private GraphicsContext gc2;
    private GraphicsContext gc1;
    private GraphicsContext gcGrid;
    private Double scale;
    Double wndHeight;
    Double wndWidth;

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
        gc1 = axisis.getGraphicsContext2D();
        gc2 = graphic.getGraphicsContext2D();
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

    public void setAxisis(){
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
        lengthOY = wndHeight -2* offset;
        lengthOX = wndWidth -2* offset;
        gc1.fillRect(0, 0, wndWidth, offset);
        gc2.fillRect(wndWidth, 0, offset, wndHeight);
        Double range = 30.0;
        gc1.setLineWidth(1);
        gc1.strokeText("0.0", offset - 20, wndHeight - offset + 20);
        for (double strokesOY = 1.0; strokesOY < lengthOY/range; strokesOY++){
            gc1.setLineWidth(3);
            gc1.strokeLine(offset - 10, wndHeight - range * strokesOY - offset, offset + 10, wndHeight - range * strokesOY - offset);
            Double cordinates = range*strokesOY;
            gc1.setLineWidth(1);
            gc1.strokeText(cordinates.toString(), 1, wndHeight - offset - range * strokesOY + 5);
        }
        for (double strokesOX = 1.0; strokesOX < lengthOX/range; strokesOX++){
            gc1.setLineWidth(3);
            gc1.strokeLine(range * strokesOX + offset, wndHeight - offset - 10, range * strokesOX + offset, wndHeight - offset + 10);
            Double cordiantes = range*strokesOX;
            gc1.setLineWidth(1);
            gc1.strokeText(cordiantes.toString(), range * strokesOX - 10 + offset, wndHeight - 5);
        }
    }

    public void setGrid(){
        grid = new Canvas(wndWidth, wndHeight);
        grid.setMouseTransparent(true);
        gcGrid = grid.getGraphicsContext2D();
        gcGrid.setStroke(Color.LIGHTGREY);
        gcGrid.setLineWidth(1);
        Double range = 30.0;
        for (double line = 1.0; line < lengthOX/range; line++){
            gcGrid.strokeLine(range *line +offset, wndHeight -offset, range *line +offset, offset);
        }
        for(double line = 1.0; line < lengthOY/range; line++){
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
    }

    public void zoom(TableView<GraphicData> table){
        gc2.clearRect(0, 0, wndHeight, wndWidth);
        setGraphic(table, scale);
    }

}

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

    public GraphicDraw(Double height, Double width) {
        wndHeight = height;
        wndWidth = width;
        deltaX = 0.0;
        deltaY = 0.0;
        offset = 45.0;
        lengthOY = wndHeight - 2 * offset - 50;
        lengthOX = wndWidth - 2 * offset - 50;
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
        scale = 3.0;
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

    public void setAxisis(TableView<Dot> table) {
        gcAxisis.setStroke(Color.BLACK);
        gcAxisis.setFill(Color.WHITESMOKE);
        gcAxisis.setLineWidth(3);
        gcAxisis.strokeLine(offset, offset, offset, wndHeight - offset + deltaY);
        gcAxisis.strokeLine(offset + deltaX, wndHeight - offset, wndWidth - offset, wndHeight - offset);
        gcAxisis.strokeLine(offset, offset, offset - 10, offset + 20);
        gcAxisis.strokeLine(offset, offset, offset + 10, offset + 20);
        gcAxisis.strokeLine(wndWidth - offset, wndHeight - offset, wndWidth - offset - 20, wndHeight - offset - 10);
        gcAxisis.strokeLine(wndWidth - offset, wndHeight - offset, wndWidth - offset - 20, wndHeight - offset + 10);
        gcAxisis.fillRect(0, 0, wndWidth, offset);
        gcGraphic.fillRect(wndWidth, 0, offset, wndHeight);
        gcAxisis.setLineWidth(1);
        gcAxisis.strokeText("1*10^-5 sec", 1, offset );
        gcAxisis.strokeText("Num.symb.", wndWidth -offset -20, wndHeight -offset +30);
        setGraphic(table);
        for (double strokesOY = 0.0; strokesOY < (lengthOY + deltaY) / rangeOY * scale; strokesOY++) {
            gcAxisis.setLineWidth(3);
            gcAxisis.strokeLine(offset - 10, wndHeight - rangeOY * strokesOY / scale - offset + deltaY, offset + 10, wndHeight - rangeOY * strokesOY / scale - offset + deltaY);
        }
        for (double strokesOX = 0.0; strokesOX < (lengthOX + Math.abs(deltaX)) / rangeOX * scale; strokesOX++) {
            gcAxisis.setLineWidth(3);
            gcAxisis.strokeLine(rangeOX * strokesOX / scale + offset + deltaX, wndHeight - offset - 10, rangeOX * strokesOX / scale + offset + deltaX, wndHeight - offset + 10);
        }
    }

    public void setGrid() {
        for (double line = 0.0; line < (lengthOX + 50 + Math.abs(deltaX)) / rangeOX * scale; line++) {
            gcGrid.strokeLine(rangeOX * line / scale + offset + deltaX, wndHeight - offset, rangeOX * line / scale + offset + deltaX, offset);
        }
        for (double line = 0.0; line < (lengthOY + 50 + deltaY) / rangeOY * scale; line++) {
            gcGrid.strokeLine(offset, wndHeight - rangeOY * line / scale - offset + deltaY, wndWidth - offset, wndHeight - rangeOY * line / scale - offset + deltaY);
        }
    }

    public void setGraphic(TableView<Dot> table) {
        gcGraphic.setStroke(Color.DARKGREEN);
        gcGraphic.setFill(Color.GREEN);
        gcGraphic.setLineWidth(2);
        for (int index = 1; index < table.getItems().size(); index++) {
            gcGraphic.strokeLine(table.getItems().get(index - 1).getX() * scale + offset + deltaX,
                    wndHeight - table.getItems().get(index - 1).getY() * scale - offset + deltaY,
                    table.getItems().get(index).getX() * scale + offset + deltaX,
                    wndHeight - table.getItems().get(index).getY() * scale - offset + deltaY);
            gcGraphic.fillOval(table.getItems().get(index).getX() * scale + offset + deltaX - 4,
                    wndHeight - table.getItems().get(index).getY() * scale - offset + deltaY - 4, 8, 8);
        }
        setCordinates(table);
    }

    public void zoom(TableView<Dot> table) {
        gcGraphic.clearRect(0, 0, wndHeight, wndWidth);
        setGraphic(table);
        gcCordinates.clearRect(0, 0, wndWidth, wndHeight);
        gcAxisis.clearRect(0, 0, wndWidth, wndHeight);
        gcGrid.clearRect(0, 0, wndWidth, wndHeight);
        setAxisis(table);
    }

    public void setCordinates(TableView<Dot> table) {
        int lastElem;
        if (table.getItems().size() == 0) {
            lastElem = 0;
        } else {
            lastElem = table.getItems().size() - 1;
        }
        setRange(table, lastElem);
        setGrid();
        Double tempOY = 0.0;
        Double tempOX = 0.0;
        for (double strokesOY = 0.0; strokesOY < (lengthOY + deltaY) / rangeOY * scale; strokesOY++) {
            gcCordinates.setLineWidth(1);
            tempOY = ((rangeOY) * strokesOY) / scale / scale;
            gcCordinates.strokeText(String.valueOf(tempOY.intValue()), 1, wndHeight - offset - rangeOY * strokesOY / scale + 5 + deltaY);
        }
        for (double strokesOX = 0.0; strokesOX < (lengthOX + Math.abs(deltaX)) / rangeOX * scale; strokesOX++) {
            gcCordinates.setLineWidth(1);
            tempOX = ((rangeOX) * strokesOX) / scale / scale;
            gcCordinates.strokeText(String.valueOf(tempOX.intValue()), rangeOX * strokesOX / scale - 10 + offset + deltaX, wndHeight - 5);
        }

    }

    public void setRange(TableView<Dot> table, Integer lastElem) {
        if (lastElem > 0) {
            if (table.getItems().get(lastElem).getY() < lengthOY / scale) {
                rangeOY = (table.getItems().get(0).getY() / scale + table.getItems().get(lastElem).getY() / scale) * scale;
            } else {
                rangeOY = (table.getItems().get(0).getY() / scale + lengthOY / scale) * scale;
            }

            if (table.getItems().get(lastElem).getX() < lengthOX / scale) {
                rangeOX = (table.getItems().get(0).getX() / scale + table.getItems().get(lastElem).getX() / scale) * 10 * scale;
            } else {
                rangeOX = (table.getItems().get(0).getX() / scale + lengthOX / scale) * 10 * scale;
            }
        } else {
            rangeOX = 300.0;
            rangeOY = 300.0;
        }
    }

    public void setDelta(Double deltaX, Double deltaY, TableView<Dot> table) {
        if (this.deltaX + deltaX < wndWidth) {
            this.deltaX += deltaX;
            if (this.deltaX > 0)
                this.deltaX = 0.0;
        }
        if (this.deltaY + deltaY < wndHeight) {
            this.deltaY += deltaY;
            if (this.deltaY < 0)
                this.deltaY = 0.0;
        }
        gcAxisis.clearRect(0, 0, wndWidth, wndHeight);
        gcGrid.clearRect(0, 0, wndWidth, wndHeight);
        gcCordinates.clearRect(0, 0, wndWidth, wndHeight);
        gcGraphic.clearRect(0, 0, wndWidth, wndHeight);
        setAxisis(table);
    }

}

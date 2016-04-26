package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by ZloiY on 26.04.2016.
 */
public class drawClass {

    private Double lengthOY;
    private Double lengthOX;
    private Double indent;
    private GraphicsContext gc1;
    private Canvas layer1;
    private Canvas layer2;
    private GraphicsContext gc2;
    Double wndHeight;
    Double wndWidth;

    drawClass(Double height, Double width){
        wndHeight = height;
        wndWidth = width;
        layer1 = new Canvas(wndWidth, wndHeight);
        layer2 = new Canvas(wndWidth, wndHeight);
        gc1 = layer1.getGraphicsContext2D();
        gc2 = layer2.getGraphicsContext2D();
    }

    public Canvas getLayer1() {
        return layer1;
    }

    public Canvas getLayer2() {
        return layer2;
    }

    public void setAxisis(){
        gc1.setStroke(Color.BLACK);
        gc1.setLineWidth(3);
        indent = 45.0;
        gc1.strokeLine(indent, indent, indent, wndHeight - indent);
        gc1.strokeLine(indent, wndHeight - indent, wndWidth - indent, wndHeight - indent);
        gc1.strokeLine(indent, indent, indent - 10, indent + 20);
        gc1.strokeLine(indent, indent, indent + 10, indent + 20);
        gc1.strokeLine(wndWidth - indent, wndHeight - indent, wndWidth - indent - 20, wndHeight - indent - 10);
        gc1.strokeLine(wndWidth - indent, wndHeight - indent, wndWidth - indent - 20, wndHeight - indent + 10);
        lengthOY = wndHeight -2*indent;
        lengthOX = wndWidth -2*indent;
        Double range = 30.0;
        gc1.setLineWidth(1);
        gc1.strokeText("0.0", indent - 20, wndHeight - indent + 20);
        for (double strokesOY = 1.0; strokesOY < lengthOY/range; strokesOY++){
            gc1.setLineWidth(3);
            gc1.strokeLine(indent - 10, wndHeight - range * strokesOY - indent, indent + 10, wndHeight - range * strokesOY - indent);
            Double cordinates = range*strokesOY;
            gc1.setLineWidth(1);
            gc1.strokeText(cordinates.toString(), 1, wndHeight - indent - range * strokesOY + 5);
        }
        for (double strokesOX = 1.0; strokesOX < lengthOX/range; strokesOX++){
            gc1.setLineWidth(3);
            gc1.strokeLine(range * strokesOX + indent, wndHeight - indent - 10, range * strokesOX + indent, wndHeight - indent + 10);
            Double cordiantes = range*strokesOX;
            gc1.setLineWidth(1);
            gc1.strokeText(cordiantes.toString(), range * strokesOX - 10 + indent, wndHeight - 5);
        }
    }

    public void setGraphic(){
        gc2.setStroke(Color.DARKGREEN);
        gc2.setLineWidth(5);
        gc2.strokeLine(indent, wndHeight - indent, 80, wndHeight - 150);
        gc2.strokeLine(80, wndHeight - 150, 180, wndHeight - 230);
        gc2.strokeLine(180, wndHeight - 230, lengthOX, wndHeight - lengthOY);
    }

}

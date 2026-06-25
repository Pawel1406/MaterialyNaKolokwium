package Dimitruk.Paddle;

import java.awt.*;

public abstract class GraphicsItem {
    protected static double canvasWidth;
    protected static double canvasHeight;
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    public static void setCanvasSize(double canvasWidth, double canvasHeight) {
        GraphicsItem.canvasWidth = canvasWidth;
        GraphicsItem.canvasHeight = canvasHeight;
    }

    public abstract void draw(Graphics2D graphics2D);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}

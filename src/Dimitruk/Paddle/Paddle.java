package Dimitruk.Paddle;

import java.awt.*;

public class Paddle extends GraphicsItem {

    public Paddle() {
        y = canvasHeight * 0.9;
        height = canvasHeight * 0.02;
        width = canvasWidth * 0.2;
        x = (canvasWidth - width) / 2;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(0xE70F37));
        graphics2D.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    public void updatePosition(double x) {
        this.x = Math.clamp(x - this.width / 2, 0,canvasWidth - width);

    }
}

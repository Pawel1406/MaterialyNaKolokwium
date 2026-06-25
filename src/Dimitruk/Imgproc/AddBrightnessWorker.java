package Dimitruk.Imgproc;

import java.awt.image.BufferedImage;

public class AddBrightnessWorker implements Runnable {
    private BufferedImage img;
    private int amount;
    private int begin;
    private int end;

    public AddBrightnessWorker(BufferedImage img, int amount, int begin, int end) {
        this.img = img;
        this.amount = amount;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void run() {
        for (int y = this.begin; y < this.end; y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int color = img.getRGB(x, y);
                int blue =   color & 0x0000FF;
                int green = (color & 0x00FF00) >> 8;
                int red =   (color & 0xFF0000) >> 16;

                blue = Math.clamp((blue + amount), 0, 255);
                green = Math.clamp((green + amount), 0, 255);
                red = Math.clamp((red + amount), 0, 255);

                img.setRGB(x, y, blue | (green << 8) | (red << 16));
            }
        }
    }

}
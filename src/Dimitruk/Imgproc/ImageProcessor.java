package Dimitruk.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.clamp;

public class ImageProcessor {
    private BufferedImage img;

    public void load (String path) throws IOException {
        File file = new File(path);
        this.img = ImageIO.read(file);
    }
    public void save (String path) throws IOException {
        File file = new File(path);
        ImageIO.write(this.img, "jpg", file);
    }
    public void addBrightness(int amount) {
        for (int y = 0; y < img.getHeight(); y++) {
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

    public void addBrightnessThreaded(int amount) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[cores];
        for(int i=0; i<cores; i++){
            AddBrightnessWorker worker = new AddBrightnessWorker(
                    this.img,
                    amount,
                    this.img.getHeight() / cores * i,
                    i==cores-1? img.getHeight() : this.img.getHeight() / cores * (i + 1)
            );
            threads[i] = new Thread(worker);
            threads[i].start();
        }
        for(int i=0; i<cores; i++){
            threads[i].join();
        }
    }
}

package Dimitruk.Imgproc;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        {
            ImageProcessor processor = new ImageProcessor();
            processor.load("11729.jpg");
            long start = System.currentTimeMillis();
            processor.addBrightnessThreaded(-100);
            long end = System.currentTimeMillis();
            System.out.println(end-start);
            processor.save("out.jpg");
        }
        {
            ImageProcessor processor = new ImageProcessor();
            processor.load("11729.jpg");
            long start = System.currentTimeMillis();
            processor.addBrightness(-100);
            long end = System.currentTimeMillis();
            System.out.println(end-start);
            processor.save("out.jpg");
        }
    }
}

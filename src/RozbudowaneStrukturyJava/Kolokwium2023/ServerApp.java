package RozbudowaneStrukturyJava.Kolokwium2023;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerApp extends Application {

    private static final String DB_URL = "jdbc:sqlite:images/index.db";

    // Metoda wywoływana przez główną klasę Server.java
    public static void launchUI(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // --- 1. Konfiguracja GUI ---
        Slider slider = new Slider(1, 15, 3);
        slider.setMajorTickUnit(2);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);

        Label radiusLabel = new Label("Promień filtra: " + Server.currentRadius);

        // Słuchacz: wymuszanie liczb nieparzystych i zapis do klasy serwera
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int val = newVal.intValue();
            if (val % 2 == 0) {
                val = (val - oldVal.intValue() >= 0) ? val + 1 : val - 1;
            }
            if (val < 1) val = 1;
            if (val > 15) val = 15;

            Server.currentRadius = val; // Bezpośredni zapis do pamięci serwera
            slider.setValue(val);
            radiusLabel.setText("Promień filtra: " + val);
        });

        VBox root = new VBox(15, radiusLabel, slider);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 350, 150);
        primaryStage.setTitle("Konfiguracja Serwera: Box Blur");
        primaryStage.setScene(scene);
        primaryStage.show();

        // --- 2. Inicjalizacja bazy ---
        initDatabase();
    }

    // --- Logika wywoływana, gdy serwer odbierze plik ---
    public static File processAndSave(File sourceFile, String savedImagePath, int radius) throws Exception {
        BufferedImage srcImage = ImageIO.read(sourceFile);
        if (srcImage == null) {
            throw new Exception("Błąd dekodowania obrazu.");
        }

        long startTime = System.currentTimeMillis();
        BufferedImage blurredImage = applyBoxBlurParallel(srcImage, radius);
        long delay = System.currentTimeMillis() - startTime;

        System.out.println("Zakończono rozmycie (promień: " + radius + ", czas: " + delay + " ms).");

        saveToDatabase(savedImagePath, radius, delay);

        File tempResponseFile = new File("images/temp_output_" + System.currentTimeMillis() + ".png");
        ImageIO.write(blurredImage, "png", tempResponseFile);

        return tempResponseFile;
    }

    private static BufferedImage applyBoxBlurParallel(BufferedImage src, int radius) throws InterruptedException {
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);
        int rowsPerThread = height / cores;

        for (int i = 0; i < cores; i++) {
            final int startY = i * rowsPerThread;
            final int endY = (i == cores - 1) ? height : (i + 1) * rowsPerThread;

            executor.submit(() -> {
                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < width; x++) {
                        long sumR = 0, sumG = 0, sumB = 0;
                        int count = 0;

                        for (int ky = -radius / 2; ky <= radius / 2; ky++) {
                            for (int kx = -radius / 2; kx <= radius / 2; kx++) {
                                int px = x + kx;
                                int py = y + ky;

                                if (px >= 0 && px < width && py >= 0 && py < height) {
                                    int rgb = src.getRGB(px, py);
                                    sumR += (rgb >> 16) & 0xFF;
                                    sumG += (rgb >> 8) & 0xFF;
                                    sumB += rgb & 0xFF;
                                    count++;
                                }
                            }
                        }

                        int avgR = (int) (sumR / count);
                        int avgG = (int) (sumG / count);
                        int avgB = (int) (sumB / count);

                        dest.setRGB(x, y, (avgR << 16) | (avgG << 8) | avgB);
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        return dest;
    }

    private static void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS images (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "path TEXT, " +
                    "size INTEGER, " +
                    "delay INTEGER)";
            stmt.execute(sql);
        } catch (Exception e) {
            System.err.println("Błąd inicjalizacji bazy: " + e.getMessage());
        }
    }

    private static void saveToDatabase(String path, int size, long delay) {
        String sql = "INSERT INTO images(path, size, delay) VALUES(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, path);
            pstmt.setInt(2, size);
            pstmt.setLong(3, delay);
            pstmt.executeUpdate();
            System.out.println("Zapisano wpis w bazie danych.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
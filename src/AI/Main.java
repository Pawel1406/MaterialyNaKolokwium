package AI;

import de.kherud.llama.LlamaModel;
import de.kherud.llama.ModelParameters;
import de.kherud.llama.InferenceParameters;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
public class Main  {
    public static void main(String[] args) {
        // 1. CAŁKOWITE WYCISZENIE LOGÓW SYSTEMOWYCH
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "error");
        // 2. LINUX HACK: Ominięcie blokady noexec na folderze /tmp w laboratoriach studenckich
        // Wymusza na bibliotece JNI wypakowanie plików .so bezpośrednio do folderu projektu
        System.setProperty("java.io.tmpdir", new File(".").getAbsolutePath());
        System.out.println("==================================================");
        System.out.println("🤖 AI CODER (Tryb: LINUX LAB RESTRICTED / OFFLINE)");
        System.out.println("==================================================");
        // 3. WERYFIKACJA PLIKU MODELU (Zabezpieczenie przed Case-Sensitivity na Linuxie)
        String modelPath = new File("qwen2.5-coder-1.5b-instruct-q4_k_m.gguf").getAbsolutePath();
        if (!new File(modelPath).exists()) {
            System.err.println("❌ BŁĄD: Nie znaleziono pliku modelu!");
            System.err.println("Upewnij się, że nazwa pliku na dysku zgadza się w 100% z wielkością liter.");
            return;
        }
        // 4. LINUX HARDWARE HACK: Bezpiecznik wątków dla maszyn wirtualnych
        int availableCores = Runtime.getRuntime().availableProcessors();
        // Bierzemy maksymalnie 4 rdzenie, aby system/administrator nie ubił procesu za przeciążenie serwera
        int threadsToUse = Math.min(4, Math.max(1, availableCores - 1));
        System.out.println("⚡ Przypisano bezpieczne rdzenie AI: " + threadsToUse + " z " +
                availableCores);
        // 5. KONFIGURACJA SPRZĘTOWA (Maksymalne bezpieczne parametry pod 3 GB RAM)
        ModelParameters modelParams = new ModelParameters()
                .setModelFilePath(modelPath)
                .setNCtx(4096) // Maksymalny bufor pamięci dla długich klas i kontekstu kodu
                .setNThreads(threadsToUse) // Optymalne, bezpieczne wątki procesora
                .setNBatch(256) // Mniejsze paczki odczytu zapobiegają skokom zużycia RAM
                .setUseMmap(true); // Mapowanie pamięci prosto z pendrive'a/dysku
        System.out.println("⏳ Ładowanie modelu do pamięci RAM (to może chwilę potrwać)...");
        try (LlamaModel model = new LlamaModel(modelParams);
             Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            System.out.println("✅ Model gotowy do pracy!");
            System.out.println("💡 INSTRUKCJA: Wklej treść zadania oraz dotychczasowy kod.");
            System.out.println(" Kiedy skończysz wklejać, wpisz słowo 'GOTOWE' w nowej linii i  wciśnij Enter.");
            System.out.println(" Wpisz 'exit', aby całkowicie wyczyścić RAM przed nowym etapem.\n");
            while (true) {
                System.out.println("👨💻 [Wklej polecenie/kod i wpisz 'GOTOWE']: ");

                StringBuilder inputBuilder = new StringBuilder();
                boolean exitRequested = false;

                // Pętla odczytu wielu linii tekstu (Obsługa wklejania dużych bloków kodu)
                while (true) {
                    String line = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(line.trim())) {
                        exitRequested = true;
                        break;
                    }
                    if ("GOTOWE".equalsIgnoreCase(line.trim())) {
                        break;
                    }
                    inputBuilder.append(line).append("\n");
                }
                if (exitRequested) {
                    System.out.println("Zamykanie środowiska. Powodzenia na kolokwium!");
                    break;
                }
                String userInput = inputBuilder.toString().trim();
                if (userInput.isEmpty()) {
                    continue;
                }
                System.out.println("🤖 [AI generuje kompletny kod...]:\n");
                // 6. SYSTEM PROMPT - Wytyczne architektoniczne + Sztywny Tech Stack z e-maila wykładowcy
                String formattedPrompt =
                        "<|im_start|>system\n" + "Jesteś precyzyjnym programistą Java (Senior). Twój kod MUSI kompilować się bez błędów. " + "Masz zakaz lania wody i używania komentarzy w kodzie (ani //, ani /* */). Odpowiadaj wyłącznie czystym kodem.\n\n" +
                                "BEZWZGLĘDNE WYTYCZNE ARCHITEKTONICZNE:\n" +
                                "1. KOMPLETNOŚĆ: Zawsze pisz CAŁĄ, KOMPLETNĄ klasę od początku do końca. Masz ZAKAZ używania skrótów typu '// reszta kodu'.\n" +
                                "2. STRUMIENIOWANIE SIECI: NIGDY nie używaj bloku try-with-resources w konstruktorach gniazd (ClientHandler) do przypisywania strumieni do pól, bo to zamyka socket! Inicjalizuj gniazda w zwykłym try-catch. Klient zawsze powinien odczytywać wiadomości z serwera w ODDZIELNYM wątku (Thread / Runnable).\n" +
                                "3. HERMETYZACJA: NIGDY nie odwołuj się bezpośrednio do prywatnych pól innych klas. Przekazuj referencje przez konstruktory i używaj metod publicznych.\n" + "4. BAZA DANYCH: Używaj WYŁĄCZNIE bazy SQLite (sqlite-jdbc). Korzystaj wyłącznie z klas: DriverManager, Connection, Statement, PreparedStatement, ResultSet.\n" +
                                "5. GUI: Używaj WYŁĄCZNIE JavaFX (z plikami .fxml). Masz absolutny zakaz używania biblioteki Swing.\n" +
                                "6. TESTY JEDNOSTKOWE: Używaj WYŁĄCZNIE JUnit 5 (junit-jupiter). Korzystaj z adnotacji @Test oraz metod Assertions.assertTrue(), Assertions.assertFalse(), Assertions.assertEquals(). Zakaz stosowania JUnit 4.\n" +
                                "7. PRZETWARZANIE RÓWNOLEGŁE: Jeśli wymagana jest pula wątków, zawsze używaj klasy ExecutorService i konstruktora Executors.newFixedThreadPool().\n" +
                                "<|im_end|>\n" +
                                "<|im_start|>user\n" +
                                userInput + "\n" +
                                "<|im_end|>\n" +
                                "<|im_start|>assistant\n";
                // 7. PARAMETRY GENEROWANIA (Zimna logika, brak limitu długości, blokada powtórzeń)
                InferenceParameters inferenceParams = new InferenceParameters(formattedPrompt)
                        .setTemperature(0.1f) // Skrajnie niska kreatywność = brak halucynacji składniowych
                        .setTopK(40) // Odciążenie CPU przy wyborze słów
                        .setTopP(0.5f) // Skupienie na najbardziej poprawnych i przewidywalnych wzorcach Javy
                        .setNPredict(-1) // BRAK LIMITU ZNAKÓW - generuje do pełnego zamknięcia klamry klasy
                        .setRepeatPenalty(1.15f) // Blokada przed zapętleniem w kółko
                        .setRepeatLastN(64); // Wielkość bufora sprawdzania powtórzeń

                // 8. STRUMIENIOWANIE WYNIKU W CZASIE RZECZYWISTYM
                for (var output : model.generate(inferenceParams)) {
                    System.out.print(output);
                }
                System.out.println("\n--------------------------------------------------");
            }
        } catch (Exception e) {
            System.err.println("\n❌ Krytyczny błąd silnika AI: " + e.getMessage());
            e.printStackTrace();
        }
    }


}

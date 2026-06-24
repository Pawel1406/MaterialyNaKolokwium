package RozbudowaneStrukturyJava.Kolokwium2025.Serwer;



import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientApp extends Application {

    private Stage primaryStage;
    private Scene loginScene;
    private Scene gameScene;

    // --- KOMPONENTY EKRANU LOGOWANIA ---
    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField(); // Maskuje wpisywane hasło
    private Button loginButton = new Button("Zaloguj się");
    private Label errorLabel = new Label();

    // --- TWOJE KOMPONENTY EKRANU GRY ---
    private TextArea chatArea = new TextArea();
    private TextField messageField = new TextField();
    private ListView<String> usersList = new ListView<>();
    private ObservableList<String> activeUsers = FXCollections.observableArrayList();
    private Button sendButton = new Button("Wyślij");
    private TableView<String> table = new TableView<>();
    private ObservableList<String> leaderboardData = FXCollections.observableArrayList();

    // Sieć
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Inicjalizacja obu widoków
        createLoginScene();
        createGameScene();

        // Na start pokazujemy ekran logowania
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Gra KPN - Logowanie");
        primaryStage.show();
    }

    // 1. BUDOWANIE EKRANU LOGOWANIA
    private void createLoginScene() {
        usernameField.setPromptText("Login");
        passwordField.setPromptText("Hasło");
        errorLabel.setStyle("-fx-text-fill: red;");

        loginButton.setOnAction(e -> handleLoginAction());

        // Możliwość kliknięcia Enter w polu hasła, żeby się zalogować
        passwordField.setOnAction(e -> handleLoginAction());

        VBox loginBox = new VBox(15,
                new Label("Zaloguj się do gry KPN"),
                usernameField,
                passwordField,
                loginButton,
                errorLabel
        );
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(30));
        loginBox.setMaxWidth(300);

        // Kontener wyrównujący na środku okna
        VBox root = new VBox(loginBox);
        root.setAlignment(Pos.CENTER);

        loginScene = new Scene(root, 400, 300);
    }

    // 2. BUDOWANIE WŁAŚCIWEGO EKRANU GRY
    private void createGameScene() {
        chatArea.setEditable(false);
        messageField.setPromptText("Wpisz wiadomość / komendę...");

        usersList.setItems(activeUsers);
        table.setItems(leaderboardData);

        // Konfiguracja kolumn TableView<String>
        TableColumn<String, String> loginColumn = new TableColumn<>("Gracz");
        loginColumn.setCellValueFactory(data -> {
            String row = data.getValue();
            return new SimpleStringProperty(row != null && row.contains(":") ? row.split(":")[0].trim() : row);
        });
        loginColumn.setMinWidth(120);

        TableColumn<String, String> pointsColumn = new TableColumn<>("Punkty");
        pointsColumn.setCellValueFactory(data -> {
            String row = data.getValue();
            if (row != null && row.contains(":")) {
                String[] parts = row.split(":");
                return new SimpleStringProperty(parts.length > 1 ? parts[1].trim() : "0");
            }
            return new SimpleStringProperty("");
        });
        pointsColumn.setMinWidth(80);

        table.getColumns().addAll(loginColumn, pointsColumn);
        table.setPlaceholder(new Label("Brak danych"));

        sendButton.setOnAction(e -> sendGameMessage());
        messageField.setOnAction(e -> sendGameMessage());

        HBox bottomBar = new HBox(10, messageField, sendButton);
        messageField.setPrefWidth(300);

        VBox leftBox = new VBox(10, new Label("Okno rozgrywki / Chat:"), chatArea, bottomBar);
        leftBox.setPadding(new Insets(10));

        VBox rightBox = new VBox(10,
                new Label("Aktywni gracze:"), usersList,
                new Label("Ranking serwera:"), table
        );
        rightBox.setPadding(new Insets(10));
        usersList.setPrefHeight(150);

        BorderPane root = new BorderPane();
        root.setCenter(leftBox);
        root.setRight(rightBox);

        gameScene = new Scene(root, 700, 500);
    }

    private void handleLoginAction() {
        String user = usernameField.getText().trim();
        String pass = passwordField.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            errorLabel.setText("Pola nie mogą być puste!");
            return;
        }

        Platform.runLater(()->{
            errorLabel.setText("Łączenie...");
            loginButton.setDisable(true);
        });


        // Uruchamiamy proces logowania i nasłuchu w osobnym wątku
        new Thread(() -> connectAndAuthenticate(user, pass)).start();
    }

    private void connectAndAuthenticate(String user, String pass) {
        try {
            socket = new Socket("localhost", 12345);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Sekwencja autentykacji zgodna z Twoim ClientHandlerem:
            in.readLine(); // Odbiera z serwera: "Podaj login: "
            out.println(user); // Wysyła login

            in.readLine(); // Odbiera z serwera: "Podaj haslo: "
            out.println(pass); // Wysyła hasło

            in.readLine(); // Odbiera z serwera: "Trwa autentykacja..........."
            String response = in.readLine(); // Odbiera wynik: "Udalo sie..." LUB "Blad..."

            if (response != null && response.contains("Udalo sie pomyslnie polaczyc")) {
                // Sukces! Przełączamy okno na właściwą grę w wątku FX
                Platform.runLater(() -> {
                    primaryStage.setScene(gameScene);
                    primaryStage.setTitle("Gra KPN - Zalogowano jako: " + user);
                });

                // Uruchamiamy pętlę główną gry (odbieranie czatu i tabeli)
                runMainGameLoop();

            } else {
                // Błąd autentykacji
                Platform.runLater(() -> {
                    errorLabel.setText("Błąd: " + response);
                    loginButton.setDisable(false);
                });
                closeConnection();
            }

        } catch (IOException e) {
            Platform.runLater(() -> {
                errorLabel.setText("Brak połączenia z serwerem.");
                loginButton.setDisable(false);
            });
            closeConnection();
        }
    }

    // Główna pętla gry po pomyślnym zalogowaniu
    private void runMainGameLoop() throws IOException {
        String line;
        boolean readingLeaderboard = false;
        boolean readingUsersList = false;

        while ((line = in.readLine()) != null) {
            String finalLine = line;

            if (finalLine.equals("WYNIK:")) {
                readingLeaderboard = true;
                Platform.runLater(() -> leaderboardData.clear());
                continue;
            }
            if (finalLine.startsWith("AKTYWNI GRACZE")) {
                readingUsersList = true;
                Platform.runLater(() -> activeUsers.clear());
                continue;
            }

            if (readingLeaderboard) {
                if (finalLine.equals("login:points") || finalLine.equals("________")) {
                    continue;
                }
                if (finalLine.contains(":")) {
                    Platform.runLater(() -> leaderboardData.add(finalLine));
                } else {
                    readingLeaderboard = false;
                    Platform.runLater(() -> chatArea.appendText(finalLine + "\n"));
                }
            }
            else if(readingUsersList){
                if(finalLine.contains("END")){
                    readingUsersList = false;
                }
                else {
                    Platform.runLater(() -> activeUsers.add(finalLine));
                }
            }
            else {
                Platform.runLater(() -> {
                    chatArea.appendText(finalLine + "\n");
                    if (finalLine.contains("opuscil chat")) {
                        String leftUser = finalLine.replace("opuscil chat", "").trim();
                        activeUsers.remove(leftUser);
                    }
                });
            }
        }
    }

    private void sendGameMessage() {
        String text = messageField.getText().trim();
        if (!text.isEmpty() && out != null) {
            out.println(text);
            messageField.clear();
        }
    }

    private void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        closeConnection();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
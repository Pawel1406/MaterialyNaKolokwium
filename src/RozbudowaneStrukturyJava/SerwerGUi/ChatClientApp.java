package RozbudowaneStrukturyJava.SerwerGUi;



import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ChatClientApp extends Application {
    private TextArea chatArea = new TextArea();
    private TextField messageField = new TextField();
    private ListView<String> usersList = new ListView<>();
    private ObservableList<String> activeUsers = FXCollections.observableArrayList();
    private Button sendButton = new Button("Wyślij");

    private ConnectionHandler connection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        chatArea.setEditable(false);
        usersList.setItems(activeUsers);
        usersList.setPrefWidth(150);


        HBox bottomBox = new HBox(10, messageField, sendButton);
        bottomBox.setPadding(new Insets(10));
        messageField.setPrefWidth(400);

        BorderPane root = new BorderPane();
        root.setCenter(chatArea);
        root.setRight(usersList);
        root.setBottom(bottomBox);


        sendButton.setOnAction(e -> sendMessage());
        messageField.setOnAction(e -> sendMessage());
        primaryStage.setOnCloseRequest(e ->  closeButtom());


        TextInputDialog loginDialog = new TextInputDialog("User");
        loginDialog.setHeaderText("Logowanie do czatu");
        loginDialog.showAndWait().ifPresent(name -> {
            connectToServer(name);
            primaryStage.setTitle("Czat - " + name);
        });

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    private void handleSending() {
        String text = messageField.getText().trim();
        if (!text.isEmpty() && connection != null) {

            connection.send("/broadcast " + text);
            messageField.clear();
        }
    }

    private void sendMessage() {
        String text = messageField.getText();
        if (!text.isEmpty()) {
            connection.send("/broadcast " + text);
            messageField.clear();
        }
    }

    private void connectToServer(String username) {
        connection = new ConnectionHandler(this);
        connection.login(username);
    }


    public void appendMessage(String msg) {
        Platform.runLater(() -> chatArea.appendText(msg + "\n"));
    }

    public void updateUsers(String[] users) {
        Platform.runLater(() -> activeUsers.setAll(users));
    }

    public void addUser(String user) {
        Platform.runLater(() -> { if(!activeUsers.contains(user)) activeUsers.add(user); });
    }

    public void removeUser(String user) {
        Platform.runLater(() -> activeUsers.remove(user));
    }

    public void closeButtom(){
                    // 1. Odpalamy zamykanie sieci w osobnym wątku (nie zablokuje to okna)
            new Thread(() -> {
                try {
                    if (connection != null) {
                        connection.closeEverything();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    // 2. Gdy sieć się zamknie (lub rzuci błędem), bezpiecznie zabijamy proces aplikacji
                    Platform.runLater(() -> {
                        Platform.exit();
                        System.exit(0);
                    });
                }
            }).start();

    }
}
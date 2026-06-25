package Dimitruk.ChatClient;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JTextArea chatArea;
    private JPanel rootPanel;
    private JList<String> userList;
    private JTextField inputField;
    private JButton sendButton;
    private JScrollPane chatPane;
    private final Client client;

    public MainWindow(String login, Client client) {
        this.setTitle(login);
        this.setMinimumSize(new Dimension(800, 200));
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.client=client;

        this.client.setOnMessageReceived(this::receive);

        sendButton.addActionListener(actionEvent -> send());
        inputField.addActionListener(actionEvent -> send());
    }
    private void send(){
        String message = inputField.getText();
        if(message.isEmpty())
            return;

        client.send(message);
    }

    private void receive(String message) {
        chatArea.append(message + '\n');
        inputField.setText("");
        JScrollBar vertical = chatPane.getVerticalScrollBar();
        vertical.setValue( vertical.getMaximum() );
    }

}

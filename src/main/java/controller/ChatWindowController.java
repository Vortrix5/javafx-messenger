package controller;

import java.net.URL;
import java.util.ResourceBundle;

import chat.Client;
import chat.ClientListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class ChatWindowController implements Initializable, ClientListener {
    @FXML
    private TextArea messageBox;
    @FXML
    private ListView chatPane;
    Client client;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        client = new Client();
        client.start(this);
    }

    public void addToChat() {
        String message = messageBox.getText();
        client.sendMessage(message);
        chatPane.getItems().add(message);
        messageBox.clear();
    }

    @Override
    public void onMessageReceived(String message) {
        Platform.runLater(() -> {
            chatPane.getItems().add(message);
        });
    }
}

package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatBoxController {

    private static ClientGUI clientGUI;
    @FXML private TextArea chatTextArea;
    @FXML private TextField messageTextField;

    public void initialize(){

        setUpConnection(this);
    }

    private void setUpConnection(ChatBoxController chatBoxController) {

        clientGUI = new ClientGUI();
        clientGUI.setChatBoxController(this);
    }

    public void updateMessages(String message) {

        String prevText = chatTextArea.getText();
        String newText = prevText + "\n" + message;
        chatTextArea.setText(newText);
    }

    public void sendMessage() {

        clientGUI.setToSend(messageTextField.getText());
    }
}

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientFormController {
    public TextArea txtArea;
    public TextField txtMessage;
    public Button btnSend;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private  String message="";
    private  String reply = "";

    @FXML
    void initialize(){

        new Thread(() -> {
            try {
                socket=new Socket("localhost",3030);


                while (!message.equals("finish")){
                    inputStream = new DataInputStream(socket.getInputStream());
                    outputStream = new DataOutputStream(socket.getOutputStream());
                    message=inputStream.readUTF();

                    txtArea.appendText("server : "+"\n\t"+message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void btnSendOnAction(ActionEvent event) throws IOException {
        outputStream.writeUTF(txtMessage.getText().trim());
        outputStream.flush();

    }
}

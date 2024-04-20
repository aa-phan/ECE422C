import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class LibraryServer {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        ServerSocket serverSocket = new ServerSocket(1234);
        while(true){
            try{
                socket = serverSocket.accept();
                BufferedReader receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter sender = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                while(true){
                    String receivedMessage = receiver.readLine();
                    System.out.println("Client: " + receivedMessage);
                    sender.write("Message received");
                    sender.newLine();
                    sender.flush();
                    if(receivedMessage.equalsIgnoreCase("bye")){
                        break;
                    }
                }
                sender.flush();
                socket.close(); receiver.close(); sender.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

public class LibraryClient {
    public static void main(String[] args) {
        Socket socket = null;
        try{
            socket = new Socket("localhost", 1234);
            BufferedReader receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter sender = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Map<Item, Boolean> lib = (Map<Item, Boolean>) ois.readObject();
            for(Item item : lib.keySet()){
                System.out.println(item.toString());
            }
            Scanner scanner = new Scanner(System.in);
            while(true){
                String messageSent = scanner.nextLine();
                sender.write(messageSent);
                sender.newLine();
                sender.flush();
                System.out.println("Server: " + receiver.readLine());
                if(messageSent.equalsIgnoreCase("bye")){
                    break;
                }
            }
            socket.close();
            receiver.close(); sender.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

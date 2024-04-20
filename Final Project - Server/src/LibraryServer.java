import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class LibraryServer {
    private static Map<Item, Boolean> library = null;

    public static void main(String[] args) throws IOException {
        String pathName = "C:\\Users\\Aaron\\Documents\\Spring24\\ECE422C\\Final Project - Server\\jsonFiles\\itemList.json";
        library = populateLibrary(pathName);

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
    private static Map<Item, Boolean> populateLibrary(String pathName){

        try{
            Map<Item, Boolean> result = new HashMap<>();
            FileReader reader = new FileReader(pathName);
            Gson gson = new Gson();
            List<Map<String, Object>> items = gson.fromJson(reader, new TypeToken<List<Map<String, Object>>>(){}.getType());
            for(Map<String, Object> item : items){

                result.put(itemConstructor(item), true);
            }
            return result;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


public class LibraryServer {
    private static Map<Item, Boolean> library = null;

    public static void main(String[] args) throws IOException {
        //String pathName = "C:\\Users\\Aaron\\Documents\\Spring24\\ECE422C\\Final Project - Server\\jsonFiles\\itemList.json";
        String pathName = "D:\\College\\ECE422C\\ECE422C\\Final Project - Server\\jsonFiles\\itemList.json";
        library = populateLibrary(pathName);

        Socket socket = null;
        ServerSocket serverSocket = new ServerSocket(1234);
        while(true){
            try{
                socket = serverSocket.accept();
                BufferedReader receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter sender = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(library);
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
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Item.class, new ItemDeserializer())
                    .create();
            List<Item> items = gson.fromJson(reader, new TypeToken<List<Item>>(){}.getType());
            for(Item item : items){
                result.put(item, true);
            }
            return result;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    static class ItemDeserializer implements JsonDeserializer<Item> {
        @Override
        public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();

            switch (type) {
                case "Book":
                    return context.deserialize(jsonObject, Book.class);
                case "DVD":
                    return context.deserialize(jsonObject, DVD.class);
                case "Audiobook":
                    return context.deserialize(jsonObject, Audiobook.class);
                case "Game":
                    return context.deserialize(jsonObject, Game.class);
                case "comicBook":
                    return context.deserialize(jsonObject, comicBook.class);
                default:
                    throw new JsonParseException("Unknown type: " + type);
            }
        }
    }
}


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LibraryClient {
    private Socket socket;
    private String username;
    private Map<Item, Boolean> localLib;
    private ArrayList<Item> checkedItems;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    //private Boolean updateFlag = false;
    public LibraryClient(Socket socket, String username){
        try{
            this.socket = socket;
            //sender = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            this.username = username;
            checkedItems = new ArrayList<>();
            if(ois.readInt()==-1){
                localLib = (Map<Item, Boolean>) ois.readObject();
                System.out.println("library received");
                for(Map.Entry<Item, Boolean> entry: localLib.entrySet()){
                    System.out.println(entry.getKey().toString() + "\nstatus: " + entry.getValue());
                }
            }

        }
        catch(IOException e){
            closeEverything(socket, ois, oos);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(){
        try{
            //sending username first
            oos.writeInt(username.length());
            oos.writeUTF(username);
            oos.flush();

            Scanner input = new Scanner(System.in);
            //sending map or messages
            while(socket.isConnected()){
                String msgtoSend = input.nextLine();
                if(msgtoSend.startsWith("/checkout")){
                    String itemName = msgtoSend.substring(msgtoSend.indexOf(" ") + 1);
                    for(Item item : localLib.keySet()){
                        if(item.getTitle().equals(itemName)){
                            itemCheckout(item);
                            break;
                        }
                    }
                    //updateLibrary();
                    continue;
                }
                if(msgtoSend.startsWith("/return")){
                    String itemName = msgtoSend.substring(msgtoSend.indexOf(" ") + 1);
                    for(Item item : localLib.keySet()){
                        if(item.getTitle().equals(itemName)){
                            itemReturn(item);
                            break;
                        }
                    }
                    continue;
                }
                if(msgtoSend.equalsIgnoreCase("/view")){
                    for(Map.Entry<Item, Boolean> entry: localLib.entrySet()){
                        System.out.println(entry.getKey().toString() + "\nstatus: " + entry.getValue());
                    }
                    continue;
                }
                if(msgtoSend.equalsIgnoreCase("/checked")){
                    for(Item item : checkedItems){
                        System.out.println(item);
                    }
                }
                else if(msgtoSend.equalsIgnoreCase("bye")){
                    oos.writeInt("bye".length());
                    oos.writeUTF("bye");
                    oos.flush();
                    break;
                }else{
                    oos.writeInt(msgtoSend.length());
                    oos.writeUTF(msgtoSend);
                    oos.flush();
                }
            }
            closeEverything(socket, ois, oos);
            System.out.println("client closed");
        }catch (IOException | ClassNotFoundException e){
            System.out.println("send message exception");
            closeEverything(socket, ois, oos);
        }
    }
    public void refreshLibrary() throws IOException, ClassNotFoundException {
        //ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.reset();
        oos.writeInt(-1);
        oos.writeObject(localLib);
        oos.flush();
    }
    public void listenForMessage(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String msgFromServer;
                boolean isUpdateMessage = false;
                try {
                    while (socket.isConnected()) {
                        if(ois.available()>0) {

                            int dataType = ois.readInt();
                            if (dataType == -1) {
                                localLib = (Map<Item, Boolean>) ois.readObject();
                                System.out.println("local lib updated");
                                for (Map.Entry<Item, Boolean> entry : localLib.entrySet()) {
                                    System.out.println(entry.getKey().toString() + "\nstatus: " + entry.getValue());
                                }
                            } else{
                                msgFromServer = ois.readUTF();
                                System.out.println(msgFromServer);
                            }

                        }
                    }
                }
                catch (IOException e){
                    System.out.println("listen message exception");
                    closeEverything(socket, ois, oos);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, ObjectInputStream reader, ObjectOutputStream writer){
        try{
            if(reader!=null){
                reader.close();
            }
            if(writer!=null){
                writer. close();
            }
            if(socket!=null){
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            System.out.println("done closing!");
        }
    }
    public void itemCheckout(Item item) throws IOException, ClassNotFoundException {
        if (localLib.get(item)) {
            localLib.put(item, false);
            checkedItems.add(item);
            //updateFlag = true;
            System.out.println("Item checked out successfully");
            refreshLibrary();

            // Exit the method after sending the library
        }
        else System.out.println("Item is already checked out, please choose another item.");
    }
    public void itemReturn(Item item) throws IOException, ClassNotFoundException {
        if(!localLib.get(item) && checkedItems.contains(item)){
            checkedItems.remove(item);
            localLib.put(item, true);
            System.out.println("item returned successfully");
            refreshLibrary();

        }
        else System.out.println("item is already in library");

    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 1234);
        LibraryClient client = new LibraryClient(socket, username);
        client.listenForMessage();
        client.sendMessage();
        System.out.println("done");
    }
}

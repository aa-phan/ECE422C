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
    private BufferedReader receiver;
    private BufferedWriter sender;
    private String username;
    private Map<Item, Boolean> localLib;
    private ArrayList<Item> checkedItems;
    //private Boolean updateFlag = false;
    public LibraryClient(Socket socket, String username){
        try{
            this.socket = socket;
            sender = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            checkedItems = new ArrayList<>();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            localLib = (Map<Item, Boolean>) ois.readObject();
            System.out.println("library received");
            for(Map.Entry<Item, Boolean> entry: localLib.entrySet()){
                System.out.println(entry.getKey().toString() + "\nstatus: " + entry.getValue());
            }

        }
        catch(IOException e){
            closeEverything(socket, receiver, sender);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(){
        try{
            sender.write(username);
            sender.newLine();
            sender.flush();

            Scanner input = new Scanner(System.in);
            while(socket.isConnected()){
                String msgtoSend = input.nextLine();
                if(msgtoSend.contains("/checkout")){
                    sender.write(msgtoSend);
                    sender.newLine();
                    sender.flush();
                    String itemName = msgtoSend.substring(msgtoSend.indexOf(" ") + 1);
                    for(Item item : localLib.keySet()){
                        if(item.getTitle().equals(itemName)){
                            itemCheckout(item);
                            refreshLibrary();
                            break;
                        }
                    }
                    //updateLibrary();
                }
                if(msgtoSend.equalsIgnoreCase("/view")){
                    for(Map.Entry<Item, Boolean> entry: localLib.entrySet()){
                        System.out.println(entry.getKey().toString() + "\nstatus: " + entry.getValue());
                    }
                }
                else if(msgtoSend.equalsIgnoreCase("bye")){
                    sender.write("bye");
                    sender.newLine();
                    sender.flush();
                    break;
                }else{
                    sender.write(msgtoSend);
                    sender.newLine();
                    sender.flush();
                }
            }
            closeEverything(socket, receiver, sender);
            System.out.println("client closed");
        }catch (IOException | ClassNotFoundException e){
            System.out.println("send message exception");
            closeEverything(socket, receiver, sender);
        }
    }
    public void refreshLibrary() throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(localLib);
        oos.flush();
    }
    public void updateLibrary() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        localLib = (Map<Item,Boolean>)ois.readObject();
        //updateFlag = false;
    }
    public void listenForMessage(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String msgFromServer;
                while(socket.isConnected()){
                    try{
                        msgFromServer = receiver.readLine();
                        if(msgFromServer.equalsIgnoreCase("- UPDATE -")){
                            updateLibrary();
                            System.out.println("local lib updated");
                        }
                        if (msgFromServer == null || msgFromServer.equalsIgnoreCase("- CLOSED -")) {
                            break;
                        }
                        else System.out.println(msgFromServer);
                    }
                    catch (IOException e){
                        System.out.println("listen message exception");
                        closeEverything(socket, receiver, sender);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket socket, BufferedReader reader, BufferedWriter writer){
        try{
            if(reader!=null){
                reader.close();
            }
            if(writer!=null){
                writer.close();
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
    public void itemCheckout(Item item){
        if (localLib.get(item)) {
            localLib.put(item, false);
            checkedItems.add(item);
            //updateFlag = true;
            System.out.println("Item checked out successfully");
            return; // Exit the method after sending the library
        }
        System.out.println("Item is already checked out, please choose another item.");
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

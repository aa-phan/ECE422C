import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

public class LibraryClient {
    private Socket socket;
    private BufferedReader receiver;
    private BufferedWriter sender;
    private String username;
    private Map<Item, Boolean> localLib;
    public LibraryClient(Socket socket, String username){
        try{
            this.socket = socket;
            sender = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            localLib = (Map<Item, Boolean>) ois.readObject();
            System.out.println("library received");
            for(Item item : localLib.keySet()){
                System.out.println(item.toString());
            }
            sender.write(username);
            sender.newLine();
            sender.flush();


            //receiveLibrary();
            //new Thread(this::receiveLibraryUpdates).start();
        }
        catch(IOException e){
            closeEverything(socket, receiver, sender);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(){
        try{
            Scanner input = new Scanner(System.in);
            while(socket.isConnected()){
                String msgtoSend = input.nextLine();
                if(msgtoSend.equalsIgnoreCase("bye")){
                    break;
                }
                sender.write(username + ": " + msgtoSend);
                sender.newLine();
                sender.flush();
            }
            closeEverything(socket, receiver, sender);
        }catch (IOException e){
            closeEverything(socket, receiver, sender);
        }
    }
    /*public void receiveLibrary(){
        try{
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            localLib = (Map<Item, Boolean>) ois.readObject();
            System.out.println("library received8");
            for(Item item : localLib.keySet()){
                System.out.println(item.toString());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/
    public void listenForMessage(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String msgFromServer;
                while(socket.isConnected()){
                    try{
                        msgFromServer = receiver.readLine();
                        System.out.println(msgFromServer);
                    }
                    catch (IOException e){
                        closeEverything(socket, receiver, sender);
                    }
                }
            }
        }).start();
    }
    public void receiveLibraryUpdates() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            while (socket.isConnected()) {
                localLib = (Map<Item, Boolean>) ois.readObject();
                // Handle the received library update, e.g., update UI or do other processing
                for(Item item : localLib.keySet()){
                    //System.out.println(item.toString());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            closeEverything(socket, receiver, sender);
        }
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
        }
    }
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 1234);
        LibraryClient client = new LibraryClient(socket, username);
        client.listenForMessage();
        client.sendMessage();
        /*try{

            BufferedReader receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter sender = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Map<Item, Boolean> lib = (Map<Item, Boolean>) ois.readObject();
            for(Item item : lib.keySet()){
                System.out.println(item.toString());
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println(receiver.readLine());
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
        }*/
    }
}

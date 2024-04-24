import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ClientHandler implements Runnable{
    private String username;
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader receiver;
    private BufferedWriter sender;
    //private Map<Item, Boolean> map;

    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
//            this.map =  map;
            sender = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientHandlers.add(this);
            //sendLibraryToClient();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void broadcastMessage(String msg) {
        synchronized (clientHandlers) {
            Iterator<ClientHandler> iterator = clientHandlers.iterator();
            while (iterator.hasNext()) {
                ClientHandler clientHandler = iterator.next();
                try {
                    if (!clientHandler.username.equals(username)) {
                        clientHandler.sender.write(msg);
                        clientHandler.sender.newLine();
                        clientHandler.sender.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    iterator.remove(); // Remove the client on exception
                }
            }
        }
    }
    public void removeClient() {
        synchronized (clientHandlers) {
            clientHandlers.remove(this);
            broadcastMessage(username + " has left the fray!");
            System.out.println(username + " disconnected");

        }
    }
    public void closeEverything(Socket socket, BufferedReader reader, BufferedWriter writer){
        removeClient();
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
    @Override
    public void run() {
        try {
            username = receiver.readLine();
            broadcastMessage(username + " has joined the fray!");
            while (socket.isConnected()) {
                String msgFromClient = receiver.readLine();
                if (msgFromClient.startsWith("/checkout") || msgFromClient.startsWith("/return")) {
                    sendLibraryToClient();
                }
                else if (msgFromClient.equalsIgnoreCase("bye")) {
                    break;
                }
                else{
                    broadcastMessage(username + " : " + msgFromClient);
                    System.out.println(username + " : " + msgFromClient);

                }

            }
            closeEverything(socket, receiver, sender);

            //close socket and buffers
            System.out.println("client closed");
        } catch (IOException e) {
            System.out.println("client handler oopsie");
            closeEverything(socket, receiver, sender);
        }
    }
    private void sendLibraryToClient() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object obj = ois.readObject();
            if(obj instanceof Map){
                LibraryServer.setLibraryUpdate((Map<Item, Boolean>) obj);
                for(ClientHandler clientHandler: clientHandlers){
                    if(!clientHandler.equals(this)){
                        clientHandler.sender.write("- UPDATE -");
                        clientHandler.sender.newLine();
                        clientHandler.sender.flush();
                        clientHandler.sender.write("--END UPDATE--"); // Delimiter
                        clientHandler.sender.newLine();
                        clientHandler.sender.flush();
                        ObjectOutputStream oos = new ObjectOutputStream(clientHandler.socket.getOutputStream());
                        oos.writeObject(LibraryServer.getLibrary());
                        oos.flush();
                    }

                }
                System.out.println("client handlers sent lib");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
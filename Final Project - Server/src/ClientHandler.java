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
    private Map<Item, Boolean> map;

    public ClientHandler(Socket socket, Map<Item, Boolean> map){
        try{
            this.socket = socket;
            this.map =  map;
            sender = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = receiver.readLine();
            clientHandlers.add(this);
            broadcastMessage(username + " has joined the fray!");
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
    public void run(){
        String msgFromClient;
        while(socket.isConnected()){
            try{
                /*ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(LibraryServer.getLibrary());*/
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Object receivedObject = in.readObject();
                if (receivedObject instanceof Map) {
                    synchronized(map){
                        map.putAll((Map<Item, Boolean>) receivedObject);
                    }
                }
                System.out.println("Map updated on server: " + map);
                for (ClientHandler client : clientHandlers) {
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(map);
                    out.flush();
                }
                msgFromClient = receiver.readLine();
                broadcastMessage(username + " : " + msgFromClient);
                if(msgFromClient.equalsIgnoreCase("bye")){
                    break;
                }
            } catch (IOException | ClassNotFoundException e) {
                closeEverything(socket, receiver, sender);
            }

            }
        /*try{
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(LibraryServer.getLibrary());
            sender.write("Hello, " + name + "!");
            sender.flush();
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
            socket.close();
            receiver.close();
            sender.close();
        }catch(IOException e){
            e.printStackTrace();
        }*/

    }
    /*private void sendLibraryToClient() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(LibraryServer.getLibrary());
            System.out.println("client handlers sent lib");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public static ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }
    public Socket getSocket() {
        return socket;
    }

}
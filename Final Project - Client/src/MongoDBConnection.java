import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;

public class MongoDBConnection {

    // MongoDB connection URI
    private static final String MONGODB_URI = "mongodb+srv://atp:422C@librarycluster.sgterow.mongodb.net/?retryWrites=true&w=majority&appName=LibraryCluster";

    // MongoDB database and collection parameters
    private static final String DATABASE_NAME = "your_database_name";
    private static final String COLLECTION_NAME = "users";

    // Establish MongoDB connection
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDBConnection() {
        // Connect to MongoDB
        mongoClient = MongoClients.create(MONGODB_URI);
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);
        if (collection == null) {
            database.createCollection(COLLECTION_NAME);
            collection = database.getCollection(COLLECTION_NAME);
        }
    }

    public void insertUser(String username, String password) {
        // Prepare data for insertion
        Document document = new Document();
        document.append("username", username);
        document.append("password", password);
        // Insert into MongoDB
        collection.insertOne(document);
    }
    public boolean userExists(String username) {
        // Check if the username exists in the collection
        return collection.countDocuments(Filters.eq("username", username)) > 0;
    }
    public boolean passwordMatches(String username, String password) {
        // Check if the username and password match in the collection
        Document user = collection.find(Filters.eq("username", username)).first();
        return user != null && user.getString("password").equals(password);
    }
    public void writeArrayListToMongo(ArrayList<Item> items, String username) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Convert ArrayList of items to JSON string
        String json = gson.toJson(items);

        // Create a new document with username and checked out items JSON
        Document checkedItems = new Document("checkedItems", json);

        // Get the existing user document from MongoDB
        Document userDocument = collection.find(Filters.eq("username", username)).first();

        if (userDocument != null) {
            // Get the existing checked items array or create a new one if it doesn't exist
            ArrayList<Document> existingCheckedItems = userDocument.get("checkedItems", ArrayList.class);
            if (existingCheckedItems == null) {
                existingCheckedItems = new ArrayList<>();
            }

            // Add the new checked items to the existing array
            existingCheckedItems.add(checkedItems);

            // Set the updated checked items array to the user document
            userDocument.put("checkedItems", existingCheckedItems);

            // Update the user document in MongoDB
            collection.replaceOne(Filters.eq("username", username), userDocument);
        } else {
            System.out.println("User not found in MongoDB.");
        }
    }
    public ArrayList<Item> getCheckedItems(String username) {
        ArrayList<Item> checkedItemsList = new ArrayList<>();

        // Find the user document by username
        Document userDocument = collection.find(Filters.eq("username", username)).first();

        if (userDocument != null) {
            // Retrieve the checked items array from the user document
            ArrayList<Document> checkedItems = userDocument.get("checkedItems", ArrayList.class);

            if (checkedItems != null) {
                // Convert each checked item document to Item object and add to the list
                for (Document itemDocument : checkedItems) {
                    Gson gson = new Gson();
                    Item item = gson.fromJson(itemDocument.toJson(), Item.class);
                    checkedItemsList.add(item);
                }
            }
        } else {
            System.out.println("User not found in MongoDB.");
        }

        return checkedItemsList;
    }
    public void close() {
        // Close MongoDB connection
        mongoClient.close();
    }
}

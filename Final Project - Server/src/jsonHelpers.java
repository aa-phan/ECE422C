import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class jsonHelpers {
    static Map<Item, Boolean> populateLibrary(String pathName){

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

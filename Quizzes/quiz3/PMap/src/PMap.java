import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Stuff{

    public Object key;
    public Object value;
    Stuff(Object key, Object value){
        this.key = key;
        this.value = value;
    }
    public Object getKey(){
        return key;
    }
    public Object getValue(){
        return value;
    }
    public void setKey(Object key) {
        this.key = key;
    }
    public void setValue(Object value) {
        this.value= value;
    }
}
public class PMap {


    ArrayList<Stuff> temp;
    public PMap(){
        temp = new ArrayList<>();
    }
    public PMap(Object key, Object value){
        temp = new ArrayList<>();
        temp.add(new Stuff(key, value));
    }
    public void clearMap(){
        temp.clear();
    }
    public boolean containsKey(Object key){
        for (Stuff o : temp) {
            if (o.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }
    public boolean containsValue(Object value){
        for(Stuff o: temp){
            if(o.getValue().equals(value)){
                return true;
            }
        }
        return false;
    }
    public Object get(Object key){
        for(Stuff o: temp){
            if(o.getKey().equals(key))
                return o.getValue();
        }
        return null;
    }
    public boolean isEmpty(){
        return temp.isEmpty();
    }
    public ArrayList<Object> keySet(){
        ArrayList<Object> keys = new ArrayList<>();
        for(int i = 0; i<temp.size();i++){
            keys.set(i, temp.get(i).getKey());
        }
        return keys;
    }
    public void put(Object key, Object value){
        for(Stuff o : temp){
            if (o.getKey().equals(key)){
                o.setValue(value);
            }
            else{
                temp.add(new Stuff(key,value));
            }
        }
    }
    public ArrayList<Object> putAll(){
        return new ArrayList<>(temp);
    }
    public void remove(Object key){
        for(Stuff o : temp){
            if(o.getKey().equals(key)){
                remove(o);
            }
        }
    }
    public int size(){
        return temp.size();
    }
    public ArrayList<Object> values(){
        ArrayList<Object> newListValues = new ArrayList<>();
        for(int i = 0; i<temp.size();i++){
            newListValues.set(i, temp.get(i).getValue());
        }
        return newListValues;
    }
    public HashSet<Object> entrySet(){
        return new HashSet<>(temp);
    }



}
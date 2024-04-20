import java.io.Serializable;

public class Item implements Serializable {
    private String title;
    private String dateAdded;
    private String type;
    // Default Constructor
    public Item() {
        this.title = "";
        this.dateAdded = "";
        this.type = "";
    }
    public Item(String title){
        this.title = title;
        this.dateAdded = "";
    }
    public Item(String title, String dateAdded){
        this.title = title;
        this.dateAdded = dateAdded;
    }
    public Item(String title, String dateAdded, String type){
        this.title = title;
        this.dateAdded = dateAdded;
        this.type = type;
    }
    public String getType(){
        return type;
    }

}
class Book extends Item{
    private String author;
    public Book(){
        super();
        this.author = "";
    }
    public Book(String author){
        super();
        this.author = author;
    }
}
class DVD extends Item{
    private String productionCompany;
    private String director;
    public DVD(){
        super();
        productionCompany = "";
        director = "";
    }
    public DVD(String productionCompany, String director){
        super();
        this.productionCompany = productionCompany;
        this.director = director;
    }
}
class Audiobook extends Item{
    private String narrator;
    public Audiobook(){
        super();
        narrator = "";
    }
    public Audiobook(String narrator){
        super();
        this.narrator = narrator;
    }
}
class Game extends Item{
    private String gameDesigner;
    public Game(){
        super();
        gameDesigner = "";
    }
    public Game(String gameDesigner){
        super();
        this.gameDesigner = gameDesigner;
    }
}
class comicBook extends Item{
    private String illustrator;
    public comicBook(){
        super();
        illustrator = "";
    }
    public comicBook(String illustrator){
        this.illustrator = illustrator;
    }
}

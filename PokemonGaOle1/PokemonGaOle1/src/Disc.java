
public abstract class Disc {
    protected String name;
    protected String rarity;
    
    public Disc(String name, String rarity) {
        this.name = name;
        this.rarity = rarity;
    }
    
    public String getName() { return name; }
    public String getRarity() { return rarity; }
    
    public abstract void activate(Pokemon pokemon);
}



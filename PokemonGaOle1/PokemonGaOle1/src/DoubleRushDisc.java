
public class DoubleRushDisc extends Disc {
    private DoubleRushMove doubleRushMove;
    
    public DoubleRushDisc(String name, String rarity, DoubleRushMove doubleRushMove) {
        super(name, rarity);
        this.doubleRushMove = doubleRushMove;
    }
    
    public DoubleRushMove getDoubleRushMove() { return doubleRushMove; }
    
    @Override
    public void activate(Pokemon pokemon) {
        if (pokemon.isLegendary()) {
            // Add double rush capability
            System.out.println(pokemon.getName() + " can now use Double Rush: " + doubleRushMove.getName());
        }
    }
    
    public boolean canBeUsedBy(Pokemon pokemon) {
        return pokemon.isLegendary();
    }
}

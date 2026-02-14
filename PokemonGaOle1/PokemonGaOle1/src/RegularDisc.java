
public class RegularDisc extends Disc {
    private Move move;
    
    public RegularDisc(String name, String rarity, Move move) {
        super(name, rarity);
        this.move = move;
    }
    
    public Move getMove() { return move; }
    
    @Override
    public void activate(Pokemon pokemon) {
        if (!pokemon.isLegendary()) {
            pokemon.getMoves().add(move);
        }
    }
}


public class GrassPokemon extends Pokemon {
    public GrassPokemon(String name, int level, int maxHP, int attack, int defense, int speed, boolean isLegendary) {
        super(name, level, maxHP, attack, defense, speed, isLegendary);
    }
    
    @Override
    public String getPokemonType() {
        return "GRASS";
    }
    
    @Override
    public double calculateEffectiveness(MoveType moveType) {
        switch (moveType) {
            case FIRE: return 2.0;  // Super effective
            case WATER: return 0.5; // Not very effective
            case GRASS: return 1.0; // Normal effectiveness
            default: return 1.0;
        }
    }
    
    @Override
    protected void initializeMoves() {
        moves.add(new Move("Vine Whip", 45, 1.0, MoveType.GRASS));
        moves.add(new Move("Solar Beam", 120, 1.0, MoveType.GRASS));
        if (isLegendary) {
            moves.add(new Move("Leaf Storm", 130, 0.9, MoveType.GRASS));
        }
    }
}

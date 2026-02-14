
public class FirePokemon extends Pokemon {
    public FirePokemon(String name, int level, int maxHP, int attack, int defense, int speed, boolean isLegendary) {
        super(name, level, maxHP, attack, defense, speed, isLegendary);
    }
    
    @Override
    public String getPokemonType() {
        return "FIRE";
    }
    
    @Override
    public double calculateEffectiveness(MoveType moveType) {
        switch (moveType) {
            case WATER: return 2.0; // Super effective
            case GRASS: return 0.5; // Not very effective
            case FIRE: return 1.0;  // Normal effectiveness
            default: return 1.0;
        }
    }
    
    @Override
    protected void initializeMoves() {
        moves.add(new Move("Ember", 40, 1.0, MoveType.FIRE));
        moves.add(new Move("Flame Thrower", 90, 1.0, MoveType.FIRE));
        if (isLegendary) {
            moves.add(new Move("Fire Blast", 110, 0.85, MoveType.FIRE));
        }
    }
}


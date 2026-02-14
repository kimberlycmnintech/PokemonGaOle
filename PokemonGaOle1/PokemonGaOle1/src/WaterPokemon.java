
public class WaterPokemon extends Pokemon {
    public WaterPokemon(String name, int level, int maxHP, int attack, int defense, int speed, boolean isLegendary) {
        super(name, level, maxHP, attack, defense, speed, isLegendary);
    }
    
    @Override
    public String getPokemonType() {
        return "WATER";
    }
    
    @Override
    public double calculateEffectiveness(MoveType moveType) {
        switch (moveType) {
            case GRASS: return 2.0; // Super effective
            case FIRE: return 0.5;  // Not very effective
            case WATER: return 1.0; // Normal effectiveness
            default: return 1.0;
        }
    }
    
    @Override
    protected void initializeMoves() {
        moves.add(new Move("Water Gun", 40, 1.0, MoveType.WATER));
        moves.add(new Move("Surf", 90, 1.0, MoveType.WATER));
        if (isLegendary) {
            moves.add(new Move("Hydro Pump", 110, 0.8, MoveType.WATER));
        }
    }
}

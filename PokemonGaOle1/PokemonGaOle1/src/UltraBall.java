import java.util.Random;
public class UltraBall extends PokeBall {
    private float bonusCaptureRate = 2.0f;
    
    public UltraBall() {
        super("Ultra Ball", 2.0, 2); 
    }
    public UltraBall(int quantity) {
        super("Ultra Ball", 2.0, quantity); // Allows setting quantity
    }
    
    @Override
    public boolean attemptCatch(Pokemon pokemon) {
        Random random = new Random();
        int catchChance = (int)(pokemon.getCatchRate() * catchMultiplier * bonusCaptureRate);
        int roll = random.nextInt(100);
        return roll < catchChance;
    }
}

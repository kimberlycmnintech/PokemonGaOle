import java.util.Random;
public class GreatBall extends PokeBall {
    private float bonusCaptureRate = 1.5f;
    
    public GreatBall() {
        super("Great Ball", 1.5, 3); 
    }
    public GreatBall(int quantity) {
        super("Great Ball", 1.5, quantity); // Allows setting quantity
    }

    @Override
    public boolean attemptCatch(Pokemon pokemon) {
        Random random = new Random();
        int catchChance = (int)(pokemon.getCatchRate() * catchMultiplier * bonusCaptureRate);
        int roll = random.nextInt(100);
        return roll < catchChance;
    }
}
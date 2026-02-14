
public class MasterBall extends PokeBall {
    public MasterBall() {
        super("Master Ball", 100.0,1);
    }
    
    public void guaranteedCatch() {
        // Master Ball always catches
    }
    public MasterBall(int quantity) {
        super("Master Ball", 100.0, quantity); // Allows setting quantity
    }
    
    @Override
    public boolean attemptCatch(Pokemon pokemon) {
        return true; // Master Ball always succeeds
    }
}

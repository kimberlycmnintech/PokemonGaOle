import java.util.Random;
public class PokeBall {
	 protected String name;
	    protected double catchMultiplier;
	    protected int quantity;
	    
	    public PokeBall(String name, double catchMultiplier, int quantity) {
	        this.name = name;
	        this.catchMultiplier = catchMultiplier;
	        this.quantity = quantity;
	    }
	    // Get the remaining quantity
	    public int getQuantity() {
	        return quantity;
	    }

	    // Decrease quantity by 1 when used
	    public void decrementQuantity() {
	        if (quantity > 0) {
	            quantity--;
	        }
	    }
	    
	    public String getName() { return name; }
	    public double getCatchMultiplier() { return catchMultiplier; }
	    
	    public boolean attemptCatch(Pokemon pokemon) {
	        Random random = new Random();
	        double catchChance = pokemon.getCatchRate() * catchMultiplier;
	        int roll = random.nextInt(100);
	        return roll < catchChance;
	    }
	}

	

	
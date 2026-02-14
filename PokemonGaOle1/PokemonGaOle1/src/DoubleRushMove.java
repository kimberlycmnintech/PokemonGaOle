import java.util.Random;
public class DoubleRushMove {
	 private String name;
	    private int firstMovePower;
	    private int secondMovePower;
	    private double accuracy;
	    private MoveType moveType;
	    
	    public DoubleRushMove(String name, int firstMovePower, int secondMovePower, double accuracy, MoveType moveType) {
	        this.name = name;
	        this.firstMovePower = firstMovePower;
	        this.secondMovePower = secondMovePower;
	        this.accuracy = accuracy;
	        this.moveType = moveType;
	    }
	    
	    public String getName() { 
	    	return name; 
	    	}
	    public int getFirstMovePower() { 
	    	return firstMovePower;
	    	}
	    public int getSecondMovePower() { 
	    	return secondMovePower; 
	    	}
	    public double getAccuracy() { 
	    	return accuracy; 
	    	}
	    public MoveType getMoveType() { 
	    	return moveType; 
	    	}
	    
	    public int executeDoubleRush(Pokemon attacker, Pokemon defender) {
	        Random random = new Random();
	        int totalDamage = 0;
	        
	        // First hit
	        if (random.nextDouble() <= accuracy) {
	            int damage1 = (int)(firstMovePower * defender.calculateEffectiveness(moveType));
	            totalDamage += damage1;
	            System.out.printf("%s used %s for %d damage! (1st hit)\n", 
	                attacker.getName(), name, damage1);
	        } else {
	            System.out.printf("%s's %s missed! (1st hit)\n", attacker.getName(), name);
	        }

	        // Second hit
	        if (random.nextDouble() <= accuracy) {
	            int damage2 = (int)(secondMovePower * defender.calculateEffectiveness(moveType));
	            totalDamage += damage2;
	            System.out.printf("%s used %s for %d damage! (2nd hit)\n", 
	                attacker.getName(), name, damage2);
	        } else {
	            System.out.printf("%s's %s missed! (2nd hit)\n", attacker.getName(), name);
	        }

	        return totalDamage; 
	    }
}


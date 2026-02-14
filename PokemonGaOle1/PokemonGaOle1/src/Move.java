
public class Move {
	 private String name;
	    private int power;
	    private double accuracy;
	    private MoveType moveType;
	    
	    public Move(String name, int power, double accuracy, MoveType moveType) {
	        this.name = name;
	        this.power = power;
	        this.accuracy = accuracy;
	        this.moveType = moveType;
	    }
	    
	    public String getName() { 
	    	return name;
	    	}
	    public int getPower() {
	    	return power; 
	    	}
	    public double accuracy() {
	    	return accuracy;
	    	}
	    public MoveType getMoveType() { 
	    	return moveType;
	    	}
	    
	    public int calculateDamage(Pokemon target) {
	        double effectiveness = target.calculateEffectiveness(this.moveType);
	        return (int)(power * effectiveness);
	    }
	}



import java.util.*;

public abstract class Pokemon {
    protected String name;
    protected int level;
    protected int experience;
    protected String trainerName;
    protected int catchRate;
    protected int currentHP;
    protected int maxHP;
    protected int attack;
    protected int defense;
    protected int speed;
    protected boolean isLegendary;
    protected List<Move> moves;
    protected boolean isDefeated;
    
    public Pokemon(String name, int level, int maxHP, int attack, int defense, int speed, boolean isLegendary) {
        this.name = name;
        this.level = level;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.isLegendary = isLegendary;
        this.experience = 0;
        this.trainerName = "";
        this.catchRate = isLegendary ? 10 : 30;
        this.moves = new ArrayList<>();
        this.isDefeated = false;
        initializeMoves();
    }
    
    protected abstract void initializeMoves();
    public abstract String getPokemonType();
    public abstract double calculateEffectiveness(MoveType moveType);
    
    // Getters
    public String getName() { 
    	return name; 
    	}
    public int getLevel() { 
    	return level;
    	}
    public int getExperience() { 
    	return experience;
}
    public String getTrainerName() {
    	return trainerName; 
    	}
    public int getCatchRate() { 
    	return catchRate; 
    	}
    public int getCurrentHP() {
    	return currentHP; 
    	}
    public int getMaxHP() { 
    	return maxHP; 
    	}
    public int getAttack() { 
    	return attack; 
    	}
    public int getDefense() {
    	return defense;
}
    public int getSpeed() { 
    	return speed; 
    	}
    public boolean isLegendary() { 
    	return isLegendary; 
    	}
    public List<Move> getMoves() {
    	return moves; 
    	}
    public boolean isDefeated() {
    	return isDefeated; 
    	}
    
    public void takeDamage(int damage) {
        currentHP = Math.max(0, currentHP - damage);
        if (currentHP == 0) {
            isDefeated = true;
        }
    }
    
    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }
    
    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
        if (this.currentHP <= 0) {
            this.currentHP = 0;
            this.isDefeated = true;
        }
    }
    
    public void setDefeated(boolean isDefeated) {
        this.isDefeated = isDefeated;
        if (isDefeated) {
            this.currentHP = 0;
        }
    }
    public boolean canUseDoubleRush() {
        return isLegendary;
    }
    
    public int performDoubleRush(Pokemon target) {
        if (!canUseDoubleRush()) return 0;
        // Legendary Pokemon can perform double rush
        Move move = moves.get(0); 
        return move.calculateDamage(target) * 2;
    }
    
    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Level: " + level);
        System.out.println("HP: " + currentHP + "/" + maxHP);
        System.out.println("Attack: " + attack);
        System.out.println("Defense: " + defense);
        System.out.println("Speed: " + speed);
        System.out.println("Type: " + getPokemonType());
        System.out.println("Legendary: " + (isLegendary ? "Yes" : "No"));
        System.out.println("Available Moves:");
        for (int i = 0; i < moves.size(); i++) {
            System.out.println((i + 1) + ". " + moves.get(i).getName() + " (Power: " + moves.get(i).getPower() + ")");
        }
    }
}

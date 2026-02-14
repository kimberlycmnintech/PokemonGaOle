import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Player {
    private String name;
    private List<Pokemon> pokemonCollection;
    private List<PokeBall> pokeBalls;
    private List<Disc> discs;
    private int score;
    
    public Player(String name) {
        this.name = name;
        this.pokemonCollection = new ArrayList<>();
        this.pokeBalls = new ArrayList<>();
        this.discs = new ArrayList<>();
        this.score = 0;
        
        // Initialize some pokeballs
    
        addPokeBall(new PokeBall("Poke Ball", 1.0, 5)); 
        addPokeBall(new GreatBall(3)); 
        addPokeBall(new UltraBall(2)); 
        addPokeBall(new MasterBall(1)); 
    }
    
    public String getName() {
    	return name; 
    	}
    public List<Pokemon> getPokemonCollection() {
    	return pokemonCollection; 
    	}
    public List<PokeBall> getPokeBalls() { 
    	return pokeBalls; 
    	}
    public List<Disc> getDiscs() { 
    	return discs; 
    	}
    public int getScore() { 
    	return score; 
    	}
    
    public void addPokemon(Pokemon pokemon) {
        pokemonCollection.add(pokemon);
    }
    
    public void removePokemon(Pokemon pokemon) {
        pokemonCollection.remove(pokemon);
    }
    
    public void addPokeBall(PokeBall pokeball) {
        pokeBalls.add(pokeball);
    }
    
    public PokeBall usePokeBall(Class<? extends PokeBall> ballClass) {
        for (PokeBall ball : pokeBalls) {
            if (ballClass.isInstance(ball) && ball.getQuantity() > 0) {
                ball.decrementQuantity();
                return ball; // Return the ball without removing it
            }
        }
        return null; // No balls left
    }
    
    public void addDisc(Disc disc) {
        discs.add(disc);
    }
    
    public void addScore(int points) {
        score += points;
    }
    
    public List<Pokemon> selectPokemonForBattle() {
        if (pokemonCollection.isEmpty()) {
            System.out.println("You have no Pokemon to battle with!");
            return new ArrayList<>();
        }
        
        System.out.println("\nSelect Pokemon for battle:");
        for (int i = 0; i < pokemonCollection.size(); i++) {
            Pokemon p = pokemonCollection.get(i);
            System.out.printf("%d. %s (Level %d, HP: %d/%d)\n", 
                i + 1, p.getName(), p.getLevel(), p.getCurrentHP(), p.getMaxHP());
        }
        
        List<Pokemon> selectedPokemon = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Select first Pokemon (1-" + pokemonCollection.size() + "): ");
        try {
            int choice1 = scanner.nextInt() - 1;
            if (choice1 >= 0 && choice1 < pokemonCollection.size()) {
                selectedPokemon.add(pokemonCollection.get(choice1));
            }
        } catch (Exception e) {
            selectedPokemon.add(pokemonCollection.get(0));
        }
        
        if (pokemonCollection.size() > 1) {
            System.out.print("Select second Pokemon (1-" + pokemonCollection.size() + "): ");
            try {
                int choice2 = scanner.nextInt() - 1;
                if (choice2 >= 0 && choice2 < pokemonCollection.size() && choice2 != (selectedPokemon.isEmpty() ? -1 : pokemonCollection.indexOf(selectedPokemon.get(0)))) {
                    selectedPokemon.add(pokemonCollection.get(choice2));
                }
            } catch (Exception e) {
                if (pokemonCollection.size() > 1) {
                    selectedPokemon.add(pokemonCollection.get(1));
                }
            }
        }
        
        return selectedPokemon;
    }
    
    public boolean hasDoubleRushDisc() {
        return discs.stream().anyMatch(disc -> disc instanceof DoubleRushDisc);
    }
    
    public List<DoubleRushDisc> getDoubleRushDiscs() {
        return discs.stream()
                   .filter(disc -> disc instanceof DoubleRushDisc)
                   .map(disc -> (DoubleRushDisc) disc)
                   .collect(Collectors.toList());
    }
}
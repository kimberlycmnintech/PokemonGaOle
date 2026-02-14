import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class Game {
    private Player player;
    private List<Pokemon> availablePokemon;
    private Random random;
    private ScoreManager scoreManager;
    private Scanner scanner;
    
    public Game() {
        this.random = new Random();
        this.scoreManager = new ScoreManager();
        this.availablePokemon = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }
    
    public void startGame() {
        System.out.println("=== WELCOME TO POKEMON GA-OLE ===");
        initializePlayer();
        
        boolean playing = true;
        while (playing) {
            displayMenu();
            int choice = getMenuChoice();
            
            switch (choice) {
                case 1:
                    playBattleAndCatch();
                    break;
                case 2:
                    displayPlayerCollection();
                    break;
                case 3:
                    scoreManager.displayTopScores();
                    break;
                case 4:
                    playing = false;
                    System.out.println("Thanks for playing Pokemon Ga-Ole!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public void initializePlayer() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        player = new Player(name);
        
        // Give player some starting Pokemon
        System.out.println("\nChoose your starter Pokemon:");
        System.out.println("1. Charmander (Fire)");
        System.out.println("2. Squirtle (Water)");
        System.out.println("3. Bulbasaur (Grass)");
        
        int choice = getMenuChoice();
        Pokemon starter = null;
        switch (choice) {
            case 1:
                starter = PokemonFactory.createPokemon("Charmander", 5, "FIRE");
                break;
            case 2:
                starter = PokemonFactory.createPokemon("Squirtle", 5, "WATER");
                break;
            case 3:
                starter = PokemonFactory.createPokemon("Bulbasaur", 5, "GRASS");
                break;
            default:
                starter = PokemonFactory.createPokemon("Charmander", 5, "FIRE");
        }
        
        player.addPokemon(starter);
        System.out.println("\nYou chose " + starter.getName() + "!");
    }
     
    
    public void playBattleAndCatch() {
        System.out.println("\n=== BATTLE AND CATCH MODE ===");
        
        // Generate wild Pokemon
        List<Pokemon> wildPokemon = generateWildPokemon();
        
        // Ensure player has at least 2 Pokemon before battle
        while (player.getPokemonCollection().size() < 2) {
            System.out.println("\nYou need at least 2 Pokemon to battle!");
            System.out.println("Wild Pokemon appeared:");
            
            for (int i = 0; i < wildPokemon.size(); i++) {
                System.out.println((i + 1) + ". " + wildPokemon.get(i).getName() + 
                                 " (Level " + wildPokemon.get(i).getLevel() + ")");
            }
            
        
            catchPhase(wildPokemon);
            
            // Regenerate wild Pokemon if all were skipped/failed
            if (wildPokemon.isEmpty()) {
                wildPokemon = generateWildPokemon();
            }
        }
        
        // Proceed to battle phase
        battlePhase();
    }
    public List<Pokemon> generateWildPokemon() {
        List<Pokemon> wildPokemon = new ArrayList<>();
        
        // Generate 3 wild Pokemon with varying levels
        for (int i = 0; i < 3; i++) {
            int level = 3 + random.nextInt(8); // Level 3-10
            Pokemon pokemon = PokemonFactory.createRandomPokemon(level);
            wildPokemon.add(pokemon);
        }
        
        // 20% chance for one to be legendary
        if (random.nextDouble() < 0.2) {
            int replaceIndex = random.nextInt(wildPokemon.size());
            String[] legendaryNames = {"Moltres", "Kyogre", "Entei", "Suicune", "Celebi", "Reshiram", "Keldeo", "Shaymin", "Virizion"};
            String legendaryName = legendaryNames[random.nextInt(legendaryNames.length)];
            String legendaryType = "FIRE"; // Default
            
            switch (legendaryName) {
                case "Kyogre":
                    legendaryType = "WATER";
                    break;
                case "Suicune":
                    legendaryType = "WATER";
                    break;
                case "Keldeo":
                    legendaryType = "WATER";
                    break;
                case "Celebi":
                    legendaryType = "GRASS";
                    break;
                case "Shaymin":
                    legendaryType = "GRASS";
                    break;
                case "Virizion":
                    legendaryType = "GRASS";
                    break;
                    case "Reshiram":
                    case "Entei":
                    case "Moltres":
                    default:
                        legendaryType = "FIRE"; 
            }
            
            Pokemon legendary = PokemonFactory.createPokemon(legendaryName, 8 + random.nextInt(5), legendaryType);
            wildPokemon.set(replaceIndex, legendary);
        }
        return wildPokemon;
    }
    
    public void catchPhase(List<Pokemon> wildPokemon) {
        System.out.println("\n=== CATCH PHASE ===");
        
        // Display wild Pokemon
        for (int i = 0; i < wildPokemon.size(); i++) {
            System.out.println("\n" + (i + 1) + ". " + wildPokemon.get(i).getName());
            wildPokemon.get(i).displayInfo();
        }
        
        System.out.print("\nWhich Pokemon do you want to try to catch? (1-" + wildPokemon.size() + ", 0 to skip): ");
        int choice = getMenuChoice();
        
        if (choice >= 1 && choice <= wildPokemon.size()) {
            Pokemon targetPokemon = wildPokemon.get(choice - 1);
            attemptCatch(targetPokemon);
            
            if (targetPokemon.getTrainerName().equals(player.getName())) {
                wildPokemon.remove(choice - 1);
                System.out.println(targetPokemon.getName() + " was caught!");
            } else {
                System.out.println("Failed to catch " + targetPokemon.getName() + "!");
            }
        } else {
            System.out.println("You skipped catching a Pokemon.");
        }
    }
    
    public void battlePhase() {
        System.out.println("\n=== BATTLE PHASE ===");
        
        if (player.getPokemonCollection().isEmpty()) {
            System.out.println("You have no Pokemon to battle with!");
            return;
        }
        
        // Generate new wild Pokemon for battle if needed
        List<Pokemon> wildPokemon = generateWildPokemon();
        
        System.out.println("Wild Pokemon for battle:");
        for (Pokemon p : wildPokemon) {
            System.out.println("- " + p.getName() + " (Level " + p.getLevel() + ")");
        }
        
        // Player selects Pokemon
        List<Pokemon> selectedPokemon = player.selectPokemonForBattle();
        
        if (selectedPokemon.isEmpty()) {
            System.out.println("No Pokemon selected for battle!");
            return;
        }
        
        // Start battle
        Battle battle = new Battle(player, wildPokemon, selectedPokemon);
        battle.startBattle();
        
        // After battle, attempt to catch defeated wild Pokemon
        System.out.println("\n=== POST-BATTLE CATCH ATTEMPT ===");
        for (Pokemon wildPok : wildPokemon) {
            if (wildPok.isDefeated()) {
                System.out.println("\nYou can attempt to catch the defeated " + wildPok.getName() + "!");
                attemptCatch(wildPok);
            }
        }
        
        // Add battle score to leaderboard
        scoreManager.addScore(new ScoreEntry(player.getName(), player.getScore(), new Date()));
    }
    
    public void attemptCatch(Pokemon pokemon) {
        System.out.println("\nAvailable Poke Balls:");
        List<PokeBall> availableBalls = player.getPokeBalls();
        
        if (availableBalls.isEmpty()) {
            System.out.println("You have no Poke Balls left!");
            return;
        }
        
        for (int i = 0; i < availableBalls.size(); i++) {
            System.out.println((i + 1) + ". " + availableBalls.get(i).getName() + 
                             " (x" + availableBalls.get(i).getQuantity() + ")");
        }
        
        System.out.print("Choose a Poke Ball (1-" + availableBalls.size() + "): ");
        int ballChoice = getMenuChoice() - 1;
        
        if (ballChoice >= 0 && ballChoice < availableBalls.size()) {
            PokeBall chosenBall = availableBalls.get(ballChoice);
            if (chosenBall.getQuantity() <= 0) {
                System.out.println("You don't have any " + chosenBall.getName() + "s left!");
                return;
            }
            
            boolean caught = chosenBall.attemptCatch(pokemon);
            chosenBall.decrementQuantity(); // Decrease quantity instead of removing
            
            if (caught) {
                System.out.println("Success! " + pokemon.getName() + " was caught!");
                pokemon.setTrainerName(player.getName());
                pokemon.setCurrentHP(pokemon.getMaxHP());
                pokemon.setDefeated(false);
                player.addPokemon(pokemon);
                if (pokemon.isLegendary()) {
                    grantLegendaryDisc(pokemon);
                }
            } else {
                System.out.println("Oh no! " + pokemon.getName() + " broke free!");
            }
        }
    }
    private void grantLegendaryDisc(Pokemon legendaryPokemon) {
        String discName = legendaryPokemon.getName() + " Double Rush Disc";
        DoubleRushMove doubleRushMove = createLegendaryDoubleRushMove(legendaryPokemon);
        
        player.addDisc(new DoubleRushDisc(discName, "Legendary", doubleRushMove));
        System.out.println("Obtained " + discName + " for " + legendaryPokemon.getName() + "!");
    }

    private DoubleRushMove createLegendaryDoubleRushMove(Pokemon legendary) {
        String moveName = "Double " + legendary.getName() + " Strike";
        
        // Determine MoveType based on the Pokémon's subclass
        MoveType moveType;
        if (legendary instanceof FirePokemon) {
            moveType = MoveType.FIRE;
        } else if (legendary instanceof WaterPokemon) {
            moveType = MoveType.WATER;
        } else if (legendary instanceof GrassPokemon) {
            moveType = MoveType.GRASS;
        } else {
            moveType = MoveType.FIRE; 
        }

        // Define move stats per legendary
        switch (legendary.getName()) {
            case "Kyogre":
                moveName = "Origin Pulse";
                return new DoubleRushMove(moveName, 70, 60, 0.9, moveType);
            case "Moltres":
                moveName = "Incinerate";
                return new DoubleRushMove(moveName, 60, 80, 0.9, moveType);
            case "Celebi":
                moveName = "Magical Leaf";
                return new DoubleRushMove(moveName, 50, 70, 1.0, moveType);
            case "Reshiram":
                moveName = "Fushion Flare";
                return new DoubleRushMove(moveName, 60, 70, 1.0, moveType);
            case "Entei":
                moveName = "Eruption";
                return new DoubleRushMove(moveName, 60, 50, 1.0, moveType);
            case "Suicune":
                moveName = "Hydro Pump";
                return new DoubleRushMove(moveName, 70, 50, 1.0, moveType);
            case "Keldeo":
                moveName = "Aqua Tail";
                return new DoubleRushMove(moveName, 50, 70, 1.0, moveType);
            case "Virizion":
                moveName = "Giga Drain";
                return new DoubleRushMove(moveName, 50, 70, 1.0, moveType);
            case "Shaymin":
                moveName = "Seed Flare";
                return new DoubleRushMove(moveName, 50, 70, 1.0, moveType);
            default:
                return new DoubleRushMove(moveName, 60, 80, 0.9, moveType);
        }
    }
    
    private void displayPlayerCollection() {
        System.out.println("\n=== YOUR POKEMON COLLECTION ===");
        System.out.println("Player: " + player.getName());
        System.out.println("Score: " + player.getScore());
        System.out.println("Poke Balls: " + player.getPokeBalls().size());
        
        if (player.getPokemonCollection().isEmpty()) {
            System.out.println("You have no Pokemon yet!");
        } else {
            for (int i = 0; i < player.getPokemonCollection().size(); i++) {
                System.out.println("\n" + (i + 1) + ".");
                player.getPokemonCollection().get(i).displayInfo();
            }
        }
    }
    
    public void displayMenu() {
        System.out.println("\n=== POKEMON GA-OLE MENU ===");
        System.out.println("1. Play Battle & Catch Mode");
        System.out.println("2. View Pokemon Collection");
        System.out.println("3. View Top Scores");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }
    
    private int getMenuChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            return choice;
        } catch (Exception e) {
            scanner.nextLine(); 
            return -1;
        }
    }
}
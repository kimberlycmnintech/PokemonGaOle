import java.util.*;

public class Battle {
	 private Player player;
	    private List<Pokemon> wildPokemon;
	    private List<Pokemon> playerPokemon;
	    private BattleResult result;
	    private int battleScore;
	    private boolean doubleRushActivated;
	    
	    public Battle(Player player, List<Pokemon> wildPokemon, List<Pokemon> playerPokemon) {
	        this.player = player;
	        this.wildPokemon = new ArrayList<>(wildPokemon);
	        this.playerPokemon = new ArrayList<>(playerPokemon);
	        this.battleScore = 0;
	        this.doubleRushActivated = false;
	    }
	    
	    public void startBattle() {
	        System.out.println("\n=== BATTLE START ===");
	        Scanner scanner = new Scanner(System.in);
	        Random random = new Random();
	        
	        while (!checkBattleEnd()) {
	            System.out.println("\n--- Turn ---");
	            
	            // Display current status
	            System.out.println("Your Pokemon:");
	            for (Pokemon p : playerPokemon) {
	                if (!p.isDefeated()) {
	                    System.out.printf("  %s: %d/%d HP\n", p.getName(), p.getCurrentHP(), p.getMaxHP());
	                }
	            }
	            
	            System.out.println("Wild Pokemon:");
	            for (Pokemon p : wildPokemon) {
	                if (!p.isDefeated()) {
	                    System.out.printf("  %s: %d/%d HP\n", p.getName(), p.getCurrentHP(), p.getMaxHP());
	                }
	            }
	            
	            // Player turn
	            Pokemon activePlayerPokemon = getActivePlayerPokemon();
	            Pokemon activeWildPokemon = getActiveWildPokemon();
	            
	            if (activePlayerPokemon != null && activeWildPokemon != null) {
	                System.out.println("\n" + activePlayerPokemon.getName() + "'s turn!");
	                
	                // Check if player can use Double Rush
	                if (activePlayerPokemon.canUseDoubleRush() && player.hasDoubleRushDisc() && !doubleRushActivated) {
	                    System.out.print("Use Double Rush? (y/n): ");
	                    String choice = scanner.nextLine().toLowerCase();
	                    if (choice.equals("y")) {
	                        List<DoubleRushDisc> doubleRushDiscs = player.getDoubleRushDiscs();
	                        if (!doubleRushDiscs.isEmpty()) {
	                            activateDoubleRush(activePlayerPokemon, doubleRushDiscs.get(0));
	                            int damage = processDoubleRushTurn(activePlayerPokemon, activeWildPokemon, doubleRushDiscs.get(0).getDoubleRushMove());
	                            activeWildPokemon.takeDamage(damage); // Damage is already printed by executeDoubleRush()
	                            doubleRushActivated = true;
	                            continue;
	                        }
	                    }
	                }
	                
	                // Regular move selection
	                List<Move> moves = activePlayerPokemon.getMoves();
	                System.out.println("Select a move:");
	                for (int i = 0; i < moves.size(); i++) {
	                    System.out.printf("%d. %s (Power: %d)\n", i + 1, moves.get(i).getName(), moves.get(i).getPower());
	                }
	                
	                int moveChoice = 0;
	                try {
	                    System.out.print("Your choice: ");
	                    moveChoice = scanner.nextInt() - 1;
	                    scanner.nextLine(); 
	                } catch (Exception e) {
	                    scanner.nextLine(); 
	                    moveChoice = 0;
	                }
	                
	                if (moveChoice >= 0 && moveChoice < moves.size()) {
	                    int damage = processTurn(activePlayerPokemon, activeWildPokemon, moves.get(moveChoice));
	                    System.out.printf("%s used %s for %d damage!\n", 
	                        activePlayerPokemon.getName(), moves.get(moveChoice).getName(), damage);
	                    activeWildPokemon.takeDamage(damage);
	                }
	                
	                if (checkBattleEnd()) break;
	                
	                // Wild Pokemon turn
	                activeWildPokemon = getActiveWildPokemon();
	                activePlayerPokemon = getActivePlayerPokemon();
	                
	                if (activeWildPokemon != null && activePlayerPokemon != null) {
	                    List<Move> wildMoves = activeWildPokemon.getMoves();
	                    Move wildMove = wildMoves.get(random.nextInt(wildMoves.size()));
	                    int damage = processTurn(activeWildPokemon, activePlayerPokemon, wildMove);
	                    System.out.printf("%s used %s for %d damage!\n", 
	                        activeWildPokemon.getName(), wildMove.getName(), damage);
	                    activePlayerPokemon.takeDamage(damage);
	                }
	            }
	        }
	        
	        // Determine battle result
	        determineBattleResult();
	        calculateBattleScore();
	        
	        System.out.println("\n=== BATTLE END ===");
	        System.out.println("Result: " + result);
	        System.out.println("Battle Score: " + battleScore);
	        
	        player.addScore(battleScore);
	    }
	    
	    private Pokemon getActivePlayerPokemon() {
	        for (Pokemon p : playerPokemon) {
	            if (!p.isDefeated()) return p;
	        }
	        return null;
	    }
	    
	    private Pokemon getActiveWildPokemon() {
	        for (Pokemon p : wildPokemon) {
	            if (!p.isDefeated()) return p;
	        }
	        return null;
	    }
	    
	    public int processTurn(Pokemon attacker, Pokemon defender, Move move) {
	        Random random = new Random();
	        if (random.nextDouble() > move.accuracy()) {
	            System.out.println(attacker.getName() + "'s attack missed!");
	            return 0;
	        }
	        
	        int baseDamage = move.calculateDamage(defender);
	        int finalDamage = Math.max(1, baseDamage - (defender.getDefense() / 4));
	        return finalDamage;
	    }
	    
	    public int processDoubleRushTurn(Pokemon attacker, Pokemon defender, DoubleRushMove doubleRushMove) {
	        System.out.printf("%s unleashes a Double Rush with %s!\n", 
	            attacker.getName(), doubleRushMove.getName());
	        
	        
	        int totalDamage = doubleRushMove.executeDoubleRush(attacker, defender);
	      
	        return totalDamage;
	    }
	    
	    public boolean checkBattleEnd() {
	        boolean allPlayerDefeated = playerPokemon.stream().allMatch(Pokemon::isDefeated);
	        boolean allWildDefeated = wildPokemon.stream().allMatch(Pokemon::isDefeated);
	        return allPlayerDefeated || allWildDefeated;
	    }
	    
	    private void determineBattleResult() {
	        boolean allPlayerDefeated = playerPokemon.stream().allMatch(Pokemon::isDefeated);
	        boolean allWildDefeated = wildPokemon.stream().allMatch(Pokemon::isDefeated);
	        
	        if (allWildDefeated && !allPlayerDefeated) {
	            result = BattleResult.WIN;
	        } else if (allPlayerDefeated && !allWildDefeated) {
	            result = BattleResult.LOSE;
	        } else {
	            result = BattleResult.DRAW;
	        }
	    }
	    
	    public int calculateBattleScore() {
	        int score = 0;
	        
	        // Base score for participating
	        score += 100;
	        
	        // Bonus for winning
	        if (result == BattleResult.WIN) {
	            score += 500;
	        } else if (result == BattleResult.DRAW) {
	            score += 200;
	        }
	        
	        // Bonus for defeating each wild Pokemon
	        for (Pokemon wild : wildPokemon) {
	            if (wild.isDefeated()) {
	                score += wild.getLevel() * 10;
	                if (wild.isLegendary()) {
	                    score += 300;
	                }
	            }
	        }
	        
	        // Bonus for using Double Rush
	        if (doubleRushActivated) {
	            score += 200;
	        }
	        
	        this.battleScore = score;
	        return score;
	    }
	    
	    public BattleResult getResult() { return result; }
	    public int getBattleScore() { return battleScore; }
	    
	    public boolean activateDoubleRush(Pokemon pokemon, DoubleRushDisc disc) {
	        if (disc.canBeUsedBy(pokemon)) {
	            disc.activate(pokemon);
	            return true;
	        }
	        return false;
	    }
	}

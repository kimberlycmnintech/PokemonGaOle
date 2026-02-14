import java.util.*;
public class PokemonFactory {
	 private static final String[] FIRE_NAMES = {"Charmander", "Vulpix", "Growlithe", "Ponyta", "Magmar", "Flareon","Litleo", "Moltres", "Entei","Reshiram"};
	    private static final String[] WATER_NAMES = {"Squirtle", "Psyduck", "Poliwag", "Tentacool", "Seel", "Goldeen", "Staryu", "Magikarp", "Gyarados", "Lapras", "Kyogre", "Suicune","Keldeo"};
	    private static final String[] GRASS_NAMES = {"Bulbasaur", "Oddish", "Paras", "Venonat", "Bellsprout", "Exeggcute", "Tangela", "Scyther","Leafeon", "Celebi","Virizion","Shaymin"};
	    private static final String[] LEGENDARY_NAMES = {"Moltres", "Kyogre", "Entei", "Suicune", "Celebi","Reshiram","Keldeo","Shaymin","Virizion"};
	    
	    public static Pokemon createPokemon(String name, int level, String type) {
	        Map<String, Integer> stats = generateRandomStats(level);
	        boolean isLegendary = Arrays.asList(LEGENDARY_NAMES).contains(name);
	        
	        switch (type.toUpperCase()) {
	            case "FIRE":
	                return new FirePokemon(name, level, stats.get("hp"), stats.get("attack"), 
	                                     stats.get("defense"), stats.get("speed"), isLegendary);
	            case "WATER":
	                return new WaterPokemon(name, level, stats.get("hp"), stats.get("attack"), 
	                                      stats.get("defense"), stats.get("speed"), isLegendary);
	            case "GRASS":
	                return new GrassPokemon(name, level, stats.get("hp"), stats.get("attack"), 
	                                      stats.get("defense"), stats.get("speed"), isLegendary);
	            default:
	                return new FirePokemon(name, level, stats.get("hp"), stats.get("attack"), 
	                                     stats.get("defense"), stats.get("speed"), isLegendary);
	        }
	    }
	    
	    public static Pokemon createRandomPokemon(int level) {
	        Random random = new Random();
	        String[] types = {"FIRE", "WATER", "GRASS"};
	        String type = types[random.nextInt(types.length)];
	        String name;
	        
	        switch (type) {
	            case "FIRE":
	                name = FIRE_NAMES[random.nextInt(FIRE_NAMES.length)];
	                break;
	            case "WATER":
	                name = WATER_NAMES[random.nextInt(WATER_NAMES.length)];
	                break;
	            case "GRASS":
	                name = GRASS_NAMES[random.nextInt(GRASS_NAMES.length)];
	                break;
	            default:
	                name = FIRE_NAMES[0];
	        }
	        
	        return createPokemon(name, level, type);
	    }
	    
	    public static Map<String, Integer> generateRandomStats(int level) {
	        Random random = new Random();
	        Map<String, Integer> stats = new HashMap<>();
	        
	        int baseHP = 50 + (level * 2) + random.nextInt(20);
	        int baseAttack = 30 + (level) + random.nextInt(15);
	        int baseDefense = 25 + (level) + random.nextInt(15);
	        int baseSpeed = 20 + level + random.nextInt(10);
	        
	        stats.put("hp", baseHP);
	        stats.put("attack", baseAttack);
	        stats.put("defense", baseDefense);
	        stats.put("speed", baseSpeed);
	        
	        return stats;
	    }
	}

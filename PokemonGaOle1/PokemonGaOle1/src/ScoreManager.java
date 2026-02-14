import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ScoreManager {
    private List<ScoreEntry> topScores;
    private int currentScore;
    private final String SCORES_FILE = "scores.txt";
    
    public ScoreManager() {
        this.topScores = new ArrayList<>();
        this.currentScore = 0;
        loadScoresFromFile();
    }
    
    public void addScore(ScoreEntry scoreEntry) {
        topScores.add(scoreEntry);
        topScores.sort(null);
        if (topScores.size() > 5) {
            topScores = topScores.subList(0, 5);
        }
        saveScoresToFile();
    }
    
    public List<ScoreEntry> getTopScores() {
        return new ArrayList<>(topScores);
    }
    
    public void saveScoresToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORES_FILE))) {
            for (ScoreEntry entry : topScores) {
                writer.println(entry.getPlayerName() + "," + entry.getScore() + "," + entry.getDate().getTime());
            }
        } catch (IOException e) {
            System.out.println("Error saving scores: " + e.getMessage());
        }
    }
    
    public void loadScoresFromFile() {
        try (Scanner scanner = new Scanner(new File(SCORES_FILE))) {
            topScores.clear();
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    Date date = new Date(Long.parseLong(parts[2]));
                    topScores.add(new ScoreEntry(name, score, date));
                }
            }
            topScores.sort(null);
        } catch (FileNotFoundException e) {
            
            topScores = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error loading scores: " + e.getMessage());
            topScores = new ArrayList<>();
        }
    }
    
    public void displayTopScores() {
        System.out.println("\n=== TOP 5 SCORES ===");
        if (topScores.isEmpty()) {
            System.out.println("No scores recorded yet!");
            return;
        }
        
        for (int i = 0; i < topScores.size(); i++) {
            ScoreEntry entry = topScores.get(i);
            System.out.printf("%d. %s - %d points (%s)\n", 
                i + 1, entry.getPlayerName(), entry.getScore(), 
                new SimpleDateFormat("yyyy-MM-dd").format(entry.getDate()));
        }
    }
}

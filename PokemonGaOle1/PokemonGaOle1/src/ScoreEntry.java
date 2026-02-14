
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreEntry implements Comparable<ScoreEntry> {
    private String playerName;
    private int score;
    private Date date;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public ScoreEntry(String playerName, int score, Date date) {
        this.playerName = playerName;
        this.score = score;
        this.date = date;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public int getScore() {
        return score;
    }
    
    public Date getDate() {
        return date;
    }
    
    public String getFormattedDate() {
        return dateFormat.format(date);
    }
    
    @Override
    public int compareTo(ScoreEntry other) {
        // Sort by score in descending order
        return Integer.compare(other.score, this.score);
    }
    
    @Override
    public String toString() {
        return String.format("%-15s %8d points    %s", 
                           playerName, score, getFormattedDate());
    }
}
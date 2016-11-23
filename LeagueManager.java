import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.LeagueOrganizer;

import java.util.Set;
import java.util.TreeSet;

public class LeagueManager {

  public static void main(String[] args) {
    Set<Team> teams = new TreeSet<Team>();
    Player[] players = Players.load();
    System.out.printf("%n%nThere are currently %d registered players.%n", players.length);
    
    // Your code here!
    LeagueOrganizer leagueOrganizer = new LeagueOrganizer(players, teams);
    leagueOrganizer.run();
    
  }

}

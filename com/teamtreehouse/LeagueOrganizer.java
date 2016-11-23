package com.teamtreehouse;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.Collections;
import java.util.Comparator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;

import java.util.Iterator;

public class LeagueOrganizer {

  private ArrayList<String> mMenu;
  private BufferedReader mReader;
  private ArrayList<Player> mAvailablePlayers;
  private Set<Team> mTeams;
  private int totalPlayers;

public LeagueOrganizer(Player[] players, Set teams) {
  mAvailablePlayers = new ArrayList<Player>(Arrays.asList(players));
  mTeams = teams;
  totalPlayers = mAvailablePlayers.size();
  //MENU
  mMenu = new ArrayList<String>();
  mMenu.add("Create a new team");  // index 0   --option 1
  mMenu.add("Add a player to a team");   // index 1  ---option 2
  mMenu.add("Remove a player from a team");   // index 2  --option 3
  mMenu.add("View a report of a team");   // index 3  --option 4
  mMenu.add("League Balance Report");   // index 4  --option 5
  mMenu.add("Print out a roster");   // index 5  ---option 6
  mMenu.add("Exit the program");   // index 6  ---option 7
  //READER
  mReader = new BufferedReader(new InputStreamReader(System.in));

}
  
  
public void run() {
  
  String choice = "";
  Team team;
  Team promptedTeam;
  Player promptedPlayer;
  Player promptedPlayerInATeam;
  
  do {
      try {
        choice = promptAction();
        switch(choice) {
          case "1":  //"Create a new team"
          
          if((totalPlayers/11) >= (mTeams.size()+1)) {         
            boolean wasAdded = false;
            team = promptNewTeam();            
            wasAdded = mTeams.add(team); //if it is not already registered
            if(!wasAdded) {
              System.out.printf("%nSorry, the team was not registered.%n" + 
                                "The team's name \"%s\" is already taken.%n%n",team.getTeamName());
            } else {
              System.out.printf("%nYou added a new team named \"%s\" with coach: %s %n %n",
                              team.getTeamName(),
                              team.getCoachName());
            }
          } else {            
            System.out.printf("%nSorry, we cannot add another team.%n" + 
                                "There are not enough players in the League to setup another team.%n%n");
          }                    
            break;
          
          case "2":  //"Add a player to a team"
            if (mTeams.size() == 0) {
              System.out.printf("%nThere are no teams registered yet. Please Add a team first.%n%n");
              break;
            }          
            promptedTeam = promptTeam();            
            if(promptedTeam == null) {
              System.out.printf("%nSorry, you did not select a valid index. Try again%n");
              break;
            } else {
              System.out.printf("%nYou selected team \"%s\" to add players. %n %n",
                              promptedTeam.getTeamName());
            }
          
          if(promptedTeam.teamPlayers.size() == 11){
            System.out.printf("%nSorry, we cannot add another player.%n" + 
                                "Team \"%s\" is complete. It has already 11 players.%n%n",promptedTeam.getTeamName());
            break;
          }
          
            promptedPlayer = promptPlayer();
            if(promptedPlayer == null) {
              System.out.printf("%nSorry, you did not select a valid index. Try again%n");
              promptedTeam = null;
              break;
            } else {
              promptedTeam.addPlayer(promptedPlayer);
              System.out.printf("%nYou added player \"%s, %s\" to team \"%s\". %n %n",
                              promptedPlayer.getLastName(),
                              promptedPlayer.getFirstName(),
                              promptedTeam.getTeamName());
              mAvailablePlayers.remove(promptedPlayer);
            }            
            break;
          
          case "3":  //"Remove a player from a team"
            if (mTeams.size() == 0) {
              System.out.printf("%nThere are no teams registered yet. Please Add a team and players first.%n%n");
              break;
            }
            promptedTeam = promptTeam();            
            if(promptedTeam == null) {
              System.out.printf("%nSorry, you did not select a valid index. Try again%n");
              break;
            } else {
              System.out.printf("%nYou selected team \"%s\" with %d players. %n %n",
                              promptedTeam.getTeamName(),
                              promptedTeam.teamPlayers.size());
            }
            if(promptedTeam.teamPlayers.size() == 0) {
              System.out.printf("%nSorry, team \"%s\" doesn't have players to remove. Try again%n%n",
                                promptedTeam.getTeamName());
              break;
            }          
            promptedPlayer = promptPlayerInATeam(promptedTeam);          
            if(promptedPlayer == null) {
              System.out.printf("%nSorry, you did not select a valid index. Try again%n");
              break;
            } else {
              promptedTeam.removePlayer(promptedPlayer);
              System.out.printf("%nYou removed player \"%s, %s\" from team \"%s\". %n %n",
                              promptedPlayer.getLastName(),
                              promptedPlayer.getFirstName(),
                              promptedTeam.getTeamName());
              mAvailablePlayers.add(promptedPlayer);
            }            
            break;
          
          case "4": //"View a report of a team"
            if (mTeams.size() == 0) {
              System.out.printf("%nThere are no teams registered yet. Please Add a team and players first.%n%n");
              break;
            }
            promptedTeam = promptTeam();            
            if(promptedTeam == null) {
              System.out.printf("%nSorry, you did not select a valid index. Try again%n");
              break;
            } else {
              System.out.printf("%nYou selected team \"%s\" with %d players. %n %n",
                              promptedTeam.getTeamName(),
                              promptedTeam.teamPlayers.size());
            }
            if(promptedTeam.teamPlayers.size() == 0) {
              System.out.printf("%nSorry, team \"%s\" doesn't have players to make a report. Try again%n%n",
                                promptedTeam.getTeamName());
              break;
            }
            
            teamReport(promptedTeam);
          
            break;
             
          case "5":  //"League Balance Report"
            leagueBalanceReport();
            break;
          
          case "6":  //"Print out a roster"
            if (mTeams.size() == 0) {
              System.out.printf("%nThere are no teams registered yet. Please Add a team and players first.%n%n");
              break;
            }
            promptedTeam = promptTeam();            
            if(promptedTeam == null) {
              System.out.printf("%nSorry, you did not select a valid index. Try again%n");
              break;
            }
            if(promptedTeam.teamPlayers.size() == 0) {
              System.out.printf("%nSorry, team \"%s\" doesn't have players to print a roster. Add players first.%n%n",
                                promptedTeam.getTeamName());
              break;
            }            
            printRoster(promptedTeam);          
            break;
          
          case "7":  //"Exit the program"
            System.out.printf("%nYou chose quit. See you soon!%n%n");
            break;
          default:
            System.out.printf("Unknown choice, (select a number from the menu)%n");
        }
      } catch(IOException ioe) {
        System.out.println("Problem with input");
        ioe.printStackTrace();
      }
    } while(!choice.equals("7"));  // option 7 is for quit.
}
  
private String promptAction() throws IOException {
    System.out.printf("%nThere are %d teams registered and there are %d players without a team. %n" +
                      "Select a number from the menu accordingly: %n%n",mTeams.size(),mAvailablePlayers.size());
    for (int i = 0; i < mMenu.size(); i++) {
      System.out.printf("%d - %s %n",
                        i+1,
                        mMenu.get(i));
    }
    System.out.printf("%nWhat do you want to do:  ");
    String choice = mReader.readLine();
    return choice.trim();
  }  
  
  private Team promptNewTeam() throws IOException {
    System.out.print("Enter the team's name:  ");
    String teamName = mReader.readLine();
    System.out.print("Enter the coach's name:  ");
    String coachName = mReader.readLine();
    return new Team(teamName, coachName);
  }

  private Team promptTeam() throws IOException {    
    List<Team> teamsWithIndex = new ArrayList<Team>();    
    int counter = 1;          
    System.out.printf("%nThere are %d registered teams to choose from.%n%n",mTeams.size());
    Iterator<Team> itr = mTeams.iterator();    
    while (itr.hasNext() ) {     
      teamsWithIndex.add(itr.next());
      System.out.printf("%d - %s %n",counter,teamsWithIndex.get(counter-1));
      counter++;
    }    
    System.out.printf("%nChoose a team by its number:  ");    
    String optionAsString = mReader.readLine();
    
    int choice;
    try {
      choice = Integer.parseInt(optionAsString.trim());
    } catch (NumberFormatException nfe) {
      return null;
    }
    
    if (choice < 1 || choice > teamsWithIndex.size()) {
      return null;
    }    
    return teamsWithIndex.get(choice-1);
  }  
  
  private Player promptPlayer() throws IOException {
    Collections.sort(mAvailablePlayers);
    System.out.printf("%nThere are %d available players to choose from.%n%n",mAvailablePlayers.size());
    int counter = 1;
    for(Player availablePlayer : mAvailablePlayers) {
      System.out.printf("%d - %s %n",counter,availablePlayer);
      counter++;
    }  
    System.out.printf("%nChoose a player by its number:  ");    
    String optionAsString = mReader.readLine();
    int choice;
    try {
      choice = Integer.parseInt(optionAsString.trim());
    } catch (NumberFormatException nfe) {
      return null;
    }    
    if (choice < 1 || choice > mAvailablePlayers.size()) {
      return null;
    }    
    return mAvailablePlayers.get(choice-1);
  }
  
  private Player promptPlayerInATeam(Team aTeam) throws IOException {
    Collections.sort(aTeam.teamPlayers);
    System.out.printf("%nThere are %d players in the team.%n%n",aTeam.teamPlayers.size()); 
    int counter = 1;
    for(Player aPlayer : aTeam.teamPlayers) {      
      System.out.printf("%d - %s %n",counter, aPlayer);    
      counter++;
    }
    System.out.printf("%nChoose a player to remove him from the team by its number:  ");    
    String optionAsString = mReader.readLine();
    int choice;
    try {
      choice = Integer.parseInt(optionAsString.trim());
    } catch (NumberFormatException nfe) {
      return null;
    }
      
    if (choice < 1 || choice > aTeam.teamPlayers.size()) {
      return null;
    }
    System.out.println(aTeam.teamPlayers.get(choice-1));    
    return aTeam.teamPlayers.get(choice-1);
  }

  private void teamReport(Team aTeam) {  
    List<Player> playersByHeight = aTeam.teamPlayers;
    int numberOfPlayers = playersByHeight.size();
    int experiencedPlayers = 0;
    int totalSize = 0;    
    playersByHeight.sort(new Comparator<Player>() {    
      @Override
      public int compare(Player player1, Player player2) {
        if(player1.equals(player2)) {
          return 0;
        }        
        int heightCmp = player2.getHeightInInches() - player1.getHeightInInches(); //substration backwards change the order       
        if (heightCmp < 0) {
          heightCmp = -1;
        } else if (heightCmp > 0) {
          heightCmp = 1;
        } else {
          heightCmp = 0;
        }                
        if (heightCmp == 0) {
          heightCmp = player1.getLastName().compareTo(player2.getLastName());          
          if (heightCmp == 0) {
            return player1.getFirstName().compareTo(player2.getFirstName());
          }          
        }
        return heightCmp;        
      }    
    });    
    //REPORT
    for(Player aPlayer : playersByHeight) {
      System.out.println(aPlayer);
      if (aPlayer.isPreviousExperience()) {
        experiencedPlayers++;        
      }
      totalSize += aPlayer.getHeightInInches();
    }    
    System.out.printf("%nThe average experience for team \"%s\" is: %d %%.",
                      aTeam.getTeamName(),
                      experiencedPlayers*100/numberOfPlayers);
    System.out.printf("%nThe average size for team \"%s\" is: %d inches.%n%n",
                      aTeam.getTeamName(),
                      totalSize/numberOfPlayers);    
  } 
  
  private void leagueBalanceReport() {
    int experiencedPlayers = 0;   
    int inexperiencedPlayers = 0;
    Map<Integer, Integer> teamPlayersBySize;
    
    for (Team aTeam : mTeams) {      
      for (Player aPlayer : aTeam.teamPlayers) {
        if(aPlayer.isPreviousExperience() == true) {
          experiencedPlayers++;
        } else {
          inexperiencedPlayers++;
        }
      }      
      System.out.printf("%nTeam \"%s\" have: %d experienced players and %d unexperienced players.",
                       aTeam.getTeamName(),
                       experiencedPlayers,
                       inexperiencedPlayers);      
      experiencedPlayers = 0;
      inexperiencedPlayers = 0;      
      System.out.printf("%n");
      teamPlayersBySize = aTeam.howManyBySize();
      for (Map.Entry<Integer, Integer> entry : teamPlayersBySize.entrySet()) {
	       System.out.println("There are: " + entry.getValue() + " players with: " + entry.getKey() + " inches.");
       }
    }  
  System.out.printf("%n"); 
  }
  
  private void printRoster(Team aTeam) {
    Collections.sort(aTeam.teamPlayers);
    System.out.printf("%nThis is the roster for team \"%s\": %n%n",aTeam.getTeamName()); 
    int counter = 1;
    for(Player aPlayer : aTeam.teamPlayers) {      
      System.out.printf("%d - %s %n",counter, aPlayer);    
      counter++;
    }        
    System.out.printf("%n%n");  
  }
  
  
}
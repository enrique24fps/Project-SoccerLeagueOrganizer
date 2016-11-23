package com.teamtreehouse.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Team implements Comparable<Team>, Serializable {
  
  private String teamName;
  private String coachName;
  public List<Player> teamPlayers;
  
  public Team(String teamName, String coachName) {
    this.teamName = teamName;
    this.coachName = coachName;
    this.teamPlayers = new ArrayList<Player>();    
  }
  
  public void setTeamName(String name) {
    teamName = name;
  }
  
  public void setcoachName(String name) {
    coachName = name;
  }
  
  public void addPlayer(Player player) {
    teamPlayers.add(player);
  
  }
  
  public void removePlayer(Player player) {
    teamPlayers.remove(player);
  
  }
  
  public String getTeamName() {
    return teamName;
  }
  
  public String getCoachName() {
    return coachName;
  }
  
  @Override
  public String toString() {
    return String.format("%s with coach %s has %d players",
                        teamName,
                        coachName,
                        teamPlayers.size());
  }
  
  
  @Override
  public int compareTo(Team other) {
    // We always want to sort by Team name then coach name
    if(equals(other)) {
      return 0;
    } 
    int coachNameCmp = this.teamName.compareTo(other.teamName);
    if (coachNameCmp == 0) {
      return this.coachName.compareTo(other.coachName);
    }
    
    return coachNameCmp;
  }

  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Team)) return false;

    Team team = (Team) o;
    return teamName.equals(team.teamName);
  }

  @Override
  public int hashCode() {
    int result = teamName.hashCode();
    result = 31 * result + this.teamName.hashCode();
    result = 31 * result + this.coachName.hashCode();
    return result;
  }
  
  public Map<Integer, Integer> howManyBySize() {
    Map<Integer, Integer> howManyBySize = new TreeMap<>();
    for(Player aPlayer : teamPlayers) {
      Integer counter = howManyBySize.get(aPlayer.getHeightInInches());
      if (counter == null) {
        counter = 0;
        howManyBySize.put(aPlayer.getHeightInInches(),counter);       
      }
      howManyBySize.replace(aPlayer.getHeightInInches(),counter+1);      
    }  
    return howManyBySize;
  }
  
  

  
  
}
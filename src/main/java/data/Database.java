package data;

import misc.Player;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Player> players;
    private String parentalPassword;
    private long timeLimit;

    public Database() {
        players = new ArrayList<>();
        parentalPassword = "";
        timeLimit = 0;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void printPlayers() {
        players.forEach(player -> System.out.println(player.getUsername()));
    }

    public String getParentalPassword() {
        return parentalPassword;
    }

    public void setParentalPassword(String password) {
        this.parentalPassword = password;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }
}
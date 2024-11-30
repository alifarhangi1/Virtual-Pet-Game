package data;

import misc.Player;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Player> players;
    private String parentalPassword;

    public Database() {
        players = new ArrayList<>();
        parentalPassword = "";
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
}
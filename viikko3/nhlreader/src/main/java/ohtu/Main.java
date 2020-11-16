/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;
import java.util.*;
/**
 *
 * @author mluukkai
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://nhlstatisticsforohtu.herokuapp.com/players";
        
        String bodyText = Request.Get(url).execute().returnContent().asString();

        Gson mapper = new Gson();
        Player[] players = mapper.fromJson(bodyText, Player[].class);
        ArrayList<Player> pelaajat = new ArrayList<>();
        for (Player player : players) {
            if (player.getNationality().equals("FIN")) {
                pelaajat.add(player);
            } 
        }
        pelaajat.sort(Comparator.comparing(Player::getPoints).reversed());
        
        System.out.println("Oliot:");
        for (Player player : pelaajat) {
            System.out.println(player);
            
        }   
    }    
}

package org.peidevs.waro.function;

import org.peidevs.waro.player.*;
import org.peidevs.waro.strategy.*;
import org.peidevs.waro.table.*;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

import java.util.stream.*;
import static java.util.stream.Collectors.toList;

public class GameTest {
    @Test
    public void testDetermineWinner_Basic() {
        int numPlayers = 3;
        int numCards = 12;

        var p1 = new Player("p1", new PlayerStats(5,0,0));
        var p2 = new Player("p2", new PlayerStats(9,0,0));
        var p3 = new Player("p3", new PlayerStats(4,0,0));

        var players = new ArrayList<Player>(List.of(p1, p2, p3));

        // test
        var newPlayers = new Game(numCards, false).determineWinner(players);

        assertEquals(3, newPlayers.size());
        assertEquals(1, newPlayers.stream()
                                  .filter(p -> p.getName().equals("p1") && p.getPlayerStats().getNumGamesWon() == 0)
                                  .count());
        assertEquals(1, newPlayers.stream()
                                  .filter(p -> p.getName().equals("p2") && p.getPlayerStats().getNumGamesWon() == 1)
                                  .count());
        assertEquals(1, newPlayers.stream()
                                  .filter(p -> p.getName().equals("p3") && p.getPlayerStats().getNumGamesWon() == 0)
                                  .count());
    }

    @Test
    public void testApply_Basic() {
        int numCards = 12;
        int maxCard = numCards;
        var strategy = new NextCard();
        
        var p1 = new Player("p1", strategy, maxCard, new Hand());
        var p2 = new Player("p2", strategy, maxCard, new Hand());
        var p3 = new Player("p3", strategy, maxCard, new Hand());
        
        var players = new ArrayList<Player>(List.of(p1, p2, p3));
                
        // test
        var newPlayers = new Game(numCards, false).apply(players);
        
        assertEquals(3, newPlayers.size());
        assertEquals(0, newPlayers.get(0).getNumCardsInHand());
        assertEquals(0, newPlayers.get(1).getNumCardsInHand());
        assertEquals(0, newPlayers.get(2).getNumCardsInHand());
        assertEquals(1, newPlayers.stream().filter(p -> p.getPlayerStats().getNumGamesWon() == 1).count());
        assertEquals(2, newPlayers.stream().filter(p -> p.getPlayerStats().getNumGamesWon() == 0).count());
        int maxTotal = maxCard + (maxCard - 1) + (maxCard - 2);
        assertEquals(3, newPlayers.stream().filter(p -> p.getPlayerStats().getTotal() <= maxTotal).count());
        assertEquals(3, newPlayers.stream().map(p -> p.getPlayerStats().getNumRoundsWon()).mapToInt(i->i).sum());                
    }

    @Test
    public void testPlay_Basic() {
        int numCards = 12;
        int maxCard = numCards;
        var strategy = new NextCard();
        
        var h1 = new Hand(List.of(1,5,9));
        var p1 = new Player("p1", strategy, maxCard, h1);

        var h2 = new Hand(List.of(4,8,6));
        var p2 = new Player("p2", strategy, maxCard, h2);

        var h3 = new Hand(List.of(7,2,3));
        var p3 = new Player("p3", strategy, maxCard, h3);
        
        var players = new ArrayList<Player>(List.of(p1, p2, p3));
        
        var kitty = new Hand(List.of(10,11,12));
        
        // test
        var newPlayers = new Game(numCards, false).play(kitty, players.stream()).collect(toList());
        
        assertEquals(3, newPlayers.size());
        assertEquals(0, newPlayers.get(0).getNumCardsInHand());
        assertEquals(0, newPlayers.get(1).getNumCardsInHand());
        assertEquals(0, newPlayers.get(2).getNumCardsInHand());
        assertEquals(3, newPlayers.stream().filter(p -> p.getPlayerStats().getNumRoundsWon() == 1).count());
        assertEquals(1, newPlayers.stream().filter(p -> p.getPlayerStats().getTotal() == 10).count());
        assertEquals(1, newPlayers.stream().filter(p -> p.getPlayerStats().getTotal() == 11).count());
        assertEquals(1, newPlayers.stream().filter(p -> p.getPlayerStats().getTotal() == 12).count());
    }
}

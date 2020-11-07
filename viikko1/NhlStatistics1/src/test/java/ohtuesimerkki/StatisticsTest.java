/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nate
 */
public class StatisticsTest {
    
    Reader readerStub = new Reader() {
        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<>();
            players.add(new Player("MacJesus", "EDM", 100, 100));
            players.add(new Player("Palms", "NJD", 15, 8));
            players.add(new Player("Moose", "AVS", 20, 11));
            players.add(new Player("Hughes", "NJD", 0, 0));
            
            return players;
        }
        
    };
    
    Statistics stats;
    
    public StatisticsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        stats = new Statistics(readerStub);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void searchReturnsTheCorrectPlayer() {
        Player fromStats = stats.search("Palms");
        assertEquals("Palms", fromStats.getName());
        assertEquals("NJD", fromStats.getTeam());
        assertEquals(15, fromStats.getGoals());
        assertEquals(8, fromStats.getAssists());
    }
    @Test
    public void searchReturnsNullIfNoSuchPlayerIsInTheStats() {
        Player fromStats = stats.search("Kurri");
        assertEquals(fromStats, null);
    }
    @Test
    public void teamReturnsAListOfAllPlayersWhoBelongToThatTeam() {
        List<Player> fromStats = stats.team("NJD");
        for (Player player : fromStats) {
            assertEquals("NJD", player.getTeam());
        }
    }
    @Test
    public void teamReturnsAnEmptyListIfNoPlayersBelongToTheTeam() {
        List<Player> fromStats = stats.team("DET");
        assertEquals(0, fromStats.size());
    }
    @Test
    public void topScorersReturnsTheDesiredAmountOfPlayers () {
        List<Player> scorers = stats.topScorers(3);
        assertEquals(3, scorers.size());
    }
    @Test
    public void topScorersReturnsThePlayersSortedByGoalsScored () {
        List<Player> scorers = stats.topScorers(3);
        assertEquals("MacJesus", scorers.get(0).getName());
    }
    @Test
    public void topScorersReturnsEmptyIfhowManyIsLessThanOrEqualToZero () {
        List<Player> scorers = stats.topScorers(-2);
        assertEquals(0, scorers.size());
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

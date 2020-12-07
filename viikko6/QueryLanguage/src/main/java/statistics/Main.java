package statistics;

import statistics.matcher.*;

public class Main {
    public static void main(String[] args) {
        String url = "https://nhlstatisticsforohtu.herokuapp.com/players.txt";

        Statistics stats = new Statistics(new PlayerReaderImpl(url));
          
        QueryBuilder builder = new QueryBuilder();
        
        Matcher m = builder.oneOf(builder.playsIn("PHI").hasAtLeast(10, "assists").hasFewerThan(5, "goals").build(),
                                  builder.playsIn("EDM").hasAtLeast(40, "points").build()).build();
        
        
        for (Player player : stats.matches(m)) {
            System.out.println(player);
        }
    }
}

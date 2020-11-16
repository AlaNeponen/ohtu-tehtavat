
package ohtu;

public class Player {
    private String name;
    private int goals;
    private int assists;
    private int points;
    private String nationality;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }
    @Override
    public String toString() {
        return name + ", " + goals + " goals, " + assists + " assists";
    }
      
}

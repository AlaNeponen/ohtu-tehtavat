package ohtu;

public class Multiplier {
    private int value;
    public Multiplier(int luku) {
        this.value = luku;
    }

    public int multipliedBy(int arvo) {
        return this.value * arvo;
    }
}
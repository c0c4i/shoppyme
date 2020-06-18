package shoppyme;

import java.time.LocalDate;

public class FidelityCard {
    private final int id;
    private LocalDate emission_date;
    private int points;

    public FidelityCard(int id, LocalDate emission_date, int points) {
        this.id = id;
        this.emission_date = emission_date;
        this.points = points;
    }

    public FidelityCard() {
        Stock s = Stock.getInstance();
        this.id = s.getNewFidelityID();
        this.emission_date = LocalDate.now();
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public int getID() {
        return id;
    }

    public void addPoints(int points) {
        this.points = this.points + points;
    }

    @Override
    public String toString() {
        return "FidelityCard{" +
                "id=" + id +
                ", emission_date=" + emission_date +
                ", points=" + points +
                '}';
    }
}

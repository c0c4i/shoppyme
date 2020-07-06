package shoppyme.model;

import java.time.LocalDate;

public class FidelityCard {
    public final int id;
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

    public LocalDate getEmissionDate() {
        return emission_date;
    }

    public void addPoints(int points) {
        this.points = this.points + points;
    }

    @Override
    public String toString() {
        return String.format("\t{\n\t\t\"id\": %d, \n\t\t\"emission_date\": %d, \n\t\t\"points\": %d\n\t},\n",
                id, emission_date.toEpochDay(), points);
    }
}

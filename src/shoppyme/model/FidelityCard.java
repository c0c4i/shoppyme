package shoppyme.model;

import java.time.LocalDate;

public class FidelityCard {
    public final int id;
    private LocalDate emissionDate;
    private int points;

    public FidelityCard(int id, LocalDate emission_date, int points) {
        this.id = id;
        this.emissionDate = emission_date;
        this.points = points;
    }

    public FidelityCard() {
        Stock s = Stock.getInstance();
        this.id = s.getNewFidelityID();
        this.emissionDate = LocalDate.now();
        this.points = 0;
        s.addFidelityCard(this);
    }

    public int getPoints() {
        return points;
    }

    public LocalDate getEmissionDate() {
        return emissionDate;
    }

    public void addPoints(int points) {
        this.points = this.points + points;
        Stock.updateFidelityCard(this);
    }

    @Override
    public String toString() {
        return String.format("\t{\n\t\t\"id\": %d, \n\t\t\"emission_date\": %d, \n\t\t\"points\": %d\n\t},\n",
                id, emissionDate.toEpochDay(), points);
    }
}

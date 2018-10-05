import java.util.List;
import java.util.ArrayList;

public class Apiary {
    private static final Apiary instance = new Apiary();
    private List<Beehive> beehives;

    private Apiary() {
        this.beehives = new ArrayList<Beehive>();
    }

    public static Apiary getInstance() {
        return instance;
    }

    public void spawnBeehive(Species species) {
        this.addNewBeeHiveFromSpecies(species);
    }

    public void addNewBeeHiveFromSpecies(Species species) {
        Beehive newBeehive = new Beehive(species);
        this.beehives.add(newBeehive);
        newBeehive.spawnHive();
    }

    public void summary() {
        System.out.printf("Beehive numbers: %d\n", Apiary.getInstance().beehives.size());
        for (Beehive beehiv: this.beehives) {
            beehiv.summary();
        }
    }
}
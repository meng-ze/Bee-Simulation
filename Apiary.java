import java.util.List;
import java.util.ArrayList;

public class Apiary {
    private static final Apiary instance = new Apiary();
    public List<Beehive> beehives;

    private Apiary() {
        this.beehives = new ArrayList<Beehive>();
    }

    public static Apiary getInstance() {
        return instance;
    }

    public void spawnBeehive(Species species) {
        Beehive newBeehive = new Beehive(species);
        Apiary.getInstance().beehives.add(newBeehive);
    }
    public void summary() {
        System.out.printf("Beehive numbers: %d", Apiary.getInstance().beehives.size());
    }
}
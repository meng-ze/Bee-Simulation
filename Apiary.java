import java.util.List;
import java.util.ArrayList;

/*
 * Apiary is designed using 'Singleton pattern'.
 * For the reason of 'private constructor', users could never 
 * call the constructor this class's constructor themselves
 * and thus this instance would be the only copy of instance in the program.
 * Users could only use the instance by calling 'Apiary.getInstance()' to get the singleton.
 * 
 * The instance is located at the first line of the class, this instance is 
 * in type of 'static' type, meaning that durring the program running,
 * this instance will always be available
 *
 * spawnBeehives(Species) is called when user wish to spawn a new Beehive instance for a certain species
 * summary() lets user get the brief summary of this apiary
 */ 

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

    public void summary() {
        System.out.printf("Beehive numbers: %d\n", Apiary.getInstance().beehives.size());
        for (Beehive beehiv: this.beehives) {
            beehiv.summary();
        }
    }

    public List<Beehive> getBeehives() {
        return this.beehives;
    }

    public List<Bee> getAllBees() {
        ArrayList<Bee> returningBee = new ArrayList<Bee>();
        for (Beehive beehive: this.beehives) {
            returningBee.add(beehive.queen);
            List<Bee> tmpBeeList = beehive.getAllBees();
            for (Bee bee: tmpBeeList) {
                returningBee.add(bee);
            }
        }

        return returningBee;
    }

    private void addNewBeeHiveFromSpecies(int posX, int posY, Species species) {
        Beehive newBeehive = new Beehive(posX, posY, species);
        this.beehives.add(newBeehive);
        newBeehive.spawnHive(0);
    }

    private void addNewBeeHiveFromSpecies(Species species) {
        Beehive newBeehive = new Beehive(species);
        this.beehives.add(newBeehive);
        newBeehive.spawnHive(0);
    }

}
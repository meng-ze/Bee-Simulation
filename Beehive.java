import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

 public class Beehive<T> {
    private T species;
    private List<Hive> hives;
    private Bee queen;

    private float posX;
    private float posY;

    public Beehive(T species) {
        this.species = species;
        this.hives = new ArrayList<Hive>();
    }

    public Beehive(float posX, float posY, T species) {
        this.posX = posX;
        this.posY = posY;
        this.species = species;
        this.hives = new ArrayList<Hive>();
    }
    public void spawnQueen() {
        this.queen = new Bee(this, RoleEnum.QUEEN);
    }

    public float getPositionX() {
        return this.posX;
    }
    public float getPositionY() {
        return this.posY;
    }

    public void spawnHive() {
        Hive newHive = new Hive(this);
        this.hives.add(newHive);
    }
    public List<Bee> getAllBees() {
        ArrayList<Bee> totalBeesInBeehive = new ArrayList<Bee>();
        for (Hive hive: this.hives) {
            ArrayList<Bee> bees = hive.getAllBees();
            for (Bee bee: bees) {
                totalBeesInBeehive.add(bee);
            }
        }
        return totalBeesInBeehive;
    }

    public int getBeesCount() {
        if (this.queen != null) {
            int tmp_sum = 1;
            for (Hive hive: this.hives) {
                tmp_sum += hive.getBeesCount();
            }
            return tmp_sum;
        }
        return 0;
    }

    public T getSpecies() {
        return this.species;
    }

    public void summary() {
        System.out.printf("Species: %s\n", this.species);
        System.out.printf("---------- Hive numbers   : %d\n", this.hives.size());
        System.out.printf("---------- Total Bee count: %d\n", this.getBeesCount());
        for (Hive hive: this.hives) {
            hive.summaryHive();
        }
    }
}


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
        this.queen = new Bee(this, RoleEnum.QUEEN);
        this.hives = new ArrayList<Hive>();
    }

    public Beehive(float posX, float posY, T species) {
        this.posX = posX;
        this.posY = posY;
        this.species = species;
        this.queen = new Bee(this, RoleEnum.QUEEN);
        this.hives = new ArrayList<Hive>();
    }

    public void spawnHive() {
        Hive newHive = new Hive(this);
        this.hives.add(newHive);
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

class Hive {
    private Map<RoleEnum, ArrayList<Bee>> bees;
    private Beehive ownerBeehive;

    public void spawnBee(RoleEnum role) {
        this.bees.get(role).add(new Bee(this.ownerBeehive, role));
    }

    public Hive(Beehive owner) {
        this.ownerBeehive = owner;
        this.bees = new HashMap<RoleEnum, ArrayList<Bee>>();
        this.bees.put(RoleEnum.WORKER, new ArrayList<Bee>());
        this.bees.put(RoleEnum.WARRIOR, new ArrayList<Bee>());
        this.bees.put(RoleEnum.KILLER, new ArrayList<Bee>());
        this.randomSpawnBee(10);
    }

    public void randomSpawnBee(int number) {
        for (int i=0; i<number; ++i) {
            Random random = new Random();
            RoleEnum role = RoleEnum.values()[random.nextInt(3)];
            this.spawnBee(role);
        }
    }

    public void summaryHive() {
        for (RoleEnum iter: this.bees.keySet()) {
            if (iter != RoleEnum.QUEEN) {
                System.out.printf("---------- [%d]: %d\n", iter.ordinal(), this.bees.get(iter).size());
            }
        }
    }

    public int getBeesCount() {
        int tmp_sum = 0;
        for (RoleEnum iter: this.bees.keySet()) {
            tmp_sum += this.bees.get(iter).size();
        }
        return tmp_sum;
    }
}

class Room {
    public int maxResident = 20;
}

class Bee {
    Object species;
    public Bee(Beehive beehive, RoleEnum role) {
        this.species = beehive.getSpecies();
    }
}
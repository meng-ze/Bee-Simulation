import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

 public class Beehive<T> {
    public T species;
    public ArrayList<Hive> hives;
    public Hive stillBuildingHive;
    public Bee queen;

    public int posX;
    public int posY;

    public Beehive(T species) {
        this.species = species;
        this.hives = new ArrayList<Hive>();
        this.spawnQueen();
    }
    public Beehive(int posX, int posY, T species) {
        this.posX = posX;
        this.posY = posY;
        this.species = species;
        this.hives = new ArrayList<Hive>();
        this.spawnQueen();
    }

    public void spawnQueen() {
        this.queen = new Bee(this, RoleEnum.QUEEN);
        this.queen.posX = this.posX;
        this.queen.posY = this.posY;
    }

    public void spawnHive(int remainingTime) {
        Hive newHive = new Hive(this);
        if (remainingTime != 0) {
            this.stillBuildingHive = newHive;
            newHive.remainingBuildingTime = remainingTime;    
        }
        this.hives.add(newHive);
    }

    public ArrayList<Bee> getAllBees() {
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
    public int getMaxResidentNumber() {
        int tmpSum = 0;
        for (Hive hive: this.hives) {
            tmpSum += hive.getMaxResidentLimit();
        }
        return tmpSum;
    }

    public Beehive_Status getStatus() {
        Beehive_Status status = new Beehive_Status(this);
        return status;
    }

    public void removeBee(Bee bee) {

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


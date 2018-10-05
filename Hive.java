import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hive implements Space {
    public Map<RoleEnum, ArrayList<Bee>> bees;
    private Beehive beehive;
    private int maxResidentNumber;
    public int remainingBuildingTime;

    @Override
    public void setMaxResidentLimit(int value) {
        
    }

    @Override
    public int getMaxResidentLimit() {
        return 0;
    }

    @Override
    public Space previousConnectedSpace() {
        return null;
    }

    @Override
    public Space nextConnectedSpace() {
        return null;
    }

    public void spawnBee(RoleEnum role) {
        this.bees.get(role).add(new Bee(this.beehive, role));
    }

    public Hive(Beehive owner, int maxResidentNumber) {
        this.beehive = owner;
        this.maxResidentNumber = maxResidentNumber;
        this.bees = new HashMap<RoleEnum, ArrayList<Bee>>();
        this.bees.put(RoleEnum.WORKER, new ArrayList<Bee>());
        this.bees.put(RoleEnum.WARRIOR, new ArrayList<Bee>());
        this.bees.put(RoleEnum.KILLER, new ArrayList<Bee>());
        this.randomSpawnBee(10);
    }

    public Hive(Beehive owner) {
        this.beehive = owner;
        this.maxResidentNumber = 20;
        this.bees = new HashMap<RoleEnum, ArrayList<Bee>>();
        this.bees.put(RoleEnum.WORKER, new ArrayList<Bee>());
        this.bees.put(RoleEnum.WARRIOR, new ArrayList<Bee>());
        this.bees.put(RoleEnum.KILLER, new ArrayList<Bee>());
        this.randomSpawnBee(10);
    }

    public ArrayList<Bee> getAllBees() {
        ArrayList<Bee> bees = new ArrayList<Bee>();
        for (RoleEnum key: this.bees.keySet()) {
            ArrayList<Bee> tmpBeeList = this.bees.get(key);
            for (Bee bee: tmpBeeList) {
                bees.add(bee);
            }
        }
        return bees;
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
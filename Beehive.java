import java.util.List;
import java.util.ArrayList;
import java.util.Map;

 public class Beehive <T> {
    private T species;
    private List<Hive> hives;

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

    public T getSpecies() {
        return this.species;
    }
}

class Hive {
    private Map<RoleEnum, List<Bee>> bees;
    public void spawnBee(Beehive ownerBeehive, Role role) {
        this.bees.get(role.getRole()).add(new Bee(ownerBeehive, role));
    }

    public Hive() {
        this.bees.put(RoleEnum.WORKER, new ArrayList<Bee>());
        this.bees.put(RoleEnum.WARRIOR, new ArrayList<Bee>());
        this.bees.put(RoleEnum.KILLER, new ArrayList<Bee>());
    }
    
    public void summaryHive() {
        for (RoleEnum iter: this.bees.keySet()) {
            System.out.printf("Role %d: member %d", iter, this.bees.get(iter).size());;
        }
    }
}

class Bee {
    Object species;
    public Bee(Beehive beehive, Role role) {
        this.species = beehive.getSpecies();
    }
}
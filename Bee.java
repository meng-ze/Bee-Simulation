import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.Math;

/*
 * Decorator Pattern Part:
 * 
 * Bee class is a descendent from Species class, and it holds the basic property of Bee, and perform the basis of decorator pattern.
 * For the later created class, such as Bee_Honey, Bee_Andrena, Bee_Hornet, are all programed in a decorator way.
 * Those constructor contains nothing but append 'Bonus' type attribute to a Bee object so that it would be easier
 * to inherit 'attribute' after a queen is killed.
 * 
 */
public class Bee implements Species {
    public int energyLeft;
    public Map<Bonus, Integer> attributesList;
    public Bee enemy;
    public int defaultRestingTime;
    public ArrayList<Beehive> enemiesBeehives;

    public int posX = 0;
    public int posY = 0;

    public RoleEnum role;
    public Beehive home;
    public Hive originHive;
    private Object species;
    private int defaultForce = 0;

    public Status status;
    public int statusValue;

    public Bee() {
        this.status = Status.RESTING;
        this.defaultRestingTime = 50;
        this.attributesList = new HashMap<Bonus, Integer>();
    }

    /*
    * Factory Pattern Part:
    * 
    * For each Bee, regardless of species, has to be assigned a 'role' in their group,
    * The rold include 'Queen', 'Killer', 'Warrior', and 'Worker'
    * Because this requirement must be hold cross any subclass of Bee, it is proper for me to program 'Role' property in this class
    * When a Queen is spawing a Bee, she must specify a role of that Bee, For example
    * Bee newBee = new Bee(this.home, role)
    * where this.home contains the templated-version of Beehive and the property of this type,
    * 'role' parameter specify the role of this created bee
    * 
    */
    public Bee(Beehive beehive, RoleEnum role) {
        this.species = beehive.species;
        this.home = beehive;
        this.status = Status.RESTING;
        this.attributesList = new HashMap<Bonus, Integer>();
        this.role = role;

        switch (this.role) {
            case WORKER:
                this.defaultRestingTime = 15; 
                break;
            case WARRIOR:
                this.defaultRestingTime = 10;
                this.defaultForce = 20;
                break;
            case KILLER:
                this.defaultRestingTime = 20;
                this.defaultForce = 10;
                break;
            case QUEEN:
                this.defaultRestingTime = 0;
                break;
        }
    }

    public ArrayList<Beehive> getEnemiesBeehives() {
        Apiary apiary = Apiary.getInstance();
        ArrayList<Beehive> otherBeehives = new ArrayList<Beehive>();
        for (int i=0; i<apiary.beehives.size(); ++i) {
            if (apiary.beehives.get(i) != this.home) {
                otherBeehives.add(apiary.beehives.get(i));
            }
        }
        return otherBeehives;
    }

    public Bee(Beehive beehive, RoleEnum role, int restingTime) {
        this.species = beehive.species;
        this.home = beehive;
        this.role = role;
        this.status = Status.RESTING;
        this.statusValue = restingTime;
        this.defaultRestingTime = restingTime;
    }

    public void fightWith(Bee anotherBee) {
        if (this.status != Status.FIGHTING) {
            this.status = Status.FIGHTING;
            anotherBee.status = Status.FIGHTING;
            this.statusValue = 1;
            this.enemy = anotherBee;
            anotherBee.enemy = this;
        } else {
            int thisExtraForce = this.attributesList.containsKey(Bonus.EXTRA_FORCE)? this.getAttribute(Bonus.EXTRA_FORCE): 0;
            int anotherExtraForce = anotherBee.attributesList.containsKey(Bonus.EXTRA_FORCE)? anotherBee.getAttribute(Bonus.EXTRA_FORCE): 0;
            int thisForce = this.defaultForce + thisExtraForce;
            int anotherForce = anotherBee.defaultForce + anotherExtraForce;
            Random random = new Random();
            if (random.nextInt(thisForce+anotherForce+1) > thisForce) {
                anotherBee.status = Status.FLYING;
                this.killed();
            } else {
                this.status = Status.FLYING;
                anotherBee.killed();
            }
        }
    }

    public void spawnNewBees(int number) {
        if (this.role == RoleEnum.QUEEN) {
            for (int i=0; i<number; i++) {
                Bee newBee = this.spawnNewBee();
                this.allocateHiveForBee(newBee);
                newBee.posX = this.posX;
                newBee.posY = this.posY;
            }
        }
    }

    public Bee spawnNewBee() {
        if (this.role == RoleEnum.QUEEN) {
            Random random = new Random();
            RoleEnum role = RoleEnum.values()[random.nextInt(3)];
            Bee newBee = new Bee(this.home, role);
            return newBee;
        }
        return null;
    }

    private void allocateHiveForBee(Bee newBee) {
        for (int i=0; i<this.home.hives.size(); i++) {
            Hive hive = (Hive)this.home.hives.get(i);
            if (hive.getMaxResidentLimit() > hive.getBeesCount()) {
                hive.bees.get(newBee.role).add(newBee);
                newBee.originHive = hive;
                return;
            }
        }
    }

    public void flyToBeehive(Beehive beehive) {
        int speedup = 1;
        if (this.attributesList.containsKey(Bonus.FASTER_FLYING)) {
            speedup = this.getAttribute(Bonus.FASTER_FLYING);
        }
        int deltaX;
        int deltaY;
        do {
            deltaX = beehive.posX - this.posX;
            deltaY = beehive.posY - this.posY;
            if (deltaX != 0) {
                this.posX += deltaX/Math.abs(deltaX);
            }
            if (deltaY != 0) {
                this.posY += deltaY/Math.abs(deltaY);
            }

            if (deltaX == 0 && deltaY == 0) {
                this.status = Status.RESTING;
                this.statusValue = this.defaultRestingTime;
                break;
            }
            speedup -= 1;
        } while (speedup > 0);
    }

    public void buildHive() {
        if (this.role == RoleEnum.WORKER) {
            if (home.stillBuildingHive == null) {
                home.spawnHive(20);
            } else {
                home.stillBuildingHive.remainingBuildingTime -= 1;
            }
        }
    }

    public void harvest() {
        if (this.role == RoleEnum.WORKER) {
            int totalBees = this.home.getBeesCount();
            Random random = new Random();
            for (int i=0; i<5; ++i) {
                Bee tmp = (Bee)this.home.getAllBees().get(random.nextInt(totalBees));
                tmp.energyLeft += 1;
            }
        }
    }

    public int getAttribute(Bonus bonus) {
        return this.attributesList.get(bonus);
    }

    public void setAttribute(Bonus bonus, Integer value) {
        this.attributesList.put(bonus, value);
        switch (bonus) {
            case DODGE:
                break;
            case FASTER_FLYING:
                break;
            case EXTRA_FORCE:
                break;
            case LONG_TIME_WORKING:
                this.energyLeft = value;
                break;
            case LESS_RECOVERY_TIME:
                this.defaultRestingTime = value;
                break;
        }
    }

    public void rest() {
        if (this.status != Status.RESTING) {
            this.status = Status.RESTING;
            this.statusValue = this.defaultRestingTime;
        }
        this.statusValue -= 1;
    }

    public void killed() {
        if (this.role == RoleEnum.QUEEN) {
            this.home.queen = this.enemy.home.queen;
        } else {
            for (int i=0; i<this.originHive.bees.get(this.role).size(); ++i) {
                ArrayList<Bee> bees = this.originHive.bees.get(this.role);
                if (bees.get(i) == this) {
                    bees.remove(i);
                    return;
                }
            }
        }
    }

    public void summary() {
        System.out.printf("Bee[%s]-> Energy[%d], Enemy[%s], Position(%d, %d), Role[%s], Home[%s], Species[%s]\n", this, this.energyLeft, this.enemy, this.posX, this.posY, this.role, this.home, this.species);
        System.out.printf("- - - Status[%s, %d]\n", this.status, this.statusValue);
    }
}
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.lang.Math;

public class Bee implements Species {
    public int energyLeft;
    public int healthLeft;
    public Map<Bonus, Integer> attributesList;
    public Bee enemy;
    public int defaultRestingTime;

    public int posX = 0;
    public int posY = 0;

    public RoleEnum role;
    public Beehive home;
    private Object species;
    private int defaultForce = 0;

    public Status status;
    public int statusValue;

    public Bee() {
        this.status = Status.RESTING;
        this.defaultRestingTime = 50;
        this.attributesList = new HashMap<Bonus, Integer>();
    }

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

    public Bee(Beehive beehive, RoleEnum role, int restingTime) {
        this.species = beehive.species;
        this.home = beehive;
        this.role = role;
        this.status = Status.RESTING;
        this.statusValue = restingTime;
        this.defaultRestingTime = restingTime;
    }

    public void fightWith(Bee anotherBee) {
        if (this.role == RoleEnum.KILLER || this.role == RoleEnum.WARRIOR) {
            if (this.status != Status.FIGHTING) {
                this.status = Status.FIGHTING;
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
                    this.home.removeBee(this);
                }
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

    public void allocateHiveForBee(Bee newBee) {
        for (int i=0; i<this.home.hives.size(); i++) {
            Hive hive = (Hive)this.home.hives.get(i);
            if (hive.getMaxResidentLimit() > hive.getBeesCount()) {
                hive.bees.get(role).add(newBee);
                break;
            }
        }
    }

    public void flyingHome() {
        int speedup = 1;
        if (this.attributesList.containsKey(Bonus.FASTER_FLYING)) {
            speedup = this.getAttribute(Bonus.FASTER_FLYING);
        }
        int deltaX;
        int deltaY;
        do {
            deltaX = this.home.posX - this.posX;
            deltaY = this.home.posY - this.posY;
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
    }

    public void rest() {
        if (this.status != Status.RESTING) {
            this.status = Status.RESTING;
            this.statusValue = this.defaultRestingTime;
        }
        this.statusValue -= 1;
    }


}
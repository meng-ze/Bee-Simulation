import java.util.Map;

public class Bee implements Species {
    protected int energyLeft;
    protected int healthLeft;
    protected Map<Bonus, Integer> attributesList;
    protected Bee enemy;
    protected int defaultRestingTime;
    private RoleEnum role;
    private Beehive home;
    private Object species;

    protected Status status;
    protected Integer statusValue;

    public Bee() {
        this.status = Status.RESTING;
        this.defaultRestingTime = 50;
    }

    public Bee(Beehive beehive, RoleEnum role) {
        this.species = beehive.getSpecies();
        this.home = beehive;
        this.status = Status.RESTING;
        this.role = role;

        switch (this.role) {
            case WORKER:
                this.defaultRestingTime = 15; 
                break;
            case WARRIOR:
                this.defaultRestingTime = 10;
                break;
            case KILLER:
                this.defaultRestingTime = 20;
                break;
            case QUEEN:
                this.defaultRestingTime = 0;
                break;
        }
    }

    public Bee(Beehive beehive, RoleEnum role, int restingTime) {
        this.species = beehive.getSpecies();
        this.home = beehive;
        this.role = role;
        this.status = Status.RESTING;
        this.statusValue = restingTime;
        this.defaultRestingTime = restingTime;
    }

    public void fightWith(Bee anotherBee) {
        this.status = Status.FIGHTING;
        this.statusValue = 1;
        this.setEnemy(anotherBee);
        anotherBee.setEnemy(this);
    }

    public void spawnNewBee() {
    }

    public int getAttribute(Bonus bonus) {
        return this.attributesList.get(bonus);
    }

    public void setAttribute(Bonus bonus, Integer value) {
        this.attributesList.put(bonus, value);
    }

    public void setEnemy(Bee enemy) {
        this.enemy = enemy;
    }

    public Bee getEnemy() {
        return this.enemy;
    }

    public void rest() {
        if (this.status != Status.RESTING) {
            this.status = Status.RESTING;
            this.statusValue = this.defaultRestingTime;
        }
        this.statusValue -= 1;
    }

    /*
     * Haven't implement location system yet
     */
    @Override
    public void performAction() {
        if (this.role == RoleEnum.QUEEN) {
            if (this.statusValue == 0) {
                this.spawnNewBee();
            }
        } else {
            if (energyLeft == 0) {
                this.status = Status.FLYING;
                this.statusValue = 0;
            }
            // add some location stuff
            
            switch (this.role) {
            case WORKER:
                break;
            case WARRIOR:
                break;
            case KILLER:
                break;
            case QUEEN:
                break;
            }
        } 
    }
}
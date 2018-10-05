public class Bee_Honey extends Bee {
    public Bee_Honey(Bee bee) {
        bee.setAttribute(Bonus.LONG_TIME_WORKING, 90);
    }
   
    public Bee_Honey(Beehive beehive, RoleEnum role) {
        super(beehive, role);
        this.setAttribute(Bonus.LONG_TIME_WORKING, 90);
    } 

    @Override
    public Bee spawnNewBee() {
        return new Bee_Honey(super.spawnNewBee());
    }
}
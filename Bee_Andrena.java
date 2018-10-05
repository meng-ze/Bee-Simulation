public class Bee_Andrena extends Bee {
    public Bee_Andrena(Bee bee) {
        bee.setAttribute(Bonus.LONG_TIME_WORKING, 90);
        bee.setAttribute(Bonus.FASTER_FLYING, 4);
    }
   
    public Bee_Andrena(Beehive beehive, RoleEnum role) {
        super(beehive, role);
        this.setAttribute(Bonus.LONG_TIME_WORKING, 90);
        this.setAttribute(Bonus.FASTER_FLYING, 4);
    } 
}
public class Bee_Hornet extends Bee {
    public Bee_Hornet(Bee bee) {
        bee.setAttribute(Bonus.EXTRA_FORCE, 50);
    }

    public Bee_Hornet(Beehive beehive, RoleEnum role) {
        super(beehive, role);
        this.setAttribute(Bonus.EXTRA_FORCE, 50);
    }
}
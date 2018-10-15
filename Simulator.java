import java.util.*;

public class Simulator {
    public static void simulateOneTick() {
        List<Bee> allBees = Apiary.getInstance().getAllBees();
        for (Bee bee: allBees) {
            maintainBeeBehavior(bee);
            bee.summary();
        }
    }

    /*
     * Mediator Pattern part:
     * 
     * As the requirement state, I use a tick based simulation to perform this program.
     * This Simulator class will mediate the behavior among all bees.
     * In every tick, this program will check "EVERY" bee's behavior/status and check whether they conform the rule.
     * The rule checking part is written in the function: maintainBeeBehavior
     * After each checking, every bee will have a slightly different attribute than previous tick.
     * The tick function does not directly implement in Bee's class, rather, they are implemented in this class.
     */

    private static void maintainBeeBehavior(Bee bee) {
        /*
        * Haven't implement location system yet
        */
        if (bee == null) {
            return;
        }
        if (bee.role == RoleEnum.QUEEN) {
            if (bee.home.getMaxResidentNumber() > bee.home.getBeesCount()) {
                if (bee.statusValue == 0) {
                    bee.spawnNewBees(1);
                }
            }
        } else {
            if (bee.status == Status.RESTING && bee.statusValue > 0) {
                bee.statusValue -= 1;
                return;
            }
            if (bee.energyLeft == 0) {
                bee.status = Status.FLYING;
                bee.statusValue = 0;
            }

            if (bee.status == Status.FLYING && bee.statusValue == 0) {
                bee.flyToBeehive(bee.home);
                return;
            }

            switch (bee.role) {
            case WORKER:
                Random random = new Random();
                int switcher = random.nextInt(2);
                if (switcher % 2 == 0) {
                    bee.buildHive();
                } else {
                    bee.harvest();
                }
                break;
            case WARRIOR:
                
                break;
            case KILLER:
                break;

            default:
                break;
            }
        } 
    }
    public static void main(String [] argc) {
        Apiary apiary = Apiary.getInstance();
        apiary.addNewBeehiveForSpecies(new Bee_Honey(new Bee()), 0, 0);
        apiary.addNewBeehiveForSpecies(new Bee_Hornet(new Bee()), 20, 50);
        apiary.addNewBeehiveForSpecies(new Bee_Andrena(new Bee()), 100, 20);
        apiary.summary();

        Simulator.simulateOneTick();
    }
}
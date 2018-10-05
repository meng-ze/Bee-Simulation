import java.util.*;

public class Simulator {
    public static void simulateOneTick() {
        List<Bee> allBees = Apiary.getInstance().getAllBees();
        for (Bee bee: allBees) {
            maintainBeeBehavior(bee);
        }
    }

    private static void maintainBeeBehavior(Bee bee) {
        /*
        * Haven't implement location system yet
        */
        if (bee.role == RoleEnum.QUEEN) {
            if (bee.home.getMaxResidentNumber() > bee.home.getBeesCount()) {
                if (bee.statusValue == 0) {
                    Bee newBee = bee.spawnNewBee();
                    bee.allocateHiveForBee(newBee);
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
                bee.flyingHome();
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
            }
        } 
    }
    public static void main(String [] argc) {
        Apiary apiary = Apiary.getInstance();
        apiary.spawnBeehive(new Bee_Honey(new Bee()));
        apiary.spawnBeehive(new Bee_Hornet(new Bee()));
        apiary.spawnBeehive(new Bee_Andrena(new Bee()));
        apiary.summary();

        Simulator.simulateOneTick();
    }
}
import java.util.*;

public class Simulator {
    public static void main(String [] argc) {
        Apiary singleton = Apiary.getInstance();
        Species honey = new HoneyBee(new DummySpecies());
        singleton.spawnBeehive(honey);
        singleton.summary();
    }
}
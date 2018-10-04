public class DummySpecies implements Species {
    @Override
    public void addAttribute(Bonus bonus, Boolean value) {
        System.out.printf("Create dummy species!");
    }
}
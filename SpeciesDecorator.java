import java.util.*;

public abstract class SpeciesDecorator implements Species {
    protected Map<Bonus, Boolean> attributes;
    protected Species species;

    public SpeciesDecorator(Species species) {
        this.species = species;
    }

    public void addAttribute(Bonus bonus, Boolean value) {
        this.species.addAttribute(bonus, value);
    }

    public Map<Bonus, Boolean> getAttributes() {
        return attributes;
    }
}
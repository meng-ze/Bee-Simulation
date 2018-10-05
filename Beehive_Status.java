public class Beehive_Status {
    public int currentResident;
    public int maxResident;
    public Beehive_Status(Beehive beehive) {
        this.currentResident = beehive.getBeesCount();
        this.maxResident = beehive.getMaxResidentNumber();
    }
}
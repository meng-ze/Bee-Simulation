public interface Space {
    public int getMaxResidentLimit();
    public void setMaxResidentLimit(int value);

    public Space previousConnectedSpace();
    public Space nextConnectedSpace();
}
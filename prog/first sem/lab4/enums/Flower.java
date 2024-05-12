package enums;

public enum Flower {
    MARGARINES(0), ROSES(1), ORCHIDS(2), AAA(3);
    private int index;
    private Flower(int index){
        this.index = index;
    }
    public int getIndex(){
        return index;
    }

}

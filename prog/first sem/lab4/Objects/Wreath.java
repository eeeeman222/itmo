package Objects;

import enums.Flower;

public class Wreath {
    public static String name;
    @Override
    public String toString() {
        return name;
    }
    public Wreath(String name) {
        this.name = name;
    }
    int firstFl = 0;

    public void choose1(){
        int a = (int)(Math.random() * 3);
        firstFl = a;
        if(a == Flower.MARGARINES.getIndex()){
            name = name + " из маргаринок";
        }
        if(a == Flower.ORCHIDS.getIndex()){
            name = name + " из орхидей";
        }
        if(a == Flower.ROSES.getIndex()){
            name = name + " из роз";
        }
    }
    public void choose2(){
        int a = firstFl;
        while (a == firstFl) {
            a = (int)(Math.random() * 3);
        }
        if(a == Flower.MARGARINES.getIndex()){
            name = name + " и маргаринок";
        }
        if(a == Flower.ORCHIDS.getIndex()){
            name = name + " и орхидей";
        }
        if(a == Flower.ROSES.getIndex()){
            name = name + " и роз";
        }
    }
}
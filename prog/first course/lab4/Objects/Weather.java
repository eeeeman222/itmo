package Objects;

import enums.Infl;

public class Weather {
    static private String name;
    @Override
    public String toString() {
        return name;
    }
    public Weather(String name) {
        this.name = name;
    }
    public void influence(Infl i){
        if(i == Infl.HOT){
            System.out.println(name + " была жаркой.");
        }
    }

}
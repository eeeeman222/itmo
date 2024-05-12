package moves;
import ru.ifmo.se.pokemon.*;

public class Thunderbolt extends SpecialMove {
    public thunderbolt(double power, double acc){
        super(Type.ELECTRIC, power, acc);
    }
    @Override
    protected void applyOppEffects(Pokemon opp) {
        if(Math.random() < 0.1){
            Effect.paralyze(opp);
        }
    }
    @Override
    protected java.lang.String describe() {
        return "неожиданно атакует используя Thunderbolt!";
    }
}

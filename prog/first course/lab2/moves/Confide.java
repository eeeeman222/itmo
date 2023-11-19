package moves;

import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove {
    @Override
    protected String describe() {
        return "использует Confide и понижает Special Attack оппонента!";
    }
    public Confide(double power, double acc){
        super(Type.GHOST, power, acc);
    }
    protected void applyOppEffects(Pokemon opp) {
        Effect Confide_E = new Effect().chance(1).turns(1).stat(Stat.SPECIAL_ATTACK, +1);
        opp.addEffect(Confide_E);
    }
}

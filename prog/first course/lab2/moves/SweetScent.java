package moves;
import ru.ifmo.se.pokemon.*;
public class SweetScent extends StatusMove {
    public SweetScent(double power, double acc) {
        super(Type.NORMAL, power, acc);
    }
    @Override
    protected void applyOppEffects(Pokemon opp) {
        Effect SweetScentE = new Effect().chance(1).turns(1).stat(Stat.EVASION, -1);
        opp.addEffect(Sweet_ScentE);
    }
    @Override
    protected String describe() {
        return "применяет Sweet Scent и уменьшает уклонение.";
    }

}

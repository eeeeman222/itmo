package moves;
import ru.ifmo.se.pokemon.*;
public class Sweet_Scent extends StatusMove {
    public Sweet_Scent(double power, double acc) {
        super(Type.NORMAL, power, acc);
    }
    @Override
    protected void applyOppEffects(Pokemon opp) {
        Effect Sweet_Scent_E = new Effect().chance(1).turns(1).stat(Stat.EVASION, -1);
        opp.addEffect(Sweet_Scent_E);
    }
    @Override
    protected String describe() {
        return "применяет Sweet Scent и уменьшает уклонение.";
    }

}

package moves;
import ru.ifmo.se.pokemon.*;
public class Stun_Spore extends StatusMove {
    public Stun_Spore(double power, double acc) {
        super(Type.GRASS, power, acc);
    }
    @Override
    protected void applyOppEffects(Pokemon opp) {
        if(!opp.hasType(Type.ELECTRIC)) {
            Effect.paralyze(opp);
        }
    }
    @Override
    protected java.lang.String describe() {
        return "применяет Stun Spores и пытается парализовать оппонента!!!";
    }
}

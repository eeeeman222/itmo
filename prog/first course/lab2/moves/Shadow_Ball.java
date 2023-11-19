package moves;
import ru.ifmo.se.pokemon.*;
public class ShadowBall extends SpecialMove{
    public ShadowBall(double power, double acc){
        super(Type.GHOST, power, acc);
    }
    @Override
    protected String describe() {
        return "атакует с помощью ShadowBall!";
    }
    @Override
    protected void applyOppEffects(Pokemon opp) {
        Effect ShadowBall_E = new Effect().chance(0.2).turns(1).stat(Stat.DEFENSE, -1);
        opp.addEffect(ShadowBall_E);
    }



}

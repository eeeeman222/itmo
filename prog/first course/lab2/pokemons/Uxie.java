package pokemons;

import moves.Confide;
import moves.Psychic;
import moves.ShadowBall;
import moves.Thunderbolt;
import ru.ifmo.se.pokemon.*;

public class Uxie extends Pokemon {
    public Uxie(String name, int lvl){
        //pokemon_info
        super(name, lvl);
        super.setType(Type.PSYCHIC);
        super.setStats(75, 75, 130, 75, 130, 95);

        //pokemon_moves

        ShadowBall shadowball = new ShadowBall(80,100);
        Confide confide = new Confide(0, 100);
        Thunderbolt thunderbolt = new Thunderbolt(90, 100);
        Psychic psychic = new Psychic(90, 100);
        super.setMove(shadowball, confide, thunderbolt, psychic);
    }
}

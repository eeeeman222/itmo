package pokemons;
import moves.Thunderbolt;
import ru.ifmo.se.pokemon.*;
import moves.Confide;
import moves.SweetScent;
import moves.Doubleteam;

public class Surskit extends Pokemon {
    public surskit(String name, int lvl) {
        //pokemon_info
        super(name, lvl);
        super.setType(Type.BUG, Type.WATER);
        super.setStats(40, 30, 32, 50, 52, 65);

        //pokemon_moves

        Confide confide = new Confide(0,100);
        SweetScent sweetscent = new SweetScent(0, 100);
        Doubleteam doubleteam = new Doubleteam(0, 100);
        super.setMove(sweetscent, confide, doubleteam);
    }

}

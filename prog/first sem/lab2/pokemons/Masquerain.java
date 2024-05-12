package pokemons;
import ru.ifmo.se.pokemon.*;
import moves.*;
public class Masquerain extends Pokemon {
    public masquerain(String name, int lvl) {
        //pokemon_info
        super(name, lvl);
        super.setType(Type.BUG, Type.FLYING);
        super.setStats(100, 60, 100, 100, 82, 80);

        //pokemon_moves

        Confide confide = new Confide(0, 100);
        Doubleteam doubleteam = new Double_team(0, 100);
        SweetScent sweetscent = new Sweet_Scent(0, 100);
        StunSpore stunspore = new Stun_Spore(0, 75);
        super.setMove(stunspore, confide, doubleteam, sweetscent);
    }
}

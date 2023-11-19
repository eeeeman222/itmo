package pokemons;
import ru.ifmo.se.pokemon.*;
import moves.*;
public class Masquerain extends Pokemon {
    public Masquerain(String name, int lvl) {
        //pokemon_info
        super(name, lvl);
        super.setType(Type.BUG, Type.FLYING);
        super.setStats(100, 60, 100, 100, 82, 80);

        //pokemon_moves

        Confide confide = new Confide(0, 100);
        Double_team double_team = new Double_team(0, 100);
        Sweet_Scent sweet_scent = new Sweet_Scent(0, 100);
        Stun_Spore stun_spore = new Stun_Spore(0, 75);
        super.setMove(stun_spore);

    }
}

import ru.ifmo.se.pokemon.*;
import pokemons.*;
public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Uxie Uxie = new Uxie("", 1);
        Surskit Surskit = new Surskit("", 1);
        Masquerain Masquerain = new Masquerain("", 1);
        Pikachu Pikachu = new Pikachu("", 1);
        Pichu Pichu = new Pichu("", 1);
        Raichu Raichu = new Raichu("", 1);
        b.addAlly(Uxie);
        b.addAlly(Surskit);
        b.addAlly(Masquerain);
        b.addFoe(Pikachu);
        b.addFoe(Pichu);
        b.addFoe(Raichu);
        b.go();
    }
}

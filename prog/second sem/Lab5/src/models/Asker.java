package models;
import myExceptions.NullCoordinateException;
import utilities.*;

import java.io.IOException;
import java.util.NoSuchElementException;

public class Asker {

    public static class AskBreak extends Exception {
    }

    public static Route askRoute(Console console, long id) throws AskBreak {

        boolean scrM;

        scrM = MyConsole.getFileReader() != null;

        /*
        Поля основного класса:
        private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        private String name; //Поле не может быть null, Строка не может быть пустой
        private Coordinates coordinates; //Поле не может быть null
        private Location from; //Поле не может быть null
        private Place to; //Поле может быть null
        private int distance; //Значение поля должно быть больше 1
         */
        try {
            String name;
            int distance = 0;
            while (true) {
                if(!scrM) {
                    console.print("name: ");
                    name = console.readln().trim();
                    if (name.equals("exit")) throw new AskBreak();
                }
                else {
                    name = console.filereadln().trim();
                }
                if (name.equals("exit")) throw new AskBreak();
                if(!scrM) {
                    if (!name.isEmpty()) break;
                    else console.println("это значение не может быть null!(предлагаю ввести значение снова.");
                }
                else{
                    break;
                }

            }
            var coordinates = askCoordinates(console);
            var to = askPlace(console);
            var from = askLocation(console);
            while (true) {
                String line;
                if(!scrM) {
                    console.print("distance: ");
                    line = console.readln().trim();
                }
                else {
                    line = console.filereadln().trim();
                }
                if (line.equals("exit")) throw new AskBreak();
                if(!scrM) {
                    try {
                        distance = Integer.parseInt(line);
                        if (!scrM) {
                            if (distance > 1) break;
                            else {
                                console.println("distanse должна быть больше 1!");
                            }
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        console.println("вводимое вами значение должно быть числом!");
                    }
                }
                else{
                    try{
                        distance = Integer.parseInt(line);
                    }
                    catch (NumberFormatException e) {
                        return null;
                    }
                    break;
                }
            }
            if(!scrM) {
                return new Route(id, name, coordinates, from, to, distance);
            }
            else{
                Route a =  new Route(id, name, coordinates, from, to, distance);
                if(a.validate()){
                    return new Route(id, name, coordinates, from, to, distance);
                }
            }
        } catch (NoSuchElementException | IllegalStateException | IOException e) {
            console.printError("Ошибка чтения");
            return null;
        } catch (NullCoordinateException e) {
            return null;
        }
        return null;
    }

    public static Coordinates askCoordinates(Console console) throws AskBreak {

        boolean scrM;

        scrM = MyConsole.getFileReader() != null;


        try {
            int x;
            while (true) {
                String line;
                if(!scrM) {
                    console.print("coordinates.x: ");
                    line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.isEmpty()) {
                        try {
                            x = Integer.parseInt(line);
                            break;
                        } catch (NumberFormatException e) {
                            console.println("прошу ввести число");
                        }
                    }
                    else {
                        console.println("эта координата не может быть null!(предлагаю ввести значение снова.)");
                    }
                }
                else {
                    line = console.filereadln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    try {
                        x = Integer.parseInt(line);
                    }
                    catch (NumberFormatException e){
                        return null;
                    }
                    break;
                }
            }
            Float y;
            while (true) {
                if(!scrM) {
                    console.print("coordinates.y: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.isEmpty()) {
                        try {
                            y = Float.parseFloat(line);
                            if (y < 56) {
                                break;
                            } else {
                                console.println("поле не может содержать значение более 56");
                            }
                        } catch (NumberFormatException e) {
                            console.println("прошу ввести число");
                        }
                    } else {
                        console.println("это значение не может быть null!(предлагаю ввести значение снова.");
                    }
                }
                else {
                    var line = console.filereadln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    try {
                        y = Float.parseFloat(line);
                    }
                    catch (NumberFormatException e){
                        return null;
                    }
                    break;
                }
            }
            int a = 1;
            String s = String.valueOf(a);
            return new Coordinates((long) x, y);
        } catch (NoSuchElementException | IllegalStateException | IOException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Location askLocation(Console console) throws AskBreak, IOException {
        boolean scrM;

        scrM = MyConsole.getFileReader() != null;

        try {
            String name;
            while (true) {
                if(!scrM) {
                    console.print("location_name: ");
                    name = console.readln().trim();
                    if (name.equals("exit")) throw new AskBreak();
                    if (!name.isEmpty()) break;
                    else console.println("это значение не может быть null!(предлагаю ввести значение снова.");
                }
                else{
                    name = console.filereadln().trim();
                    if (name.equals("exit")) throw new AskBreak();
                    break;
                }
            }
            Integer x;
            while (true) {
                if(!scrM) {
                    console.print("location.x: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.isEmpty()) {
                        try {
                            x = Integer.parseInt(line);
                            break;
                        } catch (NumberFormatException e) {
                            console.println("прошу ввести число");
                        }
                    } else {
                        console.println("эта координата не может быть null!(предлагаю ввести значение снова)");
                    }
                }
                else{
                    var line = console.filereadln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    try {
                        x = Integer.parseInt(line);
                    }
                    catch (NumberFormatException e) {
                        return null;
                    }
                    break;
                }
            }
            float y;
            while (true) {
                if(!scrM) {
                    console.print("location.y: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.isEmpty()) {
                        try {
                            y = Integer.parseInt(line);
                            break;
                        } catch (NumberFormatException e) {
                            console.println("прошу ввести число");
                        }
                    } else {
                        return new Location(x, name);
                    }
                }
                else {
                    var line = console.filereadln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.isEmpty()){
                        try {
                            y = Integer.parseInt(line);
                        }
                        catch (NumberFormatException e) {
                            return null;
                        }
                    }
                    else{
                        return new Location(x, name);
                    }
                    break;
                }
            }
            return new Location(x, y, name);
        } catch (NoSuchElementException | IllegalStateException | IOException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Place askPlace(Console console) throws AskBreak, IOException, NullCoordinateException {
        boolean scrM;
        scrM = MyConsole.getFileReader() != null;
        if(!scrM) {
            console.print("данное поле может быть null, укажите здесь значение null, если хотите сделать его таковым, иначе введите любое значение --- ");
            String ln = console.readln().trim();
            if(ln.isEmpty()){
                return null;
            }
        }
        else {
            String ln = console.filereadln().trim();
            if (ln.isEmpty()) {
                return null;
            }
        }
        try {
            String name;
            while (true) {
                if(!scrM) {
                    console.print("place_name: ");
                    name = console.readln().trim();
                    if (name.equals("exit")) throw new AskBreak();
                    if (name.length() > 705){
                        console.println("длина поля не может быть более 705");
                    }
                    if (!name.isEmpty()) break;
                    else{
                        console.println("эта координата не может быть null!(предлагаю ввести значение снова)");
                    }
                }
                else{
                    name = console.filereadln().trim();
                    if (name.equals("exit")) throw new AskBreak();
                    break;
                }
            }
            long x;
            boolean nl = false;
            while (true) {
                if(!scrM) {
                    console.print("place.x: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if(line.isEmpty()){
                        nl = true;
                        x = 0;
                        break;
                    }
                    else {
                        try {
                            x = Integer.parseInt(line);
                            break;
                        } catch (NumberFormatException e) {
                            console.println("прошу ввести число");
                        }
                    }
                }
                else{
                    var line = console.filereadln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if(line.isEmpty()){
                        nl = true;
                        x = 0;
                        break;
                    }
                    else {
                        try {
                            x = Integer.parseInt(line);
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    }
                    break;
                }
            }

            Integer y;
            while (true) {
                if(!scrM) {
                    console.print("place.y: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.isEmpty()) {
                        try {
                            y = Integer.parseInt(line);
                            break;
                        } catch (NumberFormatException e) {
                            console.println("прошу ввести число");
                        }
                    }
                    else {
                        console.println("эта координата не может быть null!(предлагаю ввести значение снова.)");
                    }
                }
                else{
                    var line = console.filereadln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    try {
                        y = Integer.parseInt(line);
                    }
                    catch (NumberFormatException e){
                        return null;
                    }
                    break;
                }
            }
            if(nl){
                return new Place(y, name);
            }
            return new Place(x, y, name);
        } catch (NoSuchElementException | IllegalStateException | IOException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
}
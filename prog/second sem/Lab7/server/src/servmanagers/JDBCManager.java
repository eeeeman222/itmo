package servmanagers;

import models.*;
import net.*;

import java.security.MessageDigest;
import java.sql.*;
import java.time.LocalDateTime;

public class JDBCManager {

    public static void deleteRouteById(long id) {
        String select = "DELETE FROM Route WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(Conf.url, Conf.creator, Conf.pass);
             PreparedStatement statement = connection.prepareStatement(select)) {

            statement.setLong(1, id);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addRoute(Route route) {
        String select = "INSERT INTO Route (id, name, coordinates_x, coordinates_y, creationDate, from_x, from_y, from_name, to_x, to_y, to_name, distance, creatorId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(Conf.url, Conf.creator, Conf.pass);
             PreparedStatement statement = connection.prepareStatement(select)) {

            statement.setLong(1, route.getId());
            statement.setString(2, route.getName());
            statement.setLong(3, route.getCoordinates().getX());
            statement.setFloat(4, route.getCoordinates().getY());
            statement.setTimestamp(5, java.sql.Timestamp.valueOf(route.getCreationDate()));
            statement.setInt(6, route.getFrom().getX());
            statement.setDouble(7, route.getFrom().getY());
            statement.setString(8, route.getFrom().getName());
            if(route.getTo() == null) {
                statement.setLong(9, -1);
                statement.setInt(10, -1);
                statement.setString(11, null);
            }
            else{
                statement.setLong(9, route.getTo().getX());
                statement.setInt(10, route.getTo().getY());
                statement.setString(11, route.getTo().getName());
            }
            statement.setInt(12, route.getDistance());
            statement.setLong(13, route.getCreatorId());

            statement.executeUpdate();

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // SQLState for unique constraint violation
                UDPDatagramServer.logger.info("Ошибка: Маршрут с таким идентификатором уже существует.");
            } else {
                e.printStackTrace();
            }
        }
    }


    public static void updateRouteById(Route route) {
        String select = "UPDATE Route SET id = ?, name = ?, coordinates_x = ?, coordinates_y = ?, creationDate = ?, from_x = ?, from_y = ?, from_name = ?, to_x = ?, to_y = ?, to_name = ?, distance = ?, creatorId = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(Conf.url, Conf.creator, Conf.pass);
             PreparedStatement statement = connection.prepareStatement(select)) {

            statement.setLong(1, route.getId());
            statement.setString(2, route.getName());
            statement.setLong(3, route.getCoordinates().getX());
            statement.setFloat(4, route.getCoordinates().getY());
            statement.setTimestamp(5, java.sql.Timestamp.valueOf(route.getCreationDate()));
            statement.setInt(6, route.getFrom().getX());
            statement.setDouble(7, route.getFrom().getY());
            statement.setString(8, route.getFrom().getName());
            if(route.getTo() == null) {
                statement.setLong(9, -1);
                statement.setInt(10, -1);
                statement.setString(11, null);
            }
            else{
                statement.setLong(9, route.getTo().getX());
                statement.setInt(10, route.getTo().getY());
                statement.setString(11, route.getTo().getName());
            }
            statement.setInt(12, route.getDistance());
            statement.setLong(13, route.getCreatorId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAllRoutes(CollectionManager collectionManager) {
        String select = "SELECT * FROM Route";


        try (Connection connection = DriverManager.getConnection(Conf.url, Conf.creator, Conf.pass);
             PreparedStatement statement = connection.prepareStatement(select);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long creatorId = resultSet.getLong("creatorId");
                String name = resultSet.getString("name");
                long coordinates_x = resultSet.getLong("coordinates_x");
                double coordinates_y = resultSet.getDouble("coordinates_y");
                LocalDateTime creationDate = resultSet.getTimestamp("creationDate").toLocalDateTime();
                int from_x = resultSet.getInt("from_x");
                double from_y = resultSet.getDouble("from_y");
                String from_name = resultSet.getString("from_name");
                long to_x = resultSet.getLong("to_x");
                int to_y = resultSet.getInt("to_y");
                String to_name = resultSet.getString("to_name");
                int distance = resultSet.getInt("distance");

                Coordinates coordinates = new Coordinates(coordinates_x, (float) coordinates_y);
                Location from = new Location(from_x, from_y, from_name);
                Place to = new Place(to_x, to_y, to_name);

                Route route = new Route(id, name, coordinates, creationDate, from, to, distance, creatorId);
                collectionManager.insert(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAllUsers(CollectionManager collectionManager) {
        String select = "SELECT * FROM users";


        try (Connection connection = DriverManager.getConnection(Conf.url, Conf.creator, Conf.pass);
             PreparedStatement statement = connection.prepareStatement(select);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String psw = resultSet.getString("password");

                User user = new User(id, name, psw, null);
                collectionManager.getUsers().add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void addUser(int id, String name, String password, String salt) {
        String query = "INSERT INTO users (id, name, password, salt) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(Conf.url, Conf.creator, Conf.pass);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            String psw = hashPassword(password, salt);
            statement.setString(3, psw);

            statement.setString(4, salt);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int authenticateUser(String name, String password) throws SQLException {

        String selectUserSQL = "SELECT id, salt, password FROM users WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(Conf.url, Conf.creator, Conf.pass);
             PreparedStatement statement = connection.prepareStatement(selectUserSQL)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String salt = resultSet.getString("salt");
                    String expectedHashedPassword = resultSet.getString("password");
                    String actualHashedPassword = hashPassword(password, salt);
                    if (expectedHashedPassword.equals(actualHashedPassword)) {
                        return id;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        }
    }

    public static String hashPassword(String password, String salt) {
        password += salt;
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(password.getBytes());
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        } catch (Exception e) {
            System.out.println("проблема с паролем");
        }
        return hexString.toString();
    }
}
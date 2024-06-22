package commands;

import requests.Request;
import responses.Response;

import java.io.IOException;
import java.sql.SQLException;

/**
 * интерфейс для команд
 */
public interface Executable {
    Response apply(Request request) throws IOException, SQLException;
}

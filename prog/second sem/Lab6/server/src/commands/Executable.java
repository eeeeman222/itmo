package commands;

import requests.Request;
import responses.Response;

import java.io.IOException;

/**
 * интерфейс для команд
 */
public interface Executable {
    Response apply(Request request) throws IOException;
}

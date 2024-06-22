package commands;

import models.User;
import requests.AuthenticateRequest;
import requests.Request;
import responses.AuthenticateResponse;
import responses.HelpResponse;
import responses.Response;
import servmanagers.CollectionManager;
import servmanagers.CommandManager;
import servmanagers.JDBCManager;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class Authorizate extends Command {

    CollectionManager collectionManager;

    public Authorizate(CollectionManager collectionManager) {
        super("auth", "вход в аккаунт");
        this.collectionManager = collectionManager;
    }
    public Response apply(Request request) throws SQLException {
        var req = (AuthenticateRequest)request;
        int a = JDBCManager.authenticateUser(request.getUser().getName(), request.getUser().getPassword());
        if(a == 0){
            return new AuthenticateResponse(null, "auth error!");
        }
        try {
            req.getUser().setId(a);
            return new AuthenticateResponse(req.getUser(), null);
        }
        catch (Exception e){
            return new AuthenticateResponse(null, e.toString());
        }
    }

}

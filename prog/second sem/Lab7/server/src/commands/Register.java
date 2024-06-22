package commands;

import models.User;
import requests.AuthenticateRequest;
import requests.RegisterRequest;
import requests.Request;
import responses.AuthenticateResponse;
import responses.HelpResponse;
import responses.RegisterResponse;
import responses.Response;
import servmanagers.CollectionManager;
import servmanagers.CommandManager;

import java.util.stream.Collectors;

public class Register extends Command {

    CollectionManager collectionManager;

    public Register(CollectionManager collectionManager) {
        super("register", "регистрация пользователя");
        this.collectionManager = collectionManager;
    }
    public Response apply(Request request) {
        var req = (RegisterRequest)request;
        int a = collectionManager.regInManager(req.getUser().getName(), req.getUser().getPassword());
        if(a != -1) {
            req.getUser().setId(a);
            return new RegisterResponse(req.getUser(), null);
        }
        else {
            return new RegisterResponse(req.getUser(), "имя занято");
        }
    }

}


package com.github.chiangj8L.WebServer.CommandHandler;

import com.github.chiangj8L.WebServer.CommandPayloadType.CommandPayloadType;

import java.util.HashMap;

public class CommandHandler {

    private ICommandHandlerLambda lambdaFunction;

    public CommandHandler(ICommandHandlerLambda lambdaFunc) {
        this.lambdaFunction = lambdaFunc;
    }

    public void execute(HashMap<CommandPayloadType, Object> payload) {
        this.lambdaFunction.operation(payload);
    }
}


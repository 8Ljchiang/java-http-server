package com.github.chiangj8L.WebServer.CommandHandler;

import com.github.chiangj8L.WebServer.CommandPayloadType.CommandPayloadType;

import java.util.HashMap;

@FunctionalInterface
public interface ICommandHandlerLambda {
    void operation(HashMap<CommandPayloadType, Object> payload);
}
package com.github.chiangj8L.WebServer.SocketConnection;

import java.io.IOException;

public interface ISocketConnection {
    String readInputData() throws IOException;
    void sendOutputData(String text) throws IOException;
    void closeSocketConnection() throws IOException;
}
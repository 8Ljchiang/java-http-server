package SocketConnection;

import java.io.IOException;

public interface ISocketConnection {
    public String readInputData() throws IOException;
    public void sendOutputData(String text) throws IOException;
    public void closeSocketConnection() throws IOException;
}

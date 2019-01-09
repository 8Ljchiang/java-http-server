package com.github.chiangj8L.WebServer.SocketConnection;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class SocketConnection {
    private Socket socket;
    private Charset charset;

    public SocketConnection(Socket socket, Charset charset) {
        this.socket = socket;
        this.charset = charset;
    }

    public String readFromInputStream() throws IOException {
        InputStream is = socket.getInputStream();
        String result = convertInputStreamToString(is);
        return result;
    }

    public void writeToOutputStream(String text) throws IOException {
        if (text != null && text.length() > 0) {
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.print(text);
            pw.close();
        }
    }

    public void closeSocketConnection() throws IOException {
        socket.close();
    }

    private String convertInputStreamToString(InputStream is) throws IOException{
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(is, charset);
        int readByteSize = in.read(buffer, 0, buffer.length);
        while (readByteSize > 0) {
            out.append(buffer, 0, readByteSize);
            if (readByteSize < bufferSize) {
                break;
            } else {
                readByteSize = in.read(buffer, 0, buffer.length);
            }
        }
        return out.toString();
    }
}


package com.github.chiangj8L.WebServer.HttpStatus;

public enum HttpStatus {
    OK("200", "Ok"),
    MOVED_PERMANENTLY("301", "Moved Permanently"),
    BAD_REQUEST("400", "Bad Request"),
    NOT_FOUND("404", "Not Found"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error");

    private String statusCode;
    private String statusDescription;

    HttpStatus(String codeDescription, String statusDescription) {
        this.statusCode = codeDescription;
        this.statusDescription = statusDescription;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public String getStatusDescription() {
        return this.statusDescription;
    }
}

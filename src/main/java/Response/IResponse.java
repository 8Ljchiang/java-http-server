package Response;

public interface IResponse {
    public void status(String status);
    public void addHeader(String key, String value);
    public void contentType(String type);
    public void send(String body);
}

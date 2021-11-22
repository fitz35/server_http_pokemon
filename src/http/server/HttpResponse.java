package http.server;

import java.util.HashMap;

public class HttpResponse {
    private String httpVersion;
    private int statusCode;
    private String textStatusCode;
    private String body;
    private static HashMap<Integer,String> statusCodeMap;

    public void createStatusCodeMap()
    {
        statusCodeMap= new HashMap<>();
        statusCodeMap.put(200 , "OK");
        statusCodeMap.put(400 , "Bad Request");
        statusCodeMap.put(401 , "Unauthorized");
        statusCodeMap.put(403 , "Forbidden");
        statusCodeMap.put(404 , "Not Found");
        statusCodeMap.put(500 , "Internal Server Error");
        statusCodeMap.put(502 , "Bad Gateway");
        statusCodeMap.put(503 , "Service Unavailable");
        statusCodeMap.put(504 , "Gateway Timeout");

    }

    public static HashMap<Integer, String> getStatusCodeMap() {
        return statusCodeMap;
    }

    public HttpResponse(String httpVersion, int statusCode, String body) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.textStatusCode = statusCodeMap.get(this.statusCode);
        this.body = body;
    }
}

package http.server;

import java.util.HashMap;

public class HttpResponse {
    private String httpVersion;
    private int statusCode;
    private String textStatusCode;
    private HashMap<String, String> header;
    private String body;
    public static HashMap<Integer,String> statusCodeMap;

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

    public HttpResponse(String httpVersion, int statusCode, String textStatusCode, HashMap<String, String> header, String body) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.textStatusCode = textStatusCode;
        this.header = header;
        this.body = body;
    }
}

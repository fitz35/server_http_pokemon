package http.server;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;

public class HttpResponse {
    private String httpVersion;
    private int statusCode;
    private String body;
    private String textStatusCode;
    private static HashMap<Integer,String> statusCodeMap;

    public static void createStatusCodeMap()
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

    @Override
    public String toString() {
        return "HttpResponse{" +
                "httpVersion='" + httpVersion + '\'' +
                ", statusCode=" + statusCode +
                ", body='" + body + '\'' +
                ", textStatusCode='" + textStatusCode + '\'' +
                '}';
    }

    public void sendHttpResponse(PrintWriter out)
    {
        String line1= this.httpVersion+ " " + this.statusCode+ " " + statusCodeMap.get(statusCode);
        String line2= "Content-Type: text/html";
        String contentLength;
        String body= "";
        if(this.body!=null)
        {
             contentLength= "Content-Type: "+ this.body.length();
             body = this.body;
        }
        else {
             contentLength = "Content-Type: " + "null";
        }


        out.println(line1);
        out.println(line2);
        out.println(contentLength);
        out.println();
        out.println(body);
        out.flush();

    }
}

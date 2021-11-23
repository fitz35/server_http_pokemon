package http.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;

public class HttpResponse {
    private String httpVersion;
    private String contentType;
    private int statusCode;
    private byte[] body;
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

    public HttpResponse(String httpVersion, int statusCode, byte[]body, String contentType) {
        this.httpVersion = httpVersion;
        this.contentType=contentType;
        this.statusCode = statusCode;
        this.textStatusCode = statusCodeMap.get(this.statusCode);
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "httpVersion='" + httpVersion + '\'' +
                "Content-Type"+ contentType+ '\'' +
                ", statusCode=" + statusCode +
                ", body='" + body + '\'' +
                ", textStatusCode='" + textStatusCode + '\'' +
                '}';
    }

    public void sendHttpResponse(DataOutputStream os ) throws IOException {
        String line1= this.httpVersion+ " " + this.statusCode+ " " + statusCodeMap.get(statusCode);
        String line2= "Content-Type:"+ this.contentType;
        String contentLength;
        byte[]body= new byte[6000000];
        if(this.body!=null)
        {
             contentLength= "Content-Type: "+ this.body.length;
            byte[] bytesContentLength = contentLength.getBytes();
             body = this.body;
        }
        else {
             contentLength = "Content-Type: " + "null";
        }


        os.writeBytes(line1 + "\n");
        os.writeBytes(line2 + "\n");
        os.writeBytes(contentLength + "\n");
        os.writeBytes("\n");
        os.write(body);
        os.flush();

    }
}

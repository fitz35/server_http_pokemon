package http.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 *  Class that creates and manages Http Responses
 *  @author Tushita Ramkaran and Clement Lahoche
 *   @version 1.0
 */
public class HttpResponse {
    private final String httpVersion;
    private final String contentType;
    private final int statusCode;
    private final byte[] body;
    private final String textStatusCode;
    private static HashMap<Integer,String> statusCodeMap;

    /**
     * Creates the Map with key= the error codes and value= the error codes in text
     */
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

    /**
     * Sends the created Http Response through a Data Stream Output
     * @param os the Data Stream Output
     * @throws IOException
     */
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

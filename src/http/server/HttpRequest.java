package http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

/**
 *  Class that creates and manages Http Requests
 *  @author Tushita Ramkaran and Clement Lahoche
 *   @version 1.0
 */
public class HttpRequest {
    private final Method method;
    private final String host;
    private final String path;
    private final String httpVersion;
    private final HashMap<String, String> queryString;
    private final String body;

    public HttpRequest(Method method, String host, String path, String httpVersion, HashMap<String, String> queryString, String body) {
        this.method = method;
        this.host = host;
        this.path = path;
        this.httpVersion = httpVersion;
        this.queryString = queryString;
        this.body = body;
    }

    /**
     * Converts the data sent from the Web Server into an Http Request
     * @param request the array of Strings sent from the Web Server
     * @return HttpRequest
     */
    public static HttpRequest convertRequestToHttpRequest(String[] request)
    {
        HashMap<String, String> queryStringTemp= null;
        String[] parts1 = request[0].split(" ");
        String path=null;
        try
        {
            Method method= Method.valueOf( parts1[0]);
            String httpVersion=parts1[2];


            String[] parts11 = parts1[1].split("\\?");
            path=parts11[0];
            if(parts11[0].compareTo(parts1[1])!=0)
            {
                String[] parts111 = parts11[1].split("&");
                queryStringTemp = new HashMap<>();

                for(String s: parts111)
                {
                    String[] keyValue=s.split("=");
                    String key=keyValue[0];
                    String value= keyValue[1];
                    queryStringTemp.put(key, value);
                    System.out.println("key:"+key+"value"+value);
                }
            }
            String[] parts2 = request[1].split(":");
            String host= parts2[1];

            String body = request[2];
            return new HttpRequest(method,host,path,httpVersion,queryStringTemp,body);

        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Method getMethod() {
        return method;
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public HashMap<String, String> getQueryString() {
        return queryString;
    }

    public String getBody() {
        return body;
    }
}

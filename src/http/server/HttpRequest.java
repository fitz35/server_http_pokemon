package http.server;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest
{
    private static Method method;
    private static String host;
    private static String path;
    private static String httpVersion;
    private static HashMap queryString;
    private static String body;

    public HttpRequest(Method method, String host, String path, String httpVersion, HashMap queryString, String body) {
        this.method = method;
        this.host = host;
        this.path = path;
        this.httpVersion = httpVersion;
        this.queryString = queryString;
        this.body = body;
    }

    public static HttpRequest convertRequestToHttpRequest(String[] request)
    {
        String[] parts1 = request[0].split(" ");
        try
        {
            method= Method.valueOf( parts1[0]);
            httpVersion=parts1[2];
            String[] parts11 = parts1[1].split("\\?");
            path=parts11[0];
            String[] parts111 = parts11[1].split("&");
            for(String s: parts111)
            {
                String[] keyValue=s.split("=");
                String key=keyValue[0];
                String value= keyValue[1];
                queryString.put(key, value);
            }
            String[] parts2 = request[1].split(":");
            host= parts2[1];
            body=request[2];

            return new HttpRequest(method,host,path,httpVersion,queryString,body);

        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }

    }
}

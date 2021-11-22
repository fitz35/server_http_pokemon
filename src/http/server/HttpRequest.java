package http.server;

import java.util.HashMap;

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

    public static HttpRequest convertRequestToHttpRequest(String[] request)
    {
        String[] parts1 = request[0].split(" ");
        try
        {
            Method method= Method.valueOf( parts1[0]);
            String httpVersion=parts1[2];
            String[] parts11 = parts1[1].split("\\?");
            String path=parts11[0];
            String[] parts111 = parts11[1].split("&");
            HashMap<String, String> queryStringTemp = new HashMap<>();
            for(String s: parts111)
            {
                String[] keyValue=s.split("=");
                String key=keyValue[0];
                String value= keyValue[1];
                queryStringTemp.put(key, value);
            }
            String[] parts2 = request[1].split(":");
            String host= parts2[1];
            String body=request[2];

            return new HttpRequest(method,host,path,httpVersion,queryStringTemp,body);

        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }

    }
}

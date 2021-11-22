package http.server;

import http.server.Data.ListPokemon;
import http.server.Data.TypePokemon;

import java.net.ServerSocket;
import java.net.URI;
import java.util.HashMap;

public class Router
{
    public static HttpResponse get(HttpRequest httpRequest)
    {
        HashMap<Integer,String> statusCodeMap= HttpResponse.getStatusCodeMap();

        if(httpRequest.getPath().compareTo("/listPokemon")==0 && httpRequest.getQueryString()==null)
        {
            ListPokemon listPokemon= WebServer.getListOfPokemon();
            String htmlContent=listPokemon.getHtmlContent();
            HttpResponse httpResponse= new HttpResponse(httpRequest.getHttpVersion(),200,htmlContent);
            return httpResponse;
        }
        else if(httpRequest.getPath().compareTo("/listPokemon/search")==0 && httpRequest.getQueryString()!=null)
        {
            String nameOfPokemon;
            TypePokemon typeOne=null;
            TypePokemon typeTwo=null;
            ListPokemon listPokemon= WebServer.getListOfPokemon();
            if(httpRequest.getQueryString().containsKey("name"))
            {
                nameOfPokemon= httpRequest.getQueryString().get("name");

            }
            if(httpRequest.getQueryString().containsKey("type1"))
            {
                try
                {
                    typeOne= TypePokemon.valueOf(httpRequest.getQueryString().get("type1"));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return new HttpResponse(httpRequest.getHttpVersion(),400, "This type of Pokemon has not been discovered yet! Catch them all!");
                }
            }
            if(httpRequest.getQueryString().containsKey("type2"))
            {
                try
                {
                    typeTwo= TypePokemon.valueOf(httpRequest.getQueryString().get("type2"));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return new HttpResponse(httpRequest.getHttpVersion(),400, null);
                }
            }

            String htmlContent=listPokemon.getHtmlContent();
            HttpResponse httpResponse= new HttpResponse(httpRequest.getHttpVersion(),200,htmlContent);
            return httpResponse;
        }
        else
        {
            return new HttpResponse(httpRequest.getHttpVersion(),404, null);
        }
    }
}

package http.server;

import http.server.Data.ListPokemon;
import http.server.Data.TypePokemon;

import java.net.ServerSocket;
import java.net.URI;
import java.util.HashMap;

public class Router
{
    public static HttpResponse differentiateCallMethods(HttpRequest httpRequest)
    {
        Method callMethod= httpRequest.getMethod();

        if (callMethod.compareTo(Method.GET)==0){
                return get(httpRequest);
            }
        else
        {
            return new HttpResponse(httpRequest.getHttpVersion(),404, null);
        }

    }



    public static HttpResponse get(HttpRequest httpRequest)
    {

        if(httpRequest.getPath().compareTo("/listPokemon")==0 && httpRequest.getQueryString()==null)
        {
            ListPokemon listPokemon= WebServer.getListOfPokemon();
            String htmlContent=listPokemon.getHtmlContent();
            HttpResponse httpResponse= new HttpResponse(httpRequest.getHttpVersion(),200,htmlContent);
            return httpResponse;
        }
        else if(httpRequest.getPath().compareTo("/listPokemon/search")==0 && httpRequest.getQueryString()!=null)
        {
            String nameOfPokemon= null;
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
                    return new HttpResponse(httpRequest.getHttpVersion(),400, "This type of Pokemon has not been discovered yet! Catch them all!");
                }
            }
            System.out.println("name of pokemon:"+ nameOfPokemon+ ","+typeOne+","+typeTwo);
            String htmlContent=listPokemon.getHtmlContent(nameOfPokemon,typeOne,typeTwo);

            HttpResponse httpResponse= new HttpResponse(httpRequest.getHttpVersion(),200,htmlContent);
            return httpResponse;
        }
        else
        {
            return new HttpResponse(httpRequest.getHttpVersion(),404, null);
        }
    }

  /*  public static HttpResponse post(HttpRequest httpRequest)
    {
        String body= httpRequest.getBody();
        String[] dataGroup= body.split("&");
        if(httpRequest.getBody().containsKey("name"))
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
                return new HttpResponse(httpRequest.getHttpVersion(),400, "This type of Pokemon has not been discovered yet! Catch them all!");
            }
    }*/

}

package http.server;

import http.server.Data.ListPokemon;
import http.server.Data.Pokemon;
import http.server.Data.TypePokemon;

import java.io.*;
import java.net.ServerSocket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * class Router
 * Decides which Method needs to be called for the Http Request depending on the parameters used in the request
 * (GET/POST/PUT/DELETE)
 * (HTML/IMAGES/VIDEOS)
 * @author Tushita Ramkaran and Clement Lahoche
 * @version 1.0
 */
public class Router
{
    /**
     * Calls different methods depending on  the parameters used in the http request
     * @param httpRequest the httpRequest
     * @return an Http Response to the httpRequest
     * @throws IOException
     */
    public static HttpResponse differentiateCallMethods(HttpRequest httpRequest) throws IOException {
        Method callMethod= httpRequest.getMethod();

        if (callMethod.compareTo(Method.GET)==0){
                return get(httpRequest);
        }else if(callMethod.compareTo(Method.POST)==0){
            return post(httpRequest);
        }
        else if(callMethod.compareTo(Method.DELETE)==0){
         return delete(httpRequest);
        }
        else if(callMethod.compareTo(Method.PUT)==0){
            return put(httpRequest);
        }
        else
        {
            return new HttpResponse(httpRequest.getHttpVersion(),404, null, "text/html");
        }
    }

    /**
     * Used to manage the Method GET
     * @param httpRequest the Http Request
     * @return
     * @throws IOException
     */
    public static HttpResponse get(HttpRequest httpRequest) throws IOException {

        //to get the list of all pokemons
        if (httpRequest.getPath().compareTo("/listPokemon") == 0 && httpRequest.getQueryString() == null) {
            System.out.println("in listpokemon");
            ListPokemon listPokemon = WebServer.getListOfPokemon();
            String htmlContent = listPokemon.getHtmlContent();
            byte[] bytesHtmlContent = htmlContent.getBytes();
            HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion(), 200, bytesHtmlContent, "text/html");
            return httpResponse;

            //to serach for a pokemon in the list
        } else if (httpRequest.getPath().compareTo("/listPokemon/search") == 0 && httpRequest.getQueryString() != null) {
            String nameOfPokemon = null;
            TypePokemon typeOne = null;
            TypePokemon typeTwo = null;
            ListPokemon listPokemon = WebServer.getListOfPokemon();
            if (httpRequest.getQueryString().containsKey("name")) {
                nameOfPokemon = httpRequest.getQueryString().get("name");


            }
            if (httpRequest.getQueryString().containsKey("type1")) {
                try {
                    typeOne = TypePokemon.valueOf(httpRequest.getQueryString().get("type1"));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return new HttpResponse(httpRequest.getHttpVersion(), 400, "This type of Pokemon has not been discovered yet! Catch them all!".getBytes(), "text/html");
                }
            }
            if (httpRequest.getQueryString().containsKey("type2")) {
                try {
                    typeTwo = TypePokemon.valueOf(httpRequest.getQueryString().get("type2"));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return new HttpResponse(httpRequest.getHttpVersion(), 400, "This type of Pokemon has not been discovered yet! Catch them all!".getBytes(), "text/html");
                }
            }
            System.out.println("name of pokemon:" + nameOfPokemon + "," + typeOne + "," + typeTwo);
            String htmlContent = listPokemon.getHtmlContent(nameOfPokemon, typeOne, typeTwo);
            byte[] bytesHtmlContent = htmlContent.getBytes();
            HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion(), 200, bytesHtmlContent, "text/html");
            return httpResponse;
        }   // to get the images of the Pokemon
            else if (httpRequest.getPath().compareTo("/listPokemon/images") == 0 && httpRequest.getQueryString() != null) {
            System.out.println("in image");
            String nameOfPokemon = null;
            ListPokemon listPokemon = WebServer.getListOfPokemon();
            if (httpRequest.getQueryString().containsKey("name")) {
                nameOfPokemon = httpRequest.getQueryString().get("name");
            }
            Pokemon toGetPicture = listPokemon.getPokemonByName(nameOfPokemon);


            if (toGetPicture != null) {
                byte[] arrayOfPictureBytes = toGetPicture.getPokemonPicture();
                HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion(), 200, arrayOfPictureBytes, "image/png");
                return httpResponse;
            } else {
                HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion(), 404, null, "text/html");
                return httpResponse;
            }


            //To get the video
        } else if (httpRequest.getPath().compareTo("/listPokemon/videos") == 0 && httpRequest.getQueryString() != null) {
            String videoName = null;
            if (httpRequest.getQueryString().containsKey("name")) {
                videoName = httpRequest.getQueryString().get("name");
            }

            String video = "videos/" + videoName + ".mp4";
            InputStream in = Router.class.getClassLoader()
                    .getResourceAsStream(video);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[6000000];

            while ((nRead = in.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion(), 200, buffer.toByteArray(), "video/mp4");
            return httpResponse;
        }

            //To get the static ressource html file with all the links
        else if (httpRequest.getPath().compareTo("/") == 0 && httpRequest.getQueryString() == null)
        {
            InputStream in = Router.class.getClassLoader()
                    .getResourceAsStream("html/index.html");
            String s = new BufferedReader(new InputStreamReader(in))
                    .lines().collect(Collectors.joining("\n"));
            HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion(), 200, s.getBytes(), "text/html");
            return httpResponse;
        }
        // To get the dynamic ressource page provided by Moodle
        else if (httpRequest.getPath().compareTo("/codeMoodle") == 0 && httpRequest.getQueryString() == null)
        {
            InputStream in = Router.class.getClassLoader()
                    .getResourceAsStream("html/codeMoodle.html");
            String s = new BufferedReader(new InputStreamReader(in))
                    .lines().collect(Collectors.joining("\n"));
            HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion(), 200, s.getBytes(), "text/html");
            return httpResponse;
        }


        else {
            HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion(), 404, null, "text/html");
            return httpResponse;
         }


        }

    /**
     * Used to manage the Method POST
     * @param httpRequest the Http Request
     * @return
     * @throws IOException
     */

    public static HttpResponse post(HttpRequest httpRequest) {
        if (httpRequest.getPath().compareTo("/listPokemon") == 0 && httpRequest.getQueryString() == null) {
            String nameOfPokemon = null;
            TypePokemon typeOne = null;
            TypePokemon typeTwo = null;
            String body = httpRequest.getBody();
            String[] dataGroup = body.split("&");
            HashMap<String, String> mapOfValues = new HashMap<>();
            for (String s : dataGroup) {
                String[] keyValuePair = s.split("=");
                mapOfValues.put(keyValuePair[0], keyValuePair[1]);
            }
            System.out.println(mapOfValues);
            if (mapOfValues.containsKey("name")) {
                nameOfPokemon = mapOfValues.get("name");

                if (mapOfValues.containsKey("type1")) {
                    try {
                        typeOne = TypePokemon.valueOf(mapOfValues.get("type1"));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        return new HttpResponse(httpRequest.getHttpVersion(), 400, "This type of Pokemon has not been discovered yet! Catch them all!".getBytes(), "text/html");
                    }
                }
                if (mapOfValues.containsKey("type2")) {
                    try {
                        typeTwo = TypePokemon.valueOf(mapOfValues.get("type2"));
                        if(typeOne.compareTo(typeTwo)==0)
                        {
                            return new HttpResponse(httpRequest.getHttpVersion(), 401, "The first and second types should be different".getBytes(), "text/html");
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        return new HttpResponse(httpRequest.getHttpVersion(), 400, "This type of Pokemon has not been discovered yet! Catch them all!".getBytes(), "text/html");
                    }
                }
                Pokemon newPokemon = new Pokemon(nameOfPokemon, typeOne, typeTwo);
                WebServer.getListOfPokemon().addPokemon(newPokemon);

                HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion(), 200,  WebServer.getListOfPokemon().getHtmlContent().getBytes(), "text/html");
                return httpResponse;

            }
            else
            {
                return new HttpResponse(httpRequest.getHttpVersion(), 401, "Your Pokemon should have a name!".getBytes(), "text/html");
            }
        }
        else
        {
            return new HttpResponse(httpRequest.getHttpVersion(), 404, null, "text/html");
        }
    }

    /**
     * Used to manage the Method DELETE
     * @param httpRequest the Http Request
     * @return
     * @throws IOException
     */

    public static HttpResponse delete(HttpRequest httpRequest) {
        if (httpRequest.getPath().compareTo("/listPokemon") == 0 && httpRequest.getQueryString() != null) {
            String nameOfPokemon = null;
            HashMap<String, String> mapOfValues= httpRequest.getQueryString();
            if (mapOfValues.containsKey("name")) {
                nameOfPokemon = mapOfValues.get("name");
                boolean removeSuccessful= WebServer.getListOfPokemon().removePokemon(nameOfPokemon);

                if(removeSuccessful)
                {
                    HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion(), 200,  WebServer.getListOfPokemon().getHtmlContent().getBytes(), "text/html");
                    return httpResponse;
                }
                else
                {
                    HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion(), 400,  WebServer.getListOfPokemon().getHtmlContent().getBytes(), "text/html");
                    return httpResponse;
                }
            }
            else
            {
                return new HttpResponse(httpRequest.getHttpVersion(), 401, "Your Pokemon should have a name!".getBytes(), "text/html");
            }
        }
        else
        {
            return new HttpResponse(httpRequest.getHttpVersion(), 404, null, "text/html");
        }
    }
    /**
     * Used to manage the Method PUT
     * @param httpRequest the Http Request
     * @return
     * @throws IOException
     */

    public static HttpResponse put(HttpRequest httpRequest) {
        String path= httpRequest.getPath();
        if (path.compareTo("/listPokemon") == 0 && httpRequest.getQueryString() != null) {
            HashMap<String, String> queryString = httpRequest.getQueryString();
            String nameOfPokemon= queryString.get("name");
            Pokemon pokemonToModify= WebServer.getListOfPokemon().getPokemonByName(nameOfPokemon);
            if(pokemonToModify!=null)
            {
                TypePokemon typeOne = null;
                TypePokemon typeTwo = null;
                String body = httpRequest.getBody();
                String[] dataGroup = body.split("&");
                HashMap<String, String> mapOfValues = new HashMap<>();
                for (String s : dataGroup) {
                    String[] keyValuePair = s.split("=");
                    mapOfValues.put(keyValuePair[0], keyValuePair[1]);
                }
                System.out.println(mapOfValues);
                if (mapOfValues.containsKey("name")) {
                    nameOfPokemon = mapOfValues.get("name");
                    pokemonToModify.setName(nameOfPokemon);

                    if (mapOfValues.containsKey("type1")) {
                        try {
                            typeOne = TypePokemon.valueOf(mapOfValues.get("type1"));
                            pokemonToModify.setType1(typeOne);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            return new HttpResponse(httpRequest.getHttpVersion(), 400, "This type of Pokemon has not been discovered yet! Catch them all!".getBytes(), "text/html");
                        }
                    }
                    if (mapOfValues.containsKey("type2")) {
                        try {
                            typeTwo = TypePokemon.valueOf(mapOfValues.get("type2"));
                            if(typeOne.compareTo(typeTwo)==0)
                            {
                                return new HttpResponse(httpRequest.getHttpVersion(), 401, "The first and second types should be different".getBytes(), "text/html");
                            }
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            return new HttpResponse(httpRequest.getHttpVersion(), 400, "This type of Pokemon has not been discovered yet! Catch them all!".getBytes(), "text/html");
                        }
                    }
                    pokemonToModify.setType2(typeTwo);
                    HttpResponse httpResponse = new HttpResponse(httpRequest.getHttpVersion(), 200,  WebServer.getListOfPokemon().getHtmlContent().getBytes(), "text/html");
                    return httpResponse;

                }
                else
                {
                    return new HttpResponse(httpRequest.getHttpVersion(), 401, "Your Pokemon should have a name!".getBytes(), "text/html");
                }
            }
            else
            {
                return new HttpResponse(httpRequest.getHttpVersion(), 404, null, "text/html");
            }
        }
        else
        {
            return new HttpResponse(httpRequest.getHttpVersion(), 403, null, "text/html");
        }

    }




}

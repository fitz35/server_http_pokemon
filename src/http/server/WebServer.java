///A Simple Web Server (WebServer.java)

package http.server;

import http.server.Data.ListPokemon;
import http.server.Data.Pokemon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Example program from Chapter 1 Programming Spiders, Bots and Aggregators in
 * Java Copyright 2001 by Jeff Heaton
 * 
 * WebServer is a very simple web-server. Any request is responded with a very
 * simple web-page.
 * 
 * @author Jeff Heaton
 * @version 1.0
 */
public class WebServer {

  private  ServerSocket serverSocket;
  private static ListPokemon listOfPokemon;


  public static ListPokemon getListOfPokemon() {
    return listOfPokemon;
  }


  protected void start() {


    System.out.println("Webserver starting up on port 3000");
    System.out.println("(press ctrl-c to exit)");
    try {
      // create the main server socket
      serverSocket = new ServerSocket(3000);
    } catch (Exception e) {
      System.out.println("Error: " + e);
      return;
    }
    listOfPokemon= new ListPokemon();
    HttpResponse.createStatusCodeMap();

    System.out.println("Waiting for connection");
    for (;;) {
      try {
        // wait for a connection
        Socket remote = serverSocket.accept();
        // remote is now the connected socket
        System.out.println("Connection, sending data.");
        BufferedReader in = new BufferedReader(new InputStreamReader(
            remote.getInputStream()));
        PrintWriter out = new PrintWriter(remote.getOutputStream());

        String str = ".";
        ArrayList<String> listOfInputs= new ArrayList<>();
        while (str != null && !str.equals(""))
        {
          str = in.readLine();
          listOfInputs.add(str);

        }
        StringBuilder payload = new StringBuilder();
        while(in.ready()){
          payload.append((char) in.read());
        }
        listOfInputs.add(payload.toString());

        System.out.println(listOfInputs);
        String[]listOfUsableInputs= new String[3];
        listOfUsableInputs[0]= listOfInputs.get(0);
        listOfUsableInputs[1]=listOfInputs.get(1);
        listOfUsableInputs[2]=listOfInputs.get(listOfInputs.size() - 1);


        System.out.println("listOfUsableInputs: "+listOfUsableInputs[0]+","+listOfUsableInputs[1]+","+listOfUsableInputs[2]);
         HttpRequest httpRequest= HttpRequest.convertRequestToHttpRequest(listOfUsableInputs);
         HttpResponse httpResponse= Router.differentiateCallMethods(httpRequest);
         httpResponse.sendHttpResponse(out);



        // Send the response

        // Send the HTML page


        remote.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Start the application.
   * 
   * @param args
   *            Command line parameters are not used.
   */
  public static void main(String args[]) {
    WebServer ws = new WebServer();
    ws.start();
  }
}

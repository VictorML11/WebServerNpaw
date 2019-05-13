package me.victorml.npaw.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utils {


    public static Request getClientRequest(InputStream in) {
        //REQUEST EXAMPLE: http://dataService.com/getData?accountCode=clienteA&targetDevice=XBox&pluginVersion=3.3.1
        Request request = null;
        try {

            // Get the Client input & read the line
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String requestLine = reader.readLine(); // Line format ex:  GET /npaw?name=hola HTTP/1.1 | GET / HTTP/1.1

            if (requestLine != null) {
                String[] requestInfo = requestLine.split(" ");

                // Check if browser is asking for icon in that case we dont have it!
                if(requestInfo[1].contains("favicon")){
                    return null;
                }

                // Read the header HTTP request
                HashMap<String, String> header = new HashMap<>();
                String headerRequestLine = reader.readLine();

                while (headerRequestLine != null && !headerRequestLine.equals("")) {
                    String[] headerInf = headerRequestLine.split(": ", 2);

                    if (headerInf.length == 2) {
                        header.put(headerInf[0], headerInf[1]);
                    } else {
                        throw new IOException("Error reading the header http request [too long]: " + headerRequestLine);
                    }

                    headerRequestLine = reader.readLine();
                }

                //Read the body
                ArrayList<String> body = new ArrayList<>();

                // Mientras haya body lo guardamos
                while (reader.ready()) {
                    String bodyLine = reader.readLine();
                    body.add(bodyLine);
                }

                // Build the request
                request = new Request.RequestBuilder()
                        .withHttpMethod(requestInfo[0])
                        .withUrl(requestInfo[1])
                        .usingProtocol(requestInfo[2])
                        .withHeaderInfo(header)
                        .withBodyInfo(body)
                        .build();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
    }

    public static Response getResponse(Request request) {
        Response response = null;

        //If the requested was a GET
        if (request.getHttpMethod().equals("GET")) {

            HashMap<String, String> parameters = request.getParameters();
            // Verify the parameters

            /*
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }*/

            // Do we have the correct lenght? it means we have all parameters otherwhise we are lack of info
            if (parameters.size() == 3) {
                // Check if the account is from the server:
                String account = parameters.get(RequestParameters.Parameters.ACCOUNT_CODE.getParameter());

                if (account.equalsIgnoreCase("clienteA") || account.equalsIgnoreCase("clienteB")) {
                    // localhost:8080/getData?accountCode=clienteA&targetDevice=XBox&pluginVersion=3.3.1
                    System.out.println("Pertenece a la plataforma");
                    response = new Response(request.getProtocol(), StatusCode.OK);
                } else {
                    // TODO: DEVOLVER RESPUESTA VACIA
                    System.out.println("No pertenece!");
                }


            } else {
                // TODO: DEVOLVER RESPUESTA VACIA
                System.out.println("Faltan parametros");
            }


        } else {
            response = new Response(request.getProtocol(), StatusCode.NOT_IMPLEMENTED);
        }




        return response;
    }


}

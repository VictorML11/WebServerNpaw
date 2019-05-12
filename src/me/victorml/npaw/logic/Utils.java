package me.victorml.npaw.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {


    public static Request getClientRequest(InputStream in) {
        //REQUEST EXAMPLE: http://dataService.com/getData?accountCode=clienteA&targetDevice=XBox&pluginVersion=3.3.1
        Request request = null;
        try {

            // Get the Client input & read the line
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String info = reader.readLine(); // Line format ex:  GET /npaw?name=hola HTTP/1.1 | GET / HTTP/1.1
            //System.out.println(info);
            if(info != null){
                String [] infoArgs = info.split(" ");

                // Build the request
                request = new Request.RequestBuilder()
                        .withHttpMethod(infoArgs[0])
                        .withUrl(infoArgs[1])
                        .usingProtocol(infoArgs[2])
                        .build();

                info = reader.readLine();
                System.out.println(info);

            }
            //System.out.println(line);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }
}

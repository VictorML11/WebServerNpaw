package me.victorml.npaw.logic;

import java.util.Arrays;
import java.util.HashMap;

public class RequestParameters {

    private String url;
    private HashMap<String,String> parameters;

    public RequestParameters(String url) {
        this.parameters = new HashMap<>();
        this.url = url;
        this.getParametersFromUrl();
    }

    private void getParametersFromUrl(){
        // Some examples GET /npaw?name=hola HTTP/1.1 | GET / HTTP/1.1
        if(url != null){
            // First we get the parameter section
            String [] urlsplit = url.split("\\?");

            // Check if we are using the correct route
            if(urlsplit[0].equalsIgnoreCase("/getData")){
                // Get each parameter k=v
                String [] urlparams = urlsplit[1].split("&");
                // For each parameter get the key and value
                for (String urlparam : urlparams) {
                    String[] sparam = urlparam.split("=");

                    if(verifyKeyParameter(sparam[0])){
                        parameters.put(sparam[0], sparam[1].toLowerCase());
                    }else{
                        System.err.println("Error the parameter: " + sparam[0] + " does not exist!");
                    }
                }
            }
        }else{
            // We should never reach hear but who knows!
            System.err.println("Error! the url of the requests was absolutely null!");
        }
    }

    /**
     * Verifies that the key parameter is a possible key
     * @param s key to check
     * @return boolean
     */
    public boolean verifyKeyParameter(String s){
        return Arrays.stream(Parameters.values()).anyMatch(parameter -> s.equals(parameter.getParameter()));
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

}



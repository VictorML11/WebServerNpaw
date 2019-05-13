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
            if(urlsplit[0].equals("/getData")){
                // Get each parameter k=v
                String [] urlparams = urlsplit[1].split("&");
                // For each parameter get the key and value
                for (String urlparam : urlparams) {
                    String[] sparam = urlparam.split("=");

                    if(verifyKeyParameter(sparam[0])){
                        parameters.put(sparam[0], sparam[1]);
                    }else{
                        System.err.println("Error the parameter: " + sparam[0] + " does not exist!");
                    }
                }
            }
        }else{
            // We should never reach hear but who knows!
            System.err.println("Error! the url of the requests was absolutly null!");
        }
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public boolean verifyKeyParameter(String s){
        return Arrays.stream(Parameters.values()).anyMatch(parameter -> s.equalsIgnoreCase(parameter.getParameter()));
    }


    public enum Parameters{

        ACCOUNT_CODE("accountCode"),
        TARGET_DEVICE("targetDevice"),
        PLUGIN_VERSION("pluginVersion");

        private String parameter;

        Parameters(String parameter) {
            this.parameter = parameter;
        }

        public String getParameter() {
            return parameter;
        }
    }

}



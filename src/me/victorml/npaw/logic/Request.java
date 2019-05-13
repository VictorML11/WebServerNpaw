package me.victorml.npaw.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Request {

    /*
     * An HTTP request has:
     * <HTTP method><space><Request-URI><space><protocolspecification>\r\n
     * Accept tipus: de contingut acceptables pel client
     * Connection: ”Keep-Alive”...
     * Host: ...
     * === Un body === [No sempre]
     */
    private String httpMethod;
    private String url;
    private String protocol;
    private HashMap<String, String> header;
    private ArrayList<String> body;
    private RequestParameters requestParameters;


    public Request(String httpMethod, String url, String protocol, HashMap<String, String> header, ArrayList<String> body) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.protocol = protocol;
        this.header = header;
        this.body = body;
        this.requestParameters = new RequestParameters(url);
    }

    public Request(String httpMethod, String url, String protocol) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.protocol = protocol;
        this.header = new HashMap<>();
        this.body = new ArrayList<>();
        this.requestParameters = new RequestParameters(url);
    }

    public void addHeaderInfo(String s1, String s2){
        this.header.put(s1, s2);
    }

    public void addBoddyInfo(String s){
        body.add(s);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }

    public RequestParameters getRequestParameters() {
        return requestParameters;
    }

    public HashMap<String, String> getParameters(){
        return this.requestParameters.getParameters();
    }

    @Override
    public String toString() {
        StringBuilder srequest = new StringBuilder(httpMethod + " " + url + " " + protocol + "\n");

        for(Map.Entry<String,String> header : header.entrySet()){
            srequest.append(header.getKey()).append(": ").append(header.getValue()).append("\n");
        }

        body.forEach(s -> srequest.append(s).append("\n"));

        return srequest.toString();
    }

    //Apply Builder Pattern

    public static class RequestBuilder{
        private String httpMethod;
        private String url;
        private String protocol;
        private HashMap<String, String> header;
        private ArrayList<String> body;

        public RequestBuilder(){

        }

        public RequestBuilder withHttpMethod(String httpMethod){
            this.httpMethod = httpMethod;
            return this;
        }

        public RequestBuilder withUrl(String url){
            this.url = url;
            return this;
        }

        public RequestBuilder usingProtocol(String protocol){
            this.protocol = protocol;
            return this;
        }

        public RequestBuilder withHeaderInfo(HashMap<String, String> header){
            this.header = header;
            return this;
        }

        public RequestBuilder withBodyInfo(ArrayList<String> body){
            this.body = body;
            return this;
        }


        public Request build(){
            return new Request(httpMethod, url, protocol, header, body);
        }

    }
}

package me.victorml.npaw.logic;

public class Request {

    /*
     * An HTTP request has: <HTTP method><space><Request-URI><space><protocolspecification>\r\n
     */
    private String httpMethod;
    private String url;
    private String protocol;


    public Request(String httpMethod, String url, String protocol) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.protocol = protocol;
    }

    //Apply Builder Pattern

    public static class RequestBuilder{
        private String httpMethod;
        private String url;
        private String protocol;

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

        public Request build(){
            return new Request(httpMethod, url, protocol);
        }

    }
}

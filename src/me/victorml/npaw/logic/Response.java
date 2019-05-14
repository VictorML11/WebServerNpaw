package me.victorml.npaw.logic;


import me.victorml.npaw.model.HostResponse;

import java.util.Date;
import java.util.HashMap;

public class Response {
    /*
     * Format: <protocol><sp><status-code><sp><description>\r\n
     * Date: example: Date: Wed, 25 Apr 2012 11:35:20 GMT
     * Content-Length & Content-Type + body(could be null ex: HEAD)
     */

    private String protocol;
    private Date date;
    private StatusCode statusCode;
    private HashMap<String, String> header;
    private byte [] body;

    private HostResponse hostResponse;

    public Response(HostResponse hostResponse, String protocol, StatusCode statusCode) {
        this.hostResponse = hostResponse;
        this.protocol = protocol;
        this.statusCode = statusCode;
        this.date = new Date();
        this.header = new HashMap<>();
        this.setUp();
    }

    private void setUp(){
        this.setDate();
        String xml = generateXml();
        this.setContentLength(xml);
        this.setContentType("text/xml"); //TODO: "text/html" ...
        this.setBody(xml);
    }

    private void setDate(){
        this.header.put("Date", date.toString());
    }

    private void setContentLength(String xml){
        header.put("Content-Length",String.valueOf(xml.getBytes().length));
    }

    private void setContentType(String c){
        header.put("Content-Type", c);
    }

    private void setBody(String xml){
        body = xml.getBytes();
    }


    private String generateXml(){
        // Just for testing now do a parsing info from request!
        if (hostResponse == null) return "";

        return "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<q>\n" +
                "<h>" + this.hostResponse.getName() + "</h>\n" +
                "<pt>" + this.hostResponse.getPingTime() + "</pt>\n" +
                "<c>"+ this.hostResponse.getId() +"</c>\n" +
                "</q>";
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(protocol + " " + statusCode.getInfo() + "\n");
        for (String key : header.keySet()) {
            result.append(key).append(": ").append(header.get(key)).append("\n");
        }
        result.append("\r\n");
        if (body != null) {
            result.append(new String(body));
        }
        return result.toString();
    }
}

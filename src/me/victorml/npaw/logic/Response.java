package me.victorml.npaw.logic;


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

    public Response(String protocol, StatusCode statusCode) {
        this.protocol = protocol;
        this.statusCode = statusCode;
        this.date = new Date();
        this.header = new HashMap<>();
        this.setUp();
    }

    private void setUp(){
        this.setDate();
        String xml = xmlTest();
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


    private String xmlTest(){
        // Just for testing now do a parsing info from request!
        return "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<q>\n" +
                "<h>clusterA.com</h>\n" +
                "<pt>5</pt>\n" +
                "<c>7xnj85f06yqswc5x</c>\n" +
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

package me.victorml.npaw.logic;

import java.util.Date;

public class Response {
    /*
     * Format: <protocol><sp><status-code><sp><description>\r\n
     * Date: example: Date: Wed, 25 Apr 2012 11:35:20 GMT
     *
     */

    private String protocol;
    private Date date;
    private StatusCode statusCode;

    public Response(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}

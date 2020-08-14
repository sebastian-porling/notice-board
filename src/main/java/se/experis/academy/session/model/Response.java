package se.experis.academy.session.model;

/**
 *  A response for the user client in a HTTP Request
 */
public class Response {
    private Object data;
    private String msg;

    /**
     * Constructor
     * @param data
     * @param msg
     */
    public Response(Object data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
}

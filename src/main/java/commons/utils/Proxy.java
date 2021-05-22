package commons.utils;

public class Proxy {
    private Object connection = null;

    private Object base = null;

    public void setConnection(Object obj) {
        connection = obj;
    }

    public void setBase(Object obj) {
        base = obj;
    }

    public Object getConnection() {
        return connection;
    }

    public Object getBase() {
        return base;
    }
}

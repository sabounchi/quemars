package ir.softernet.lib.quemars;

/**
 * Created by saman on 4/19/16.
 */
public class QuemarsConfig {

    private final int       dbIndex;
    private final String    namespace;

    private String          ip;
    private int             port;

    public QuemarsConfig(int dbIndex, String namespace) {
        this.dbIndex        = dbIndex;
        this.namespace      = namespace;

        this.ip             = "127.0.0.1";
        this.port           = 6379;
    }

    public int getDbIndex() {
        return dbIndex;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}

package edu.chl.mailbowser.account;

/**
 * Created by filip on 09/04/15.
 *
 * An abstract class for mail servers. This class provides a hostname and a port.
 */
public abstract class AbstractMailServer implements IMailServer {
    private static final long serialVersionUID = 3297957952585426890L;

    private String hostname;
    private String port;

    /**
     * Creates a new AbstractMailServer with the specified hostname and port.
     *
     * @param hostname
     * @param port
     */
    public AbstractMailServer(String hostname, String port) {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Returns the hostname for this mail server.
     *
     * @return hostname
     */
    public String getHostname(){
        return hostname;
    }

    /**
     * Returns the port for this mail server.
     *
     * @return port
     */
    public String getPort(){
        return port;
    }

    /**
     * {@inheritDoc}
     *
     * This AbstractMailServer is equal to another object if the other object is a AbstractMailServer of the same type,
     * and its hostname and port are the same.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!this.getClass().equals(o.getClass())) return false;

        AbstractMailServer that = (AbstractMailServer) o;

        if (hostname != null ? !hostname.equals(that.hostname) : that.hostname != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     *
     * A hashcode is built using the hostname and port of this mail server.
     */
    @Override
    public int hashCode() {
        int result = hostname != null ? hostname.hashCode() : 0;
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }
}

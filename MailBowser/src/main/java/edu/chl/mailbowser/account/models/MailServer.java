package edu.chl.mailbowser.account.models;

/**
 * Created by filip on 09/04/15.
 */
public abstract class MailServer{
    private String hostname;
    private String port;

    /**
     * Creates a new MailServer with the specified hostname and port.
     *
     * @param hostname
     * @param port
     */
    public MailServer(String hostname, String port) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!this.getClass().equals(o.getClass())) return false;

        MailServer that = (MailServer) o;

        if (hostname != null ? !hostname.equals(that.hostname) : that.hostname != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hostname != null ? hostname.hashCode() : 0;
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }
}

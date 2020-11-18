public enum Port {

    w1("w1", "Goto6"),
    w2("w2", "Goto8"),
    did0("did0", "Goto14"),
    diq0("diq0", "Goto15"),
    eid("eid", "Goto9"),
    eiq("eiq", "Goto12"),
    ia("ia", "Goto1"),
    ib("ib", "Goto2"),
    ic("ic", "Goto33"),
    id("id", "Goto3"),
    iq("iq", "Goto4"),
    spd("spd", "Goto5");

    private String name;
    private String port;

    private Port(String name, String port) {
        this.name = name;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getPort() {
        return port;
    }
}

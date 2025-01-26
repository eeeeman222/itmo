public class Result {
    public final double x;
    public final double y;
    public final double r;
    public final boolean hit;
    public final String localTime;
    public final long proccessTime;
    public Result(double x, double y, double r, boolean hit, String localTime, long proccessTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.localTime = localTime;
        this.proccessTime = proccessTime;
    }

    @Override
    public String toString() {
        return "{"
                + "\"x\": " + x + ","
                + "\"y\": " + y + ","
                + "\"r\": " + r + ","
                + "\"hit\": " + hit + ","
                + "\"localTime\": \"" + localTime + "\","
                + "\"proccessTime\": \"" + proccessTime + "\""
                + "}";
    }

}

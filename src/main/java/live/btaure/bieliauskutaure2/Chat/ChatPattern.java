package live.btaure.bieliauskutaure2.Chat;

public class ChatPattern
{
    public int[][] getPattern()
    {
        return pattern;
    }

    public LocationType getLocation()
    {
        return location;
    }

    private final int[][] pattern;
    private final LocationType location;
    public ChatPattern(int[][] pattern, LocationType location)
    {
        this.pattern=pattern;
        this.location=location;
    }
}

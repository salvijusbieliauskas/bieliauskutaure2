package live.btaure.bieliauskutaure2.Chat;

public class ChatPattern
{
    private final int[][] pattern;
    private final LocationType location;

    public ChatPattern(int[][] pattern, LocationType location)
    {
        this.pattern = pattern;
        this.location = location;
    }

    public int[][] getPattern()
    {
        return pattern;
    }

    public LocationType getLocation()
    {
        return location;
    }
}
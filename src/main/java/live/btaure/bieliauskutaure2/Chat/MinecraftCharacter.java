package live.btaure.bieliauskutaure2.Chat;

public class MinecraftCharacter
{
    private final char character;
    private final int width;

    public MinecraftCharacter(char character, int width)
    {
        this.character = character;
        this.width = width;
    }

    public char getCharacter()
    {
        return character;
    }

    public int getWidth()
    {
        return width;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof MinecraftCharacter && ((MinecraftCharacter) other).getCharacter() == this.getCharacter())
            return true;
        else return other instanceof Character && (Character) other == this.getCharacter();
    }

    public MapFont.CharacterSprite asCharacterSprite()
    {
        return new MapFont.CharacterSprite(width, 0, new boolean[0]);
    }

    @Override
    public String toString()
    {
        return String.valueOf(character);
    }
}
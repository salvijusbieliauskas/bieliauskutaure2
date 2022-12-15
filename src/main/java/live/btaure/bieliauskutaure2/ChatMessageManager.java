package live.btaure.bieliauskutaure2;

import live.btaure.bieliauskutaure2.Helpers.Wrappers.Result;
import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import live.btaure.bieliauskutaure2.Participants.Logger;
import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.map.MapFont;
import org.bukkit.map.MinecraftFont;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageManager
{
    private static ChatMessageManager chatMessageManagerInstance = null;
    private static final int lineLengthPx = 320;
    private static final MinecraftCharacter borderHorizontal = new MinecraftCharacter('═',8);
    private static final MinecraftCharacter borderVertical = new MinecraftCharacter('║',7);
    private static final MinecraftCharacter borderTopLeft = new MinecraftCharacter('╔',8);
    private static final MinecraftCharacter borderTopRight = new MinecraftCharacter('╗',7);
    private static final MinecraftCharacter borderBottomLeft = new MinecraftCharacter('╚',8);
    private static final MinecraftCharacter borderBottomRight = new MinecraftCharacter('╝',7);
    /*private static final MinecraftCharacter filler9 = new MinecraftCharacter('▒',9);
    private static final MinecraftCharacter filler7 = new MinecraftCharacter('░',7);*/
    //private static final MinecraftCharacter filler6 = new MinecraftCharacter('■',5);
    private static final MinecraftCharacter filler4 = new MinecraftCharacter('▌',4);
    //private static final int scuffedLength = 1;
    //private static final MinecraftCharacter filler2 = new MinecraftCharacter('|',1);
    private static final List<MinecraftCharacter> lithuanianLetters = new ArrayList<>(){{
        add(new MinecraftCharacter('Ą',5));
        add(new MinecraftCharacter('ą',5));
        add(new MinecraftCharacter('Č',5));
        add(new MinecraftCharacter('č',5));
        add(new MinecraftCharacter('Ę',5));
        add(new MinecraftCharacter('ę',5));
        add(new MinecraftCharacter('Ė',5));
        add(new MinecraftCharacter('ė',5));
        add(new MinecraftCharacter('Į',3));
        add(new MinecraftCharacter('į',2));
        add(new MinecraftCharacter('Š',5));
        add(new MinecraftCharacter('š',5));
        add(new MinecraftCharacter('Ų',5));
        add(new MinecraftCharacter('ų',5));
        add(new MinecraftCharacter('Ū',5));
        add(new MinecraftCharacter('ū',5));
        add(new MinecraftCharacter('Ž',5));
        add(new MinecraftCharacter('ž',5));
    }};
    private static final String captionText = "MC Bieliauskų Taurė 2";
    public final MinecraftFont customFont = new MinecraftFont();
    public List<MinecraftCharacter> getLithuanianLetters()
    {
        return lithuanianLetters;
    }
    private ChatMessageManager()
    {
        customFont.setChar(borderHorizontal.getCharacter(),borderHorizontal.asCharacterSprite());
        customFont.setChar(borderVertical.getCharacter(),borderVertical.asCharacterSprite());
        customFont.setChar(borderTopLeft.getCharacter(),borderTopLeft.asCharacterSprite());
        customFont.setChar(borderTopRight.getCharacter(),borderTopRight.asCharacterSprite());
        customFont.setChar(borderBottomLeft.getCharacter(),borderBottomLeft.asCharacterSprite());
        customFont.setChar(borderBottomRight.getCharacter(),borderBottomRight.asCharacterSprite());
        /*customFont.setChar(filler9.getCharacter(), filler9.asCharacterSprite());
        customFont.setChar(filler7.getCharacter(),filler7.asCharacterSprite());*/
        customFont.setChar(filler4.getCharacter(), filler4.asCharacterSprite());
        //customFont.setChar(filler6.getCharacter(),filler6.asCharacterSprite());
        for(MinecraftCharacter character : lithuanianLetters)
        {
            customFont.setChar(character.getCharacter(),character.asCharacterSprite());
        }

    }
    public static ChatMessageManager getInstance()
    {
        if(chatMessageManagerInstance == null)
            chatMessageManagerInstance = new ChatMessageManager();
        return chatMessageManagerInstance;
    }
    private StringBuilder insert(StringBuilder stringBuilder, char symbol, ChatColor fillerColor, int fillerNum, boolean bold, int toInvert, int leftExclusionSize, int rightExclusionSize, boolean sizeTest, int addedLeft)
    {
        StringBuilder newBuilder = new StringBuilder(stringBuilder);
        int offset;
        if (fillerNum % 2 == 0)
        {
            offset = leftExclusionSize + 2;
        }
        else
            offset = newBuilder.length() - rightExclusionSize;
        if(toInvert>0)
        {
            if(!bold)
            {
                newBuilder.insert(offset, fillerColor);
                newBuilder.insert(offset, ChatColor.RESET);
                newBuilder.insert(offset, symbol);
                newBuilder.insert(offset, ChatColor.BOLD);
                if (fillerNum % 2 == 0)
                    addedLeft+=6;
            }
            else
            {
                newBuilder.insert(offset, ChatColor.BOLD);
                newBuilder.insert(offset, fillerColor);
                newBuilder.insert(offset, symbol);
                newBuilder.insert(offset, fillerColor);
                newBuilder.insert(offset, ChatColor.RESET);
                if (fillerNum % 2 == 0)
                    addedLeft+=8;
            }
        }
        else
            newBuilder.insert(offset, symbol);
        if (fillerNum % 2 == 0)
            addedLeft++;
        if(sizeTest)
        {
            newBuilder.insert(newBuilder.length() - rightExclusionSize, ChatColor.RESET);
            newBuilder.insert(leftExclusionSize + 2 + addedLeft, ChatColor.RESET);
        }
        return newBuilder;
    }
    private Result<String> fill(String string, char symbol, int leftExclusionSize, int rightExclusionSize, int widthPx, ChatColor fillerColor, boolean bold)
    {
        if(getLength(string)>widthPx)
            return new Result<String>(null,false);
        StringBuilder filled = new StringBuilder(string);
        int toInvert = 0;//if bold == true, this number is the amount of characters to NOT bold.
        if(bold)
        {
            int boldLength = getLength(ChatColor.BOLD+String.valueOf(symbol));
            /*if(symbol == filler4.getCharacter())
                toInvert = boldLength-((widthPx-getLength(filled.toString())+scuffedLength)%boldLength);
            else*/
            toInvert = boldLength-((widthPx-getLength(filled.toString()))%boldLength);
            if(toInvert==boldLength)
                toInvert = 0;
            Logger.getInstance().info("bold length: "+String.valueOf(boldLength));
            Logger.getInstance().info("to invert: "+String.valueOf(toInvert));
            Logger.getInstance().info("(widthPx-getLength(filled.toString())+scuffedLength): "+(widthPx-getLength(filled.toString())));
        }
        else
        {
            int length = getLength(String.valueOf(symbol));
            toInvert = (widthPx-getLength(filled.toString()))%length;
            Logger.getInstance().info(String.valueOf(toInvert));
        }
        int borderNum = 0;
        if(bold)
            filled.insert(leftExclusionSize,ChatColor.BOLD);
        filled.insert(leftExclusionSize,fillerColor);
        filled.insert(filled.length() - rightExclusionSize,fillerColor);
        if(bold)
            filled.insert(filled.length() - rightExclusionSize,ChatColor.BOLD);
        if(bold)
            leftExclusionSize+=2;
        int addedLeft = 0;
        do
        {
            StringBuilder inserted = insert(filled, symbol,fillerColor,borderNum,bold,toInvert,leftExclusionSize,rightExclusionSize,false,addedLeft);
            toInvert--;
            if (borderNum % 2 == 0)
            {
                addedLeft += inserted.length()-filled.length();
            }
            filled = inserted;
            borderNum++;

        } while (getLength(insert(filled, symbol,fillerColor,borderNum+1,bold,toInvert,leftExclusionSize,rightExclusionSize,true,addedLeft).toString()) <= widthPx);
        filled.insert(filled.length() - rightExclusionSize,ChatColor.RESET);
        filled.insert(leftExclusionSize+2+addedLeft,ChatColor.RESET);
        return new Result<String>(filled.toString(),true);
    }
    private Result<String[]> addBorder(List<String> message, int widthPx, ChatColor borderColor, ChatColor captionColor)
    {
        String[] bordered = new String[message.size()+2];
        Result<String> topFillResult = fill(borderColor+""+borderTopLeft+""+captionColor+""+ChatColor.BOLD+captionText+borderColor+borderTopRight, borderHorizontal.getCharacter(), 3,3, widthPx, borderColor,false);
        if(!topFillResult.isSuccessful())
            return new Result<>(null,false);
        widthPx = getLength(topFillResult.getResult());
        Result<String> bottomFillResult = fill(borderColor+""+borderBottomLeft+""+borderColor+borderBottomRight,borderHorizontal.getCharacter(),3,3,widthPx, borderColor,false);
        if(!bottomFillResult.isSuccessful())
            return new Result<>(null,false);
        bordered[0] = topFillResult.getResult();
        bordered[bordered.length-1] = bottomFillResult.getResult();
        for(int x = 0; x < message.size();x++)
        {
            bordered[x+1] = borderColor+""+borderVertical+ChatColor.RESET+message.get(x)+ChatColor.RESET+borderColor+borderVertical;
            if(getLength(bordered[x+1])>lineLengthPx)
                return new Result<String[]>(null,false);
        }
        return new Result<String[]>(bordered, true);
    }
    private char getNextChar(String str, int index)
    {
        for(int x = index+1; x < str.length();)
        {
            if(str.charAt(x)=='§')
                x+=2;
            else
                return str.charAt(x);
        }
        return '§';
    }
    private boolean isLithuanianLetter(char character)
    {
        for(MinecraftCharacter mcc : lithuanianLetters)
        {
            if(mcc.getCharacter() == character)
                return true;
        }
        return false;
    }
    public int getLength(String str)
    {
        float sizeMod = 0;
        for(int x = 0; x < str.length();x++)
        {
            if(str.charAt(x) == '§' && x != str.length()-1)
            {
                sizeMod -= customFont.getWidth(str.charAt(x) + String.valueOf(str.charAt(x + 1))) + 1;
                if(str.charAt(x+1) == 'l')
                {
                    int index = str.indexOf('§',x+1);
                    if(index == -1)
                    {
                        index = str.length();
                    }
                    for(int y = x+2; y < index;y++)
                    {
                        if(isLithuanianLetter(str.charAt(y)))
                            sizeMod+=0.5f;
                        else
                            sizeMod++;
                    }
                }
            }
        }

        return customFont.getWidth(str)+Math.round(sizeMod+1);//not a fucking clue kodel reikia 1 pridet
    }
    public Result<String[]> constructMessage(List<String> message, boolean border, int height, int maxWidth)
    {
        int width = Math.min(maxWidth, lineLengthPx);
        height = Math.max(message.size(), height);
        List<String> clonedMessage = new ArrayList<>(message);
        int sizeDiff = height- message.size();
        for(int x = 0; x < sizeDiff;x++)
        {
            if(x%2==0)
                clonedMessage.add(0,"");
            else
                clonedMessage.add("");
        }
        String[] constructedMessage;
        if(border)
        {
            Result<String[]> addBorderResult = addBorder(clonedMessage,width, ChatColor.GOLD,ChatColor.RED);
            if(!addBorderResult.isSuccessful())
                return new Result<String[]>(null,false);
            constructedMessage = addBorderResult.getResult();
        }
        else
        {
            String[] tempArray = new String[clonedMessage.size()];
            constructedMessage = clonedMessage.toArray(tempArray);
        }
        if(border)
            width = getLength(constructedMessage[0]);
        for(int x = border?1:0;x < (border?constructedMessage.length-1:constructedMessage.length);x++)
        {
            Result<String> fillResult =  fill(constructedMessage[x], filler4.getCharacter(),border?5:0,border?3:0,width, ChatColor.GRAY,true);
            if(!fillResult.isSuccessful())
                return new Result<String[]>(null,false);
            else
                constructedMessage[x] = fillResult.getResult();
        }
        return new Result<String[]>(constructedMessage,true);
    }
    private String joinMessage(String[] splitMessage)
    {
        StringBuilder sb = new StringBuilder();
        for(String message : splitMessage)
        {
            Logger.getInstance().info(message);
            Logger.getInstance().info("size: "+getLength(message));
            sb.append(message);
            sb.append('\n');
        }
        return sb.toString();
    }
    public boolean broadcastMessage(String message, Sound soundToPlay)
    {
        return broadcastMessage(new ArrayList<String>(){{add(message);}},soundToPlay);
    }
    public boolean broadcastMessage(List<String> message, Sound soundToPlay)
    {
        Result<String[]> constructedMessageResult = constructMessage(message,true, message.size() + 2,lineLengthPx);
        if(!constructedMessageResult.isSuccessful())
            return false;
        String joinedMessage = joinMessage(constructedMessageResult.getResult());
        for(BTPlayer player : PlayerManager.getInstance().getBTPlayers())
        {
            if(!player.isOnline())
                continue;
            player.getPlayer().sendMessage(joinedMessage);
            if(soundToPlay!=null)
                SoundManager.getInstance().playSound(player,soundToPlay);
        }
        return true;
    }
    public boolean sendMessage(BTPlayer player, String message, Sound soundToPlay)
    {
        return sendMessage(player, new ArrayList<String>(){{add(message);}},soundToPlay);
    }
    public boolean sendMessage(BTPlayer player, List<String> message, Sound soundToPlay)
    {
        if(!player.isOnline())
            return false;
        Result<String[]> constructedMessageResult = constructMessage(message,true, message.size() + 2,lineLengthPx);
        if(!constructedMessageResult.isSuccessful())
            return false;
        String joinedMessage = joinMessage(constructedMessageResult.getResult());
        player.getPlayer().sendMessage(joinedMessage);
        if(soundToPlay!=null)
            SoundManager.getInstance().playSound(player,soundToPlay);
        return true;
    }
}

class MinecraftCharacter
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
        if(other instanceof MinecraftCharacter && ((MinecraftCharacter)other).getCharacter() == this.getCharacter())
            return true;
        else return other instanceof Character && (Character) other == this.getCharacter();
    }
    public MapFont.CharacterSprite asCharacterSprite()
    {
        return new MapFont.CharacterSprite(width,0,new boolean[0]);
    }
    @Override
    public String toString()
    {
        return String.valueOf(character);
    }
}
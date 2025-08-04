package live.btaure.bieliauskutaure2.Chat;

import live.btaure.bieliauskutaure2.BieliauskuTaure2;
import live.btaure.bieliauskutaure2.Helpers.Wrappers.Result;
import live.btaure.bieliauskutaure2.Logger;
import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import live.btaure.bieliauskutaure2.SoundManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageManager
{
    private static final int lineLengthPx = 320;
    private static final MinecraftCharacter borderHorizontal = new MinecraftCharacter('═', 8);
    private static final MinecraftCharacter borderVertical = new MinecraftCharacter('║', 7);
    private static final MinecraftCharacter borderTopLeft = new MinecraftCharacter('╔', 8);
    private static final MinecraftCharacter borderTopRight = new MinecraftCharacter('╗', 7);
    private static final MinecraftCharacter borderBottomLeft = new MinecraftCharacter('╚', 8);
    private static final MinecraftCharacter borderBottomRight = new MinecraftCharacter('╝', 7);
    private static final ChatColor constantBorderColor = ChatColor.GOLD;
    private static final ChatColor constantFillerColor = ChatColor.DARK_AQUA;
    private static final ChatColor constantCaptionColor = ChatColor.RED;
    private static final MinecraftCharacter whatCharacter = new MinecraftCharacter(ChatColor.COLOR_CHAR, 5);
    /*private static final MinecraftCharacter filler9 = new MinecraftCharacter('▒',9);
    private static final MinecraftCharacter filler7 = new MinecraftCharacter('░',7);*/
    //private static final MinecraftCharacter filler6 = new MinecraftCharacter('■',5);
    private static final MinecraftCharacter filler4 = new MinecraftCharacter('▌', 4);
    //private static final int scuffedLength = 1;
    //private static final MinecraftCharacter filler2 = new MinecraftCharacter('|',1);
    private static final List<MinecraftCharacter> lithuanianLetters = new ArrayList<>()
    {{
        add(new MinecraftCharacter('Ą', 5));
        add(new MinecraftCharacter('ą', 5));
        add(new MinecraftCharacter('Č', 5));
        add(new MinecraftCharacter('č', 5));
        add(new MinecraftCharacter('Ę', 5));
        add(new MinecraftCharacter('ę', 5));
        add(new MinecraftCharacter('Ė', 5));
        add(new MinecraftCharacter('ė', 5));
        add(new MinecraftCharacter('Į', 3));
        add(new MinecraftCharacter('į', 2));
        add(new MinecraftCharacter('Š', 5));
        add(new MinecraftCharacter('š', 5));
        add(new MinecraftCharacter('Ų', 5));
        add(new MinecraftCharacter('ų', 5));
        add(new MinecraftCharacter('Ū', 5));
        add(new MinecraftCharacter('ū', 5));
        add(new MinecraftCharacter('Ž', 5));
        add(new MinecraftCharacter('ž', 5));
    }};
    private static final String captionText = "MC Bieliauskų Taurė 2";
    private static ChatMessageManager chatMessageManagerInstance = null;
    public final MinecraftFont customFont = new MinecraftFont();

    private ChatMessageManager()
    {
        customFont.setChar(borderHorizontal.getCharacter(), borderHorizontal.asCharacterSprite());
        customFont.setChar(borderVertical.getCharacter(), borderVertical.asCharacterSprite());
        customFont.setChar(borderTopLeft.getCharacter(), borderTopLeft.asCharacterSprite());
        customFont.setChar(borderTopRight.getCharacter(), borderTopRight.asCharacterSprite());
        customFont.setChar(borderBottomLeft.getCharacter(), borderBottomLeft.asCharacterSprite());
        customFont.setChar(borderBottomRight.getCharacter(), borderBottomRight.asCharacterSprite());
        /*customFont.setChar(filler9.getCharacter(), filler9.asCharacterSprite());
        customFont.setChar(filler7.getCharacter(),filler7.asCharacterSprite());*/
        customFont.setChar(filler4.getCharacter(), filler4.asCharacterSprite());
        //customFont.setChar(filler6.getCharacter(),filler6.asCharacterSprite());
        customFont.setChar(whatCharacter.getCharacter(), whatCharacter.asCharacterSprite());
        for (MinecraftCharacter character : lithuanianLetters)
        {
            customFont.setChar(character.getCharacter(), character.asCharacterSprite());
        }

    }

    public static ChatMessageManager getInstance()
    {
        if (chatMessageManagerInstance == null)
            chatMessageManagerInstance = new ChatMessageManager();
        return chatMessageManagerInstance;
    }

    public List<MinecraftCharacter> getLithuanianLetters()
    {
        return lithuanianLetters;
    }

    private boolean isBold(String string, int index)
    {
        //find last symbol looking backwards, if it's the bold symbol return true.
        for (int x = index - 1; x >= 0; x--)
        {
            if (string.charAt(x) == '§')
            {
                return string.charAt(x + 1) == 'l';
            }
        }
        return false;
    }

    private int getCharLength(String string)
    {
        //find last symbol looking backwards, if it's the bold symbol return true.
        int sizeMod = 0;
        for (int x = 0; x < string.length(); x++)
        {
            if (string.charAt(x) == '§' && x != string.length() - 1)
            {
                sizeMod -= 2;
            }
        }
        return string.length() + sizeMod;
    }

    private String strip(String string)
    {
        StringBuilder stringBuilder = new StringBuilder(string);
        for (int x = 0; x < stringBuilder.length(); x++)
        {
            if (stringBuilder.charAt(x) == '§')
            {
                stringBuilder.delete(x, x + 2);
                x--;
            }
        }
        return stringBuilder.toString();
    }

    private boolean patternFits(String[] originalMessage, int[][] pattern, boolean hasBorder, LocationType location)
    {
        int xSize = pattern[0].length;
        int ySize = pattern.length;
        if ((hasBorder ? -2 : 0) + originalMessage.length < ySize)
            return false;
        for (int x = hasBorder ? 1 : 0; x < (hasBorder ? originalMessage.length - 1 : originalMessage.length); x++)
        {
            int yStart = Integer.MAX_VALUE;
            int yEnd = Integer.MAX_VALUE;
            if (location.equals(LocationType.LEFT))
            {
                yStart = (hasBorder ? 1 : 0);
                yEnd = yStart + xSize + 1;
            } else if (location.equals(LocationType.RIGHT))
            {
                yEnd = (hasBorder ? strip(originalMessage[x]).length() - 1 : strip(originalMessage[x]).length());
                yStart = yEnd - xSize - 1;
            }
            if (countChars(strip(originalMessage[x]).substring(yStart, yEnd), filler4.getCharacter()) < xSize)
                return false;
        }
        return true;
    }

    private Result<String[]> insertPattern(String[] originalMessage, ChatColor originalColor, boolean hasBorder, ChatPattern pattern)
    {
        int xSize = pattern.getPattern()[0].length;
        int ySize = pattern.getPattern().length;
        String[] modifiedMessage = originalMessage.clone();
        if (!patternFits(originalMessage, pattern.getPattern(), hasBorder, pattern.getLocation()))
        {
            return new Result<String[]>(null, false);
        }

        for (int x = hasBorder ? 1 : 0; x < (hasBorder ? originalMessage.length - 1 : originalMessage.length); x++)
        {
            int yStart = Integer.MAX_VALUE;
            int yEnd = Integer.MAX_VALUE;
            int yModifier = Integer.MAX_VALUE;
            int lastIndex = Integer.MAX_VALUE;
            if (pattern.getLocation().equals(LocationType.LEFT))
            {
                yStart = 0;
                yEnd = xSize;
                yModifier = 1;
                lastIndex = -1;
            } else if (pattern.getLocation().equals(LocationType.RIGHT))
            {
                yStart = xSize - 1;
                yEnd = -1;
                lastIndex = modifiedMessage[x].length() - 1;
                yModifier = -1;
            }
            for (int y = yStart; y != yEnd; )
            {
                if (pattern.getLocation().equals(LocationType.LEFT))
                    lastIndex = getNextChar(modifiedMessage[x], lastIndex);
                else if (pattern.getLocation().equals(LocationType.RIGHT))
                    lastIndex = getPreviousChar(modifiedMessage[x], lastIndex);
                if (lastIndex == -1)
                    return new Result<String[]>(null, false);
                if (modifiedMessage[x].charAt(lastIndex) == filler4.getCharacter())
                {
                    if (pattern.getPattern()[hasBorder ? x - 1 : x][y] != 0)
                    {
                        String changedColor = setColorAtIndex(modifiedMessage[x], lastIndex, originalColor, getColorByNumber(pattern.getPattern()[hasBorder ? x - 1 : x][y]), isBold(modifiedMessage[x], lastIndex), isBold(modifiedMessage[x], lastIndex));
                        if (pattern.getLocation().equals(LocationType.LEFT))
                            lastIndex += (changedColor.length() - modifiedMessage[x].length());
                        modifiedMessage[x] = changedColor;
                    }
                    y += yModifier;
                }
            }

        }


        return new Result<String[]>(modifiedMessage, true);
    }

    private ChatColor getColorByNumber(int number)
    {
        switch (number)
        {
            case 1:
                return ChatColor.RED;
            default:
                return ChatColor.WHITE;
        }
    }

    private String setColorAtIndex(String string, int index, ChatColor originalColor, ChatColor newColor, boolean newBold, boolean originalBold)
    {
        StringBuilder stringBuilder = new StringBuilder(string);
        //add after effects.
        if (originalBold)
            stringBuilder.insert(index + 1, ChatColor.BOLD);
        stringBuilder.insert(index + 1, originalColor);
        stringBuilder.insert(index + 1, ChatColor.RESET);
        if (newBold)
            stringBuilder.insert(index, ChatColor.BOLD);
        stringBuilder.insert(index, newColor);
        stringBuilder.insert(index, ChatColor.RESET);
        return stringBuilder.toString();
    }

    private StringBuilder insert(StringBuilder stringBuilder, char symbol, ChatColor fillerColor, int fillerNum, boolean bold, int toInvert, int leftExclusionSize, int rightExclusionSize, boolean sizeTest, int addedLeft)
    {
        StringBuilder newBuilder = new StringBuilder(stringBuilder);
        int offset;
        if (fillerNum % 2 == 0)
        {
            offset = leftExclusionSize + 2;
        } else
            offset = newBuilder.length() - rightExclusionSize;
        if (toInvert > 0)
        {
            if (!bold)
            {
                newBuilder.insert(offset, fillerColor);
                newBuilder.insert(offset, ChatColor.RESET);
                newBuilder.insert(offset, symbol);
                newBuilder.insert(offset, ChatColor.BOLD);
                if (fillerNum % 2 == 0)
                    addedLeft += 6;
            } else
            {
                newBuilder.insert(offset, ChatColor.BOLD);
                newBuilder.insert(offset, fillerColor);
                newBuilder.insert(offset, symbol);
                newBuilder.insert(offset, fillerColor);
                newBuilder.insert(offset, ChatColor.RESET);
                if (fillerNum % 2 == 0)
                    addedLeft += 8;
            }
        } else
            newBuilder.insert(offset, symbol);
        if (fillerNum % 2 == 0)
            addedLeft++;
        if (sizeTest)
        {
            newBuilder.insert(newBuilder.length() - rightExclusionSize, ChatColor.RESET);
            newBuilder.insert(leftExclusionSize + 2 + addedLeft, ChatColor.RESET);
        }
        return newBuilder;
    }

    private Result<String> fill(String string, char symbol, int leftExclusionSize, int rightExclusionSize, int widthPx, ChatColor fillerColor, boolean bold)
    {
        if (getLength(string) > widthPx)
            return new Result<String>(null, false);
        StringBuilder filled = new StringBuilder(string);
        int toInvert = 0;//if bold == true, this number is the amount of characters to NOT bold.
        if (bold)
        {
            int boldLength = getLength(ChatColor.BOLD + String.valueOf(symbol));
            toInvert = boldLength - ((widthPx - getLength(filled.toString())) % boldLength);
            if (toInvert == boldLength)
                toInvert = 0;
        } else
        {
            int length = getLength(String.valueOf(symbol));
            toInvert = (widthPx - getLength(filled.toString())) % length;
        }
        int borderNum = 0;
        if (bold)
            filled.insert(leftExclusionSize, ChatColor.BOLD);
        filled.insert(leftExclusionSize, fillerColor);
        filled.insert(filled.length() - rightExclusionSize, fillerColor);
        if (bold)
            filled.insert(filled.length() - rightExclusionSize, ChatColor.BOLD);
        if (bold)
            leftExclusionSize += 2;
        int addedLeft = 0;
        do
        {
            StringBuilder inserted = insert(filled, symbol, fillerColor, borderNum, bold, toInvert, leftExclusionSize, rightExclusionSize, false, addedLeft);
            toInvert--;
            if (borderNum % 2 == 0)
            {
                addedLeft += inserted.length() - filled.length();
            }
            filled = inserted;
            borderNum++;

        } while (getLength(insert(filled, symbol, fillerColor, borderNum + 1, bold, toInvert, leftExclusionSize, rightExclusionSize, true, addedLeft).toString()) <= widthPx);
        filled.insert(filled.length() - rightExclusionSize, ChatColor.RESET);
        filled.insert(leftExclusionSize + 2 + addedLeft, ChatColor.RESET);
        return new Result<String>(filled.toString(), true);
    }

    private Result<String[]> addBorder(String[] message, int widthPx, ChatColor borderColor, ChatColor captionColor)
    {
        String[] bordered = new String[message.length + 2];
        Result<String> topFillResult = fill(borderColor + "" + borderTopLeft + captionColor + ChatColor.BOLD + captionText + borderColor + borderTopRight, borderHorizontal.getCharacter(), 3, 3, widthPx, borderColor, false);
        if (!topFillResult.isSuccessful())
            return new Result<>(null, false);
        widthPx = getLength(topFillResult.getResult());
        Result<String> bottomFillResult = fill(borderColor + "" + borderBottomLeft + borderColor + borderBottomRight, borderHorizontal.getCharacter(), 3, 3, widthPx, borderColor, false);
        if (!bottomFillResult.isSuccessful())
            return new Result<>(null, false);
        bordered[0] = topFillResult.getResult();
        bordered[bordered.length - 1] = bottomFillResult.getResult();
        for (int x = 0; x < message.length; x++)
        {
            bordered[x + 1] = borderColor + "" + borderVertical + ChatColor.RESET + message[x] + ChatColor.RESET + borderColor + borderVertical;
            if (getLength(bordered[x + 1]) > lineLengthPx)
                return new Result<String[]>(null, false);
        }
        return new Result<String[]>(bordered, true);
    }

    private int getNextChar(String str, int index)
    {
        for (int x = index + 1; x < str.length(); )
        {
            if (str.charAt(x) == '§')
                x += 2;
            else
                return x;
        }
        return -1;
    }

    private int getPreviousChar(String str, int index)
    {
        for (int x = index - 1; x >= 0; )
        {
            if (str.charAt(x - 1) == '§')
                x -= 2;
            else if (str.charAt(x) == '§')
                x -= 1;
            else
                return x;
        }
        return -1;
    }

    private int countChars(String str, char character)
    {
        int count = 0;
        for (int x = 0; x < str.length(); x++)
        {
            if (str.charAt(x) == character)
                count++;
        }
        return count;
    }

    private boolean isLithuanianLetter(char character)
    {
        for (MinecraftCharacter mcc : lithuanianLetters)
        {
            if (mcc.getCharacter() == character)
                return true;
        }
        return false;
    }

    public int getLength(String str)
    {
        float sizeMod = 0;
        for (int x = 0; x < str.length(); x++)
        {
            if (str.charAt(x) == '§' && x != str.length() - 1)
            {
                sizeMod -= customFont.getWidth(str.charAt(x) + String.valueOf(str.charAt(x + 1))) + 1;
                if (str.charAt(x + 1) == 'l')
                {
                    int index = str.indexOf('§', x + 1);
                    if (index == -1)
                    {
                        index = str.length();
                    }
                    for (int y = x + 2; y < index; y++)
                    {
                        sizeMod++;
                    }
                }
            } else if (str.charAt(x) == '(' || str.charAt(x) == ')')
                sizeMod--;
        }

        return customFont.getWidth(str) + Math.round(sizeMod + 1);//not a fucking clue kodel reikia 1 pridet
    }

    private int getLongestStringIndex(String[] stringArr)
    {
        int longest = 0;
        for (int x = 1; x < stringArr.length; x++)
        {
            if (getLength(stringArr[x]) > getLength(stringArr[longest]))
                longest = x;
        }
        return longest;
    }

    private String getLongestString(String[] stringArr)
    {
        return stringArr[getLongestStringIndex(stringArr)];
    }

    public Result<String[]> constructMessage(String[] message, boolean border, int height, int maxWidth, ChatColor borderColor, ChatColor captionColor, ChatColor fillerColor, ChatPattern[] patterns)
    {
        int width = Math.min(maxWidth, lineLengthPx);
        int estimatedTextSize = getLength((border ? String.valueOf(borderVertical.getCharacter()) + borderVertical.getCharacter() : "") + getLongestString(message));
        if (estimatedTextSize > width)
        {
            Logger.getInstance().warning("Failed to construct a message, because the contents were too long.");
            return new Result<String[]>(null, false);
        }
        height = Math.max(message.length, height);
        List<String> clonedMessage = new ArrayList<String>(List.of(message.clone()));
        int sizeDiff = height - message.length;
        for (int x = 0; x < sizeDiff; x++)
        {
            if (x % 2 == 0)
                clonedMessage.add(0, "");
            else
                clonedMessage.add("");
        }
        String[] constructedMessage;
        if (border)
        {
            Result<String[]> addBorderResult = addBorder(clonedMessage.toArray(new String[0]), width, borderColor, captionColor);
            if (!addBorderResult.isSuccessful())
                return new Result<String[]>(null, false);
            constructedMessage = addBorderResult.getResult();
        } else
        {
            String[] tempArray = new String[clonedMessage.size()];
            constructedMessage = clonedMessage.toArray(tempArray);
        }
        if (border)
            width = getLength(constructedMessage[0]);
        for (int x = border ? 1 : 0; x < (border ? constructedMessage.length - 1 : constructedMessage.length); x++)
        {
            Result<String> fillResult = fill(constructedMessage[x], filler4.getCharacter(), border ? 5 : 0, border ? 3 : 0, width, fillerColor, true);
            if (!fillResult.isSuccessful())
                return new Result<String[]>(null, false);
            else
                constructedMessage[x] = fillResult.getResult();
        }
        for (ChatPattern pattern : patterns)
        {
            Result<String[]> patterInsertResult = insertPattern(constructedMessage, fillerColor, border, pattern);
            if (patterInsertResult.isSuccessful())
                constructedMessage = patterInsertResult.getResult();
            else
            {
                Logger.getInstance().warning("Failed inserting pattern while constructing message.");
                return new Result<String[]>(null, false);
            }
        }


        return new Result<String[]>(constructedMessage, true);
    }

    private String joinMessage(String[] splitMessage)
    {
        StringBuilder sb = new StringBuilder();
        for (String message : splitMessage)
        {
            sb.append(message);
            sb.append('\n');
        }
        return sb.toString();
    }

    private int[] getWordCount(String[][] messages)
    {
        int[] counts = new int[messages.length];
        for (int x = 0; x < messages.length; x++)
        {
            for (String string : messages[x])
            {
                if (!string.equals("") && !string.equals(" "))
                    counts[x] += countChars(string, ' ') + 1;
            }
        }
        return counts;
    }

    public boolean broadcastMessagesWithDelay(String[][] messages, Sound soundToPlay, ChatPattern[] patterns)//TODO: add more overloads
    {
        int[] wordCounts = getWordCount(messages);
        long timeSoFar = 0L;

        for (int x = 0; x < messages.length; x++)
        {
            int finalX = x;
            Bukkit.getScheduler().runTaskLaterAsynchronously(BieliauskuTaure2.getPlugin(BieliauskuTaure2.class), new Runnable()
            {
                @Override
                public void run()
                {
                    broadcastMessage(messages[finalX], soundToPlay, patterns);
                }
            }, (wordCounts[x] * 10L) + timeSoFar);
            timeSoFar += wordCounts[x] * 10L;
        }
        return true;
    }

    public boolean broadcastMessage(String message, Sound soundToPlay, ChatPattern[] patterns)
    {
        return broadcastMessage(new String[]{message}, soundToPlay, patterns);
    }

    public boolean broadcastMessage(String[] message, Sound soundToPlay, ChatPattern[] patterns)
    {
        Result<String[]> constructedMessageResult = constructMessage(message, true, Math.max(message.length + 2, 8), lineLengthPx, constantBorderColor, constantCaptionColor, constantFillerColor, patterns);
        if (!constructedMessageResult.isSuccessful())
            return false;
        String joinedMessage = "\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n" + joinMessage(constructedMessageResult.getResult());
        for (BTPlayer player : PlayerManager.getInstance().getBTPlayers())
        {
            if (!player.isOnline())
                continue;
            player.getPlayer().sendMessage(joinedMessage);
            if (soundToPlay != null)
                SoundManager.getInstance().playSound(player, soundToPlay);
        }
        return true;
    }

    public boolean sendMessage(BTPlayer player, String message, Sound soundToPlay, ChatPattern[] patterns)
    {
        return sendMessage(player, new String[]{message}, soundToPlay, patterns);
    }

    public boolean sendMessage(BTPlayer player, String[] message, Sound soundToPlay, ChatPattern[] patterns)
    {
        if (!player.isOnline())
            return false;
        Result<String[]> constructedMessageResult = constructMessage(message, true, message.length + 2, lineLengthPx, constantBorderColor, constantCaptionColor, constantFillerColor, patterns);
        if (!constructedMessageResult.isSuccessful())
            return false;
        String joinedMessage = joinMessage(constructedMessageResult.getResult());
        player.getPlayer().sendMessage(joinedMessage);
        if (soundToPlay != null)
            SoundManager.getInstance().playSound(player, soundToPlay);
        return true;
    }
}
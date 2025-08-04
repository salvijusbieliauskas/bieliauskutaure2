package live.btaure.bieliauskutaure2.Participants;

/**
 * Enum which represents various ways to register a new player
 */
public enum AddModeType
{
    /**
     * Adds the player without performing any checks
     */
    FORCE,
    /**
     * Checks if the player is already registered and does not add him if he already exists
     */
    CHECK,
    /**
     * Removes previously existing occurrences of the player (if there are any) and adds
     */
    REPLACE
}

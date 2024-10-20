package live.btaure.bieliauskutaure2.Minigames.Data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("ParkourCheckpoint")
public class ParkourCheckpoint implements ConfigurationSerializable
{
    public final int index;
    public final Location teleportTarget;
    public final Location checkpointLocation;
    public final List<PotionEffect> activePotionEffects;
    public final List<Material> bannedBlockTypes;
    public ParkourCheckpoint(int index, Location teleportTarget, Location checkpointLocation, List<PotionEffect> activePotionEffects, List<Material> bannedBlockTypes)
    {
        this.index = index;
        this.teleportTarget = teleportTarget;
        this.checkpointLocation = checkpointLocation;
        this.activePotionEffects = activePotionEffects;
        this.bannedBlockTypes = bannedBlockTypes;
    }
    public ParkourCheckpoint(Map<String, Object> map)
    {
        this.index = (Integer) map.get("index");
        this.teleportTarget = (Location) map.get("target");
        this.checkpointLocation = (Location) map.get("location");
        this.activePotionEffects = (ArrayList<PotionEffect>) map.get("potionEffects");
        ArrayList<String> bannedMaterialsString = (ArrayList<String>) map.get("bannedBlocks");
        this.bannedBlockTypes = new ArrayList<>();
        for(String str : bannedMaterialsString)
        {
            this.bannedBlockTypes.add(Material.valueOf(str));
        }
    }
    @Override
    public @NotNull Map<String, Object> serialize()
    {
        HashMap<String,Object> serialized = new HashMap<>();
        serialized.put("index",index);
        serialized.put("target",teleportTarget);
        serialized.put("location",checkpointLocation);
        serialized.put("potionEffects",activePotionEffects);
        ArrayList<String> serializableBannedBlockTypes = new ArrayList<>();
        if(!(this.bannedBlockTypes ==null))
        {
            for(Material mat : bannedBlockTypes)
            {
                serializableBannedBlockTypes.add(mat.toString());
            }
        }
        serialized.put("bannedBlocks",serializableBannedBlockTypes);

        return serialized;
    }
    public static ParkourCheckpoint valueOf(Map<String, Object> map)
    {
        return new ParkourCheckpoint(map);
    }
    public static ParkourCheckpoint deserialize(Map<String, Object> map)
    {
        return new ParkourCheckpoint(map);
    }
}
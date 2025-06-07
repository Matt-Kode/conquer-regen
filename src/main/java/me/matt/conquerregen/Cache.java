package me.matt.conquerregen;

import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {

    public static Map<String, EntityType> entities = new HashMap<>();
    public static Map<String, ItemStack[]> armorstands = new HashMap<>();
    public static List<BlockState> blocks = new ArrayList<>();

}

package com.K3V1N32.AcorusHousing;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * AcorusHousing listener
 * @author K3V1N32
 */
public class AcorusHousingBlockListener extends BlockListener {
    private final AcorusHousing plugin;

    public AcorusHousingBlockListener(final AcorusHousing plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getBlock();
    }

    @Override
    public void onBlockCanBuild(BlockCanBuildEvent event) {
        Material mat = event.getMaterial();

        if (mat.equals(Material.CACTUS)) {
            event.setBuildable(true);
        }
    }
}
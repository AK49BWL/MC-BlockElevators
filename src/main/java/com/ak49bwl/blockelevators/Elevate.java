package com.ak49bwl.blockelevators;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Elevate implements Listener {

    // Changeable Vars
    int maxElev = 64; // Max elevation change
    Material block = Material.IRON_BLOCK; // Elevator Block


    @EventHandler
    public void ElevDown(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);

        if (p.isSneaking() && b.getType().equals(block)) {
            b = b.getRelative(BlockFace.DOWN, 3);
            int i = maxElev;
            while (i > 0 && !(b.getType() == block && b.getRelative(BlockFace.UP).getType().equals(Material.AIR) && b.getRelative(BlockFace.UP, 2).getType().equals(Material.AIR)) ) {
                i--;
                b = b.getRelative(BlockFace.DOWN);
            }
            if (i > 0) {
                Location l = p.getLocation();
                l.setY(l.getY() - maxElev - 3 + i);
                p.teleport(l);
            }
        }
    }

    @EventHandler
    public void ElevUp(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Block b = e.getTo().getBlock().getRelative(BlockFace.DOWN);
        if (e.getFrom().getY() < e.getTo().getY() && b.getType().equals(block)) {
            b = b.getRelative(BlockFace.UP, 3);
            int i = maxElev;
            while (i > 0 && !(b.getType() == block && b.getRelative(BlockFace.UP).getType().equals(Material.AIR) && b.getRelative(BlockFace.UP, 2).getType().equals(Material.AIR)) ) {
                i--;
                b = b.getRelative(BlockFace.UP);
            }
            if (i > 0) {
                Location l = p.getLocation();
                l.setY(l.getY() + maxElev + 3 - i);
                p.teleport(l);
            }
        }
    }
}
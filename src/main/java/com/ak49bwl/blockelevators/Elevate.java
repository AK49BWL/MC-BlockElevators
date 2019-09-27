package com.ak49bwl.blockelevators;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Elevate implements Listener {

    // Configurable Vars
    private final int MAX_ELEV = 64; // Max elevation change
    private final Material TRIGGER_TYPE = Material.IRON_BLOCK; // Elevator Block

    //Constants named for readability
    private final int ABOVE = 1;
    private final int BELOW = -1;

    @EventHandler
    public void ElevDown(PlayerToggleSneakEvent event) {
        if(! event.isSneaking() ) return; //person is coming out of sneak, ignore
        //this will still execute if you step on a slab, stair, piston push up, explosion, etc

        Block underneath = event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
        if(! underneath.getType().equals(TRIGGER_TYPE) )return; //not directly above a trigger, ignore

        Location destination = getDestination( event.getPlayer().getLocation(), BELOW );
        if( destination == null ) return; //no valid spot found, ignore

        event.getPlayer().teleport(destination);
    }
    @EventHandler
    public void ElevUp(PlayerMoveEvent event) {
        if( event.getFrom().getY() >= event.getTo().getY() ) return; //ignore falling and horizontal
        //this will still execute if you step on a slab, stair, piston push up, explosion, etc

        Block underneath = event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
        if(! underneath.getType().equals(TRIGGER_TYPE) )return; //not directly above a trigger, ignore

        Location destination = getDestination( event.getPlayer().getLocation(), ABOVE );
        if( destination == null ) return; //no valid spot found, ignore

        event.getPlayer().teleport(destination);
    }
    private Location getDestination( Location from , int directionChanger ){
        Location checkMe;
        for( int searchY = 2; searchY < MAX_ELEV; searchY++ ){ // searchY can't be <2 because it will see the block we're standing on.
            checkMe = from.add( 0, searchY*directionChanger, 0 );
            if( checkMe.getBlock().getType().equals(TRIGGER_TYPE) &&
                checkMe.getBlock().getRelative(BlockFace.UP, 1).getType().equals(Material.AIR) &&
                checkMe.getBlock().getRelative(BlockFace.UP, 2).getType().equals(Material.AIR) ){
                return checkMe.add(0,1,0); //checkMe is the Trigger, return the spot immediately above it
            } else from.subtract( 0, searchY*directionChanger, 0 );
        }
        return null;
    }
}
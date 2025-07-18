package com.natamus.chorusfruitdropsnearby.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChorusEvent {
	private static final CopyOnWriteArrayList<BlockPos> lastChorusBlock = new CopyOnWriteArrayList<BlockPos>();
	private static final HashMap<BlockPos, Date> lastAction = new HashMap<BlockPos, Date>();
	
	public static void onChorusFruit(Level level, Entity entity) {
		if (level.isClientSide) {
			return;
		}
		
		if (!(entity instanceof ItemEntity itemEntity)) {
			return;	
		}

        ItemStack itemstack = itemEntity.getItem();
		if (!(itemstack.getItem().equals(Items.CHORUS_FRUIT))) {
			return;
		}
		
		Date now = new Date();
		
		BlockPos chorusPos = entity.blockPosition();
		BlockPos lowChorusPos = new BlockPos(chorusPos.getX(), 1, chorusPos.getZ());
		for (BlockPos lspos : lastChorusBlock) {
			if (lastAction.containsKey(lspos)) {
				Date lastdate = lastAction.get(lspos);
				long ms = (now.getTime()-lastdate.getTime());
				if (ms > 2000) {
					lastChorusBlock.remove(lspos);
					lastAction.remove(lspos);
					continue;
				}			
			}
			
			if (lowChorusPos.closerThan(new BlockPos(lspos.getX(), 1, lspos.getZ()), 20)) {
				entity.teleportTo(lspos.getX(), lspos.getY()+1, lspos.getZ());
				lastAction.put(lspos.immutable(), now);
			}
		}
	}
	
	public static void onBlockBreak(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if (level.isClientSide) {
			return;
		}
		
		Block block = state.getBlock();
		if (block.equals(Blocks.CHORUS_PLANT)) {
			lastChorusBlock.add(pos);
			lastAction.put(pos, new Date());
		}
	}
}

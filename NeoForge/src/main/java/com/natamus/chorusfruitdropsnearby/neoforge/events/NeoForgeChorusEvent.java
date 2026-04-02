package com.natamus.chorusfruitdropsnearby.neoforge.events;

import com.natamus.chorusfruitdropsnearby.events.ChorusEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class NeoForgeChorusEvent {
	@SubscribeEvent
	public static void onChorusFruitItem(EntityJoinLevelEvent e) {
		ChorusEvent.onChorusFruit(e.getLevel(), e.getEntity());
	}
	
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		ChorusEvent.onBlockBreak(level, e.getPlayer(), e.getPos(), e.getState(), null);
	}
}

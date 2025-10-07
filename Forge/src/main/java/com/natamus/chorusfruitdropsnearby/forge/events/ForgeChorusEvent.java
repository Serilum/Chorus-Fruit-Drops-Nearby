package com.natamus.chorusfruitdropsnearby.forge.events;

import com.natamus.chorusfruitdropsnearby.events.ChorusEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeChorusEvent {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeChorusEvent.class);
	}

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

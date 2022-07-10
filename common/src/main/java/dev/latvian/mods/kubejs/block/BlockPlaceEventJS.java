package dev.latvian.mods.kubejs.block;

import dev.latvian.mods.kubejs.entity.EntityEventJS;
import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.level.LevelJS;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * @author LatvianModder
 */
public class BlockPlaceEventJS extends EntityEventJS {
	public static final EventHandler EVENT = EventHandler.server(BlockPlaceEventJS.class).cancelable().legacy("block.place");

	private final Entity entity;
	private final Level level;
	private final BlockPos pos;
	private final BlockState state;

	public BlockPlaceEventJS(@Nullable Entity entity, Level level, BlockPos pos, BlockState state) {
		this.entity = entity;
		this.level = level;
		this.pos = pos;
		this.state = state;
	}

	@Override
	public LevelJS getLevel() {
		return levelOf(level);
	}

	@Override
	public EntityJS getEntity() {
		return entity == null ? null : entityOf(entity);
	}

	public BlockContainerJS getBlock() {
		return new BlockContainerJS(level, pos) {
			@Override
			public BlockState getBlockState() {
				return state;
			}
		};
	}
}
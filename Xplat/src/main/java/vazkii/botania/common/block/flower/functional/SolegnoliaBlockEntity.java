/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.block.flower.functional;

import com.google.common.collect.MapMaker;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;
import vazkii.botania.common.block.BotaniaFlowerBlocks;

import java.util.Collections;
import java.util.Set;

public class SolegnoliaBlockEntity extends FunctionalFlowerBlockEntity {
	private static final double RANGE = 5;
	private static final double RANGE_MINI = 1;

	private static final Set<SolegnoliaBlockEntity> existingFlowers = Collections.newSetFromMap(new MapMaker().concurrencyLevel(2).weakKeys().makeMap());

	protected SolegnoliaBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public SolegnoliaBlockEntity(BlockPos pos, BlockState state) {
		this(BotaniaFlowerBlocks.SOLEGNOLIA, pos, state);
	}

	@Override
	public void tickFlower() {
		super.tickFlower();

		if (!existingFlowers.contains(this)) {
			existingFlowers.add(this);
		}
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		existingFlowers.remove(this);
	}

	@Override
	public boolean acceptsRedstone() {
		return true;
	}

	public static boolean hasSolegnoliaAround(Entity e) {
		for (var flower : existingFlowers) {
			if (flower.redstoneSignal == 0 && flower.getLevel() == e.getLevel()
					&& flower.getEffectivePos().distToCenterSqr(e.getX(), e.getY(), e.getZ())
							<= flower.getRange() * flower.getRange()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getMaxMana() {
		return 1;
	}

	@Override
	public int getColor() {
		return 0xC99C4D;
	}

	public double getRange() {
		return RANGE;
	}

	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Circle(getEffectivePos(), getRange());
	}

	public static class Mini extends SolegnoliaBlockEntity {
		public Mini(BlockPos pos, BlockState state) {
			super(BotaniaFlowerBlocks.SOLEGNOLIA_CHIBI, pos, state);
		}

		@Override
		public double getRange() {
			return RANGE_MINI;
		}
	}

}

/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.block.flower.generating;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.state.BlockState;

import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.block.BotaniaFlowerBlocks;
import vazkii.botania.common.handler.BotaniaSounds;

public class ThermalilyBlockEntity extends FluidGeneratorBlockEntity {
	public ThermalilyBlockEntity(BlockPos pos, BlockState state) {
		super(BotaniaFlowerBlocks.THERMALILY, pos, state, FluidTags.LAVA, 900, 20, 6000);
	}

	@Override
	public int getColor() {
		return 0xD03C00;
	}

	@Override
	public void doBurnParticles() {
		WispParticleData data = WispParticleData.wisp((float) Math.random() / 6, 0.7F, 0.05F, 0.05F, 1);
		emitParticle(data, 0.5 + Math.random() * 0.2 - 0.1, 0.9 + Math.random() * 0.2 - 0.1, 0.5 + Math.random() * 0.2 - 0.1, 0, (float) Math.random() / 60, 0);
	}

	@Override
	public void playSound() {
		getLevel().playSound(null, getEffectivePos(), BotaniaSounds.thermalily, SoundSource.BLOCKS, 1F, 1F);
	}

	@Override
	public int getMaxMana() {
		return 500;
	}
}

package tfc.vbogenerateditems.client.mixin;

import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {
	@Accessor("colorMap") ItemColors getColorMap();
}

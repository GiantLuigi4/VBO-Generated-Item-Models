package tfc.vbogenerateditems.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.tuples.Pair;
import tfc.vbogenerateditems.client.VBOGeneratedItemsClient;
import tfc.vbogenerateditems.client.VBOModel;
import tfc.vbogenerateditems.client.VBORenderer;

import java.util.HashMap;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	@Inject(at = @At("HEAD"), method = "renderBakedItemModel", cancellable = true)
	public void preRenderItem(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices, CallbackInfo ci) {
		VBORenderer.doRender(model, stack, light, overlay, matrices, vertices, ci);
	}
}

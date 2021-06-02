package tfc.vbogenerateditems.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.vbogenerateditems.client.mixin.ItemRendererAccessor;

import java.awt.*;
import java.util.HashMap;

public class VBORenderer {
	public static void doRender(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices, CallbackInfo ci) {
		if (model instanceof VBOModel) {
//			if (true) return;
			for (int i = 0; i < ((VBOModel) model).getTextures().size(); i++) {
				SpriteIdentifier texture = ((VBOModel) model).getTextures().get(i);
				Sprite sprite = getSprite(texture);
				if (!resolutionVBOMap.containsKey(sprite.getWidth() + ", " + sprite.getHeight())) {
					BufferBuilder bufferBuilder = new BufferBuilder(64);
					bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);
					float sclX =  1f / (float) sprite.getWidth();
					float sclY =  1f / (float) sprite.getHeight();
					float z = 0.5f;
					float zSize = 0.5f / 16;
					for (int x = 0; x < sprite.getWidth(); x++) {
						// Y Axis Lines
						//TODO: normals
						{
							bufferBuilder
									.vertex(1, x * sclX, z + zSize)
									.color(1, 1, 1, 1)
									.texture(0, (x + 1) * sclX)
									.light(1, 1)
									.normal(1, 0.5f, 0)
									.next();
							bufferBuilder
									.vertex(0, x * sclX, z + zSize)
									.color(1, 1, 1, 1)
									.texture(1, (x + 1) * sclX)
									.light(1, 1)
									.normal(1, 0.5f, 0)
									.next();
							bufferBuilder
									.vertex(0, x * sclX, z - zSize)
									.color(1, 1, 1, 1)
									.texture(1, x * sclX)
									.light(1, 1)
									.normal(1, 0.5f, 0)
									.next();
							bufferBuilder
									.vertex(1, x * sclX, z - zSize)
									.color(1, 1, 1, 1)
									.texture(0, x * sclX)
									.light(1, 1)
									.normal(1, 0.5f, 0)
									.next();
						}
						{
							bufferBuilder
									.vertex(1, (x + 1) * sclX, z - zSize)
									.color(1, 1, 1, 1)
									.texture(0, x * sclX)
									.light(1, 1)
									.normal(0.5f, 1, 0)
									.next();
							bufferBuilder
									.vertex(0, (x + 1) * sclX, z - zSize)
									.color(1, 1, 1, 1)
									.texture(1, x * sclX)
									.light(1, 1)
									.normal(0.5f, 1, 0)
									.next();
							bufferBuilder
									.vertex(0, (x + 1) * sclX, z + zSize)
									.color(1, 1, 1, 1)
									.texture(1, (x + 1) * sclX)
									.light(1, 1)
									.normal(0.5f, 1, 0)
									.next();
							bufferBuilder
									.vertex(1, (x + 1) * sclX, z + zSize)
									.color(1, 1, 1, 1)
									.texture(0, (x + 1) * sclX)
									.light(1, 1)
									.normal(0.5f, 1, 0)
									.next();
						}
						{
							bufferBuilder
									.vertex(((sprite.getWidth() - 1) - x) * sclX, 0, z + zSize)
									.color(1, 1, 1, 1)
									.texture((x + 1) * sclX, 0)
									.light(1, 1)
									.normal(1, 1, 0)
									.next();
							bufferBuilder
									.vertex(((sprite.getWidth() - 1) - x) * sclX, 1, z + zSize)
									.color(1, 1, 1, 1)
									.texture((x + 1) * sclX, 1)
									.light(1, 1)
									.normal(1, 1, 0)
									.next();
							bufferBuilder
									.vertex(((sprite.getWidth() - 1) - x) * sclX, 1, z - zSize)
									.color(1, 1, 1, 1)
									.texture(x * sclX, 1)
									.light(1, 1)
									.normal(1, 1, 0)
									.next();
							bufferBuilder
									.vertex(((sprite.getWidth() - 1) - x) * sclX, 0, z - zSize)
									.color(1, 1, 1, 1)
									.texture(x * sclX, 0)
									.light(1, 1)
									.normal(1, 1, 0)
									.next();
						}
						{
							bufferBuilder
									.vertex((((sprite.getWidth() - 1) - x) + 1) * sclX, 0, z - zSize)
									.color(1, 1, 1, 1)
									.texture(x * sclX, 0)
									.light(1, 1)
									.normal(1, 1, 0)
									.next();
							bufferBuilder
									.vertex((((sprite.getWidth() - 1) - x) + 1) * sclX, 1, z - zSize)
									.color(1, 1, 1, 1)
									.texture(x * sclX, 1)
									.light(1, 1)
									.normal(1, 1, 0)
									.next();
							bufferBuilder
									.vertex((((sprite.getWidth() - 1) - x) + 1) * sclX, 1, z + zSize)
									.color(1, 1, 1, 1)
									.texture((x + 1) * sclX, 1)
									.light(1, 1)
									.normal(1, 1, 0)
									.next();
							bufferBuilder
									.vertex((((sprite.getWidth() - 1) - x) + 1) * sclX, 0, z + zSize)
									.color(1, 1, 1, 1)
									.texture((x + 1) * sclX, 0)
									.light(1, 1)
									.normal(1, 1, 0)
									.next();
						}
						//TODO: remove this loop, it is redundant and can be done via making the front and back a single quad instead of sizeX*sizeY quads
//						for (int y = 0; y < sprite.getWidth(); y++) {
//							//Front
//							{
//								bufferBuilder
//										.vertex(((sprite.getWidth() - 1) - x) * sclX, y * sclY, z + zSize)
//										.color(1, 1, 1, 1)
//										.texture(x * sclX, y * sclY)
//										.light(1, 1)
//										.normal(0, 1, 0)
//										.next();
//								bufferBuilder
//										.vertex((((sprite.getWidth() - 1) - x) + 1) * sclX, y * sclY, z + zSize)
//										.color(1, 1, 1, 1)
//										.texture((x + 1) * sclX, y * sclY)
//										.light(1, 1)
//										.normal(0, 1, 0)
//										.next();
//								bufferBuilder
//										.vertex((((sprite.getWidth() - 1) - x) + 1) * sclX, (y + 1) * sclY, z + zSize)
//										.color(1, 1, 1, 1)
//										.texture((x + 1) * sclX, (y + 1) * sclY)
//										.light(1, 1)
//										.normal(0, 1, 0)
//										.next();
//								bufferBuilder
//										.vertex(((sprite.getWidth() - 1) - x) * sclX, (y + 1) * sclY, z + zSize)
//										.color(1, 1, 1, 1)
//										.texture(x * sclX, (y + 1) * sclY)
//										.light(1, 1)
//										.normal(0, 1, 0)
//										.next();
//							}
//							//Back
//							{
//								bufferBuilder
//										.vertex(((sprite.getWidth() - 1) - x) * sclX, (y + 1) * sclY, z - zSize)
//										.color(1, 1, 1, 1)
//										.texture(x * sclX, (y + 1) * sclY)
//										.light(1, 1)
//										.normal(0, 1, 0)
//										.next();
//								bufferBuilder
//										.vertex((((sprite.getWidth() - 1) - x) + 1) * sclX, (y + 1) * sclY, z - zSize)
//										.color(1, 1, 1, 1)
//										.texture((x + 1) * sclX, (y + 1) * sclY)
//										.light(1, 1)
//										.normal(0, 1, 0)
//										.next();
//								bufferBuilder
//										.vertex((((sprite.getWidth() - 1) - x) + 1) * sclX, y * sclY, z - zSize)
//										.color(1, 1, 1, 1)
//										.texture((x + 1) * sclX, y * sclY)
//										.light(1, 1)
//										.normal(0, 1, 0)
//										.next();
//								bufferBuilder
//										.vertex(((sprite.getWidth() - 1) - x) * sclX, y * sclY, z - zSize)
//										.color(1, 1, 1, 1)
//										.texture(x * sclX, y * sclY)
//										.light(1, 1)
//										.normal(0, 1, 0)
//										.next();
//							}
//						}
					}
					{
						bufferBuilder
								.vertex(1, 1, z + zSize)
								.color(1, 1, 1, 1)
								.texture(0, 1)
								.light(1, 1)
								.normal(0, 1, 0)
								.next();
						bufferBuilder
								.vertex(0, 1, z + zSize)
								.color(1, 1, 1, 1)
								.texture(1, 1)
								.light(1, 1)
								.normal(0, 1, 0)
								.next();
						bufferBuilder
								.vertex(0, 0, z + zSize)
								.color(1, 1, 1, 1)
								.texture(1, 0)
								.light(1, 1)
								.normal(0, 1, 0)
								.next();
						bufferBuilder
								.vertex(1, 0, z + zSize)
								.color(1, 1, 1, 1)
								.texture(0, 0)
								.light(1, 1)
								.normal(0, 1, 0)
								.next();
					}
					{
						bufferBuilder
								.vertex(0, 1, z - zSize)
								.color(1, 1, 1, 1)
								.texture(1, 1)
								.light(1, 1)
								.normal(0, 1, 0)
								.next();
						bufferBuilder
								.vertex(1, 1, z - zSize)
								.color(1, 1, 1, 1)
								.texture(0, 1)
								.light(1, 1)
								.normal(0, 1, 0)
								.next();
						bufferBuilder
								.vertex(1, 0, z - zSize)
								.color(1, 1, 1, 1)
								.texture(0, 0)
								.light(1, 1)
								.normal(0, 1, 0)
								.next();
						bufferBuilder
								.vertex(0, 0, z - zSize)
								.color(1, 1, 1, 1)
								.texture(1, 0)
								.light(1, 1)
								.normal(0, 1, 0)
								.next();
					}
					VertexBuffer buffer = resolutionVBOMap.get(sprite.getWidth() + ", " + sprite.getHeight());
					if (buffer == null) buffer = new VertexBuffer();
					else resolutionVBOMap.remove(sprite.getWidth() + ", " + sprite.getHeight());
					bufferBuilder.end();
					buffer.upload(bufferBuilder);
					resolutionVBOMap.put(sprite.getWidth() + ", " + sprite.getHeight(), buffer);
				}
				{
					VertexBuffer buffer = resolutionVBOMap.get(sprite.getWidth() + ", " + sprite.getHeight());
					VBOGeneratedItemsClient.vboShaderLayer.startDrawing();
					if (VBOGeneratedItemsClient.shader.vectorUV != null) VBOGeneratedItemsClient.shader.vectorUV.method_35657(sprite.getMinU(), sprite.getMaxU(), sprite.getMinV(), sprite.getMaxV());
					if (VBOGeneratedItemsClient.shader.lightCoord != null) VBOGeneratedItemsClient.shader.lightCoord.set((float) LightmapTextureManager.getBlockLightCoordinates(light), (float) LightmapTextureManager.getSkyLightCoordinates(light));
					int c = ((ItemRendererAccessor)MinecraftClient.getInstance().getItemRenderer()).getColorMap().getColorMultiplier(stack, i);
					if (VBOGeneratedItemsClient.shader.color != null) VBOGeneratedItemsClient.shader.color.method_35657(
							((c >> 16) & 0xFF) / 255f,
							((c >> 8) & 0xFF) / 255f,
							((c) & 0xFF) / 255f,
							((c >> 24) & 0xFF) / 255f
					); //TODO
					Matrix4f mat = RenderSystem.getModelViewMatrix().copy();
					mat.multiply(matrices.peek().getModel());
					buffer.setShader(mat, RenderSystem.getProjectionMatrix(), VBOGeneratedItemsClient.shader);
					VBOGeneratedItemsClient.vboShaderLayer.endDrawing();
				}
			}
			ci.cancel();
		}
	}
	
	private static final HashMap<String, VertexBuffer> resolutionVBOMap = new HashMap<>();
	
	private static Sprite getSprite(SpriteIdentifier id) {
		return MinecraftClient.getInstance()
				.getSpriteAtlas(id.getAtlasId())
				.apply(id.getTextureId())
				;
	}
}

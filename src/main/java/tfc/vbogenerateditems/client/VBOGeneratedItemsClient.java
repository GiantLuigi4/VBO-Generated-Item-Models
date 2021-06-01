package tfc.vbogenerateditems.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import tfc.vbogenerateditems.client.mixin.RenderLayerAccessor;

@Environment(EnvType.CLIENT)
public class VBOGeneratedItemsClient implements ClientModInitializer {
	public static final RenderLayer vboShaderLayer;
	public static VBOShader shader;
	public static final RenderPhase.Shader VBO_SHADER = new RenderPhase.Shader(()->shader);
	
	static {
		vboShaderLayer = RenderLayerAccessor.of(
				"vbo_shader",
				VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
				VertexFormat.DrawMode.QUADS, 131072,
				true, false,
				RenderLayer.MultiPhaseParameters.builder()
						.lightmap(RenderPhase.ENABLE_LIGHTMAP)
						.shader(VBO_SHADER)
						.texture(RenderPhase.BLOCK_ATLAS_TEXTURE)
						.build(true)
		);
	}
	
	public VBOGeneratedItemsClient() {
	}
	
	@Override
	public void onInitializeClient() {
	}
}

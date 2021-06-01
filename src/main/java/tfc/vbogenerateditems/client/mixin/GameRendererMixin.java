package tfc.vbogenerateditems.client.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.vbogenerateditems.client.VBOGeneratedItemsClient;
import tfc.vbogenerateditems.client.VBOShader;

import java.util.Map;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Shadow @Final private Map<String, Shader> shaders;
	
	@Inject(at = @At("TAIL"), method = "loadShaders(Lnet/minecraft/resource/ResourceManager;)V")
	public void postSetupShaders(ResourceManager manager, CallbackInfo ci) {
		try {
//			if (VBOGeneratedItemsClient.shader != null) VBOGeneratedItemsClient.shader.close();
			VBOGeneratedItemsClient.shader = new VBOShader(manager, "vbo_shader", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);
			shaders.put("vbo_shader", VBOGeneratedItemsClient.shader);
		} catch (Throwable err) {
			RuntimeException err1 = new RuntimeException(err.getMessage());
			err1.setStackTrace(err.getStackTrace());
			throw err1;
		}
	}
}

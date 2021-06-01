package tfc.vbogenerateditems.client.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(JsonUnbakedModel.class)
public interface JsonUnbakedModelAccessor {
	@Accessor("parent") JsonUnbakedModel getParent();
	@Accessor("textureMap") Map<String, Either<SpriteIdentifier, String>> getTextureMap();
}

package tfc.vbogenerateditems.client;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.vbogenerateditems.client.mixin.JsonUnbakedModelAccessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class JSONModelBaker {
	public static void preBake(ModelLoader loader, JsonUnbakedModel parent, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings settings, Identifier id, boolean hasDepth, CallbackInfoReturnable<BakedModel> cir) {
//		if (true) return;
		JsonUnbakedModel functionalRoot = getRootFunctionalModel(parent);
		if (functionalRoot.toString().equals("generation marker")) {
//			// idk if this is needed, so I'm leaving it
//			parent.getTextureDependencies(
//					loader::getOrLoadModel,
//					Set.of()
//			);
			Collection<SpriteIdentifier> sprites = new ArrayList<>();
			// this is an assumption, idk if this works
			for (Either<SpriteIdentifier, String> value : ((JsonUnbakedModelAccessor) parent).getTextureMap().values()) {
				if (value.left().isPresent()) sprites.add(value.left().get());
				else if (value.right().isPresent()) sprites.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(value.right().get())));
			}
//			ArrayList<SpriteIdentifier> arrayList = new ArrayList<>();
//			boolean foundEmpty = false;
//			for (SpriteIdentifier sprite : sprites) {
//				if (!foundEmpty) {
//					if (sprite.getAtlasId().toString().equals("minecraft:textures/atlas/blocks.png") && sprite.getTextureId().toString().equals("minecraft:missingno")) {
//						// ah yes, logic
//						// I have no idea why the game always has a missing texture in every single item model, but it does seem to do so
//						// this gets around that
//						// the reason I'm doing it here, is because the order which they are passed in is sorted alphabetically, meaning the missing texture is not always in the same spot in the list
//						foundEmpty = true;
//						continue;
//					} else {
//						arrayList.add(sprite);
//					}
//				} else {
//					arrayList.add(sprite);
//				}
//			}
//			sprites = arrayList;
			ModelOverrideList list = new ModelOverrideList(loader, parent, loader::getOrLoadModel, parent.getOverrides());
			cir.setReturnValue(
					new VBOModel(sprites, parent.getTransformations(), list)
			);
		}
	}
	
	private static JsonUnbakedModel getRootFunctionalModel(JsonUnbakedModel mdl) {
		JsonUnbakedModel lastRoot = mdl;
		JsonUnbakedModel root = null;
		while (lastRoot != root) {
			root = lastRoot;
			assert lastRoot != null;
			if (lastRoot.getElements().size() == 0) {
				lastRoot = ((JsonUnbakedModelAccessor) lastRoot).getParent();
				if (lastRoot == null) return root;
			}
		}
		return lastRoot;
	}
}

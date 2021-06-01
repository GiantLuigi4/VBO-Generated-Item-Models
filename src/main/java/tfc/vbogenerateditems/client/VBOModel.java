package tfc.vbogenerateditems.client;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class VBOModel implements BakedModel {
	private final List<SpriteIdentifier> textures;
	private final ImmutableList<BakedQuad> quads = ImmutableList.of();
	private final ModelTransformation transformation;
	private final ModelOverrideList overrideList;
	
	public VBOModel(Collection<SpriteIdentifier> textures, ModelTransformation transformation, ModelOverrideList overrideList) {
		this.textures = ImmutableList.copyOf(textures.toArray(new SpriteIdentifier[0]));
		this.transformation = transformation;
		this.overrideList = overrideList;
	}
	
	public List<SpriteIdentifier> getTextures() {
		return textures;
	}
	
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
		return quads;
	}
	
	@Override
	public boolean useAmbientOcclusion() {
		return false;
	}
	
	@Override
	public boolean hasDepth() {
		return false;
	}
	
	@Override
	public boolean isSideLit() {
		return false;
	}
	
	@Override
	public boolean isBuiltin() {
		return false;
	}
	
	@Override
	public Sprite getSprite() {
		return MinecraftClient.getInstance()
				.getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
				.apply(textures.get(0).getTextureId())
				;
	}
	
	@Override
	public ModelTransformation getTransformation() {
		return transformation;
	}
	
	@Override
	public ModelOverrideList getOverrides() {
		return overrideList;
	}
}

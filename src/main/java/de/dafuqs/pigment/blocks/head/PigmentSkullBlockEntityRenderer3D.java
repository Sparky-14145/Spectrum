package de.dafuqs.pigment.blocks.head;

import de.dafuqs.pigment.PigmentCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.SkullEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.awt.image.renderable.RenderContext;
import java.util.Locale;

@Environment(EnvType.CLIENT)
public class PigmentSkullBlockEntityRenderer3D implements BlockEntityRenderer<PigmentSkullBlockEntity> {

    private static EntityModelLoader entityModelLoader;
    private static SkullEntityModel model;

    public PigmentSkullBlockEntityRenderer3D(BlockEntityRendererFactory.Context renderContext) {
        model = new SkullEntityModel(entityModelLoader.getModelPart(EntityModelLayers.PLAYER_HEAD));
    }

    public static void setModelLoader(EntityModelLoader entityModelLoader) {
        PigmentSkullBlockEntityRenderer3D.entityModelLoader = entityModelLoader;
    }

    public void render(PigmentSkullBlockEntity pigmentSkullBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int j) {
        BlockState blockState = pigmentSkullBlockEntity.getCachedState();
        boolean bl = blockState.getBlock() instanceof WallSkullBlock;
        Direction direction = bl ? blockState.get(WallSkullBlock.FACING) : null;
        float h = 22.5F * (float)(bl ? (2 + direction.getHorizontal()) * 4 : blockState.get(SkullBlock.ROTATION));
        PigmentSkullBlock.Type skullType = pigmentSkullBlockEntity.getSkullType();
        if(skullType == null) {
            skullType = PigmentSkullBlock.Type.PIG;
        }
        RenderLayer renderLayer = getRenderLayer(skullType);
        renderSkull(direction, h, 0, matrixStack, vertexConsumerProvider, light, renderLayer);
    }

    public static void renderSkull(@Nullable Direction direction, float yaw, float animationProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, RenderLayer renderLayer) {
        matrices.push();
        if (direction == null) {
            matrices.translate(0.5D, 0.0D, 0.5D);
        } else {
            float f = 0.25F;
            matrices.translate((0.5F - (float)direction.getOffsetX() * 0.25F), 0.25D, (0.5F - (float)direction.getOffsetZ() * 0.25F));
        }

        matrices.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
        model.setHeadRotation(animationProgress, yaw, 0.0F);
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();
    }

    public static RenderLayer getRenderLayer(PigmentSkullBlock.Type type) {
        Identifier identifier = new Identifier(PigmentCommon.MOD_ID, "textures/entity/mob_head/" + type.toString().toLowerCase(Locale.ROOT) + ".png");
        RenderLayer renderLayer = RenderLayer.getEntityCutoutNoCullZOffset(identifier);
        if(renderLayer == null) {
            return RenderLayer.getEntityCutoutNoCullZOffset(new Identifier("textures/entity/zombie/zombie.png"));
        } else {
            return renderLayer;
        }
    }

}
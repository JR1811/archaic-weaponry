package net.shirojr.archaicweaponry.block.blockentity.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.shirojr.archaicweaponry.ArchaicWeaponry;
import net.shirojr.archaicweaponry.block.GuillotineBlock;
import net.shirojr.archaicweaponry.block.blockentity.GuillotineBlockEntity;
import net.shirojr.archaicweaponry.block.blockentity.client.model.GuillotineBlockEntityModel;
import net.shirojr.archaicweaponry.util.helper.MultiBlockUtil;

public class GuillotineBlockEntityRenderer implements BlockEntityRenderer<GuillotineBlockEntity> {
    private static final Identifier GUILLOTINE_TEXTURE = new Identifier(ArchaicWeaponry.MODID, "textures/block/guillotine.png");
    private GuillotineBlockEntityModel guillotineModel;

    public GuillotineBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        guillotineModel = new GuillotineBlockEntityModel(ctx);
    }

    @Override
    public void render(GuillotineBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!(entity.getWorld() instanceof ClientWorld clientWorld)) return;
        BlockState state = entity.getCachedState();
        if (!state.contains(GuillotineBlock.PART) || !state.contains(GuillotineBlock.DIRECTION)) return;
        if (!state.get(GuillotineBlock.PART).equals(MultiBlockUtil.GuillotinePart.TOP)) return;

        //TODO: only take in blade for rendering inside the custom model class
        Direction direction = state.get(GuillotineBlock.DIRECTION);
        matrices.push();
        matrices.multiply(direction.getOpposite().getRotationQuaternion());
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
        matrices.translate(0.5, -1, -0.5);

        var layer = this.guillotineModel.getLayer(GUILLOTINE_TEXTURE);
        var vertexConsumer = vertexConsumers.getBuffer(layer);

        guillotineModel.render(matrices, vertexConsumer,
                LightmapTextureManager.getBlockLightCoordinates(clientWorld.getLightLevel(entity.getPos().up(2))),
                overlay, 1, 1, 1, 1);
        matrices.pop();

    }
}

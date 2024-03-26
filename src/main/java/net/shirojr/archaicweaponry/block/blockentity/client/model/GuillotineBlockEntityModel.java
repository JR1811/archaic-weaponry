package net.shirojr.archaicweaponry.block.blockentity.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryBlockEntityRenderers;

public class GuillotineBlockEntityModel extends Model {
    private final ModelPart guillotine;

    public GuillotineBlockEntityModel(BlockEntityRendererFactory.Context context) {
        super(RenderLayer::getEntityCutout);
        ModelPart modelPart = context.getLayerModelPart(ArchaicWeaponryBlockEntityRenderers.GUILLOTINE_MODEL_LAYER);
        this.guillotine = modelPart.getChild("guillotine");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData guillotine = modelPartData.addChild("guillotine", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.0F, 0.0F));
        ModelPartData blade = guillotine.addChild("blade", ModelPartBuilder.create().uv(0, 9).cuboid(-5.0F, 1.0F, 0.0F, 10.0F, 7.0F, 0.0F, new Dilation(0.0F))
                .uv(48, 13).cuboid(-4.0F, -0.25F, -0.5F, 8.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.guillotine.render(matrices, vertices, light, overlay);
    }
}

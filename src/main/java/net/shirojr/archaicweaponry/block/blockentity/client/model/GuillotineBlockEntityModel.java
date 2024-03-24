package net.shirojr.archaicweaponry.block.blockentity.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.shirojr.archaicweaponry.registry.ArchaicWeaponryBlockEntityRenderers;

public class GuillotineBlockEntityModel extends Model {

    private final ModelPart guillotine;
    private final ModelPart plank, frame;

    public GuillotineBlockEntityModel(BlockEntityRendererFactory.Context context) {
        super(RenderLayer::getEntityCutout);
        ModelPart modelPart = context.getLayerModelPart(ArchaicWeaponryBlockEntityRenderers.GUILLOTINE_MODEL_LAYER);
        this.guillotine = modelPart.getChild("guillotine");
        this.plank = this.guillotine.getChild("plank");
        this.frame = this.guillotine.getChild("frame");

    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData guillotine = modelPartData.addChild("guillotine", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, -8.0F));

        ModelPartData plank = guillotine.addChild("plank", ModelPartBuilder.create().uv(4, 0).cuboid(-7.0F, -8.0F, 0.0F, 14.0F, 4.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData frame = guillotine.addChild("frame", ModelPartBuilder.create().uv(27, 20).cuboid(4.75F, -8.25F, 13.75F, 2.5F, 8.25F, 2.5F, new Dilation(0.0F))
                .uv(27, 20).cuboid(-7.25F, -8.25F, 13.75F, 2.5F, 8.25F, 2.5F, new Dilation(0.0F))
                .uv(48, 10).cuboid(-8.0F, -1.25F, 14.0F, 16.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(27, 20).cuboid(-1.0F, -1.25F, 0.0F, 2.0F, 1.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.guillotine.render(matrices, vertices, light, overlay);
        // this.plank.render(matrices, vertices, light, overlay);
        // this.frame.render(matrices, vertices, light, overlay);
    }


}

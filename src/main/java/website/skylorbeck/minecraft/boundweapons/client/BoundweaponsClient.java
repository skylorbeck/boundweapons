package website.skylorbeck.minecraft.boundweapons.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import website.skylorbeck.minecraft.boundweapons.Boundweapons;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class BoundweaponsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(Boundweapons.boundBowBasic, new Identifier("pull"), (itemStack, clientWorld, livingEntity,i) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
        });

        ModelPredicateProviderRegistry.register(Boundweapons.boundBowBasic, new Identifier("pulling"), (itemStack, clientWorld, livingEntity,i) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });
        ModelPredicateProviderRegistry.register(Boundweapons.boundBowAdvanced, new Identifier("pull"), (itemStack, clientWorld, livingEntity,i) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
        });

        ModelPredicateProviderRegistry.register(Boundweapons.boundBowAdvanced, new Identifier("pulling"), (itemStack, clientWorld, livingEntity,i) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });
        ModelPredicateProviderRegistry.register(Boundweapons.boundBowEpic, new Identifier("pull"), (itemStack, clientWorld, livingEntity,i) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
        });

        ModelPredicateProviderRegistry.register(Boundweapons.boundBowEpic, new Identifier("pulling"), (itemStack, clientWorld, livingEntity,i) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });
    }
}

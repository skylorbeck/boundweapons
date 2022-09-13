package website.skylorbeck.minecraft.boundweapons;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import static net.minecraft.util.Rarity.RARE;

public class SummonSword extends Item {
    private int tier = 0;
    public SummonSword(int tier) {
        super(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).fireproof().rarity(RARE));
        this.tier = tier;
    }


    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (world.isClient) {
            return;
        }
        if (remainingUseTicks > getMaxUseTime(stack) - 20) {
            return;
        }
        if (user instanceof PlayerEntity player){
            if (player.experienceLevel >= 1+tier){
                ItemStack itemStack = switch (tier) {
                    case 0 -> Boundweapons.boundSwordBasic.getDefaultStack();
                    case 1 -> Boundweapons.boundSwordAdvanced.getDefaultStack();
                    case 2 -> Boundweapons.boundSwordEpic.getDefaultStack();
                    default -> ItemStack.EMPTY;
                };
                player.setStackInHand(Hand.MAIN_HAND, itemStack);
                player.addExperienceLevels(-(1+tier));
            } else {
                player.sendMessage(Text.of("You need "+ (tier+1) +" experience levels to summon your Bound Sword!"),true);
                return;
            }
        }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return true;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
}

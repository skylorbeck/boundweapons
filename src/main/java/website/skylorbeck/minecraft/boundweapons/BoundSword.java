package website.skylorbeck.minecraft.boundweapons;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.util.Rarity.RARE;
import static website.skylorbeck.minecraft.boundweapons.SummonSpell.DamageCheck;

public class BoundSword extends SwordItem {
    public BoundSword(int tier) {
        super(ToolMaterials.IRON, 3, -2.4f, new FabricItemSettings().maxCount(1).maxDamage(32).fireproof().rarity(RARE));
        this.tier = tier;
    }

    private int tier = 0;
    public int getTier() {
        return tier;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        DamageCheck(stack, attacker,tier);
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean postMine = super.postMine(stack, world, state, pos, miner);
        DamageCheck(stack, miner,tier);
        return postMine;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        stack.addEnchantment(Enchantments.LOOTING, tier + 1);
        stack.setRepairCost(999);
        return stack;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

}
package website.skylorbeck.minecraft.boundweapons;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.util.Rarity.RARE;

public class BoundSword extends SwordItem {
    private int tier = 0;
    public BoundSword(int tier) {
        super(ToolMaterials.IRON,3,-2.4f, new FabricItemSettings().maxCount(1).maxDamage(32).fireproof().rarity(RARE));
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        if (stack.getDamage() >= stack.getMaxDamage()) {
            stack.decrement(1);
            ItemStack itemStack = switch (tier) {
                case 0 -> Boundweapons.summonSwordBasic.getDefaultStack();
                case 1 -> Boundweapons.summonSwordAdvanced.getDefaultStack();
                case 2 -> Boundweapons.summonSwordEpic.getDefaultStack();
                default -> ItemStack.EMPTY;
            };
            ItemEntity itemEntity = new ItemEntity(attacker.world, attacker.getX(), attacker.getY(), attacker.getZ(),itemStack);
            attacker.world.spawnEntity(itemEntity);
        }
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        stack.damage(4, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        if (stack.getDamage() >= stack.getMaxDamage()) {
            stack.decrement(1);
            ItemStack itemStack = switch (tier) {
                case 0 -> Boundweapons.summonSwordBasic.getDefaultStack();
                case 1 -> Boundweapons.summonSwordAdvanced.getDefaultStack();
                case 2 -> Boundweapons.summonSwordEpic.getDefaultStack();
                default -> ItemStack.EMPTY;
            };
            ItemEntity itemEntity = new ItemEntity(miner.world, miner.getX(), miner.getY(), miner.getZ(),itemStack);
            miner.world.spawnEntity(itemEntity);
        }
        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        stack.addEnchantment(Enchantments.LOOTING,tier+1);
        return stack;
    }
//    @Override
//    public boolean isEnchantable(ItemStack stack) {
//        return false;
//    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }


    //todo toggle for damaging the summoned weapon over time
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
//        if (world.getTime() % 20 == 0) {
//            stack.damage(1, (LivingEntity) entity, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
//            if (stack.getDamage() >= stack.getMaxDamage()) {
//                stack.decrement(1);
//                ItemEntity itemEntity = new ItemEntity(entity.world, entity.getX(), entity.getY(), entity.getZ(),Boundweapons.summonSword.getDefaultStack());
//                entity.world.spawnEntity(itemEntity);
//            }
//        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}

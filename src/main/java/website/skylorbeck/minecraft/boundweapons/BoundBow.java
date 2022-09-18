package website.skylorbeck.minecraft.boundweapons;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Predicate;

import static net.minecraft.util.Rarity.RARE;
import static website.skylorbeck.minecraft.boundweapons.SummonSpell.DamageCheck;

public class BoundBow extends BowItem {
    public BoundBow(int tier) {
        super(new FabricItemSettings().maxCount(1).maxDamage(32).fireproof().rarity(RARE));
        this.tier = tier;
    }

    private int tier = 0;
    public int getTier() {
        return tier;
    }
    @Override
    public Predicate<ItemStack> getProjectiles() {
        return stack -> stack.isOf(Items.SPECTRAL_ARROW);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        float f;
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity playerEntity = (PlayerEntity)user;
        ItemStack itemStack = new ItemStack(Items.ARROW);
        if ((double)(f = BowItem.getPullProgress(this.getMaxUseTime(stack) - remainingUseTicks)) < 0.1) {
            return;
        }
        if (!world.isClient) {
            int k;
            int j;
            ArrowItem arrowItem = (ArrowItem)(Items.SPECTRAL_ARROW);
            PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, itemStack, playerEntity);
            persistentProjectileEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, f * 3.0f, 1.0f);
            if (f == 1.0f) {
                persistentProjectileEntity.setCritical(true);
            }
            if ((j = EnchantmentHelper.getLevel(Enchantments.POWER, stack)) > 0) {
                persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + (double)j * 0.5 + 0.5);
            }
            if ((k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack)) > 0) {
                persistentProjectileEntity.setPunch(k);
            }
            if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                persistentProjectileEntity.setOnFireFor(100);
            }
            stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(playerEntity.getActiveHand()));
                persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            world.spawnEntity(persistentProjectileEntity);
        }
        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (world.getRandom().nextFloat() * 0.4f + 1.2f) + f * 0.5f);
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        DamageCheck(stack,playerEntity,tier);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(4, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
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
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        stack.addEnchantment(Enchantments.INFINITY, tier + 1);
        return stack;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }
}

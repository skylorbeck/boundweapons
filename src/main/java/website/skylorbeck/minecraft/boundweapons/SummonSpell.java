package website.skylorbeck.minecraft.boundweapons;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.util.Rarity.RARE;

public class SummonSpell extends Item {
    private int tier = 0;

    public SummonSpell(int tier) {
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
        if (remainingUseTicks > getMaxUseTime(stack) - 20 ) {
            if (!user.isSneaking()){
                return;
            }
        }
        if (user instanceof PlayerEntity player) {
            if (player.isSneaking()) {
                int mode = stack.getOrCreateNbt().getInt("mode");
                mode++;
                if (mode > 5) {
                    mode = 0;
                }
                stack.getOrCreateNbt().putInt("mode", mode);
                switch (mode) {
                    case 0 -> player.sendMessage(Text.of("Bound Sword"), true);
                    case 1 -> player.sendMessage(Text.of("Bound Bow"), true);
                    case 2 -> player.sendMessage(Text.of("Bound Pickaxe"), true);
                    case 3 -> player.sendMessage(Text.of("Bound Axe"), true);
                    case 4 -> player.sendMessage(Text.of("Bound Shovel"), true);
                    case 5 -> player.sendMessage(Text.of("Bound Hoe"), true);
                }
                return;
            }

            if (player.experienceLevel >= 1 + tier) {
                ItemStack itemStack = switch (stack.getOrCreateNbt().getInt("mode")) {
                    case 0 -> switch (tier) {
                        case 0 -> Boundweapons.boundSwordBasic.getDefaultStack();
                        case 1 -> Boundweapons.boundSwordAdvanced.getDefaultStack();
                        case 2 -> Boundweapons.boundSwordEpic.getDefaultStack();
                        default -> ItemStack.EMPTY;
                    };
                    case 1 -> switch (tier) {
                        case 0 -> Boundweapons.boundBowBasic.getDefaultStack();
                        case 1 -> Boundweapons.boundBowAdvanced.getDefaultStack();
                        case 2 -> Boundweapons.boundBowEpic.getDefaultStack();
                        default -> ItemStack.EMPTY;
                    };
                    case 2 -> switch (tier) {
                        case 0 -> Boundweapons.boundPickaxeBasic.getDefaultStack();
                        case 1 -> Boundweapons.boundPickaxeAdvanced.getDefaultStack();
                        case 2 -> Boundweapons.boundPickaxeEpic.getDefaultStack();
                        default -> ItemStack.EMPTY;

                    };
                    case 3 -> switch (tier) {
                        case 0 -> Boundweapons.boundAxeBasic.getDefaultStack();
                        case 1 -> Boundweapons.boundAxeAdvanced.getDefaultStack();
                        case 2 -> Boundweapons.boundAxeEpic.getDefaultStack();
                        default -> ItemStack.EMPTY;
                    };
                    case 4 -> switch (tier) {
                        case 0 -> Boundweapons.boundShovelBasic.getDefaultStack();
                        case 1 -> Boundweapons.boundShovelAdvanced.getDefaultStack();
                        case 2 -> Boundweapons.boundShovelEpic.getDefaultStack();
                        default -> ItemStack.EMPTY;
                    };
                    case 5 -> switch (tier) {
                        case 0 -> Boundweapons.boundHoeBasic.getDefaultStack();
                        case 1 -> Boundweapons.boundHoeAdvanced.getDefaultStack();
                        case 2 -> Boundweapons.boundHoeEpic.getDefaultStack();
                        default -> ItemStack.EMPTY;
                    };
                    default -> ItemStack.EMPTY;
                };
                if (itemStack.isEmpty()) {
                    return;
                }
                if (stack.hasCustomName()) {
                    itemStack.setCustomName(stack.getName());
                }
                player.setStackInHand(Hand.MAIN_HAND, itemStack);
                player.addExperienceLevels(-(1 + tier));
            } else {
                player.sendMessage(Text.of("You need " + (tier + 1) + " experience levels to summon your Bound Tool!"), true);
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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of("Hold Right click to summon a Bound Tool"));
        tooltip.add(Text.of("Sneak Right click to change Selected Tool"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public static void DamageCheck(ItemStack stack, LivingEntity miner, int tier) {
        if (stack.getDamage() >= stack.getMaxDamage()) {
            stack.decrement(1);
            ItemStack itemStack = switch (tier) {
                case 0 -> Boundweapons.summonSpellBasic.getDefaultStack();
                case 1 -> Boundweapons.summonSpellAdvanced.getDefaultStack();
                case 2 -> Boundweapons.summonSpellEpic.getDefaultStack();
                default -> ItemStack.EMPTY;
            };
            if (stack.hasCustomName()) {
                itemStack.setCustomName(stack.getName());
            }
            ItemEntity itemEntity = new ItemEntity(miner.world, miner.getX(), miner.getY(), miner.getZ(), itemStack);
            miner.world.spawnEntity(itemEntity);
        }
    }
}
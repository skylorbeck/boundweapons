package website.skylorbeck.minecraft.boundweapons;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import website.skylorbeck.minecraft.skylorlib.Registrar;

public class Boundweapons implements ModInitializer {
    @Override
    public void onInitialize() {
        Registrar.regItem("summon_spell_basic_", summonSpellBasic, "boundweapons");
        Registrar.regItem("summon_spell_advanced_", summonSpellAdvanced, "boundweapons");
        Registrar.regItem("summon_spell_epic_", summonSpellEpic, "boundweapons");

        Registrar.regItem("bound_sword_basic_", boundSwordBasic, "boundweapons");
        Registrar.regItem("bound_sword_advanced_", boundSwordAdvanced, "boundweapons");
        Registrar.regItem("bound_sword_epic_", boundSwordEpic, "boundweapons");

        Registrar.regItem("bound_axe_basic_", boundAxeBasic, "boundweapons");
        Registrar.regItem("bound_axe_advanced_", boundAxeAdvanced, "boundweapons");
        Registrar.regItem("bound_axe_epic_", boundAxeEpic, "boundweapons");

        Registrar.regItem("bound_bow_basic_", boundBowBasic, "boundweapons");
        Registrar.regItem("bound_bow_advanced_", boundBowAdvanced, "boundweapons");
        Registrar.regItem("bound_bow_epic_", boundBowEpic, "boundweapons");

        Registrar.regItem("bound_hoe_basic_", boundHoeBasic, "boundweapons");
        Registrar.regItem("bound_hoe_advanced_", boundHoeAdvanced, "boundweapons");
        Registrar.regItem("bound_hoe_epic_", boundHoeEpic, "boundweapons");

        Registrar.regItem("bound_shovel_basic_", boundShovelBasic, "boundweapons");
        Registrar.regItem("bound_shovel_advanced_", boundShovelAdvanced, "boundweapons");
        Registrar.regItem("bound_shovel_epic_", boundShovelEpic, "boundweapons");

        Registrar.regItem("bound_pickaxe_basic_", boundPickaxeBasic, "boundweapons");
        Registrar.regItem("bound_pickaxe_advanced_", boundPickaxeAdvanced, "boundweapons");
        Registrar.regItem("bound_pickaxe_epic_", boundPickaxeEpic, "boundweapons");
    }

    public static Item summonSpellBasic = new SummonSpell(0);
    public static Item summonSpellAdvanced = new SummonSpell(1);
    public static Item summonSpellEpic = new SummonSpell(2);

    public static Item boundSwordBasic = new BoundSword(0);
    public static Item boundSwordAdvanced = new BoundSword(1);
    public static Item boundSwordEpic = new BoundSword(2);

    public static Item boundBowBasic = new BoundBow(0);
    public static Item boundBowAdvanced = new BoundBow(1);
    public static Item boundBowEpic = new BoundBow(2);

    public static Item boundPickaxeBasic = new BoundPickaxe(0);
    public static Item boundPickaxeAdvanced = new BoundPickaxe(1);
    public static Item boundPickaxeEpic = new BoundPickaxe(2);

    public static Item boundAxeBasic = new BoundAxe(0);
    public static Item boundAxeAdvanced = new BoundAxe(1);
    public static Item boundAxeEpic = new BoundAxe(2);

    public static Item boundShovelBasic = new BoundShovel(0);
    public static Item boundShovelAdvanced = new BoundShovel(1);
    public static Item boundShovelEpic = new BoundShovel(2);

    public static Item boundHoeBasic = new BoundHoe(0);
    public static Item boundHoeAdvanced = new BoundHoe(1);
    public static Item boundHoeEpic = new BoundHoe(2);

    //todo fishing rod
}
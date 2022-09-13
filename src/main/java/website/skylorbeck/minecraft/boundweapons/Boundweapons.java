package website.skylorbeck.minecraft.boundweapons;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import website.skylorbeck.minecraft.skylorlib.Registrar;

public class Boundweapons implements ModInitializer {
    @Override
    public void onInitialize() {
        Registrar.regItem("summon_sword_basic_", summonSwordBasic,"boundweapons");
        Registrar.regItem("summon_sword_advanced_", summonSwordAdvanced,"boundweapons");
        Registrar.regItem("summon_sword_epic_", summonSwordEpic,"boundweapons");
        Registrar.regItem("bound_sword_basic_", boundSwordBasic,"boundweapons");
        Registrar.regItem("bound_sword_advanced_", boundSwordAdvanced,"boundweapons");
        Registrar.regItem("bound_sword_epic_", boundSwordEpic,"boundweapons");
    }
    public static Item summonSwordBasic = new SummonSword(0);
    public static Item boundSwordBasic = new BoundSword(0);
    public static Item summonSwordAdvanced = new SummonSword(1);
    public static Item boundSwordAdvanced = new BoundSword(1);
    public static Item summonSwordEpic = new SummonSword(2);
    public static Item boundSwordEpic = new BoundSword(2);
}

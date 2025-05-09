package world.maryt.tinker_slashblade_patch;

import net.minecraftforge.fml.common.Mod;

import static world.maryt.tinker_slashblade_patch.TinkerSlashBladePatch.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDENCIES)
public class TinkerSlashBladePatch {
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String MOD_NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "after:slashblade;after:mm-lib;after:mixinbooter";
}

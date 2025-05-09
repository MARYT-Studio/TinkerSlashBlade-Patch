package world.maryt.tinker_slashblade_patch;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

import static world.maryt.tinker_slashblade_patch.TinkerSlashBladePatch.MOD_ID;

@SuppressWarnings("unused")
public class MixinInit implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins." + MOD_ID + ".json");
    }
}

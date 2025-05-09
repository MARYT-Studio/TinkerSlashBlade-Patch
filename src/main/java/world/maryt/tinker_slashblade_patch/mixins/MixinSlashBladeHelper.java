package world.maryt.tinker_slashblade_patch.mixins;

import cn.mmf.slashblade_tic.blade.SlashBladeCore;
import cn.mmf.slashblade_tic.blade.TinkerSlashBladeEvent;
import cn.mmf.slashblade_tic.util.SlashBladeHelper;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.common.TinkerNetwork;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.tools.common.network.ToolBreakAnimationPacket;
import slimeknights.tconstruct.tools.modifiers.ModReinforced;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = SlashBladeHelper.class, remap = false)
public abstract class MixinSlashBladeHelper {

    /**
     * @author RisingInIris2017
     * @reason Apply <a href="https://github.com/0999312/TinkerSlashBlade/pull/13">Patch in #13</a> to TinkerSlashBlade 1.5.0. Thanks to the original author of this patch: <a href="https://github.com/yuttyann">yuttyann</a>
     */
    @Overwrite
    static float getfloatTag(ItemStack stack, String key) {
        NBTTagCompound tag = TagUtil.getToolTag(stack);
        return tag.getFloat(key);
    }

    /**
     * @author RisingInIris2017
     * @reason Apply <a href="https://github.com/0999312/TinkerSlashBlade/pull/13">Patch in #13</a> to TinkerSlashBlade 1.5.0. Thanks to the original author of this patch: <a href="https://github.com/yuttyann">yuttyann</a>
     */
    @Overwrite
    public static float getAttackStat(ItemStack stack) {
        return getfloatTag(stack, Tags.ATTACK);
    }

    /**
     * @author RisingInIris2017
     * @reason Apply <a href="https://github.com/0999312/TinkerSlashBlade/pull/13">Patch in #13</a> to TinkerSlashBlade 1.5.0. Thanks to the original author of this patch: <a href="https://github.com/yuttyann">yuttyann</a>
     */
    @Overwrite
    public static float getActualAttack(ItemStack stack) {
        float damage = getAttackStat(stack);
        if (!stack.isEmpty() && stack.getItem() instanceof SlashBladeCore) {
            damage *= ((SlashBladeCore)stack.getItem()).damagePotential();
        }
        return damage;
    }

    /**
     * @author RisingInIris2017
     * @reason Apply <a href="https://github.com/0999312/TinkerSlashBlade/pull/13">Patch in #13</a> to TinkerSlashBlade 1.5.0. Thanks to the original author of this patch: <a href="https://github.com/yuttyann">yuttyann</a>
     */
    @Overwrite
    public static int getCurrentDurability(ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }

    /**
     * @author RisingInIris2017
     * @reason Apply <a href="https://github.com/0999312/TinkerSlashBlade/pull/13">Patch in #13</a> to TinkerSlashBlade 1.5.0. Thanks to the original author of this patch: <a href="https://github.com/yuttyann">yuttyann</a>
     */
    @Overwrite
    public static int getMaxDurability(ItemStack stack) {
        return stack.getMaxDamage();
    }

    /**
     * @author RisingInIris2017
     * @reason Apply <a href="https://github.com/0999312/TinkerSlashBlade/pull/13">Patch in #13</a> to TinkerSlashBlade 1.5.0. Thanks to the original author of this patch: <a href="https://github.com/yuttyann">yuttyann</a>
     */
    @Overwrite
    public static void damageTool(ItemStack stack, int amount, EntityLivingBase entity) {
        if (amount == 0 || isBroken(stack)) {
            return;
        }
        int actualAmount = amount;
        for (ITrait trait : TinkerUtil.getTraitsOrdered(stack)) {
            if (amount > 0) {
                actualAmount = trait.onToolDamage(stack, amount, actualAmount, entity);
                continue;
            }
            actualAmount = trait.onToolHeal(stack, amount, actualAmount, entity);
        }
        if (actualAmount > 0 && TagUtil.getTagSafe(stack).getBoolean(ModReinforced.TAG_UNBREAKABLE)) {
            actualAmount = 0;
        }
        actualAmount = Math.min(actualAmount, getCurrentDurability(stack));
        stack.setItemDamage(stack.getItemDamage() + actualAmount);
        if (getCurrentDurability(stack) == 0) {
            breakTool(stack, entity);
        }
    }

    /**
     * @author RisingInIris2017
     * @reason Apply <a href="https://github.com/0999312/TinkerSlashBlade/pull/13">Patch in #13</a> to TinkerSlashBlade 1.5.0. Thanks to the original author of this patch: <a href="https://github.com/yuttyann">yuttyann</a>
     */
    @Overwrite
    public static void healTool(ItemStack stack, int amount, EntityLivingBase entity) {
        damageTool(stack, -amount, entity);
    }

    /**
     * @author RisingInIris2017
     * @reason Apply <a href="https://github.com/0999312/TinkerSlashBlade/pull/13">Patch in #13</a> to TinkerSlashBlade 1.5.0. Thanks to the original author of this patch: <a href="https://github.com/yuttyann">yuttyann</a>
     */
    @Overwrite
    public static boolean isBroken(ItemStack stack) {
        return TagUtil.getToolTag(stack).getBoolean(Tags.BROKEN);
    }

    /**
     * @author RisingInIris2017
     * @reason Apply <a href="https://github.com/0999312/TinkerSlashBlade/pull/13">Patch in #13</a> to TinkerSlashBlade 1.5.0. Thanks to the original author of this patch: <a href="https://github.com/yuttyann">yuttyann</a>
     */
    @Overwrite
    public static void breakTool(ItemStack stack, EntityLivingBase entity) {
        NBTTagCompound tag = TagUtil.getToolTag(stack);
        if (tag.hasKey(Tags.BROKEN) && tag.getBoolean(Tags.BROKEN)) {
            return;
        }
        tag.setBoolean(Tags.BROKEN, true);
        ItemSlashBlade.IsBroken.set(tag, true);
        TagUtil.setToolTag(stack, tag);
        if (entity instanceof EntityPlayerMP) {
            TinkerNetwork.sendTo(new ToolBreakAnimationPacket(stack), (EntityPlayerMP) entity);
        }
    }

    /**
     * @author RisingInIris2017
     * @reason Apply <a href="https://github.com/0999312/TinkerSlashBlade/pull/13">Patch in #13</a> to TinkerSlashBlade 1.5.0. Thanks to the original author of this patch: <a href="https://github.com/yuttyann">yuttyann</a>
     */
    @Overwrite
    public static void unbreakTool(ItemStack stack) {
        if (isBroken(stack)) {
            stack.setItemDamage(0);
            NBTTagCompound tag = TagUtil.getToolTag(stack);
            tag.setBoolean(Tags.BROKEN, false);
            ItemSlashBlade.IsBroken.set(tag, false);
            TagUtil.setToolTag(stack, tag);
        }
    }

    /**
     * @author RisingInIris2017
     * @reason Apply <a href="https://github.com/0999312/TinkerSlashBlade/pull/13">Patch in #13</a> to TinkerSlashBlade 1.5.0. Thanks to the original author of this patch: <a href="https://github.com/yuttyann">yuttyann</a>
     */
    @Overwrite
    public static void repairTool(ItemStack stack, int amount) {
        repairTool(stack, amount, null);
    }

    /**
     * @author RisingInIris2017
     * @reason Apply <a href="https://github.com/0999312/TinkerSlashBlade/pull/13">Patch in #13</a> to TinkerSlashBlade 1.5.0. Thanks to the original author of this patch: <a href="https://github.com/yuttyann">yuttyann</a>
     */
    @Overwrite
    public static void repairTool(ItemStack stack, int amount, EntityLivingBase entity) {
        unbreakTool(stack);
        TinkerSlashBladeEvent.OnRepair.fireEvent(stack, amount);
        healTool(stack, amount, entity);
    }
}
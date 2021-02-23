package de.dafuqs.pigment.mixin.client;

import de.dafuqs.pigment.PigmentBlockCloaker;
import de.dafuqs.pigment.PigmentCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow
    public abstract Text getName();

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/item/Item;getName(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/text/Text;", cancellable = true)
    public void getName(ItemStack stack, CallbackInfoReturnable<Text> callbackInfoReturnable) {
        Item thisItem = (Item) (Object) this;
        if(PigmentBlockCloaker.isSwapped(thisItem)) {

            // Get the localized name of the item and scatter it via §k
            Language language = Language.getInstance();
            LiteralText newText = new LiteralText("§k" + language.get(thisItem.getTranslationKey()));

            callbackInfoReturnable.setReturnValue(newText);
        }
    }

}
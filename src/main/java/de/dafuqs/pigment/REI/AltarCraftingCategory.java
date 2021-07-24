package de.dafuqs.pigment.REI;

import com.google.common.collect.Lists;
import de.dafuqs.pigment.PigmentCommon;
import de.dafuqs.pigment.inventories.AltarScreen;
import de.dafuqs.pigment.recipe.altar.AltarCraftingRecipe;
import de.dafuqs.pigment.registries.PigmentBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.DisplayRenderer;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.SimpleDisplayRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;

public class AltarCraftingCategory<R extends AltarCraftingRecipe> implements DisplayCategory<AltarCraftingRecipeDisplay<R>> {

    final Identifier GUI_TEXTURE = AltarScreen.BACKGROUND;

    public static final CategoryIdentifier<AltarCraftingRecipeDisplay> ID = CategoryIdentifier.of(new Identifier(PigmentCommon.MOD_ID, "altar_crafting"));

    @Override
    public CategoryIdentifier getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("container.pigment.rei.altar_crafting.title");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(PigmentBlocks.ALTAR);
    }

    @Override
    public DisplayRenderer getDisplayRenderer(AltarCraftingRecipeDisplay<R> recipe) {
        return SimpleDisplayRenderer.from(Collections.singletonList(recipe.getInputEntries().get(0)), recipe.getOutputEntries());
    }

    @Override
    public List<Widget> setupDisplay(AltarCraftingRecipeDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 58, bounds.getCenterY() - 43);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 60, startPoint.y + 18)));

        // crafting grid slots
        List<Slot> slots = Lists.newArrayList();
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                slots.add(Widgets.createSlot(new Point(startPoint.x + 2 + x * 18, startPoint.y + 2 + y * 18)).disableBackground().markInput());

        // crafting slot contents
        List<? extends List<? extends EntryStack<?>>> input = display.getInputEntries();
        int gemstoneDustStartSlot = display.height * display.width;
        for (int i = 0; i < gemstoneDustStartSlot; i++) {
            if (!input.get(i).isEmpty()) {
                slots.get(DefaultCraftingDisplay.getSlotWithSize(display.width, i, 3)).disableBackground().entries(input.get(i));
            }
        }

        // gemstone dust slots
        for (int x = 0; x < 5; x++) {
            slots.add(Widgets.createSlot(new Point(bounds.getCenterX() + x * 18 - 45, startPoint.y + 60)).disableBackground().markInput());
            if (!input.get(gemstoneDustStartSlot+x).isEmpty()) {
                slots.get(9+x).entries(input.get(gemstoneDustStartSlot + x));
            }
        }
        widgets.addAll(slots);

        // Output
        List<EntryIngredient> results = display.getOutputEntries();
        EntryIngredient result = EntryIngredient.of(results.get(0));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 95, startPoint.y + 19)).entries(result).disableBackground().markOutput());

        // the gemstone slot background texture                  destinationX                 destinationY       sourceX, sourceY, width, height
        widgets.add(Widgets.createTexturedWidget(GUI_TEXTURE, bounds.getCenterX() - 46, startPoint.y + 59, 43, 76, 90, 18));
        // crafting input texture
        widgets.add(Widgets.createTexturedWidget(GUI_TEXTURE, startPoint.x, startPoint.y, 28, 17, 54, 54));
        // crafting output texture
        widgets.add(Widgets.createTexturedWidget(GUI_TEXTURE, startPoint.x + 94 - 4, startPoint.y + 18 - 4, 122, 32, 26, 26));

        // description text
        // special handling for "1 second". Looks nicer
        TranslatableText text;
        if(display.craftingTime == 20) {
            text = new TranslatableText("container.pigment.rei.altar.crafting_time_one_second_and_xp", 1, display.experience);
        } else {
            text = new TranslatableText("container.pigment.rei.altar.crafting_time_and_xp", (display.craftingTime / 20), display.experience);
        }
        widgets.add(Widgets.createLabel(new Point(startPoint.x, startPoint.y + 82), text).leftAligned().color(0x3f3f3f).noShadow());

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 110;
    }

}
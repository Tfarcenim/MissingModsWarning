package tfar.missingmodswarning.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModDatagen {

    public static void start(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        dataGenerator.addProvider(true,new ModLangProvider(packOutput));
    }

}

package muramasa.gtu;

import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.machines.Tier;
import muramasa.antimatter.materials.MaterialType;
import muramasa.antimatter.registration.IAntimatterRegistrar;
import muramasa.antimatter.registration.RegistrationEvent;
import muramasa.gtu.common.Data;
import muramasa.gtu.cover.CoverConveyor;
import muramasa.gtu.cover.CoverPump;
import muramasa.gtu.data.Guis;
import muramasa.gtu.data.Machines;
import muramasa.gtu.data.Materials;
import muramasa.gtu.data.Structures;
import muramasa.gtu.datagen.GregTechBlockStateProvider;
import muramasa.gtu.datagen.GregTechItemModelProvider;
import muramasa.gtu.proxy.ClientHandler;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Ref.ID)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class GregTech implements IAntimatterRegistrar {

    public static GregTech INSTANCE;
    public static Logger LOGGER = LogManager.getLogger(Ref.ID);

    public GregTech() {
        INSTANCE = this;
        DistExecutor.runWhenOn(Dist.CLIENT, () -> ClientHandler::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        AntimatterAPI.registerInternalRegistrar(INSTANCE);
        //GregTechAPI.addRegistrar(new ForestryRegistrar());
        //GregTechAPI.addRegistrar(new GalacticraftRegistrar());
        //if (ModList.get().isLoaded(Ref.MOD_UB)) GregTechAPI.addRegistrar(new UndergroundBiomesRegistrar());
        //if (ModList.get().isLoaded(Ref.MOD_CT)) GregTechAPI.addRegistrar(new GregTechTweaker());
    }

    private void setup(final FMLCommonSetupEvent e) {
        AntimatterAPI.onRegistration(RegistrationEvent.INIT);

        //AntimatterCapabilities.register(); //TODO broken

        //OreGenHandler.init();

        //TODO Ref.CONFIG = new File(e.getModConfigurationDirectory(), "GregTech/");

        //new GregTechWorldGenerator();



        AntimatterAPI.onRegistration(RegistrationEvent.MATERIAL);
        AntimatterAPI.onRegistration(RegistrationEvent.DATA);

        AntimatterAPI.onRegistration(RegistrationEvent.DATA_READY);

        //if (ModList.get().isLoaded(Ref.MOD_TOP)) TheOneProbePlugin.init();

        AntimatterAPI.onRegistration(RegistrationEvent.WORLDGEN);
        //GregTechWorldGenerator.init();
        //if (!Configs.WORLD.ORE_JSON_RELOADING) GregTechWorldGenerator.reload();
        //AntimatterAPI.onRegistration(RegistrationEvent.RECIPE);

    }

    @SubscribeEvent
    public static void onDataGather(GatherDataEvent e) {
        DataGenerator gen = e.getGenerator();
        if (e.includeClient()) {
            gen.addProvider(new GregTechBlockStateProvider(gen, e.getExistingFileHelper()));
            gen.addProvider(new GregTechItemModelProvider(gen, e.getExistingFileHelper()));
        }
        if (e.includeServer()) {

        }
    }

    @Override
    public String getId() {
        return Ref.ID;
    }

    @Override
    public void onRegistrationEvent(RegistrationEvent event) {
        switch (event) {
            case DATA_BUILD:
                Materials.init();
                Data.init();
                Machines.init();
                Structures.init();
                break;
            case GUI:
                Guis.init();
                break;
            case ITEM:
                //GregTechAPI.registerFluidCell(Data.CellTin.get(1));
                //GregTechAPI.registerFluidCell(Data.CellSteel.get(1));
                //GregTechAPI.registerFluidCell(Data.CellTungstensteel.get(1));


                AntimatterAPI.registerCover(Data.COVER_PLATE);
                AntimatterAPI.registerCover(Data.COVER_CONVEYOR);
                AntimatterAPI.registerCover(Data.COVER_PUMP);

                AntimatterAPI.registerCoverStack(Data.ConveyorLV.get(1), new CoverConveyor(Tier.LV));
                AntimatterAPI.registerCoverStack(Data.ConveyorMV.get(1), new CoverConveyor(Tier.MV));
                AntimatterAPI.registerCoverStack(Data.ConveyorHV.get(1), new CoverConveyor(Tier.HV));
                AntimatterAPI.registerCoverStack(Data.ConveyorEV.get(1), new CoverConveyor(Tier.EV));
                AntimatterAPI.registerCoverStack(Data.ConveyorIV.get(1), new CoverConveyor(Tier.IV));
                AntimatterAPI.registerCoverStack(Data.PumpLV.get(1), new CoverPump(Tier.LV));
                AntimatterAPI.registerCoverStack(Data.PumpMV.get(1), new CoverPump(Tier.MV));
                AntimatterAPI.registerCoverStack(Data.PumpHV.get(1), new CoverPump(Tier.HV));
                AntimatterAPI.registerCoverStack(Data.PumpEV.get(1), new CoverPump(Tier.EV));
                AntimatterAPI.registerCoverStack(Data.PumpIV.get(1), new CoverPump(Tier.IV));
                MaterialType.PLATE.all().forEach(m -> AntimatterAPI.registerCoverStack(m.getPlate(1), Data.COVER_PLATE));
                break;
            case WORLDGEN:
                //WorldGenLoader.init();
                break;
            case DATA_READY:

                break;
            case RECIPE:
                //OreDictLoader.init();
                //CraftingRecipeLoader.init();
                //MaterialRecipeLoader.init();
                //MachineRecipeLoader.init();
                break;
        }
    }
}

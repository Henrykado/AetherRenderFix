package henrykado.aether_renderfix;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gildedgames.the_aether.client.PlayerGloveRenderer;

@Mod(
	modid = AetherRenderFix.MODID,
	name = AetherRenderFix.NAME,
	version = AetherRenderFix.VERSION,
	dependencies = "required-after:aether_legacy",
	acceptedMinecraftVersions = "[1.12.2]"
)
public class AetherRenderFix {
	public static final String MODID = "aether_renderfix";
	public static final String NAME = "Aether Render Fix";
	public static final String VERSION = "1.0";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	@SideOnly(Side.CLIENT)
	@EventHandler
	public void initialization(FMLInitializationEvent event)
	{
		if (ARF_Config.increaseGloveSize)
		{
			Class<PlayerGloveRenderer> gloveClass = PlayerGloveRenderer.class;
			Field gloveModel; Field slimGloveModel;
			try {
				gloveModel = gloveClass.getDeclaredField("gloveModel");
				slimGloveModel = gloveClass.getDeclaredField("slimGloveModel");
				gloveModel.setAccessible(true);
				slimGloveModel.setAccessible(true);
				gloveModel.set(null, new ModelBiped(0.5F));
				slimGloveModel.set(null, new ModelPlayer(0.5F, true));
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				LOGGER.info(e.toString());
			}
		}
		
		MinecraftForge.EVENT_BUS.register(new RenderHandler());
	}
}

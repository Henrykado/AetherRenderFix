package henrykado.aether_renderfix;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = AetherRenderFix.MODID)
public class ARF_Config {
	@RequiresMcRestart
	@Comment("Increases the size of gloves when rendered in 1st person, so it more closely matches it's size when on 3rd person")
	@Name("Bigger gloves in first person")
	public static boolean increaseGloveSize = true;
	
	@Comment("Hides the cape you have bound to your skin (e.g. the Migrator's cape) when you're wearing an Aether cape so they aren't both rendered at the same time")
	@Name("Hide vanilla cape when wearing an Aether cape")
	public static boolean aetherCapeOverVanilla = true;
	
	@RequiresMcRestart
	@Comment("Disables Quark's EmoteHandler, which stops it from resetting the player model, and thus fixes an issue where gloves are slightly offset")
	@Name("Fully disable Quark's emote rendering")
	public static boolean disableQuarkEmotes = false;
	
	@Mod.EventBusSubscriber(modid = AetherRenderFix.MODID)
	public static class EventHandler // for some reason making this class private breaks the Config sync
	{
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
		{
			if (event.getModID().equals(AetherRenderFix.MODID))
			{
				ConfigManager.sync(AetherRenderFix.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
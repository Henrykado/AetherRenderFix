package henrykado.aether_renderfix;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.api.player.util.IAccessoryInventory;
import com.gildedgames.the_aether.items.accessories.ItemAccessory;
import com.gildedgames.the_aether.player.PlayerAether;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderHandler {
	@SubscribeEvent
	public void preRenderPlayer(RenderPlayerEvent.Pre event)
	{		
		if (ARF_Config.aetherCapeOverVanilla)
		{
			if (event.getRenderer().getMainModel().bipedCape.isHidden)
				event.getRenderer().getMainModel().bipedCape.isHidden = false;
			
			IPlayerAether playerAether = AetherAPI.getInstance().get((AbstractClientPlayer)event.getEntityLiving());
			IAccessoryInventory accessories = playerAether.getAccessoryInventory();
			if (accessories.getStackInSlot(1).getItem() instanceof ItemAccessory && ((PlayerAether) playerAether).shouldRenderCape)
			{
			    event.getRenderer().getMainModel().bipedCape.isHidden = true;
			}
		}
	}
}

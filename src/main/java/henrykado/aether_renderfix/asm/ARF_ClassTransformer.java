package henrykado.aether_renderfix.asm;

import java.util.HashSet;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import henrykado.aether_renderfix.ARF_Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.launchwrapper.IClassTransformer;

public class ARF_ClassTransformer implements IClassTransformer, Opcodes {
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if(ARF_Config.disableQuarkEmotes && "vazkii.quark.vanity.client.emotes.EmoteHandler".equals(transformedName)) 
		{
			ClassNode classNode = new ClassNode();
			new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);
			
			for(MethodNode method : classNode.methods) {
				if ("updateEmotes".equals(method.name))
				{
					method.instructions.clear();
					method.instructions.add(new InsnNode(RETURN));
					break;
				}
			}

			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(writer);
			return writer.toByteArray();
		}
		
		if ("com.gildedgames.the_aether.client.renders.entities.layer.AccessoriesLayer".equals(transformedName))
		{
			ClassNode classNode = new ClassNode();
			new ClassReader(basicClass).accept(classNode, ClassReader.SKIP_FRAMES);
			
			/*StringWriter sw = new StringWriter();
			PrintWriter printer = new PrintWriter(sw);
			TraceClassVisitor visitor = new TraceClassVisitor(printer);
			classNode.accept(visitor);
			AetherRenderFix.LOGGER.info(sw.toString());*/
			
			for(MethodNode method : classNode.methods) 
			{
				if ("doRenderLayer".equals(method.name))
				{
					for (AbstractInsnNode node : method.instructions.toArray())
					{
						if (node instanceof LineNumberNode && ((LineNumberNode)node).line == 147)
						{
							method.instructions.insert(node, new MethodInsnNode(INVOKESTATIC, "henrykado/aether_renderfix/asm/ARF_ClassTransformer", "unHideCape", "()V", false)); 
							break;
						}
					}
					break;
				}
			}
			
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(writer);
			return writer.toByteArray();
		}
		
		return basicClass;
	}
	
	public static void unHideCape()
	{
		for (RenderPlayer renderPlayer : new HashSet<>(Minecraft.getMinecraft().getRenderManager().getSkinMap().values()))
		{
			renderPlayer.getMainModel().bipedCape.isHidden = false;
		}
	}
}

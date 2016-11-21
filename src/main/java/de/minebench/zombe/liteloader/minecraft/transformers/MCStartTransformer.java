package de.minebench.zombe.liteloader.minecraft.transformers;

import de.minebench.zombe.core.minecraft.ZTransformer;
import de.minebench.zombe.liteloader.minecraft.ObfTable;
import de.minebench.zombe.liteloader.minecraft.extended.ZRenderGlobal;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * @author dags_ <dags@dags.me>
 */

public class MCStartTransformer extends ZTransformer
{
    // Minecraft stuff
    private final String obfInitDesc = "()V";
    private final String srgInitDesc = "()V";
    //DF Stuff
    private final String zRenderGlobalClass = ZRenderGlobal.class.getName().replace('.','/');
    private final String obfRenderGlobalDesc = "(L" + ObfTable.Minecraft.obf + ";)V";
    private final String srgRenderGlobalDesc = "(L" + ObfTable.Minecraft.srg + ";)V";

    @Override
    public boolean matches(String transformedName, boolean isObf)
    {
        return transformedName.equals(getMinecraftClass(isObf));
    }

    @Override
    public byte[] transform(String transformedName, byte[] classBytes, boolean isObf)
    {
        try
        {
            ClassNode node = new ClassNode();
            ClassReader reader = new ClassReader(classBytes);
            reader.accept(node, 0);

            final String NAME = getInitMethod(isObf);
            final String DESC = getInitDesc(isObf);

            final TypeInsnNode matchType = getRenderGlobalTypeMatch(isObf);
            final MethodInsnNode matchMethod = getRenderGlobalMethodMatch(isObf);

            for (MethodNode mn : node.methods)
            {
                if (mn.name.equals(NAME) && mn.desc.equals(DESC))
                {
                    for (AbstractInsnNode n : mn.instructions.toArray())
                    {
                        if (n instanceof TypeInsnNode)
                        {
                            TypeInsnNode tn = (TypeInsnNode) n;
                            if (tn.desc.equals(matchType.desc))
                            {
                                mn.instructions.set(n, getZRenderGlobalInstNode());
                            }
                        }
                        else if (n instanceof MethodInsnNode)
                        {
                            MethodInsnNode min = (MethodInsnNode) n;
                            if (min.owner.equals(matchMethod.owner) && min.name.equals(matchMethod.name))
                            {
                                mn.instructions.set(n, getZRenderGlobalMethodNode(isObf));
                            }
                        }
                    }
                    break;
                }
            }

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            node.accept(writer);

            return writer.toByteArray();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return classBytes;
    }

    private TypeInsnNode getRenderGlobalTypeMatch(boolean isObf)
    {
        return new TypeInsnNode(Opcodes.NEW, isObf ? ObfTable.RenderGlobal.obf : ObfTable.RenderGlobal.srg);
    }

    private MethodInsnNode getRenderGlobalMethodMatch(boolean isObf)
    {
        return new MethodInsnNode(Opcodes.INVOKESPECIAL, isObf ? ObfTable.RenderGlobal.obf : ObfTable.RenderGlobal.srg, "<init>", getZRenderGlobalTargetDesc(isObf), false);
    }

    private String getMinecraftClass(boolean isObf)
    {
        return isObf ? ObfTable.Minecraft.obf : ObfTable.Minecraft.srg;
    }

    private String getInitMethod(boolean isObf)
    {
        return isObf ? ObfTable.init.obf : ObfTable.init.srg;
    }

    private String getInitDesc(boolean isObf)
    {
        return isObf ? obfInitDesc : srgInitDesc;
    }

    private TypeInsnNode getZRenderGlobalInstNode()
    {
        return new TypeInsnNode(Opcodes.NEW, zRenderGlobalClass);
    }

    private MethodInsnNode getZRenderGlobalMethodNode(boolean isObf)
    {
        return new MethodInsnNode(Opcodes.INVOKESPECIAL, zRenderGlobalClass, "<init>", getZRenderGlobalTargetDesc(isObf), false);
    }

    private String getZRenderGlobalTargetDesc(boolean isObf)
    {
        return isObf ? obfRenderGlobalDesc : srgRenderGlobalDesc;
    }
}

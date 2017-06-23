package de.zell.zb.profiler;

import static org.objectweb.asm.Opcodes.ACC_INTERFACE;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 *
 */
public class ClassAnalyzer extends ClassVisitor
{
    public static final String CONSTRUCTOR_NAME = "<init>";
    private String className;
    private boolean isInterface;

    public ClassAnalyzer(ClassWriter writer)
    {
        super(Opcodes.ASM4, writer);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces)
    {
        cv.visit(version, access, name, signature, superName, interfaces);
        isInterface = (access & ACC_INTERFACE) != 0;
        className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
    {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (!isInterface && mv != null && !name.equals(CONSTRUCTOR_NAME))
        {
            mv = new MethodExecutionAnalyzer(mv, className, name, desc);
            return mv;
        }
        return mv;
    }

    @Override
    public void visitEnd()
    {
        super.visitEnd();
    }

}

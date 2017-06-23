package de.zell.zb.profiler;

import static org.objectweb.asm.Opcodes.ASM4;

import org.objectweb.asm.MethodVisitor;

/**
 *
 */
public class MethodExecutionAnalyzer extends MethodVisitor
{
//    private static final Logger LOGGER = Logger.getLogger(MethodExecutionAnalyzer.class.getName());

    private long startNs;
    private final String className;
    private final String methodName;
    private final String methodDescription;

    public MethodExecutionAnalyzer(MethodVisitor methodVisitor, String className, String methodName, String methodDescription)
    {
        super(ASM4, methodVisitor);
        this.className = className;
        this.methodName = methodName;
        this.methodDescription = methodDescription;
    }

    @Override
    public void visitCode()
    {
        mv.visitCode();
        startNs = System.nanoTime();
    }

    @Override
    public void visitEnd()
    {
        final long endNs = System.nanoTime();
        final String msg = "Execution of: " + className + "#" + methodName + methodDescription + " takes " + (endNs - startNs) + " ns.";
        System.out.println(msg);
    }
}

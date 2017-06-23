package de.zell.zb.profiler;

import static org.objectweb.asm.Opcodes.ASM4;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.objectweb.asm.MethodVisitor;

/**
 *
 */
public class MethodExecutionAnalyzer extends MethodVisitor
{
    private static final Logger LOGGER = Logger.getLogger(MethodExecutionAnalyzer.class.getName());

    private long startNs;
    private final String methodName;
    private final String methodDescription;

    public MethodExecutionAnalyzer(MethodVisitor methodVisitor, String methodName, String methodDescription)
    {
        super(ASM4, methodVisitor);
        this.methodName = methodName;
        this.methodDescription = methodDescription;
    }

    @Override
    public void visitCode()
    {
        mv.visitCode();
        startNs = System.nanoTime();
        System.out.println("starts" + methodName);
        super.visitCode();
    }

    @Override
    public void visitEnd()
    {
        final long endNs = System.nanoTime();
        final String msg = "Execution of: " + methodName + methodDescription + " takes " + (endNs - startNs) + " ns.";
        LOGGER.log(Level.ALL, msg);
        System.out.println(msg);
        super.visitEnd();
    }
}

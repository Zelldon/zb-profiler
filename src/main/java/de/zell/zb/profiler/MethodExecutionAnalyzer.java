package de.zell.zb.profiler;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

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
        if (!className.contains("de/zell/zb/profiler"))
        {
            mv.visitMethodInsn(INVOKESTATIC, "de/zell/zb/profiler/ExecutionTimeCollector", "getInstance", "()Lde/zell/zb/profiler/ExecutionTimeCollector;");
            mv.visitLdcInsn(className);
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(INVOKEVIRTUAL, "de/zell/zb/profiler/ExecutionTimeCollector", "startMethod", "(Ljava/lang/String;Ljava/lang/String;)V");
            mv.visitCode();
            //            mv.visitMaxs(-1, -1);
        }
    }

    @Override
    public void visitInsn(int opcode)
    {
        if (!className.contains("de/zell/zb/profiler"))
        {
            System.out.println("Opcode: " + opcode);
            switch (opcode)
            {
                case Opcodes.ARETURN:
                case Opcodes.DRETURN:
                case Opcodes.FRETURN:
                case Opcodes.IRETURN:
                case Opcodes.LRETURN:
                case Opcodes.RETURN:
                case Opcodes.ATHROW:
                    mv.visitMethodInsn(INVOKESTATIC, "de/zell/zb/profiler/ExecutionTimeCollector", "getInstance", "()Lde/zell/zb/profiler/ExecutionTimeCollector;");
                    mv.visitLdcInsn(className);
                    mv.visitLdcInsn(methodName);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "de/zell/zb/profiler/ExecutionTimeCollector", "endMethod", "(Ljava/lang/String;Ljava/lang/String;)V");
                    mv.visitMaxs(-1, -1);

            }
        }
        mv.visitInsn(opcode);
    }
}

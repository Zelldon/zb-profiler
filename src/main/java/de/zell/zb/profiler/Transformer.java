package de.zell.zb.profiler;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 *
 */
public class Transformer implements ClassFileTransformer
{
    private final String filter;

    public Transformer(String filter)
    {
        this.filter = filter;
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
        throws IllegalClassFormatException
    {
        if (filter == null || filter.isEmpty() || className.startsWith(filter))
        {
            final ClassReader classReader = new ClassReader(classfileBuffer);
            final ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
            final ClassAnalyzer classAnalyzer = new ClassAnalyzer(classWriter);
            classReader.accept(classAnalyzer, 0);
            return classWriter.toByteArray();
        }
        return null;
    }

    //    public class Profile {
    //
    //        public static void start(String className, String methodName) {
    //            System.out.println(new StringBuilder(className)
    //                                   .append(‘\t’)
    //                                   .append(methodName)
    //                                   .append(“\tstart\t”)
    //                                   .append(System.currentTimeMillis()));
    //        }
    //
    //        public static void end(String className, String methodName) {
    //            System.out.println(new StringBuilder(className)
    //                                   .append(‘\t’)
    //                                   .append(methodName)
    //                                   .append(“\end\t”)
    //                                   .append(System.currentTimeMillis()));
    //        }
    //    }

    //    public class PerfMethodAdapter extends MethodAdapter
    //    {
    //        private String className, methodName;
    //
    //        public PerfMethodAdapter(MethodVisitor visitor, String className,
    //                                 String methodName) {
    //            super(visitor);
    //            className = className;
    //            methodName = methodName;
    //        }
    //
    //        public void visitCode() {
    //            this.visitLdcInsn(className);
    //            this.visitLdcInsn(methodName);
    //            this.visitMethodInsn(INVOKESTATIC,
    //                                 "sample/profiler/Profile",
    //                                 "start",
    //                                 "(Ljava/lang/String;Ljava/lang/String;)V");
    //            super.visitCode();
    //        }
    //
    //        public void visitInsn(int inst) {
    //            switch (inst) {
    //            case Opcodes.ARETURN:
    //            case Opcodes.DRETURN:
    //            case Opcodes.FRETURN:
    //            case Opcodes.IRETURN:
    //            case Opcodes.LRETURN:
    //            case Opcodes.RETURN:
    //            case Opcodes.ATHROW:
    //                this.visitLdcInsn(className);
    //                this.visitLdcInsn(methodName);
    //                this.visitMethodInsn(INVOKESTATIC,
    //                                     "sample/profiler/Profile",
    //                                     "end",
    //                                     "(Ljava/lang/String;Ljava/lang/String;)V");
    //                break;
    //            default:
    //                break;
    //            }
    //
    //            super.visitInsn(inst);
    //        }
    //    }

}

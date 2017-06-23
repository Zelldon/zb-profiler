package de.zell.zb.profiler;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import javassist.ClassPool;

/**
 *
 */
public class Transformer implements ClassFileTransformer
{
    private final Instrumentation instrumentation;
    private final ClassPool classPool;

    public Transformer(Instrumentation inst)
    {
        instrumentation = inst;
        classPool = ClassPool.getDefault();
        instrumentation.addTransformer(this);
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
        throws IllegalClassFormatException
    {
        System.out.print("Loading class: ");
        System.out.println(className);
        return classfileBuffer;
//        try
//        {
//            classPool.insertClassPath(new ByteArrayClassPath(className, classfileBuffer));
//            final CtClass cc = classPool.get(className);
//            final CtMethod[] methods = cc.getMethods();
//            for (int k = 0; k < methods.length; k++)
//            {
//                if (methods[k].getLongName().startsWith(className))
//                {
//                    methods[k].insertBefore("System.out.println(\"Entering " + methods[k].getLongName() + "\");");
//                    methods[k].insertAfter("System.out.println(\"Exiting " + methods[k].getLongName() + "\");");
//                }
//            }
//
//            // return the new bytecode array:
//            final byte[] newClassfileBuffer = cc.toBytecode();
//            return newClassfileBuffer;
//        }
//        catch (IOException ioe)
//        {
//            System.err.println(ioe.getMessage() + " transforming class " + className + "; returning uninstrumented    class");
//        }
//        catch (NotFoundException nfe)
//        {
//            System.err.println(nfe.getMessage() + " transforming class " + className + "; returning uninstrumented class");
//        }
//        catch (CannotCompileException cce)
//        {
//            System.err.println(cce.getMessage() + " transforming class " + className + "; returning uninstrumented class");
//        }
//        return null;
    }
}

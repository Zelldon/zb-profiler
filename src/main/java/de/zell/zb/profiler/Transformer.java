package de.zell.zb.profiler;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.*;

/**
 *
 */
public class Transformer implements ClassFileTransformer
{
    private final ClassPool classPool;

    public Transformer()
    {
        classPool = ClassPool.getDefault();
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
        throws IllegalClassFormatException
    {
        try
        {
            classPool.insertClassPath(new ByteArrayClassPath(className, classfileBuffer));
            final String correctClassName = className.replace('/', '.');
            final CtClass cc = classPool.get(correctClassName);
            final CtMethod[] methods = cc.getMethods();
            for (int k = 0; k < methods.length; k++)
            {

                final CtMethod method = methods[k];

                if (method.getLongName().startsWith(correctClassName))
                {
                    method.addLocalVariable("_startTime", CtClass.longType);
                    method.insertBefore("_startTime = System.nanoTime();");

                    method.insertAfter("System.out.println(\"Executing: " +
                                           method.getLongName() +
                                           " takes \" + (System.nanoTime() - _startTime));");
                }
            }

            // return the new bytecode array:
            final byte[] newClassfileBuffer = cc.toBytecode();
            return newClassfileBuffer;
        }
        catch (IOException ioe)
        {
            System.err.println(ioe.getMessage() + " transforming class " + className + "; returning uninstrumented    class");
        }
        catch (NotFoundException nfe)
        {
            System.err.println(nfe.getMessage() + " transforming class " + className + "; returning uninstrumented class");
        }
        catch (CannotCompileException cce)
        {
            System.err.println(cce.getMessage() + " transforming class " + className + "; returning uninstrumented class");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        return null;
    }
}

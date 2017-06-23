package de.zell.zb.profiler;

import java.lang.instrument.Instrumentation;

/**
 */
public class ProfilerMain
{
    public static void premain(String args, Instrumentation instrumentation)
    {
        System.out.println("PreMain: " + args);
        instrumentation.addTransformer(new Transformer(args));
    }

    public static void agentmain(String args, Instrumentation instrumentation)
    {
        System.out.println("Agentmain: " + args);
        instrumentation.addTransformer(new Transformer(args));
    }
}

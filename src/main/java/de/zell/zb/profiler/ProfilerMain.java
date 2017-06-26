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
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                ExecutionTimeCollector.getInstance().printTimes();
            }
        }));
    }

    public static void agentmain(String args, Instrumentation instrumentation)
    {
        System.out.println("Agentmain: " + args);
        instrumentation.addTransformer(new Transformer(args));
    }
}

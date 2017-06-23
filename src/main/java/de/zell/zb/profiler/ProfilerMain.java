package de.zell.zb.profiler;

import java.lang.instrument.Instrumentation;

/**
 * Hello world!
 *
 */
public class ProfilerMain
{
    public static void premain(String args, Instrumentation inst)
    {
        inst.addTransformer(new Transformer());

    }
}

package de.zell.zb.profiler.test;

/**
 *
 */
public class MainClass
{
    public static void main(String args[])
    {
        System.out.println("Hello World!");
        final Otherclass otherclass = new Otherclass();
        final int sum = otherclass.sum();
        otherclass.test();

    }
}

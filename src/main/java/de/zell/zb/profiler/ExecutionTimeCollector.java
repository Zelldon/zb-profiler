package de.zell.zb.profiler;

import java.util.*;

/**
 *
 */
public final class ExecutionTimeCollector
{
    private final Map<String, Deque<Long>> startTimes;
    private final Map<String, List<Long>> executionTimes;

    private static ExecutionTimeCollector collector;

    private ExecutionTimeCollector()
    {
        startTimes = new HashMap<>();
        executionTimes = new HashMap<>();
    }

    public static ExecutionTimeCollector getInstance()
    {
        if (null == collector)
        {
            collector = new ExecutionTimeCollector();
        }
        return collector;
    }

    public String createIdentifier(String className, String method)
    {
        return className + "#" + method;
    }

    public void startMethod(String className, String method)
    {
        System.out.println("start" + method);
        final String identifier = createIdentifier(className, method);
        Deque<Long> startDeque = startTimes.get(identifier);
        if (null == startDeque)
        {
            startDeque = new ArrayDeque<>();
            startTimes.put(identifier, startDeque);
        }
        startDeque.push(System.nanoTime());
    }

    public void endMethod(String className, String method)
    {
        System.out.println("end" + method);
        final String identifier = createIdentifier(className, method);
        final Deque<Long> startDeque = startTimes.get(identifier);
        final Long startTime = startDeque.pollFirst();
        List<Long> endList = executionTimes.get(identifier);
        if (null == endList)
        {
            endList = new ArrayList<>();
            executionTimes.put(identifier, endList);
        }
        endList.add(System.nanoTime() - startTime);
    }

    public void printTimes()
    {
        System.out.println("shutdown");
        final Set<String> methods = executionTimes.keySet();
        for (String method : methods)
        {
            System.out.println("Execution of " + method + " takes: ");
            final List<Long> times = executionTimes.get(method);
            for (Long time : times)
            {
                System.out.println("\t" + time + " ns.");
            }
        }
    }

}

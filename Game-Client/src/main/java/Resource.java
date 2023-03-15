// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


public final class Resource extends Cacheable {

    public Resource()
    {
        incomplete = true;
    }

    int dataType;
    byte buffer[];
    int ID;
    boolean incomplete;
    int loopCycle;
}

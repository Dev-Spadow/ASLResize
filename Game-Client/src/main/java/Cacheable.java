// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


public class Cacheable extends Linkable {

    public final void unlinkSub()
    {
        if(nextNodeSub == null)
        {
        } else
        {
            nextNodeSub.prevNodeSub = prevNodeSub;
            prevNodeSub.nextNodeSub = nextNodeSub;
            prevNodeSub = null;
            nextNodeSub = null;
        }
    }

    public Cacheable()
    {
    }

    public Cacheable prevNodeSub;
    Cacheable nextNodeSub;
    public static int anInt1305;
}

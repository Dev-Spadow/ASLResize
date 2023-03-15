// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import sign.Signlink;

final class NodeCache {

    public NodeCache()
    {
        int i = 1024;//was parameter
        size = i;
        cache = new Linkable[i];
        for(int k = 0; k < i; k++)
        {
            Linkable linkable = cache[k] = new Linkable();
            linkable.prev = linkable;
            linkable.next = linkable;
        }

    }

    public Linkable findNodeByID(long l)
    {
        Linkable linkable = cache[(int)(l & (long)(size - 1))];
        for(Linkable linkable_1 = linkable.prev; linkable_1 != linkable; linkable_1 = linkable_1.prev)
            if(linkable_1.id == l)
                return linkable_1;

        return null;
    }

    public void removeFromCache(Linkable linkable, long l)
    {
        try
        {
            if(linkable.next != null)
                linkable.unlink();
            Linkable linkable_1 = cache[(int)(l & (long)(size - 1))];
                linkable.next = linkable_1.next;
                linkable.prev = linkable_1;
                linkable.next.prev = linkable;
                linkable.prev.next = linkable;
                linkable.id = l;
                return;
        }
        catch(RuntimeException runtimeexception)
        {
            Signlink.reporterror("91499, " + linkable + ", " + l + ", " + (byte)7 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private final int size;
    private final Linkable[] cache;
}

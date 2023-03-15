// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

final class Deque {

    public Deque()
    {
        head = new Linkable();
        head.prev = head;
        head.next = head;
    }

    public void insertHead(Linkable linkable)
    {
        if(linkable.next != null)
            linkable.unlink();
        linkable.next = head.next;
        linkable.prev = head;
        linkable.next.prev = linkable;
        linkable.prev.next = linkable;
    }

    public void insertTail(Linkable linkable)
    {
        if(linkable.next != null)
            linkable.unlink();
        linkable.next = head;
        linkable.prev = head.prev;
        linkable.next.prev = linkable;
        linkable.prev.next = linkable;
    }

    public Linkable popHead()
    {
        Linkable linkable = head.prev;
        if(linkable == head)
        {
            return null;
        } else
        {
            linkable.unlink();
            return linkable;
        }
    }

    public Linkable reverseGetFirst()
    {
        Linkable linkable = head.prev;
        if(linkable == head)
        {
            current = null;
            return null;
        } else
        {
            current = linkable.prev;
            return linkable;
        }
    }

    public Linkable getFirst()
    {
        Linkable linkable = head.next;
        if(linkable == head)
        {
            current = null;
            return null;
        } else
        {
            current = linkable.next;
            return linkable;
        }
    }

    public Linkable reverseGetNext()
    {
        Linkable linkable = current;
        if(linkable == head)
        {
            current = null;
            return null;
        } else
        {
            current = linkable.prev;
            return linkable;
        }
    }

    public Linkable getNext()
    {
        Linkable linkable = current;
        if(linkable == head)
        {
            current = null;
            return null;
        }
        current = linkable.next;
            return linkable;
    }

    public void removeAll()
    {
        if(head.prev == head)
            return;
        do
        {
            Linkable linkable = head.prev;
            if(linkable == head)
                return;
            linkable.unlink();
        } while(true);
    }

    private final Linkable head;
    private Linkable current;
}

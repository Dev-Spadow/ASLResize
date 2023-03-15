import java.io.*;
import java.net.Socket;
import java.util.zip.CRC32;
import java.util.zip.GZIPInputStream;
import sign.Signlink;

public final class ResourceProvider extends OnDemandFetcherParent
        implements Runnable
{

    public void readData()
    {
        try
        {
            int j = inputStream.available();
            if(expectedSize == 0 && j >= 6)
            {
                waiting = true;
                for(int k = 0; k < 6; k += inputStream.read(ioBuffer, k, 6 - k));
                int l = ioBuffer[0] & 0xff;
                int j1 = ((ioBuffer[1] & 0xff) << 8) + (ioBuffer[2] & 0xff);
                int l1 = ((ioBuffer[3] & 0xff) << 8) + (ioBuffer[4] & 0xff);
                int i2 = ioBuffer[5] & 0xff;
                current = null;
                for(Resource resource = (Resource) requested.reverseGetFirst(); resource != null; resource = (Resource) requested.reverseGetNext())
                {
                    if(resource.dataType == l && resource.ID == j1)
                        current = resource;
                    if(current != null)
                        resource.loopCycle = 0;
                }

                if(current != null)
                {
                    loopCycle = 0;
                    if(l1 == 0)
                    {
                        Signlink.reporterror("Rej: " + l + "," + j1);
                        current.buffer = null;
                        if(current.incomplete)
                            synchronized(aClass19_1358)
                            {
                                aClass19_1358.insertHead(current);
                            }
                        else
                            current.unlink();
                        current = null;
                    } else
                    {
                        if(current.buffer == null && i2 == 0)
                            current.buffer = new byte[l1];
                        if(current.buffer == null && i2 != 0)
                            throw new IOException("missing start of file");
                    }
                }
                completedSize = i2 * 500;
                expectedSize = 500;
                if(expectedSize > l1 - i2 * 500)
                    expectedSize = l1 - i2 * 500;
            }
            if(expectedSize > 0 && j >= expectedSize)
            {
                waiting = true;
                byte abyte0[] = ioBuffer;
                int i1 = 0;
                if(current != null)
                {
                    abyte0 = current.buffer;
                    i1 = completedSize;
                }
                for(int k1 = 0; k1 < expectedSize; k1 += inputStream.read(abyte0, k1 + i1, expectedSize - k1));
                if(expectedSize + completedSize >= abyte0.length && current != null)
                {
                    if(clientInstance.decompressors[0] != null)
                        clientInstance.decompressors[current.dataType + 1].method234(abyte0.length, abyte0, current.ID);
                    if(!current.incomplete && current.dataType == 3)
                    {
                        current.incomplete = true;
                        current.dataType = 93;
                    }
                    if(current.incomplete)
                        synchronized(aClass19_1358)
                        {
                            aClass19_1358.insertHead(current);
                        }
                    else
                        current.unlink();
                }
                expectedSize = 0;
            }
        }
        catch(IOException ioexception)
        {
            try
            {
                socket.close();
            }
            catch(Exception _ex) { }
            socket = null;
            inputStream = null;
            outputStream = null;
            expectedSize = 0;
        }
    }

    public void start(FileArchive fileArchive, Client client1)
    {
        byte[] abyte2 = fileArchive.readFile("map_index");
        Buffer buffer2 = new Buffer(abyte2);
        int j1 = abyte2.length / 7;
        mapIndices1 = new int[j1];
        mapIndices2 = new int[j1];
        mapIndices3 = new int[j1];
        mapIndices4 = new int[j1];
        for(int i2 = 0; i2 < j1; i2++)
        {
            mapIndices1[i2] = buffer2.readUShort();
            mapIndices2[i2] = buffer2.readUShort();
            mapIndices3[i2] = buffer2.readUShort();
            mapIndices4[i2] = buffer2.readUnsignedByte();
        }

        abyte2 = fileArchive.readFile("midi_index");
        buffer2 = new Buffer(abyte2);
        j1 = abyte2.length;
        anIntArray1348 = new int[j1];
        for(int k2 = 0; k2 < j1; k2++)
            anIntArray1348[k2] = buffer2.readUnsignedByte();

        clientInstance = client1;
        running = true;
        clientInstance.startRunnable(this, 2);
    }

    public int getNodeCount()
    {
        synchronized(nodeSubList)
        {
            return nodeSubList.getNodeCount();
        }
    }

    public void disable()
    {
        running = false;
    }

    public void method554(boolean flag)
    {
        int j = mapIndices1.length;
        for(int k = 0; k < j; k++)
            if(flag || mapIndices4[k] != 0)
            {
                method563((byte)2, 3, mapIndices3[k]);
                method563((byte)2, 3, mapIndices2[k]);
            }

    }
 
    public int getModelCount()
    {
	return 41761;
    }
	
	public void closeRequest(Resource resource)
	{
		try
		{
			if(socket == null)
			{
				long l = System.currentTimeMillis();
				if(l - openSocketTime < 4000L)
					return;
				openSocketTime = l;
				socket = clientInstance.openSocket(43593);
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				outputStream.write(15);
				for(int j = 0; j < 8; j++)
					inputStream.read();

				loopCycle = 0;
			}
			ioBuffer[0] = (byte) resource.dataType;
			ioBuffer[1] = (byte)(resource.ID >> 8);
			ioBuffer[2] = (byte) resource.ID;
			if(resource.incomplete)
				ioBuffer[3] = 2;
			else
			if(!clientInstance.loggedIn)
				ioBuffer[3] = 1;
			else
				ioBuffer[3] = 0;
			outputStream.write(ioBuffer, 0, 4);
			writeLoopCycle = 0;
			anInt1349 = -10000;
			return;
		}
		catch(IOException ioexception) { }
		try
		{
			socket.close();
		}
		catch(Exception _ex) { }
		socket = null;
		inputStream = null;
		outputStream = null;
		expectedSize = 0;
		anInt1349++;
	}

    public int getAnimCount()
    {
        return 29192;
    }

    public void provide(int i, int j)
    {
        if(i < 0 || j < 0)
            return;
        synchronized(nodeSubList)
        {
            for(Resource resource = (Resource) nodeSubList.reverseGetFirst(); resource != null; resource = (Resource) nodeSubList.reverseGetNext())
                if(resource.dataType == i && resource.ID == j)
                    return;

            Resource resource_1 = new Resource();
            resource_1.dataType = i;
            resource_1.ID = j;
            resource_1.incomplete = true;
            synchronized(mandatoryRequests)
            {
                mandatoryRequests.insertHead(resource_1);
            }
            nodeSubList.insertHead(resource_1);
        }
    }

    public void run()
    {
        try
        {
            while(running)
            {
                onDemandCycle++;
                int i = 20;
                if(anInt1332 == 0 && clientInstance.decompressors[0] != null)
                    i = 50;
                try
                {
                    Thread.sleep(i);
                }
                catch(Exception _ex) { }
                waiting = true;
                for(int j = 0; j < 100; j++)
                {
                    waiting = false;
                    checkReceived();
                    handleFailed();
                    if(uncompletedCount == 0 && j >= 5)
                        break;
                    method568();
                    if(inputStream != null)
                        readData();
                }

                boolean flag = false;
                for(Resource resource = (Resource) requested.reverseGetFirst(); resource != null; resource = (Resource) requested.reverseGetNext())
                    if(resource.incomplete)
                    {
                        flag = true;
                        resource.loopCycle++;
                        if(resource.loopCycle > 50)
                        {
                            resource.loopCycle = 0;
                            closeRequest(resource);
                        }
                    }

                if(!flag)
                {
                    for(Resource resource_1 = (Resource) requested.reverseGetFirst(); resource_1 != null; resource_1 = (Resource) requested.reverseGetNext())
                    {
                        flag = true;
                        resource_1.loopCycle++;
                        if(resource_1.loopCycle > 50)
                        {
                            resource_1.loopCycle = 0;
                            closeRequest(resource_1);
                        }
                    }

                }
                if(flag)
                {
                    loopCycle++;
                    if(loopCycle > 750)
                    {
                        try
                        {
                            socket.close();
                        }
                        catch(Exception _ex) { }
                        socket = null;
                        inputStream = null;
                        outputStream = null;
                        expectedSize = 0;
                    }
                } else
                {
                    loopCycle = 0;
                    statusString = "";
                }
                if(clientInstance.loggedIn && socket != null && outputStream != null && (anInt1332 > 0 || clientInstance.decompressors[0] == null))
                {
                    writeLoopCycle++;
                    if(writeLoopCycle > 500)
                    {
                        writeLoopCycle = 0;
                        ioBuffer[0] = 0;
                        ioBuffer[1] = 0;
                        ioBuffer[2] = 0;
                        ioBuffer[3] = 10;
                        try
                        {
                            outputStream.write(ioBuffer, 0, 4);
                        }
                        catch(IOException _ex)
                        {
                            loopCycle = 5000;
                        }
                    }
                }
            }
        }
        catch(Exception exception)
        {
            Signlink.reporterror("od_ex " + exception.getMessage());
	    exception.printStackTrace();
        }
    }

    public void method560(int i, int j)
    {
        if(clientInstance.decompressors[0] == null)
            return;
        if(anInt1332 == 0)
            return;
        Resource resource = new Resource();
        resource.dataType = j;
        resource.ID = i;
        resource.incomplete = false;
        synchronized(aClass19_1344)
        {
            aClass19_1344.insertHead(resource);
        }
    }

    public Resource getNextNode()
    {
        Resource resource;
        synchronized(aClass19_1358)
        {
            resource = (Resource)aClass19_1358.popHead();
        }
        if(resource == null)
            return null;
        synchronized(nodeSubList)
        {
            resource.unlinkSub();
        }
        if(resource.buffer == null)
            return resource;
        int i = 0;
        try
        {
            GZIPInputStream gzipinputstream = new GZIPInputStream(new ByteArrayInputStream(resource.buffer));
            do
            {
                if(i == gzipInputBuffer.length)
                    throw new RuntimeException("payload overflow!");
                int k = gzipinputstream.read(gzipInputBuffer, i, gzipInputBuffer.length - i);
                if(k == -1)
                    break;
                i += k;
            } while(true);
        }
        catch(IOException _ex)
        {
            throw new RuntimeException("error unzipping");
        }
        resource.buffer = new byte[i];
        System.arraycopy(gzipInputBuffer, 0, resource.buffer, 0, i);

        return resource;
    }

    public int method562(int i, int k, int l)
    {
        int i1 = (l << 8) + k;
        for(int j1 = 0; j1 < mapIndices1.length; j1++)
	{
            if(mapIndices1[j1] == i1)
	    {
                if(i == 0)
                    return mapIndices2[j1];
                else
                    return mapIndices3[j1];
	    }
	}
        return -1;
    }

    public void method548(int i)
    {
        provide(0, i);
    }

    public void method563(byte byte0, int i, int j)
    {
        if(clientInstance.decompressors[0] == null)
            return;
        byte abyte0[] = clientInstance.decompressors[i + 1].decompress(j);
        fileStatus[i][j] = byte0;
        if(byte0 > anInt1332)
            anInt1332 = byte0;
        totalFiles++;
    }

    public boolean landscapePresent(int i)
    {
        for(int k = 0; k < mapIndices1.length; k++)
            if(mapIndices3[k] == i)
                return true;
        return false;
    }

    public void handleFailed()
    {
        uncompletedCount = 0;
        completedCount = 0;
        for(Resource resource = (Resource) requested.reverseGetFirst(); resource != null; resource = (Resource) requested.reverseGetNext())
            if(resource.incomplete)
                uncompletedCount++;
            else
                completedCount++;

        while(uncompletedCount < 10)
        {
            Resource resource_1 = (Resource)aClass19_1368.popHead();
            if(resource_1 == null)
                break;
            if(fileStatus[resource_1.dataType][resource_1.ID] != 0)
                filesLoaded++;
            fileStatus[resource_1.dataType][resource_1.ID] = 0;
            requested.insertHead(resource_1);
            uncompletedCount++;
            closeRequest(resource_1);
            waiting = true;
        }
    }

    public void method566()
    {
        synchronized(aClass19_1344)
        {
            aClass19_1344.removeAll();
        }
    }

    public void checkReceived()
    {
        Resource resource;
        synchronized(mandatoryRequests)
        {
            resource = (Resource) mandatoryRequests.popHead();
        }
        while(resource != null)
        {
            waiting = true;
            byte abyte0[] = null;
            if(clientInstance.decompressors[0] != null)
                abyte0 = clientInstance.decompressors[resource.dataType + 1].decompress(resource.ID);
            synchronized(mandatoryRequests)
            {
                if(abyte0 == null)
                {
                    aClass19_1368.insertHead(resource);
                } else
                {
                    resource.buffer = abyte0;
                    synchronized(aClass19_1358)
                    {
                        aClass19_1358.insertHead(resource);
                    }
                }
                resource = (Resource) mandatoryRequests.popHead();
            }
        }
    }

    public void method568()
    {
        while(uncompletedCount == 0 && completedCount < 10)
        {
            if(anInt1332 == 0)
                break;
            Resource resource;
            synchronized(aClass19_1344)
            {
                resource = (Resource)aClass19_1344.popHead();
            }
            while(resource != null)
            {
                if(fileStatus[resource.dataType][resource.ID] != 0)
                {
                    fileStatus[resource.dataType][resource.ID] = 0;
                    requested.insertHead(resource);
                    closeRequest(resource);
                    waiting = true;
                    if(filesLoaded < totalFiles)
                        filesLoaded++;
                    statusString = "Loading extra files - " + (filesLoaded * 100) / totalFiles + "%";
                    completedCount++;
                    if(completedCount == 10)
                        return;
                }
                synchronized(aClass19_1344)
                {
                    resource = (Resource)aClass19_1344.popHead();
                }
            }
            for(int j = 0; j < 4; j++)
            {
                byte abyte0[] = fileStatus[j];
                int k = abyte0.length;
                for(int l = 0; l < k; l++)
                    if(abyte0[l] == anInt1332)
                    {
                        abyte0[l] = 0;
                        Resource resource_1 = new Resource();
                        resource_1.dataType = j;
                        resource_1.ID = l;
                        resource_1.incomplete = false;
                        requested.insertHead(resource_1);
                        closeRequest(resource_1);
                        waiting = true;
                        if(filesLoaded < totalFiles)
                            filesLoaded++;
                        statusString = "Loading extra files - " + (filesLoaded * 100) / totalFiles + "%";
                        completedCount++;
                        if(completedCount == 10)
                            return;
                    }

            }

            anInt1332--;
        }
    }

    public boolean method569(int i)
    {
        return anIntArray1348[i] == 1;
    }
	
	public int getVersionCount(int j) {
		return versions[j].length;
	}

	public int getModelIndex(int i) {
		return modelIndices[i] & 0xff;
	}

    public ResourceProvider()
    {
        requested = new Deque();
        statusString = "";
        crc32 = new CRC32();
        ioBuffer = new byte[500];
        fileStatus = new byte[4][];
        aClass19_1344 = new Deque();
        running = true;
        waiting = false;
        aClass19_1358 = new Deque();
        gzipInputBuffer = new byte[0x71868];
        nodeSubList = new NodeSubList();
		versions = new int[4][];
        crcs = new int[4][];
        aClass19_1368 = new Deque();
        mandatoryRequests = new Deque();
    }

    public int totalFiles;
    public final Deque requested;
    public int anInt1332;
    public String statusString;
    public int writeLoopCycle;
    public long openSocketTime;
    public int[] mapIndices3;
    public final CRC32 crc32;
    public final byte[] ioBuffer;
    public int onDemandCycle;
    public final byte[][] fileStatus;
    public Client clientInstance;
    public final Deque aClass19_1344;
    public int completedSize;
    public int expectedSize;
    public int[] anIntArray1348;
    public int anInt1349;
    public int[] mapIndices2;
    public int filesLoaded;
    public boolean running;
    public OutputStream outputStream;
    public int[] mapIndices4;
    public boolean waiting;
    public final Deque aClass19_1358;
    public final byte[] gzipInputBuffer;
    public int[] anIntArray1360;
    public final NodeSubList nodeSubList;
    public InputStream inputStream;
    public Socket socket;
    public final int[][] crcs;
    public int uncompletedCount;
    public int completedCount;
    public final Deque aClass19_1368;
    public Resource current;
    public final Deque mandatoryRequests;
    public int[] mapIndices1;
    public byte[] modelIndices;
    public int loopCycle;
	public final int[][] versions;
}

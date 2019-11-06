package com.justa.test.aws.cache;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

// https://docs.aws.amazon.com/AmazonElastiCache/latest/mem-ug/AutoDiscovery.Using.html#AutoDiscovery.Using.ModifyApp
public class AutoDiscoveryDemo {
    public static void main(String[] args) throws IOException {
        
        String configEndpoint = "mycache.qn5y3f.cfg.use1.cache.amazonaws.com";
        Integer clusterPort = 11211;

        MemcachedClient client = new MemcachedClient(
                                 new InetSocketAddress(configEndpoint, 
                                                       clusterPort));       
        // The client will connect to the other cache nodes automatically.

        // Store a data item for an hour.  
        // The client will decide which cache host will store this item. 
        
        System.out.println("add status: "+client.set("theKey", 3600, "This is the data value"));
        
        Object value = client.get("theKey");
        System.out.println(value);
    }
}

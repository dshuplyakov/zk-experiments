package com.dshuplyakov.recipe;

import com.dshuplyakov.ZookeeperInit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

public class NodeCacheApp {

    public static void main(String[] args) throws Exception {
        ZookeeperInit zookeeperInit = new ZookeeperInit();
        zookeeperInit.init();

        CuratorFramework curatorFramework = zookeeperInit.getCuratorFrameworkClient();

        final NodeCache nodeCache = new NodeCache(curatorFramework, "/cache");
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                ChildData currentData = nodeCache.getCurrentData();
                System.out.println("Data change watched, and current data = " + new String(currentData.getData()));
            }
        });

        nodeCache.start(true);

        Thread.sleep(Integer.MAX_VALUE);
    }
}

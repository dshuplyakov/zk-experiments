package com.dshuplyakov;

import org.apache.curator.framework.CuratorFramework;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception  {
        ZookeeperInit zookeeperInit = new ZookeeperInit();
        zookeeperInit.init();

        CuratorFramework curatorFramework = zookeeperInit.getCuratorFrameworkClient();

        ZookeperDao zookeperDao = new ZookeperDao(zookeeperInit.getCuratorFrameworkClient());

        zookeperDao.setData("101", "/test2");

        curatorFramework.checkExists().usingWatcher(new MyWatcher(curatorFramework, "/test2")).forPath("/test2");

        Thread.sleep(Integer.MAX_VALUE);

    }
}

package com.dshuplyakov.helloworld;

import com.dshuplyakov.ZookeeperInit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * Date: 06.11.2018
 * Time: 19:50
 *
 * @author Dmitry Shuplyakov
 */
public class EphemeralZnode {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ZookeeperInit.getCuratorFrameworkClient();
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/app");

        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/app");

        Thread.sleep(Integer.MAX_VALUE);
    }
}

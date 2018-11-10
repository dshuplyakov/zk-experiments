package com.dshuplyakov.helloworld;

import com.dshuplyakov.ZookeeperInit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

/**
 * Date: 06.11.2018
 * Time: 20:08
 *
 * @author Dmitry Shuplyakov
 */
public class WatcherExample {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ZookeeperInit.getCuratorFrameworkClient();
        client.checkExists().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent event) throws Exception {
                System.out.println(event);
            }
        }).forPath("/app_status");

        Thread.sleep(Integer.MAX_VALUE);
    }
}

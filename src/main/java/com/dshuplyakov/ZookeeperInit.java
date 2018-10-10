package com.dshuplyakov;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Date: 10.10.2018
 * Time: 22:09
 *
 * @author Dmitry Shuplyakov
 */
public class ZookeeperInit {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperInit.class);

    private CuratorFramework curatorFrameworkClient;

    public void init() {
        //building client
        curatorFrameworkClient = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .retryPolicy(new RetryNTimes(3, 1000))
                .sessionTimeoutMs(20000)
                .connectionTimeoutMs(1000)
                .build();
        curatorFrameworkClient.start();
        //block thread until zk connection is established


        try {
            curatorFrameworkClient.getZookeeperClient().blockUntilConnectedOrTimedOut();
        } catch (InterruptedException e) {
            throw  new IllegalStateException(e);
        }
        //manually check connection status, cause CuratorFramework does not throw any exception
        if (!curatorFrameworkClient.getZookeeperClient().isConnected()) {
            throw new IllegalStateException("Can't connect to Zookeeper");
        }

        LOGGER.info("Connected to Zookeeper");
    }

    public CuratorFramework getCuratorFrameworkClient() {
        return curatorFrameworkClient;
    }
}


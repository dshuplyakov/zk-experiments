package com.dshuplyakov.helloworld;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

/**
 * Date: 03.11.2018
 * Time: 12:44
 *
 * @author Dmitry Shuplyakov
 */
public class ZookeeperExample {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        ZooKeeper zkClient = new ZooKeeper("localhost:2181", 10000, event -> System.out.println(event.getState()));
        Thread.sleep(1000);

        zkClient.create("/abc2", "summer".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zkClient.setData("/abc2", "winter".getBytes(), -1);

        zkClient.getData("/abc2", false, null);

        List<String> zkNodes = zkClient.getChildren("/", true);
        System.out.println(zkNodes);
    }
}

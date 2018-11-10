package com.dshuplyakov.helloworld;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;

import java.util.List;

/**
 * Date: 03.11.2018
 * Time: 12:58
 *
 * @author Dmitry Shuplyakov
 */
public class CuratorExample {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .retryPolicy(new RetryOneTime(1000))
                .connectString("localhost:2181")
                .build();

        curatorFramework.start();
        curatorFramework.blockUntilConnected();

        curatorFramework.create().forPath("/jon_snow");
        curatorFramework.setData().forPath("/jon_snow", "mather of dragons".getBytes());
        curatorFramework.getData().forPath("/jon_snow");
        List<String> zkNodes = curatorFramework.getChildren().forPath("/");
        System.out.println(zkNodes);
    }
}

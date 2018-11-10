package com.dshuplyakov.recipes;

import com.dshuplyakov.ZookeeperInit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;

/**
 * Date: 13.10.2018
 * Time: 22:49
 *
 * @author Dmitry Shuplyakov
 */

//java -cp zk-jar-with-dependencies.jar com.dshuplyakov.recipe.DoubleBarier 1
public class DoubleBarier {

    private static final int QTY = 5;
    private static final String PATH = "/examples/barrier";

    public static void main(String[] args) throws InterruptedException {
        String clientId = args[0];
        CuratorFramework curatorFramework = ZookeeperInit.getCuratorFrameworkClient();
        final DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(curatorFramework, PATH, QTY);
        try {
            Thread.sleep((long) (30000 * Math.random()));
            System.out.println("Client #" + clientId + " wait before enter...");
            barrier.enter();
            System.out.println("Client #" + clientId + " entered! Doing some work... and wait until others client do");
            Thread.sleep((long) (30000 * Math.random()));
            barrier.leave();
            System.out.println("Client #" + clientId + " left");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

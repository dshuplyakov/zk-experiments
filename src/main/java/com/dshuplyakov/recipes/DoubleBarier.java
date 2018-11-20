package com.dshuplyakov.recipes;

import com.dshuplyakov.ZookeeperInit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.utils.ZKPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Date: 13.10.2018
 * Time: 22:49
 *
 * @author Dmitry Shuplyakov
 */

//java -cp zk-jar-with-dependencies.jar com.dshuplyakov.recipe.DoubleBarier 1
public class DoubleBarier {

    private static final int QTY = 2;
    private static final String PATH = "/examples/barrier";

    private static final Logger LOGGER = LoggerFactory.getLogger(DoubleBarier.class);

    public static void main(String[] args) throws InterruptedException {
        CuratorFramework curatorFramework = ZookeeperInit.getCuratorFrameworkClient();
        final DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(curatorFramework, PATH, QTY);
        try {
            LOGGER.info("Client # wait before enter...");
            barrier.enter(30, TimeUnit.SECONDS);
            LOGGER.info("Doing some work... ");
            //читаем значение в локальную переменую.

            if (args.length>0) {
                System.out.println("exit");
                Thread.sleep(Long.MAX_VALUE);
            }

            Thread.sleep(7000);
            LOGGER.info("wait until others client finished");
            boolean result = barrier.leave(3, TimeUnit.SECONDS);
            LOGGER.info("Client #  left {}", result);
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
        }
    }

}

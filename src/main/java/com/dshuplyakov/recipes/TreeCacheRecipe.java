package com.dshuplyakov.recipes;

import com.dshuplyakov.ZookeeperInit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 20.11.2018
 * Time: 21:58
 *
 * @author Dmitry Shuplyakov
 */
public class TreeCacheRecipe {

    private static final Logger LOGGER = LoggerFactory.getLogger(TreeCacheRecipe.class);

    private TreeCache cache ;

    public static void main(String[] args) throws Exception {
        new TreeCacheRecipe().start();
    }

    private void start() throws Exception {
        CuratorFramework curatorFramework = ZookeeperInit.getCuratorFrameworkClient();
        cache = new TreeCache(curatorFramework, "/configuration");
        cache.getListenable().addListener(( client, event) -> {
            System.out.println(event);
        });
        cache.start();
        addShutdownHook();

        Thread.sleep(Integer.MAX_VALUE);
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                cache.close();
            }
        });
    }
}

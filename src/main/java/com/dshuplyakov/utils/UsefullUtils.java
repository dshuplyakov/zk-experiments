package com.dshuplyakov.utils;

import com.dshuplyakov.ZookeeperInit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.PathUtils;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.ZooKeeper;

/**
 * Date: 12.11.2018
 * Time: 23:36
 *
 * @author Dmitry Shuplyakov
 */
public class UsefullUtils {
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = ZookeeperInit.getCuratorFrameworkClient();
        ZooKeeper zookeeper = curatorFramework.getZookeeperClient().getZooKeeper();


        PathUtils.validatePath("/path");

        ZKPaths.makePath("father/", "/child1/", "/child2", "child3");
        // /father/child1/child2/child3

        ZKPaths.PathAndNode pathAndNode = ZKPaths.getPathAndNode("/father/child1/child2");
        // /father/child1/child2=child3

        ZKPaths.getSortedChildren(zookeeper, "path");

        ZKPaths.mkdirs(zookeeper, "/father/child1/child2");

    }
}

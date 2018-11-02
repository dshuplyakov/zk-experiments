package com.dshuplyakov.recipes;

import com.dshuplyakov.ZookeeperInit;
import org.apache.curator.framework.CuratorFramework;


//java -cp zk-jar-with-dependencies.jar com.dshuplyakov.recipe.GroupMember 1
public class GroupMember {

    public static void main(String[] args) throws Exception {
        ZookeeperInit zookeeperInit = new ZookeeperInit();
        zookeeperInit.init();

        CuratorFramework curatorFramework = zookeeperInit.getCuratorFrameworkClient();
        org.apache.curator.framework.recipes.nodes.GroupMember
                group = new org.apache.curator.framework.recipes.nodes.GroupMember(curatorFramework, "/membership", args[0]);
        group.start();

        Thread.sleep(Integer.MAX_VALUE);
    }
}

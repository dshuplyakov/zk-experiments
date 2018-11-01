package com.dshuplyakov.recipe;

import com.dshuplyakov.ZookeeperInit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.nodes.GroupMember;


//java -cp zk-jar-with-dependencies.jar com.dshuplyakov.recipe.GroupMemberApp 1
public class GroupMemberApp {

    public static void main(String[] args) throws Exception {
        ZookeeperInit zookeeperInit = new ZookeeperInit();
        zookeeperInit.init();

        CuratorFramework curatorFramework = zookeeperInit.getCuratorFrameworkClient();
        GroupMember group = new GroupMember(curatorFramework, "/membership", args[0]);
        group.start();

        Thread.sleep(Integer.MAX_VALUE);
    }
}

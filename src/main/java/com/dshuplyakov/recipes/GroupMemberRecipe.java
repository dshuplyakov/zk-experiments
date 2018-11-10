package com.dshuplyakov.recipes;

import com.dshuplyakov.ZookeeperInit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.nodes.GroupMember;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;


//java -cp zk-jar-with-dependencies.jar com.dshuplyakov.recipes.GroupMemberRecipe
public class GroupMemberRecipe {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupMemberRecipe.class);

    private GroupMember group;

    public static void main(String[] args) throws Exception {
        new GroupMemberRecipe().start();
    }

    private void start() throws InterruptedException {
        CuratorFramework curatorFramework = ZookeeperInit.getCuratorFrameworkClient();

        group = new GroupMember(curatorFramework, "/membership", UUID.randomUUID().toString().substring(0, 4));
        group.start();

        addShutdownHook();
        logMembers();
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                group.close();
            }
        });
    }

    private void logMembers() throws InterruptedException {
        int membershipSize = 0;
        for (;;) {
            if (group.getCurrentMembers().size() != membershipSize) {
                membershipSize = group.getCurrentMembers().size();
                LOGGER.info("{} nodes, {}", membershipSize, group.getCurrentMembers().keySet());
            }
            Thread.sleep(10);
        }
    }

}

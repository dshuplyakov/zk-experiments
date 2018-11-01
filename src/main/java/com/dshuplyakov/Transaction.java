package com.dshuplyakov;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransactionBridge;

/**
 * Date: 30.10.2018
 * Time: 0:08
 *
 * @author Dmitry Shuplyakov
 */
public class Transaction {

    public static void main(String[] args) throws Exception {
        ZookeeperInit zookeeperInit = new ZookeeperInit();
        zookeeperInit.init();

        CuratorFramework curatorFramework = zookeeperInit.getCuratorFrameworkClient();
        curatorFramework.inTransaction()
                .create().forPath("/kokorin").and()
                .create().forPath("/mamaev").and()
                .create().forPath("/joker").and()
                .create().forPath("/batman").and().commit();

        //CuratorFramework curatorFramework = zookeeperInit.getCuratorFrameworkClient();
        curatorFramework.inTransaction()
                .check().forPath("/halloween").and()
                .delete().forPath("/kokorin").and()
                .delete().forPath("/mamaev").and()
                .create().forPath("/batman", "BatMan".getBytes()).and()
                .setData().forPath("/joker", "mr. Joker".getBytes()).and().commit();


        //client.inTransaction().check().forPath(key).and().setData().forPath(key, value.getBytes(Charsets.UTF_8)).and().commit();
    }
}

package com.dshuplyakov.perfomance;

import com.dshuplyakov.ZookeeperInit;
import com.google.common.base.Stopwatch;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Date: 25.10.2018
 * Time: 22:33
 *
 * @author Dmitry Shuplyakov
 */
public class WriteRequests {

    final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private long i;

    Stopwatch stopwatch;

    BackgroundCallback callback = new BackgroundCallback() {
        @Override
        public void processResult(CuratorFramework client,
                                  CuratorEvent event) throws Exception {
            i++;
            semaphore.release();

            if (i % 10000 == 0) {
                System.out.println(i+" -- "+stopwatch.toString());
                stopwatch.reset();
                stopwatch.start();
            }
        }
    };

    Semaphore semaphore = new Semaphore(500);

    public void start() throws Exception {
        ZookeeperInit zookeeperInit = new ZookeeperInit();
        zookeeperInit.init();
        CuratorFramework client = zookeeperInit.getCuratorFrameworkClient();

        stopwatch = Stopwatch.createStarted();
        for (long i = 0; i < 100_000_000; i++) {
            semaphore.acquire();
            createPersistent(client, "/crud4/"+UUID.randomUUID(), "c is a new node".getBytes());
        }
    }

    public static void main(String[] args) throws Exception {
        new WriteRequests().start();
    }

    public void createPersistent(CuratorFramework client, String path,
                                        byte[] payload) throws Exception {
        // this will create the given EPHEMERAL ZNode with the given data
        client.create().inBackground(callback, executorService).forPath(path, payload);

    }
}

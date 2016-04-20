package ir.softernet.lib.quemars;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by saman on 4/19/16.
 */
public abstract class QuemarsConsumer extends RedisConnector implements Runnable {

    volatile boolean stopped    = false;
    volatile boolean doConsume  = true;

    final String highKey, medKey, lowKey;


    public QuemarsConsumer(QuemarsConfig config, int workersCount) {
        super(config, workersCount);

        highKey = __QUEMARS + __SEPARATOR + namespace + __SEPARATOR + Priority.HIGH.getIdentifier();
        medKey  = __QUEMARS + __SEPARATOR + namespace + __SEPARATOR + Priority.MEDIUM.getIdentifier();
        lowKey  = __QUEMARS + __SEPARATOR + namespace + __SEPARATOR + Priority.LOW.getIdentifier();

        for (int workerId=0; workerId<workersCount; workerId++)
            new Thread(this).start();

    }

    @Override
    public void run() {
        while (!stopped) {
            if (!doConsume) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignore) {
                }
                continue;
            }

            final Jedis jedis = pool.getResource();
            jedis.select(dbIndex);
            try {
                final List<byte[]> val = jedis.blpop(__TIMEOUT, highKey.getBytes(), medKey.getBytes(), lowKey.getBytes());
                if (val != null && val.size() == 2)
                    consume(val.get(1));
            } finally {
                pool.returnResource(jedis);
            }
        }
    }
    public abstract void consume(byte[] data);


    public void pause() {
        doConsume = false;
    }

    public void resume() {
        doConsume = true;
    }

    public void stop() {
        stopped = true;
    }

}

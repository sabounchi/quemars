package ir.softernet.lib.quemars;

import redis.clients.jedis.Jedis;

/**
 * Created by saman on 4/19/16.
 */
public class QuemarsProducer extends RedisConnector {

    public QuemarsProducer(QuemarsConfig config) {
        super(config);
    }


    public void submit(byte[] data, Priority priority) {
        final byte[] queueKey = new StringBuilder()
                .append(__QUEMARS)
                .append(__SEPARATOR)
                .append(namespace)
                .append(__SEPARATOR)
                .append(priority.getIdentifier())
                .toString()
                .getBytes();

        submitTail(queueKey, data);
    }

    public void submit(byte[] data, Priority priority, boolean urgent) {
        final byte[] queueKey = new StringBuilder()
                .append(__QUEMARS)
                .append(__SEPARATOR)
                .append(namespace)
                .append(__SEPARATOR)
                .append(priority.getIdentifier())
                .toString()
                .getBytes();

        if (urgent) submitHead(queueKey, data);
        else submitTail(queueKey, data);
    }


    private void submitTail(byte[] queue, byte[] data) {
        final Jedis jedis = pool.getResource();
        jedis.select(dbIndex);
        try {
            jedis.rpush(queue, data);
        } finally {
            pool.returnResource(jedis);
        }
    }

    private void submitHead(byte[] queue, byte[] data) {
        final Jedis jedis = pool.getResource();
        jedis.select(dbIndex);
        try {
            jedis.lpush(queue, data);
        } finally {
            pool.returnResource(jedis);
        }
    }

}

package ir.softernet.lib.quemars;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by saman on 4/19/16.
 */
public abstract class RedisConnector {

    protected static final int      __TIMEOUT       = 5;

    protected static final String   __QUEMARS       = "quemars";
    protected static final char     __SEPARATOR     = ':';

    protected final JedisPool       pool;
    protected final int             dbIndex;
    protected final String          namespace;

    protected RedisConnector(QuemarsConfig config) {
        this.pool       = new JedisPool(config.getIp(), config.getPort());
        this.dbIndex    = config.getDbIndex();
        this.namespace  = config.getNamespace();
    }

    protected RedisConnector(QuemarsConfig config, int instancesCount) {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(instancesCount);

        this.pool       = new JedisPool(config.getIp(), config.getPort());
        this.dbIndex    = config.getDbIndex();
        this.namespace  = config.getNamespace();
    }

}

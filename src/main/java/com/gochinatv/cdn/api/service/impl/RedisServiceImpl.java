package com.gochinatv.cdn.api.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import com.gochinatv.cdn.api.service.RedisService;
import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;


@Component
public class RedisServiceImpl implements RedisService{

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    private Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    
    public ValueOperations<String,String> opsForValue(){
    	ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
    	return opsForValue;
    }
    

    /**
     * 添加到带有 过期时间的  缓存
     * @param key   redis主键
     * @param value 值
     * @param time  过期时间
     * @throws Exception
     */
	public void setExpire(String key, String value, long time) throws Exception {
		opsForValue().set(key, value, time, TimeUnit.SECONDS);
	}

	
    /**
     * 一次性添加数组到   过期时间的  缓存，不用多次连接，节省开销
     * @param keys   redis主键数组
     * @param values 值数组
     * @param time   过期时间
     */
	public void setExpire(Map<String, String> data, long time) throws Exception {
		if (null != data) {
			Set<Entry<String, String>> entrySet = data.entrySet();
			for (Entry<String, String> entry : entrySet) {
				opsForValue().set(entry.getKey(), entry.getValue(), time, TimeUnit.SECONDS);
			}
		}
	}


    /**
     * 一次性添加数组到   过期时间的  缓存，不用多次连接，节省开销
     */
    public void set(HashMap<String, String> data) throws Exception {
    	if (null != data) {
    		opsForValue().multiSet(data);
    	}
        /*if (redisTemplate != null) {
            redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();

                    for (int i = 0; i < keys.length; i++) {
                        String key = keys[i];
                        String value = values[i];
                        byte[] _keys = serializer.serialize(key);
                        byte[] _values = serializer.serialize(value);
                        connection.set(_keys, _values);
                        logger.info("[redisTemplate redis]放入 缓存  url:{}", key);
                    }
                    return 1L;
                }
            });
        } else {
            logger.info("[redisTemplate is null]");
        }*/
    }


    /**
     * 添加到缓存
     */
    public void set(String key, String value) throws Exception {
    	opsForValue().set(key, value);
        /*if (redisTemplate != null) {
            redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keys = serializer.serialize(key);
                    byte[] values = serializer.serialize(value);
                    connection.set(keys, values);
                    logger.info("[redisTemplate redis]放入 缓存  url:{}", key);
                    return 1L;
                }
            });
        } else {
            logger.info("[redisTemplate is null]");
        }*/
    }

    /**
     * 查询在这个时间段内即将过期的key
     */
    public List<String> willExpire(final String key, final long time) throws Exception {
        final List<String> keysList = new ArrayList<String>();
        redisTemplate.execute(new RedisCallback<List<String>>() {
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<String> keys = redisTemplate.keys(key + "*");
                for (String key : keys) {
                    Long ttl = connection.ttl(key.getBytes());
                    if (0 <= ttl && ttl <= 2 * time) {
                        keysList.add(key);
                    }
                }
                return keysList;
            }
        });
        return keysList;
    }


    /**
     * 查询在以keyPatten的所有  key
     */
    public Set<String> keys(String keyPatten) throws Exception {
    	Set<String> keys = redisTemplate.keys(keyPatten + "*");
    	return keys;
        /*return redisTemplate.execute(new RedisCallback<Set<String>>() {
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<String> keys = redisTemplate.keys(keyPatten + "*");
                return keys;
            }
        });*/
    }

    /**
     * 根据key获取对象
     */
    public String get(String key) throws Exception {
    	String value = opsForValue().get(key);
    	return value;
    	/*
        String resultStr = null;
        if (redisTemplate != null) {
            resultStr = redisTemplate.execute(new RedisCallback<String>() {
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = getRedisSerializer();
                    byte[] keys = serializer.serialize(key);
                    byte[] values = connection.get(keys);
                    if (values == null) {
                        return null;
                    }
                    String value = serializer.deserialize(values);
                    return value;
                }
            });
        }
        logger.info("[redisTemplate redis]取出 缓存  url:{} ", key);
        return resultStr;*/
    }


    /**
     * 根据key获取对象
     */
    public Map<String, String> getKeysValues(String keyPatten) throws Exception {
        Set<String> keys = redisTemplate.keys(keyPatten + "*");
        Map<String, String> map = new HashMap<String, String>();
        ValueOperations<String, String> opsForValue = opsForValue();
        for (String key : keys) {
        	String value = opsForValue.get(key);
        	map.put(key, value);
        }
        return map;
        
        /*final Map<String, String> maps = new HashMap<String, String>();
        return redisTemplate.execute(new RedisCallback<Map<String, String>>() {
            public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                Set<String> keys = redisTemplate.keys(keyPatten + "*");
                for (String key : keys) {
                    byte[] _keys = serializer.serialize(key);
                    byte[] _values = connection.get(_keys);
                    String value = serializer.deserialize(_values);
                    maps.put(key, value);
                }
                return maps;
            }
        });*/
    }

    /**
     * 集合数量
     */
    public long dbSize() {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * 清空redis存储的数据
     */
    public String flushDB() {
        return redisTemplate != null ? redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        }) : null;
    }

    /**
     * 判断某个主键是否存在
     */
    public boolean exists(String key) throws Exception {
    	boolean has = redisTemplate.hasKey(key);
    	return has;
        /*return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists((key).getBytes());
            }
        });*/
    }


    /**
     * 删除key
     */
    public void del(String... keys) {
    	for (String key : keys) {
    		redisTemplate.delete(key);
        }
        /*return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (String key : keys) {
                    result = connection.del((key).getBytes());
                }
                return result;
            }
        });*/
    }

    /**
     * 获取 RedisSerializer
     */
    public RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }

    //****************************************************Map 引擎************************************************************
    public HashOperations<String, Object, Object> opsForHash() {
        return redisTemplate.opsForHash();
    }

    /**
     * 对HashMap操作
     */
    public void putHashValue(String key, String hashKey, String hashValue) {
        logger.info("[redisTemplate redis]  putHashValue()  key={},hashKey={},hashValue={} ", key, hashKey, hashValue);
        opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * 获取单个fiel对应的值
     */
    public Object getHashValues(String key, Object hashKey) {
        logger.info("[redisTemplate redis]  getHashValues()  key={},hashKey={}", key, hashKey);
        return opsForHash().get(key, hashKey);
    }

    /**
     * 根据key值删除
     */
    public void delHashValues(String key, Object... hashKeys) {
        logger.info("[redisTemplate redis]  delHashValues()  key={}", key);
        opsForHash().delete(key, hashKeys);
    }

    /**
     * key只匹配map
     */
    public Map<Object, Object> getHashValue(String key) {
        logger.info("[redisTemplate redis]  getHashValue()  key={}", key);
        Map<Object, Object> entries = opsForHash().entries(key);
        return entries;
    }

    /**
     * 批量添加
     */
    public void putHashvalues(String key, Map<String, Object> map) {
        opsForHash().putAll(key, map);
    }
    
    //***************************************************List引擎****************************************************
    /**
     * redis List 引擎
     */
    public ListOperations<String, String> opsForList() {
        return redisTemplate.opsForList();
    }

    /**
     * redis List数据结构 : 将一个或多个值 value 插入到列表 key 的表头
     */
    public Long leftPush(String key, String value) {
        return opsForList().leftPush(key, value);
    }

    /**
     * redis List数据结构 : 移除并返回列表 key 的头元素
     */
    public String leftPop(String key) {
        return opsForList().leftPop(key);
    }

    /**
     * redis List数据结构 :将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     */
    public Long in(String key, String value) {
        return opsForList().rightPush(key, value);
    }

    /**
     * redis List数据结构 : 移除并返回列表 key 的末尾元素
     */
    public String rightPop(String key) {
        return opsForList().rightPop(key);
    }


    /**
     * redis List数据结构 : 返回列表 key 的长度 ; 如果 key 不存在，则 key 被解释为一个空列表，返回 0 ; 如果 key 不是列表类型，返回一个错误。
     */
    public Long length(String key) {
        return opsForList().size(key);
    }


    /**
     * redis List数据结构 : 根据参数 i 的值，移除列表中与参数 value 相等的元素
     */
    public void remove(String key, long i, String value) {
        opsForList().remove(key, i, value);
    }

    /**
     * redis List数据结构 : 将列表 key 下标为 index 的元素的值设置为 value
     */
    public void set(String key, long index, String value) {
        opsForList().set(key, index, value);
    }

    /**
     * redis List数据结构 : 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     */
    public List<String> getList(String key, int start, int end) {
        List<String> range = opsForList().range(key, start, end);
        return range;
    }

    /**
     * redis List数据结构 : 批量存储
     */
    public Long lpushAll(String key, List<String> list) {
        Long aLong = opsForList().leftPushAll(key, list);
        return aLong;
    }

    /**
     * redis List数据结构 : 将值 value 插入到列表 key 当中，位于值 index 之前或之后,默认之后。
     */
    public void insert(String key, long index, String value) {
        opsForList().set(key, index, value);
    }
}

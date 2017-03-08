package com.gochinatv.cdn.api.service;


import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import java.util.*;


public interface RedisService {

   
    /**
     * 添加到带有 过期时间的  缓存
     * @param key   redis主键
     * @param value 值
     * @param time  过期时间
     * @throws Exception
     */
	public void setExpire(String key, String value, long time) throws Exception;

	
    /**
     * 一次性添加数组到   过期时间的  缓存，不用多次连接，节省开销
     * @param keys   redis主键数组
     * @param values 值数组
     * @param time   过期时间
     */
	public void setExpire(Map<String, String> data, long time) throws Exception;


    /**
     * 一次性添加数组到   过期时间的  缓存，不用多次连接，节省开销
     */
    public void set(HashMap<String, String> data) throws Exception;


    /**
     * 添加到缓存
     */
    public void set(String key, String value) throws Exception;

    /**
     * 查询在这个时间段内即将过期的key
     */
    public List<String> willExpire(final String key, final long time) throws Exception;


    /**
     * 查询在以keyPatten的所有  key
     */
    public Set<String> keys(final String keyPatten) throws Exception;

    /**
     * 根据key获取对象
     */
    public String get(String key) throws Exception;


    /**
     * 根据key获取对象
     */
    public Map<String, String> getKeysValues(final String keyPatten) throws Exception ;

    /**
     * 集合数量
     */
    public long dbSize();

    /**
     * 清空redis存储的数据
     */
    public String flushDB();

    /**
     * 判断某个主键是否存在
     */
    public boolean exists(String key) throws Exception;


    /**
     * 删除key
     */
    public void del(String... keys);

    /**
     * 获取 RedisSerializer
     */
    public RedisSerializer<String> getRedisSerializer();
    

    //****************************************************Map 引擎************************************************************
    public HashOperations<String, Object, Object> opsForHash();

    /**
     * 对HashMap操作
     */
    public void putHashValue(String key, String hashKey, String hashValue);

    /**
     * 获取单个fiel对应的值
     */
    public Object getHashValues(String key, Object hashKey);

    /**
     * 根据key值删除
     */
    public void delHashValues(String key, Object... hashKeys);

    /**
     * key只匹配map
     */
    public Map<Object, Object> getHashValue(String key);

    /**
     * 批量添加
     */
    public void putHashvalues(String key, Map<String, Object> map);
    
    //***************************************************List引擎****************************************************
    /**
     * redis List 引擎
     */
    public ListOperations<String, String> opsForList();

    /**
     * redis List数据结构 : 将一个或多个值 value 插入到列表 key 的表头
     */
    public Long leftPush(String key, String value);

    /**
     * redis List数据结构 : 移除并返回列表 key 的头元素
     */
    public String leftPop(String key);

    /**
     * redis List数据结构 :将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     */
    public Long in(String key, String value);

    /**
     * redis List数据结构 : 移除并返回列表 key 的末尾元素
     */
    public String rightPop(String key);


    /**
     * redis List数据结构 : 返回列表 key 的长度 ; 如果 key 不存在，则 key 被解释为一个空列表，返回 0 ; 如果 key 不是列表类型，返回一个错误。
     */
    public Long length(String key);


    /**
     * redis List数据结构 : 根据参数 i 的值，移除列表中与参数 value 相等的元素
     */
    public void remove(String key, long i, String value);

    /**
     * redis List数据结构 : 将列表 key 下标为 index 的元素的值设置为 value
     */
    public void set(String key, long index, String value);

    /**
     * redis List数据结构 : 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     */
    public List<String> getList(String key, int start, int end);

    /**
     * redis List数据结构 : 批量存储
     */
    public Long lpushAll(String key, List<String> list);

    /**
     * redis List数据结构 : 将值 value 插入到列表 key 当中，位于值 index 之前或之后,默认之后。
     */
    public void insert(String key, long index, String value);
    
}

package com.taobei.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taobei.rest.component.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	//单机版测试
	@Test
	public void testJedisSingle()throws Exception{
		//创建一个Jedis的对象
		Jedis jedis = new Jedis("192.168.255.128", 6379);
		jedis.set("test", "hello jedis");
		
		String result = jedis.get("test");
		
		System.out.println(result);
	}
	
	//使用链接池
	@Test
	public void testJedisPool()throws Exception{
		//创建连接池
		JedisPool jedisPool = new JedisPool("192.168.255.128", 6379);
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get("test");
		System.out.println(result);
		//关闭jedis
		jedis.close();
		//关闭jedis链接池
		jedisPool.close();
	}
	
	//集群版测试
	@Test
	public void testJedisCluster() throws Exception{
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.255.128", 7001));
		nodes.add(new HostAndPort("192.168.255.128", 7002));
		nodes.add(new HostAndPort("192.168.255.128", 7003));
		nodes.add(new HostAndPort("192.168.255.128", 7004));
		nodes.add(new HostAndPort("192.168.255.128", 7005));
		nodes.add(new HostAndPort("192.168.255.128", 7006));
		
		//cluster在系统中是单实例的
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.set("name", "张三");
		cluster.set("value", "1000");
		
		String name = cluster.get("name");
		String value = cluster.get("value");
		
		System.out.println(name);
		System.out.println(value);
		cluster.close();
	}
	
	@SuppressWarnings("resource")
	@Test
	public void testJedisSpring() throws Exception{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisClient client = applicationContext.getBean(JedisClient.class);
		
		client.set("gender1", "nnnn");
		String str = client.get("gender1");
		System.out.println(str);
		
	}
	
	
	
	
	
}

package com.nurtivillage.java.nutrivillageApplication;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class EhCacheConfig extends CachingConfigurerSupport{


	@Bean
	public net.sf.ehcache.CacheManager ehCacheManager() {
		CacheConfiguration categoryCache=new CacheConfiguration();
		categoryCache.setName("category-cache");
		categoryCache.setMemoryStoreEvictionPolicy("LRU");
		categoryCache.setMaxEntriesLocalHeap(1000);
		categoryCache.setTimeToLiveSeconds(7200);
	
		CacheConfiguration productCache=new CacheConfiguration();
		productCache.setName("product-cache");
		productCache.setMemoryStoreEvictionPolicy("LRU");
		productCache.setMaxEntriesLocalHeap(1000);
		productCache.setTimeToLiveSeconds(7200);
	net.sf.ehcache.config.Configuration config=new net.sf.ehcache.config.Configuration();
	config.addCache(categoryCache);
	config.addCache(productCache);
	return net.sf.ehcache.CacheManager.newInstance(config);
}
	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}
	}

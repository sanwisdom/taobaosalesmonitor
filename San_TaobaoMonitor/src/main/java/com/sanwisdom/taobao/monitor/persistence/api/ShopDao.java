package com.sanwisdom.taobao.monitor.persistence.api;

import java.io.IOException;
import java.util.List;

import com.sanwisdom.taobao.monitor.businessobject.Shop;

public interface ShopDao {

	void create(Shop info) throws IOException;
	
	void create(List<Shop> infos) throws IOException;
	
	void update(Shop info) throws IOException;
	
	void update(List<Shop> infos) throws IOException;
	
	Shop findByPrimaryKey(Long id) throws IOException;
	
	List<Shop> findByPrimaryKeys(List<Long> id) throws IOException;
	
	List<Shop> findAll() throws IOException;
	
	void delete(Long id) throws IOException;
	
	void delete(Shop info) throws IOException;
}

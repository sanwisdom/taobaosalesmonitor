package com.sanwisdom.taobao.monitor.web.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.persistence.impl.JdbcShopDao;


@Path("/shops")
@Component
@Scope("request")
public class ShopResource {

	
	 @GET 
     // The Java method will produce content identified by the MIME Media
     // type "text/plain"
	 @Produces({MediaType.APPLICATION_JSON  + ";charset=utf-8" })
	 @Path("/all/test")
     public List<Shop> getShops() {
		 List<Shop> shops = new ArrayList<Shop>();			
		 Shop shop0 = new Shop(1L, "史坦力旗舰店", "stanley.tmall.com"); 
		 shops.add(shop0);
		 Shop shop1 = new Shop(2L, "力成旗舰店", "atomic.tmall.com"); 
		 shops.add(shop1);
		 Shop shop2 = new Shop(3L, "力成旗舰店", "atomic.tmall.com");
		 shops.add(shop2);
		 return shops;
     }
	 
	 @GET 
	 @Produces({MediaType.APPLICATION_JSON  + ";charset=utf-8" })
	 @Path("/all/dbtest")
     public List<Shop> getShopsFromDb() {
		 List<Shop> shops = new ArrayList<Shop>();			
		 JdbcShopDao dao = new JdbcShopDao();
		 List<Long> shopIds = dao.queryShopIds();
		 for (Long shopId : shopIds) {
			Shop shop = null;
			try {
				shop = dao.findByPrimaryKey(shopId);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (null != shop) {
				shops.add(shop);
			}
		}
		 return shops;
     }
	 
	 @GET 
	 @Produces({MediaType.APPLICATION_JSON  + ";charset=utf-8" })
	 @Path("/1/test")
     public Shop getShop() {
		 Shop shop0 = new Shop(1L, "史坦力旗舰店", "stanley.tmall.com");
		 return shop0;
     }
}

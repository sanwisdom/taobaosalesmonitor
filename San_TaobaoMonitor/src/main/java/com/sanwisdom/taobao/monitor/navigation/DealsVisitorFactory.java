package com.sanwisdom.taobao.monitor.navigation;

import java.net.URL;

import com.sanwisdom.taobao.monitor.businessobject.Domain;
import com.sanwisdom.taobao.monitor.navigation.impl.TaobaoDealsVisitor;
import com.sanwisdom.taobao.monitor.navigation.impl.TmallDealsVisitor;

public class DealsVisitorFactory {

	private DealsVisitorFactory() {
		
	}
	
	public static AbstractDealsVisitor createDealsVisitor(URL url) {
		try {
			if (url.getAuthority().endsWith(Domain.TMALL.getName())) {
				return TmallDealsVisitor.class.newInstance();
			} else if (url.getAuthority().endsWith(Domain.TAOBAO.getName())) {
				return TaobaoDealsVisitor.class.newInstance();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
}

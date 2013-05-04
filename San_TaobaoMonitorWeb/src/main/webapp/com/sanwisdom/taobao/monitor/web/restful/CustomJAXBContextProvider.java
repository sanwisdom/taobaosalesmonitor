//package com.sanwisdom.taobao.monitor.web.restful;
//
//import javax.ws.rs.ext.ContextResolver;
//import javax.ws.rs.ext.Provider;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//
//import org.apache.log4j.Logger;
//
//@Provider
//public class CustomJAXBContextProvider implements ContextResolver<JAXBContext> {
//
//	static Logger log = Logger.getLogger(CustomJAXBContextProvider.class);
//	
//	private JAXBContext context = null;
//	
//	@Override
//	public JAXBContext getContext(Class<?> type) {
//		if(context == null) {
//		    try {
//		        context = JAXBContext.newInstance();
//		    } catch (JAXBException e) {
//		    	log.error("provider won't/can't be used.");
//		    	throw new RuntimeException(e);
//		        // log warning/error; null will be returned which indicates that this
//		    }
//		}
//		return context;
//	}
//
//}

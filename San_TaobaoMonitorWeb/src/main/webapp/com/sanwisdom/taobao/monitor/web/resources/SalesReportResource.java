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

import com.sanwisdom.common.utils.DateUtils;
import com.sanwisdom.taobao.monitor.businessobject.Category;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.persistence.impl.JdbcReportDao;
import com.sanwisdom.taobao.monitor.salesconsole.GlobalConstants;
import com.sanwisdom.taobao.monitor.web.vo.ReportResponse;

@Path("/report")
@Component
@Scope("request")
public class SalesReportResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
	@Path("/test")
	public ReportResponse getShops() {
		ReportResponse report = new ReportResponse();
		List<Shop> shops = new ArrayList<Shop>();
		JdbcReportDao dao = new JdbcReportDao();
		try {
			shops.addAll(dao.readReports(15, Category.TOOLS, DateUtils
					.createDate("2012-11-30", GlobalConstants.DATE_FORMAT_YYYY_MM_DD)));
			report.setData(shops);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return report;
	}
//	
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//	@Path("/test1")
//	public Response getDeals() {
//		ReportResponse<Entity> report = new ReportResponse<Entity>();
//		List<Shop> shops = new ArrayList<Shop>();
//		JdbcReportDao dao = new JdbcReportDao();
////		try {
////			shops.addAll(dao.readReports(15, Category.TOOLS, DateUtils
////					.createDate("2012-11-30", GlobalConstants.DATE_FORMAT_YYYY_MM_DD)));
////			report.setData(shops);
////		} catch (IOException e) {
////			throw new RuntimeException(e);
////		}
//		report.setData(new ArrayList<Entity>());
//		report.getData().add(new Entity());
//		return Response.ok(report).build();
//	}
}

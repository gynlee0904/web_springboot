package kr.co.boot.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;

//250422
@Controller
public class ajax_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	PrintWriter pw = null;
	
	
	//ajax.jsp와 함께 사용하는 API통신 
	@PostMapping("/ajax.do")
	public String ajax(@RequestParam("product")String product, @RequestHeader("mkey")String mkey,
						HttpServletResponse res) throws IOException {
		//FormData, form태그 => get, post밖에 못씀 
		//@RequestHeader("") : API 접근에 대한 보안 키 
		
		this.pw = res.getWriter();
		
		if(mkey.equals("a123456")) {  
			this.log.info(product);
		}else {
			this.pw.print("key error");
		}
		
		this.pw.close();
		return null;
	}
	
	
	//ajax2.jsp와 함께 사용하는 API통신 
	@Autowired api_service asv;
	
	@CrossOrigin(origins="*", allowedHeaders = "*")  
	@PostMapping("/ajax2.do")
	public String ajax2(HttpServletResponse res, 
						@RequestParam(name="CID")String CID,
						@RequestParam(name="CNAME")String CNAME
						) throws IOException {
		this.pw = res.getWriter();
		int result = this.asv.insert_cms(CID, CNAME);
		
//		controller에서부터 Map에 넣어도 되긴 함 
//		Map<String, String> all = new HashMap<>();
//		all.put("CID", CID);
//		all.put("CNAME", CNAME);
//		int result = this.asv.insert_cms(all);
				
		if(result>0) {
			this.pw.print("ok");
		}else {
			this.pw.print("error");
		}

		this.pw.close();
		return null;
	}
}

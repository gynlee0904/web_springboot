package kr.co.boot.thymeleaf;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


//Thymeleaf + Spring-boot => Model이 아주아주아주 중요함!!(+AOP or extends or implement)
@Controller
public class thymeleaf_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name="all_DTO") all_DTO all_inject;  //AOP와 공용으로 사용하는 DTO 
	
	/*
	 ★★★(app2.properties 기본 환경설정 조건 하에서)
	 return null 혹은 return "aaa" => webapp 안에 있는 aaa.jsp파일 먼저 찾음 
	 return "/aaa" => templates 안에 있는 aaa.jsp를 먼저 찾음  
	 return "/aaa.html" => templates 안의 aaa.html (해당 속성의 파일명)을 찾음 
	 ※webapp 안의 WEB-INF 폴더 안에 있는 파일을 찾으려면 return "WEB-INF/파일경로" 이렇게 써야함(맨 앞의 /를 뗌)
	*/
	@GetMapping("/sample.do")
	public String sample(Model m) {
		String product = "냉장고";
		m.addAttribute("product", product);
		
//		return "/sample";
		return "/sample.html";
	}
	
	
	@GetMapping("/sample2.do")
	public String sample2(Model m) {
		String menu="Admin 관리자등록";
		m.addAttribute("menu", menu);
		
		String copy1 = "Copyright 2025 WEB";
		m.addAttribute("copy1", copy1);
		
		return "/subpage.html";
	}
	
//*****************************************************	
	
	//웹페이지 보기만 하는용 
	@GetMapping("/index_test.do")
	public String index_test() { return "/index.html"; }
	
	
	//실제 컨트롤할 do. 
	@GetMapping("/shop.do")
	public String shop(Model m) { 
		//해당객체명으로 DTO에 있는 배열값을 타임리프 html로 전달  
		m.addAttribute("menulist", this.all_inject.getMenus());
		return "/main.html";
	}
	
	//로그인 메소드
	@GetMapping("/shop_login.do")
	public String shop_login(Model m) { 
		this.log.info(this.all_inject.toString());
		m.addAttribute("menulist", this.all_inject.getMenus());
		
		return "/login.html";
	}
	
	
//**********************************************************************
	
	//타임리프 기본 언어 사용법
	@GetMapping("/thymeleaf.do")
	public String thymeleaf(Model m, HttpServletRequest req) {
		String code = "관리자 리스트 <br> <b>일반관리자</b>";
		m.addAttribute("code",code);
		
		//키배열 
		Map<String, Object> all = new HashMap<>();
		all.put("mid", "hong");
		all.put("mage", 35);
		m.addAttribute("all",all);
		
		//검색어 
		String search = "수원1창고";
		m.addAttribute("search",search);
		
		//URL 
		String page ="http://naver.com";
		m.addAttribute("page",page);
		
		//session
		HttpSession session = req.getSession();
		session.setAttribute("muser", "홍길동");
		
		//조건값 
		String result="ok";
		m.addAttribute("result",result);
		
		return "/thymeleaf.html";
	}
	
	
	//타임리프 중급 언어 사용법2
	@GetMapping("/thymeleaf2.do")
	public String thymeleaf2(Model m, HttpServletRequest req) {
		int paymethod = 1;
		m.addAttribute("paymethod",paymethod);
		
		
		//클래스배열 생성하여 뷰로 전달 
		ArrayList<String> alldata = new ArrayList<>();
		alldata.add("검정");
		alldata.add("핑크");
		alldata.add("레드");
		alldata.add("옐로우");
		alldata.add("그린");
		m.addAttribute("alldata",alldata);
		
		
		//radio, checkbox 핸들링
		String agree="Y";
		m.addAttribute("agree",agree);
		
		//select 핸들링 
		String level="일반";
		m.addAttribute("level",level);
		
		//현재 시간(PC 윈도우 시간)을 핸들링 => 문자String으로 변환하지말고 클래스 결과를 그대로 보내야함 
//		String today = LocalDateTime.now().toString(); 
//		System.out.println(today);  //2025-04-21T11:50:27.230392
		m.addAttribute("today",LocalDateTime.now());
		
		Date to = new Date(); 
		m.addAttribute("today2",to);
		
		return "/thymeleaf2.html";
	}
	
	//타임리프의 T()로 static 변수값을 로드할 수 있음 
	public static String kk="이명헌";
	
	
	
	//타임리프 중급 언어 사용법2
	@GetMapping("/thymeleaf3.do")
	public String thymeleaf3(Model m, HttpServletRequest req) {
		/*
		List<String> a = new ArrayList<>();
		Map<String, Object> aa = new HashMap<>();
		=> 데이터를 출력하는 역할 
		*/
		
		//form 안에서 name, id, value 설정하기 위함. 
		m.addAttribute("result",this.all_inject);
		m.addAttribute("act_url","/abc/thy.do");
		
		
		m.addAttribute("money",10000);
		m.addAttribute("money2",22.52);
		
		return "/thymeleaf3.html";
	}
	
	
	
	
	
	
	
	
}

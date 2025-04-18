package kr.co.boot.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class main2_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name="user_DTO") user_DTO dto;
	
	
	@GetMapping("/testaop2.do")
	public String testaop2(Model m) {
		this.log.info("test2가 실행되는 컨트롤러다제");
		return null;
	}
	
	
	@GetMapping("/testaop.do")
	public String testaop(Model m) {
		//HttpServletRequest req : 컨트롤러쪽에서 핸들링 
		String mid="hong";
		String mname="홍길동";
		
		this.dto.setMid(mid);
		this.dto.setMname(mname);

		if(mid.equals("hong")) {
			this.log.info("아이디를 확인하였습니다.");
		}
		m.addAttribute("mname",mname);
		return "test_aop";
	}
	
	
	@GetMapping("/testaop_logout.do")
	public String testaop_logout(Model m, HttpServletRequest req, @SessionAttribute(name="userid", required=false) String userid) {
		this.log.info(userid);
		HttpSession se = req.getSession();
		se.invalidate();  //세션 삭제 
		
		this.log.info("올바르게 로그아웃 되었습니다");
		
		return null;
	}
	

}

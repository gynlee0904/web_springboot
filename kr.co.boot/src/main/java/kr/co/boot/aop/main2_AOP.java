package kr.co.boot.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionBindingEvent;
import kr.co.boot.cdn_DAO;

@Aspect   //AOP로 쓰겠다는 @
@Component  //이 클래스는 컨트롤러와 상관 없이 무조건 실행시키는 @ 
public class main2_AOP {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="user_DTO") user_DTO dto;
	//@Aspect, @Component 가 붙어있기 때문에 컨트롤러에 있는 DTO와 동일한 애를 가져온것임.  
	//=>일반적인 모델의 @Resource처럼 new클래스를 가져오는 형태가 아님 
	
	@Autowired cdn_DAO service;
	
	
	/* @After :  해당 컨트롤러의 해당 메소드가 작동된 후 실행시킨다는 @
	   @Before : 해당 컨트롤러의 해당 메소드가 작동되기 전 실행시킨다는 @
	   @Arround : 찾아보세요 잘 안써요 
	   
	   ("execution(* 패키지명.컨트롤러명.메소드명(..))") : invoke의 역할. 해당 패키지에 있는 컨트롤러 및 매핑된 메소드를 실행하는 명령어  
	   (* 패키지명~ ) : 모든 패키지라는 뜻 
	   메소드명(..)대신 *(..)를 쓰면 해당 컨트롤러의 모든 메소드가 작동될 때 실행됨  
	   메소드명(..) : 해당 컨트롤러의 해당 메소드의 매개변수 다 들고와서 실행시킨다는 뜻 
	*/
	@After("execution(* kr.co.boot.aop.main2_controller.testaop(..))")
	public void tastaop_a() throws Throwable {   //Throwable : Exception 최상위 예외처리자. 사용 적극추천  
		try {
//			this.log.info("aop에서 실행되는 로그지롱");
			HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
			//AOP도 하나의 모델이기 때문에 HttpServletRequest는 메소드()안이 아니라 {}안에서 핸들링해야함
			
			HttpSession se = req.getSession();
			se.setAttribute("userid", this.dto.getMid());
			se.setAttribute("useremail", "hong@nate.com");
			this.log.info("확인 : " + se.getAttribute("userid"));
			int result = this.service.log_table(this.dto.getMid(),"login");
			if(result>0) {
				this.log.info("올바르게 로그기록을 저장했습니다");
			}
			
		} catch (Throwable e) {
			// TODO: handle exception
		}
	}
	
	
	@Before("execution(* kr.co.boot.aop.main2_controller.testaop_logout(..))")
	public void tastaop_b() throws Throwable {   
	//After가 되면 컨트롤러에서 세션이 삭제되고 나서 AOP실행되므로 로그기록 저장되지 않음(값 못가져옴)	
		try {
			HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
			HttpSession se = req.getSession();
			String userid=(String)se.getAttribute("userid")+"_out";
			
			int result = this.service.log_table(userid,"logout");
			if(result>0) {
				this.log.info("올바르게 로그기록을 저장했습니다2");
			}
			
		} catch (Throwable e) {
			// TODO: handle exception
		}
	}
	
	
}

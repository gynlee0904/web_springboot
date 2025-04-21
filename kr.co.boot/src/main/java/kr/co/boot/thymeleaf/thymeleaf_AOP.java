package kr.co.boot.thymeleaf;

import java.util.ArrayList;
import java.util.Arrays;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import jakarta.annotation.Resource;

@Aspect
@Component
public class thymeleaf_AOP {
	@Resource(name="all_DTO") all_DTO all_inject;
	
	//타임리프 컨트롤러의 해당 클래스에 있는 모든 메소드에 컨트롤러 실행전 이 AOP가 먼저 작동 
	@Before("execution(* kr.co.boot.thymeleaf.thymeleaf_controller.*(..))")
	public void top_menu() {
		String menus[]={"카테고리","로켓배송","로켓프레시","2025추석","로켓직구","골드박스","정기배송","이벤트/쿠폰","기획전","여행/티켓"};
		ArrayList<String> allmenu = new ArrayList<>(Arrays.asList(menus));
		this.all_inject.setMenus(allmenu);
	}
	

}

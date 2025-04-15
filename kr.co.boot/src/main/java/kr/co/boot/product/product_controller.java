package kr.co.boot.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class product_controller {
//패키지 만들때는 기본패키지명 뒤에 .패키지명 으로 만듦 (ex: kr.co.boot.product)
	
	
	@GetMapping("/product.do")
	public String product(Model m) {
		String product = "냉장고";
		m.addAttribute("product",product);

		return null;
	}
}

package kr.co.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

@Controller
public class cdn_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="file_DTO") file_DTO fdto;  //@Resource는 컨트롤러에서 호출하는것과 모델에서 호출하는것이 다름. new와 같은것
	@Resource(name="cdn_model") cdn_model m_cdn; //cdn서버를 모델화 
	
	
	//cdn서버로 파일 전송 및 db저장 
	@PostMapping("/cdn/cdn_fileok.do")
	public String cdn_fileok(@RequestParam(name="mfile") MultipartFile mfile[],
							@RequestParam(name="url") String url) {
	//파일이 여러개 날아올 경우 []배열로 받음. 미리 배열로 받아두면 FE에서 하나만 보내든 여러개 보내든 코드 안고쳐도됨 
		this.log.info("파일명 : "+ mfile[0].getOriginalFilename());
		
		try {
			
			//사용자가 업로드한 파일명을 리네임 한 후 ftp 전송
			boolean result = this.m_cdn.cdn_ftp(mfile[0], url);  
			
			if(result == true) {
				this.log.info("저장 성공");
			}else {
				this.log.info("저장 실패");
			}
			
		} catch (Exception e) {
			
			
		} 
		return null;
	}
}

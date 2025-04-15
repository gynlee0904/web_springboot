package kr.co.boot;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import kr.co.boot.product.product_controller;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

@Repository("cdn_model")
public class cdn_model {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private cdn_DAO service;
	
	@Resource(name="file_DTO") file_DTO fdto;

	final String user="testuser";  //데이터 파싱 방지. ftp정보를 외부에서 수정되지 않도록 설정.(ftp는 보안이 매우 중요)
	final String pass="a10041004!";
	final int port = 21;
	
	FTPClient fc = null;
	FTPClientConfig fcc = null;
	
	boolean result = false;   //ftp전송 결과값 true : 정상업로드 / false : 오류빌생 
	
	
	
	//DB저장 모델에서 해도 됨! 꼭 컨트롤러에서 안해도 된달찌 
	
	//cdn서버로 파일을 전송하는 역할 담당 모델
	public boolean cdn_ftp(MultipartFile files, String url) throws IOException {
		try {
			String new_file = this.rename_file(files.getOriginalFilename());
//			System.out.println("new_file : " + new_file);
			
			this.fc = new FTPClient();
			this.fc.setControlEncoding("utf-8");
			
			this.fcc = new FTPClientConfig();
			
			this.fc.configure(fcc);
			this.fc.connect(url , this.port);  //ftp 연결 
			
			if(this.fc.login(this.user, this.pass)) {  //ftp로그인
				this.fc.setFileType(FTP.BINARY_FILE_TYPE);  //바이너리인지 타입만 확인 (이미지, 동영상, ZIP, PDF..)
				this.result = this.fc.storeFile("/nyong/"+new_file, files.getInputStream());  //ftp디렉토리 경로 설정 후 해당 파일을 byte로 전송
				//IO는 파일 먼저 저장 후 DB저장해야함!!
				//파일저장되면 여기서  this.result = true가 됨
				
				//DB저장 
				this.fdto.setORI_FILE(files.getOriginalFilename());
				this.fdto.setNEW_FILE(new_file);
				
				String api_nm[] = new_file.split("[.]");//split은 .기준으로는 못자름, [.] 혹은 \\.이라고 써야함
				this.fdto.setAPI_FILE(api_nm[0]);

				String api_url = "http://"+url+"/nyong/"+new_file;
				this.fdto.setFILE_URL(api_url);
				
				int result2 = this.service.cdn_insert(this.fdto);
				if(result2==0) {  //DB에 insert 문제 발생시 false로 변경하여 Controller로 전송 
					this.result = false;					
				}
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			this.fc.disconnect();  //ftp 접속 해제 꼭 해야함  
		}
		
		return this.result;
	}
	
	//실제 파일명을 개발자가 원하는 이름으로 변경하는 메소드 
	public String rename_file(String ori_file) {
		Date date = new Date();  
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String today = sf.format(date);
		
		int no = (int)Math.ceil(Math.random()*1000); 
		String type = ori_file.substring(ori_file.lastIndexOf(".")); 
		
		String new_file = today+no+type;
		
		return new_file;
	}
}

package kr.co.boot;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class main_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	PrintWriter pw = null;
	
	@Autowired private membership_service ms;  //인터페이스를 로드 
	
	@Resource(name="membership_DTO") membership_DTO mdto;
	
	@GetMapping("/index.do")
	public String index(Model m) {
		List<membership_DTO> all = this.ms.alldata();  //DB로드
		this.log.info(all.get(0).getMIDX().toString());
		
		String username = "이명헌";
		m.addAttribute("username",username);
		return null;
	}
	
	
	
//	@Autowired membership_DAO2 mdao2;  //@Service 클래스를 로드(=>@Autowired 사용) 
	@Resource(name="membership_DAO2") membership_DAO2 mdao2; //@Repository를 로드 (=>@Resource 사용) 
	//@Repository - @Resource 를 달아야 다른 패키지에서도 가져다 쓸 수 있음 
	
	@GetMapping("/index2.do")
	public String index2(Model m) {
		String mid= "abc"; 
		List<membership_DTO> one = this.mdao2.onedata(mid);
		this.log.info(one.get(0).getMEMAIL());
		return null;
	}
	
	
//************************************************************
	
	//회원가입
	@RequestMapping(value={"/join.do","/joinok.do"}, method= RequestMethod.POST)
	public String join(@ModelAttribute("cp") membership_DTO mdto2,  HttpServletResponse res) {
	//@RequestMapping(value={"/join.do","/joinok.do"} => 둘 다 받을수 있음(멀티매핑, RequestMapping only)	
		res.setContentType("text/html;charset=utf-8");
		this.log.info("mdto2.getMID() : "+mdto2.getMID());
		
		try {
			this.pw = res.getWriter();
			int result = this.ms.join_member(mdto2);
			
			if(result > 0 ) {
				this.pw.write("<script>"+"alert('신규회원가입완료');"+"location.href='./login.do';"+"</script>");
				
			}else {
				this.pw.write("<script>"+"alert('가입실패');"+"history.go(-1);"+"</script>");
				
			}
			
		} catch (IOException e) {
			this.log.error("error"+e);
			e.printStackTrace();
			
		} finally {
			this.pw.close();
		}
		return null;
	}
	
//************************************************************
	//cdn서버로 이미지 및 컨텐츠 파일전송 메소드 
	@PostMapping("/cdn_uploadok.do")
	public String cdn_server(@RequestParam(name="mfile", defaultValue="") MultipartFile mfile) {
		//this.log.info(mfile.getOriginalFilename());
		
		FTPClient ftp = new FTPClient();  //FTP 서버에 접속하는 역할의 라이브러리 호출. cdn서버 연결 역할 
		ftp.setControlEncoding("utf-8");  //cdn연결 후 파일전송시 한글파일명 깨짐 방지 
		
		FTPClientConfig cf = new FTPClientConfig(); //FTP접속 클라인언트 객체 생성(ftp 접속정보를 배열로 저장)  
		
		try {
			
			String filenm = mfile.getOriginalFilename();  //FE가 전송한 파일명을 저장 
			String host = "kbsn.or.kr"; //cdn 접속경로 url.(호스트주소) 도메인 없으면 아이피 입력.
			//http : 브라우저에 접속하는 프로토콜 
			
			//FTP정보 
			String user="testuser";  //사용자명
			String pass="a10041004!";
			int port = 22;  //ftp 접속 포트번호(기본포트)
			//*sftp 기본포트 : 22
			
			ftp.configure(cf); 
			ftp.connect(host,port);
			
			if(ftp.login(user, pass)) {
				ftp.setFileType(FTP.BINARY_FILE_TYPE);  //이미지, 동영상,pdf, xls 만 전송 
//				ftp.setFileType(FTP.ASCII_FILE_TYPE);  //txt, html, js, css.. (코드화되어있는 파일) 만 전송 
				
				int result = ftp.getReplyCode(); //cdn서버에서 파일 업로드시 지연사항을 확인
				this.log.info("지연코드 : "+result);
				
				boolean ok = ftp.storeFile("/cdn_upload/"+filenm, mfile.getInputStream());
				if(ok==true) {  //정상적으로 저장한 경우 
					this.log.info("정상적으로 cdn서버에 파일 업로드 완료.");
				}else {  //저장에 실패한 경우 
					this.log.info("파일전송에 대해 오류가 발생했습니다.");
				}
			}
			else {
				this.log.info("FTP정보가 올바르지 않아서 CDN 서버 접근이 불가능합니다");
			}
			
		} catch (Exception e) {
			this.log.error("error : "+e);
			e.printStackTrace();
			
		} finally {
			try {
				ftp.disconnect();  //ftp접속 종료 
				
			} catch (IOException e) {
				this.log.error("error2 : "+e);
				e.printStackTrace();
			}  
		}
		return null;
	}
	
	
	//CDN API서버 (파라미터 값{filenm}을 이용해 API서버에 있는 이미지를 출력)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
//	@CrossOrigin(origins = "172.30.1.28", allowedHeaders = "*")
	@GetMapping("/cdn_listapi/{filenm}")
	public @ResponseBody byte[] cdn_listapi(@PathVariable String filenm) {
	//@ResponseBody	: Mapping된 페이지에서 결과값을 출력함 
	
		byte[] img = null; //FE에게 CDN경로 이미지명을 전송 
		String img_url = null; 
		
//		if(!filenm.equals("")) { //파라미터 값이 있을 때 
		if(filenm.equals("cat")) {  //파라미터 값이 cat 일때 
		//파라미터값에 맞는 DB에 정보를 확인 후 해당 정보가 있을 경우 DB에 저장된 경로를 변수에 저장 
			img_url = "http://kbsn.or.kr/cdn_upload/cat.jpg";
			
			try {
				URL url = new URL(img_url);
				//URL : http의 URL 라이브러리를 이용하여 외부접속환경을 세팅 
				
				HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
				//HttpURLConnection : http 프로토콜로 적용시 사용
				//*https 프로토콜로 적용시엔 HttpsURLConnection을 사용 
				
				InputStream is = httpcon.getInputStream();  //해당 이미지를 바이트로 가져옴 
				img = IOUtils.toByteArray(is);  //byte변수에 가져온 이미지 전체를 저장 
				
				is.close();
				httpcon.disconnect();
						
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}else {
			this.log.info("해당경로에 대한 사항이 없습니다.");
		}
		return img;  //jsp에 있는 <img src> 태그로 해당 api경로 및 파일명 사용시 이미지를 출력함 
	}
	
	
	

}

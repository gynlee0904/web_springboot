package kr.co.boot;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class cdn_controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="file_DTO") file_DTO fdto;  //@Resource는 컨트롤러에서 호출하는것과 모델에서 호출하는것이 다름. new와 같은것
	@Resource(name="cdn_model") cdn_model m_cdn; //cdn서버를 모델화 
	
	@Autowired cdn_DAO service;
//	@Autowired dbinfos mysql;
	
	PrintWriter pw = null;
	
	
	
//	@GetMapping("/cdn/mysql.do")
//	public String mysql_list() throws Exception{
//		//mysql data 로드
//		Connection con = this.mysql.mysqldb().getConnection();  //db연결 
//		this.log.info(con.toString());
//		
//		String sql ="select * from event";
//		PreparedStatement ps = con.prepareStatement(sql);
//		ResultSet rs = ps.executeQuery();
//		rs.next();
//		
//		this.log.info(rs.getString("ename"));
//		
//		rs.close();
//		ps.close();
//		con.close();
//		
//		return null;
//	}
	
	
	
	//cdn 다운로드 : 사용자가 해당 파일을 클릭시 파일을 자신의 pc,모바일에 다운로드 할수 있는 api 메소드 
	@GetMapping("/cdn/download.do/{filenm}")
	public void downloads(@PathVariable(name="filenm") String filenm, HttpServletResponse res) throws Exception {
		//외부에서 이미지 및 파일, 동영상을 ftp url로 읽어서 byte로 변환시 무조건 File 객체 사용해야함 
		/*File f = new File(this.fdto.getFILE_BIN());  //=>ftp나 경로를 직접 로드한 후에 바이트로변환 가능
		byte[] files = FileUtils.readFileToByteArray(f);  //(new File(this.fdto.getFILE_BIN())); 라고 쓸 수도 있음 
		//=> http:// 로 가져와야 하는데 http:\로 가져옴. ftp://로 가져오기 때문 (=>http랑 다름)
		 */
		
		//파일탐색기 주소창에 ftp url 입력하면 ftp 로그인 할 수 있음 
		
		//서버경로에 맞는 파일을 로드 
		URL url = new URL("http://kbsn.or.kr/nyong/"+filenm);
		
		//http로 커넥션 (통신)
		HttpURLConnection hc = (HttpURLConnection)url.openConnection();
		
		//해당경로의 이미지를 byte로 읽어들임 
		InputStream is = new BufferedInputStream(hc.getInputStream());
		//byte[] files = is.readAllBytes();  //바이트로 읽어들임 
		
		//해당컨트롤러에서 파일을 다운로드 받을 수 있도록 처리 
		res.setHeader("content-transfer-encoding", "binary");
		res.setContentType("application/x-download");
		
		//파일을 저장할수 있도록 설정 
		OutputStream os = res.getOutputStream();
		
		//서버에 있는 값을 pc로 복제
		IOUtils.copy(is, os);
		
		//IO닫기
		os.flush();
		os.close();
		is.close();
	}
	
	
	
	//cdn 이미지 삭제 
	@PostMapping("/cdn/cdn_delete.do")  //PostMapping 쓴 이유 : form으로 name을 받으니까. 아니면 DeleteMapping써도 됨 
	public String cdn_delete(@RequestParam(name="cbox")String cbox[]) throws Exception {
//		this.log.info(String.valueOf(cbox.length));
		
		int countck = 0; //성공한 결과만 +1 증가시키는 변수 
		
		for(String idx : cbox) {
			this.fdto.setAIDX(Integer.parseInt(idx)); //전달받은 aidx값을 dto에 저장
			List<file_DTO> one = this.service.aaa(1,this.fdto);
			boolean result = this.m_cdn.cdn_ftpdel(one.get(0).getNEW_FILE(), idx);
			if(result == true) {  //파일삭제도 되고 DB에서도 삭제가 된 경우 
				countck++;
			}
		}
		
		if(cbox.length==countck) {
			this.log.info("정상적으로 삭제 완료되었습니다.");
		}else {
			this.log.info("삭제되지 않은 데이터가 있습니다.");
		}
		
		return "redirect:/cdn/cdn_filelist.do";  //redirect : 해당 do로 다시 이동시킴 
	}
	/*응용문제 : FTP에 파일이 없을경우 DB에서 데이터가 삭제되지 않는 버그 발생 
	  해당 버그를 수정하여 FTP에 파일이 없어도 DB에서 데이터가 삭제되도록 수정할 것 */
	
	
	//CDN API 서버
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@ResponseBody  //ResponseBody : 해당 메소드에서 자료를 바로 출력시킨다는 뜻(printWrite처럼) => View(=jsp) 없음 
	@GetMapping("/cdn/images/{api_id}")
	public byte[] cdn_images(@PathVariable(name="api_id") String id) throws Exception {
		byte[] img = null; 
		
		if(!id.equals(null) || !id.equals("")) {
			String cdn_imgs = this.service.cdn_imgs(id); //cdn 이미지 url 가져오기
			try {
				URL url = new URL(cdn_imgs);
				//url을 가져와서 http인지 https인지 확인해야함 (IndexOf("https")로 포함여부 확인)
				//AWS, GCP => http, https 둘 다 있으므로 조건문으로 핸들링해야함 
				
				HttpURLConnection hc = (HttpURLConnection)url.openConnection();
				
				InputStream is = hc.getInputStream();  
				img = IOUtils.toByteArray(is);  
				
			} catch (Exception e) {
				this.log.error("error : "+e);
			}
			
		}
		return img;
	}
	

	/*Controller에서 모든 list 화면을 출력시 
	  1. 한페이지당 출력하는 데이터 개수 
	  2. 데이터 검색어를 받을 경우 
	  3. 사용자가 페이지 번호를 눌렀을 때 
	 위의 것들을 컨트롤러에서 핸들링 함 */
	//전체리스트 출력
	@GetMapping("/cdn/cdn_filelist.do")
	public String cdn_filelist(Model m, @RequestParam(name="word", defaultValue = "", required=false) String word) {
		//defaultValue = "" : null로 날아오면 ""로 변경 
		
		List<file_DTO> all = null;
		if(word.equals("")) {   //null은 연산기호 ==로 비교. ""는 equals()로 비교
			all = this.service.aaa(2,this.fdto);  //Integer 라 0 인식 못함. 1말고 다른 숫자 넣어 전달 
		}else {  //검색어가 있을 경우
			this.fdto.setWord(word);  //dto에 전달받은 word값 저장해서 전달 
			all = this.service.aaa(3,this.fdto);
		}
	
		
		
		m.addAttribute("all",all);
		
		return null;
	}
	
	
	
	//cdn서버로 파일 전송 및 db저장 
	@PostMapping("/cdn/cdn_fileok.do")
	public String cdn_fileok(@RequestParam(name="mfile") MultipartFile mfile[],
							@RequestParam(name="url") String url, HttpServletResponse res) {
	//파일이 여러개 날아올 경우 []배열로 받음. 미리 배열로 받아두면 FE에서 하나만 보내든 여러개 보내든 코드 안고쳐도됨 
		res.setContentType("text/html;charset=utf-8");
		this.log.info("파일명 : "+ mfile[0].getOriginalFilename());
		
		try {
			this.pw = res.getWriter();
			
			//사용자가 업로드한 파일명을 리네임 한 후 ftp 전송
			boolean result = this.m_cdn.cdn_ftp(mfile[0], url);  
			
			if(result == true) {
				this.pw.print("<script> alert('파일 정상 등록 완료'); location.href='./cdn_filelist.do'; </script>");
			}else {
				this.pw.print("<script>"+"alert('파일 등록 실패');"+"history.go(-1);"+"</script>");
			}
			
		} catch (Exception e) {
			
			
		} 
		return null;
	}
}

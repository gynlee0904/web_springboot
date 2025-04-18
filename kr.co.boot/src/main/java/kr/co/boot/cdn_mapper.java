package kr.co.boot;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface cdn_mapper {
	//★★★매퍼의 메소드명은 mapper.xml의 id와 맞춰야 함 
	//인터페이스는 private못씀(로드를 못하게 됨)protected, default 쓸것 
	
	int cdn_insert(file_DTO dto);  //이미지 등록
	List<file_DTO> cdn_select(Map<String, Object> map);   //전체리스트 출력 
	String cdn_imgs(String APINO);  //cdn 이미지 url 가져오기
	int cdn_delete(String AIDX);  //cdn 이미지 삭제 
	
	
	
	/*=== AOP 수업관련 ======================================================*/
	int log_table(String MID, String LOG_RCD);
	
}



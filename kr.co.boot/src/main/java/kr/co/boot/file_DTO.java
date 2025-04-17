package kr.co.boot;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Repository("file_DTO")
@Data
public class file_DTO {
	int  AIDX; 
    String ORI_FILE, NEW_FILE, API_FILE, FILE_URL, FILE_DATE; 
    byte[] FILE_BIN;  //DB 자료형이 BLOB이므로 byte[]로 해야함. 
    //BLOB, CLOB 자료형 쓰는 애들은 웹 출력용이 아니라 다운로드용.
    
    //테이블 컬럼에 없는 변수 만들어도 됨 (for mapper 적용)
    String word;  //검색관련 get,set. 
}

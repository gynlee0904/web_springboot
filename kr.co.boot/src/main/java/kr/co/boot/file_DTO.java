package kr.co.boot;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Repository("file_DTO")
@Data
public class file_DTO {
	int  AIDX; 
    String ORI_FILE, NEW_FILE, API_FILE, FILE_URL, FILE_DATE; 
    byte[] FILE_BIN;  //DB 자료형이 BLOB이므로 byte[]로 해야함. 
}

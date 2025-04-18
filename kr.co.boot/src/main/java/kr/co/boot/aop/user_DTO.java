package kr.co.boot.aop;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Repository("user_DTO")
@Data
public class user_DTO {
	
	String mid, mname, memail;

}

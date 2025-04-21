package kr.co.boot.thymeleaf;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("all_DTO")
public class all_DTO {
	
	//DB와 관계없이 AOP와 Controller에서 서로 상호작용을 하기위한 DTO
	ArrayList<String> menus;
	
	
	
	String mid, mpass, memail;
}

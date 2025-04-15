package kr.co.boot;

import java.util.List;


/* @Service에서 @Override 메소드를 실행함 => @Controller에서 인터페이스 로드 */
public interface membership_service {

	List<membership_DTO> alldata();  //전체데이터 출력
	
	int join_member(membership_DTO dto);  //회원가입
	
}

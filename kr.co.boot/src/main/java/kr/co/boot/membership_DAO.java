package kr.co.boot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
 * @Component : @Controller, @Service, @Repository 가 모두 포함된 @. 
 * @Service : 해당 코드 로직의 정보를 담고 있는 @. 인터페이스를 로드(호출)하면 바로 작동되는 >>클래스<<.
 * 			인터페이스 호출 방식은 @Autowired로 호출.
 * */

@Service
public class membership_DAO implements membership_service {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private mapper mp;  //mapper를 로드해서 sql 쿼리문 실행 
	
	//전체데이터 출력
	@Override
	public List<membership_DTO> alldata() {
//		this.log.atInfo();
		
		List<membership_DTO> all = this.mp.alldata();
		this.log.info(all.toString());
		
		return all;
	}

	//회원가입
	@Override
	public int join_member(membership_DTO dto) {
		int result = this.mp.save_member(dto);
		return result;
	}

}

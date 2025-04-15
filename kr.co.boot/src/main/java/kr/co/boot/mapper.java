package kr.co.boot;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

//@Mapper는 무조건 interface로 만듦
@Mapper
public interface mapper {
	
	List<membership_DTO> alldata();  //전체데이터 출력
	List<membership_DTO> onedata(String MID);  //하나의 데이터만 출력
	int save_member(membership_DTO dto);
	
	
}

package kr.co.boot.ajax;

import java.lang.annotation.Inherited;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class api_service {
/* @Service에서는 implements 하지않는다. implements ajax_mapper 를 하면 재귀함수가 됨 
 * implements 하려면? Service interface를 생성 후 @Override형태로 가능함
 * implements 는 @Mapper를 절대 로드하지 못함(상속된거라서. => @Inherited) 
 * @Mapper는 의존성으로 주입해야함 (@Autowired) 
 */

	//ajax_mapper mapper = new 
	@Autowired ajax_mapper mapper;  //interface를 로드
	
	public int insert_cms(String CID, String CNAME) {
		Map<String, String> all = new HashMap<>();
		all.put("CID", CID);
		all.put("CNAME", CNAME);
		int result =  this.mapper.api_insert(all);
		return result;
	}

	//controller에서 전달값을 map에 넣어 전달한 경우 
//	public int insert_cms(Map<String, String> all) {
//		int result =  this.mapper.api_insert(all);
//		return result;
//	}



	

}

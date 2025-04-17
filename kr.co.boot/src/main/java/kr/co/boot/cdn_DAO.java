package kr.co.boot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class cdn_DAO {
	
	@Autowired cdn_mapper cmp;
	
	//이미지 등록 
	public int cdn_insert(file_DTO dto) {
		int result = this.cmp.cdn_insert(dto);
		return result;
	}
	
	//전체리스트 출력 
	public List<file_DTO> aaa(Integer part, file_DTO dto){
		Map<String, Object> map = new HashMap<>();
		if(part==1) {  //고유값으로 데이터 찾기 
			map.put("part", part);
			map.put("AIDX", dto.getAIDX());
		}else if(part == 3 ) { //검색어로 데이터 찾기 
			map.put("part", part);
			map.put("word", dto.getWord());
		}
		
		List<file_DTO> result = this.cmp.cdn_select(map);
		return result;
	}
	
	//cdn 이미지 url 가져오기
	public String cdn_imgs(String API_FILE) {
		String result = this.cmp.cdn_imgs(API_FILE);
		return result;
	}
	
	
	//cdn 이미지 삭제 
	public int cdn_del(String AIDX) {
		int result = this.cmp.cdn_delete(AIDX);
		return result;
		
	}

}

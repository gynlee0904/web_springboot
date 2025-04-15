package kr.co.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class cdn_DAO {
	
	@Autowired cdn_mapper cmp;
	
	public int cdn_insert(file_DTO dto) {
		int result = this.cmp.cdn_insert(dto);
		return result;
	}
	

}

package kr.co.boot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/*※JPA에서는 @Service 위주로 사용함 
 * JPA에서 @Repository를 꼭 사용해야 할 경우 class에서 extends 해와야함 */

//@Service
@Repository("membership_DAO2")
public class membership_DAO2 {
	@Autowired private mapper mp;
	
	//하나의 데이터만 출력
	public List<membership_DTO> onedata(String MID) {
		List<membership_DTO> one = this.mp.onedata(MID);
		return one;
	}
	
	
}

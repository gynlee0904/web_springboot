package kr.co.boot.aop;

public class exam2 implements ex_interface{
	/*
	public static void main(String[] args) {
		 new exam2().avg();
	}
	*/
	
	@Override
	public float avg() {
		float result = total() / 4.0f;
		return result;
	}
	
	@Override
	public int total() {
		int result = 100+200+300;
		
		try {
			Thread.sleep(5000);  //5초 후 실행 
		} catch (Exception e) {
			
		}
		
		
		return result;
	}
}

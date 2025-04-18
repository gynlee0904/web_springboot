package kr.co.boot;


//java_AOP, exam_interface와 연계
//Controller 혹은 Service 
public class exam implements exam_interface{
	
	/*
	public static void main(String[] args) {
		  //여기 본코드 들어있음 
		int result = new exam().total();
		System.out.println("result : " + result);
	}
	 */
	
	@Override
	public int total() {
		int data[] = {10,20,30};
		int sum = 0;
		int w= 0;
		while(w<data.length) {
			sum += data[w];
			w++;
		}
		return sum;
	}

	@Override
	public int avg() {
		int avgs = 500;
		return avgs;
	}

}

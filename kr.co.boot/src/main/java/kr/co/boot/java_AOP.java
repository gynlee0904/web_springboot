package kr.co.boot;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//exam_interface와 연계
public class java_AOP {

	public static void main(String[] args) {
		//List<String> list = new ArrayList(); => 인터페이스 때리고 클래스 로드해서 씀  
		/* 인터페이스 로드 후 해당 인터페이스를 상속한 클래스를 호출하는 방식 
		exam_interface ex = new exam();
		int result = ex.total();
		System.out.println(result);  //=>60 출력 
		*/
		
		
		/* AOP (Proxy를 쓰면 AOP를 쓴다는 뜻) 
		   .newProxyInstance() : 새로운 객체를 만듦.    
		   => 기존 메소드도 실행 + 신규 코드의 결과값을 리턴 
		   newProxyInstance(1,2,3) 
		   ㄴ1: .class.getClassLoader() => 동적 프록시 클래스 로드하는 역할. 해당 인터페이스에 있는 클래스 다 들고오라는 뜻 
		   ㄴ2: new Class[]{} => 해당 인터페이스에 있는 기존의 클래스에 코드를 강제 덮어 쓰겠다는 뜻 
		   ㄴ3: new InvocationHandler(){} => 오바라이드된 메소드에 새로 덮어쓸 코드 작성 
		   
		   *
		   *AOP 왜씀? 혹시 문제가 되면 원래 사용했던 본코드로 빠르게 복귀할 수 있도록 함 */
		exam_interface ex = new exam();
		
		//여기서부터 AOP 사용 
		exam_interface aops = (exam_interface)Proxy.newProxyInstance(exam_interface.class.getClassLoader(),
							new Class[]{exam_interface.class}, 
							new InvocationHandler() {
			
								@Override
								public Integer invoke(Object proxy, Method method, Object[] args) throws Throwable {
									 									
									Object result = method.invoke(ex, args);  //본코드의 메소드를 실행시킨 결과값을 가져오는 역할 
									Integer total2 =Integer.parseInt(result.toString()) + 100;
									return total2;
								}
								
							});
		
		int result = ex.total(); 
		System.out.println("본코드(exam.java)에서 실행된 결과값 : "+ result);
		
		//위 코드도 실행하면서 aop에서 추가 코드를 넣어서 작동시킨 값 
		int result_aop = aops.total();
		System.out.println("AOP에서 실행된 결과값(total) : "+ result_aop);
		
		int result_aop2 = aops.avg();
		System.out.println("AOP에서 실행된 결과값(avg) : "+ result_aop2);
		
		//본클래스(exam.java)에서 실행함수가 잠겨도 AOP에서 다 작동시킬 수 있음 
		
		
	}

}

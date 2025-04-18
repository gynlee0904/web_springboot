package kr.co.boot.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//AOP를 사용하는 형태는 기본 작동되고 있는 메소드에서 추가 코드 및 옵션이 발생했을떄 사용하는 기준 
//절대 적용하지 않는 사항은 interface에 메소드추가, 본클래스 본코드에 코드 및 변수를 추가하지 않는다.
public class AOP_exam {

	public static void main(String[] args) {
		ex_interface exam = new exam2();  //본코드 로드 
		ex_interface proxy = (ex_interface)Proxy.newProxyInstance(ex_interface.class.getClassLoader(),
								new Class[] {ex_interface.class}, 
								new InvocationHandler(){

									@Override
									public Object invoke(Object proxy, Method method, Object[] aaaa) throws Throwable {
										
										//본코드(exam2.java)의 전체 소스를 실행 
										Object result2 = method.invoke(exam, aaaa);
										/*method.invoke(호출할 클래스명, 결과값을 갖고있는 변수) : 해당 클래스의 메소드를 실행 
										  해당클래스의 특정 메소드만 호출할 수도 있음. exam.total()이렇게 쓰면됨 
										*/
										//본코드에서 사용한 변수명과 동일한 변수를 사용하더라도 본코드에 영향x. (다른 변수로 인식됨)
										
										//본코드에 없는 코드를 추가 
										long start = System.currentTimeMillis();  //시작시간
										long end = System.currentTimeMillis();  //종료시간 
										
										String message = (end-start) + "ms시간이 걸림";
										System.out.println(message);  //결과값 확인 

										return result2;
									}
								});
		System.out.println("total : "+ proxy.total());
		System.out.println("avg : "+ proxy.avg());
	}

}

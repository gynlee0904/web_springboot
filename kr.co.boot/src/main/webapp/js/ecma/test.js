document.querySelector("#btn").addEventListener("click",function(){
	new member_join().input_ck();
	
});

export class member_join{
	
	input_ck(){
		if(form.MID.value==""){
			alert("아이디를 입력하세요");
		}
		else if(form.MPASS.value==""){
			alert("패스워드를 입력하세요");
		}
		else {
			form.submit();
		}
	}
}

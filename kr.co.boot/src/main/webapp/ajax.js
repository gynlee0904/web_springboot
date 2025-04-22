var ea = document.querySelectorAll(".pd");
//querySelectorAll => 오브젝트가 원시배열형태로 바뀜 

document.querySelector("#all").addEventListener("click", function(){	
	console.log(ea.length);
	console.log(this.checked);
	
	for(var a = 0; a <ea.length; a++){
		ea[a].checked = this.checked;  //this는 querySelector("#all")을 지칭
	}
});


ea[0].addEventListener("click", function(){	
	console.log("ssss");
});




function ajax(){
 	let count = 0;
	var formdata = new FormData();
	
	for(var a = 0; a <ea.length; a++){
		if(ea[a].checked == true){
//			console.log(ea[a].value);
			formdata.append("product", ea[a].value);
			count++;
		}
	}
	
	if(count==0){
		alert("최소 한개 이상 상품을 체크하세요.");
	}
	else{  //ajax post통신 
//		console.log(formdata.getAll("product"));
		//getAll("") : 해당 키 전부를 가져옴 (같은 이름으로 키를 만들었다면 이걸 사용해야함)
		//get("") : 특정 키 하나만 가져옴 
		
		//FormData, form태그 => get, post밖에 못씀
		var http = new XMLHttpRequest();
		http.onreadystatechage = function() {
	        if (http.readyState == 4 && http.status == 200) { 
				if(this.response=="ok"){
					alert("정상적으로 데이터 처리");
				}else{
					alert("데이터 처리 오류");
				}

	        }else {
	            alert("에러가 발생했습니다");
	            console.log("상태 코드:", http.status);
	        }
	    }
		http.open("POST","./ajax.do",true); 
		http.setRequestHeader("mkey","a123456");  //보안키 설정, 전송 
		http.send(formdata);
	}
}

//보내기 셋뚜셋뚜
//	http.setRequestHeader("content-type","application/x-www-form-urlencoded"),
//  http.send("product=testtest");

//	http.setRequestHeader("content-type","application/json"),
//  http.send("product="+JSON.stringify("변수값"));

//	http.setRequestHeader("mkey","a123456"),
//  http.send(formdata);
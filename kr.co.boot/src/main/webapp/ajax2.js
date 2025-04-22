//a개발자의 입력값을 받아서 b개발자가 ajax로 통신 적용 후 결과값 리턴시켜줌
function apink_love(id, name){
//	console.log(id);
//	console.log(name);
	
	var result;
	if(id =="" || name ==""){  //a개발자가 데이터를 비정상적으로 보낼 경우 
		result = "no";
		
	}else{  //정상적으로 데이터를 받을경우 
		fetch("/ajax2.do",{  //내가 서버측이면  http://172.30.1.49:8080/ajax2.do 라고 작성해볼것
		// ./ajax2.do=> 자기컴퓨터(local)의 do를 찾음. 다른사람pc의 서버로 날리려면 /ajax2.do 라고 써야함 
			method : "POST",
			body : new URLSearchParams("CID="+id+"&CNAME="+name) 

		}).then(function(aa){
			return aa.text();
			
		}).then(function(bb){
			console.log("bbbbbbb : "+bb)
			if(bb=="ok"){  //b개발자의 컨트롤러에서 SQL 결과값을 받음 
				result = "ok";   //a개발자에게 결과값을 전달하기 위함 
			}else{
				result = "error"; 
			}
			
		}).catch(function(error){
			console.log("통신오류발생" + error);	
		});
	}
	console.log("result : "+result);
	return result;  //a개발자에게 회신 
}
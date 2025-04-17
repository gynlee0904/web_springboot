//ES5는 querySelector가 없었음. ES6부터 사용 
document.querySelector("#btn").addEventListener("click",function(){
	new cdn_list().cdn_listdel();
})

document.querySelector("#sh_btn").addEventListener("click",function(){
	new cdn_list().cdn_search();
})

document.querySelector("#sh_btn2").addEventListener("click",function(){
	location.href="./cdn_filelist.do";
	
})

	
export class cdn_list{
	
	
	cdn_listdel(){
		var ob = document.querySelectorAll("#ls .ck"); 
		/*querySelector : 한개의 오브젝트 값을 가져옴 
		  querySelectorAll : 같은 이름을 가진 오브젝트를 전부 가져옴 
		*/
		/*.ck만 쓰면 class명이 ck인 모든 애들 다들고옴 
		  #ls .ck 라고 쓰면 id="ls" 하위의 class="ck"인 애들만 데려옴
		*/
		var count = 0;
		var i = 0;
		do{
			if(ob[i].checked == true){
				count++;
			}
			i++;
		}while(i <ob.length);
		
		if(count == 0){  //체크한게 하나도 없을 때 
			alert("삭제할 이미지를 선택하세요");
		}else{
			if(confirm("삭제시 데이터는 복구되지 않습니다. \n 삭제하겠습니까?")){
				frm.submit();
			}
		}
	}
	
	
	
	cdn_search(){
		if(search.word.value==""){
			alert("검색어를 입력하세요");
		}else{
			search.submit();
		}
		
	}
}






var allck = document.querySelector("#allck");
var cks = document.querySelectorAll("#ls .ck");
console.log(cks)
//var ckd[] = cks.split(",");
//console.log(ckd[0])


allck.addEventListener("change",function(){
	if(this.checked){  //이벤트 리스너 안에서 쓰이는 this는 이벤트가 발생한 요소를 가리킴 
		var w = 0;
		while(w<cks.length){
			cks[w].checked=true;
			w++;
		}	
	}else{
		var w = 0;
		while(w<cks.length){
			cks[w].checked=false;
			w++;
		}	
	}
});

var w = 0;
while(w<cks.length){
	cks[w].addEventListener("change",function(){
		var cnt=0;
		
		for(var f=0; f<cks.length; f++){
			if(cks[f].checked == true){
				cnt++;
			}
		}
		
		console.log("cnt : "+cnt)
		
		if(cnt==cks.length){
			allck.checked=true;
		}else{
			allck.checked=false;
		}
	})
	w++;
}	



	




	
	

	
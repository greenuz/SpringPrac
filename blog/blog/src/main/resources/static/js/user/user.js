let index ={
    init: function(){
        $("#btn-save").on("click",()=>{ //function(){}, () => {} this를 바인딩 하기 위해서
        //btn-save를 찾아서 이벤트를 바인드 한다. ( 리스너를 만드는 것 ) 
        //on 뒤에 첫번재 파라미터는 어떠한 이벤트가 발생하는 지에 대한 조건, 두번째 파라미터에는 무엇을 할건지에 대해
            this.save();
        }); 
        // $("#btn-login").on("click",()=>{ //function(){}, () => {} this를 바인딩 하기 위해서
        //btn-save를 찾아서 이벤트를 바인드 한다. ( 리스너를 만드는 것 ) 
        //on 뒤에 첫번재 파라미터는 어떠한 이벤트가 발생하는 지에 대한 조건, 두번째 파라미터에는 무엇을 할건지에 대해
            this.login();
        // }); 
    },

    save: function(){
        //alert("user의 save 함수 호출됨");
        let data ={ //이런 값들을 찾아서 자바스크립트 오브젝에 넣는다. username, password, email을 id 값으로 찾는다. 값을 각각 변수에 바인딩한다.
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };
    //console.log(data); //잘 들고 오는 지 확인
    // 그 다음은 ajax 요청을 하자.
    
        //ajax 호출시 default가 비동기 호출
        //비동기 호출이라서 동시에 여러개를 처리하면서 실행할 수 있음. 명령을 실행하다가 done 또는 fail의 부분으로 옮겨옴.
         //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청을 한다.
        $.ajax({
            //회원가입 수행 요청. 
            //가장 먼저 필요한 것은 type
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data), // http body 데이터
            contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
            dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이(정말 거의 대부분) 버퍼로 오기 떄문에 문자열이다. 다만, 생긴게 json이라면 -> javascript오브젝트로 변경함.

        }).done(function(resp){ //응답의 결과가 함수의 파라미터로 전달.
            console.log(resp);
            alert("회원가입이 완료되었다.");
            location.href="/";
        }).fail(function(error){
            alert(JSON.stringify(error))

        }); 
    },
    // login: function(){
    //     //alert("user의 save 함수 호출됨");
    //     let data ={ //이런 값들을 찾아서 자바스크립트 오브젝에 넣는다. username, password, email을 id 값으로 찾는다. 값을 각각 변수에 바인딩한다.
    //         username: $("#username").val(),
    //         password: $("#password").val()
    //     };
    // //console.log(data); //잘 들고 오는 지 확인
    // // 그 다음은 ajax 요청을 하자.
    
    //     //ajax 호출시 default가 비동기 호출
    //     //비동기 호출이라서 동시에 여러개를 처리하면서 실행할 수 있음. 명령을 실행하다가 done 또는 fail의 부분으로 옮겨옴.
    //      //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청을 한다.
    //     $.ajax({
    //         //회원가입 수행 요청. 
    //         //가장 먼저 필요한 것은 type
    //         type: "POST",
    //         url: "/api/user/login",
    //         data: JSON.stringify(data), // http body 데이터
    //         contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
    //         dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이(정말 거의 대부분) 버퍼로 오기 떄문에 문자열이다. 다만, 생긴게 json이라면 -> javascript오브젝트로 변경함.

    //     }).done(function(resp){ //응답의 결과가 함수의 파라미터로 전달.
    //         console.log(resp);
    //         alert("로그인이 완료.");
    //         location.href="/";
    //     }).fail(function(error){
    //         alert(JSON.stringify(error))
    //     });
    // }
}

index.init();
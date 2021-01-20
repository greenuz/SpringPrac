package com.greenux.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

/*
    사용자가 요청을했을 때, HTML응답을 해주는 컨트롤러를 만들고 싶을 때
    @Controller를 어노테이션 만들면 된다.

    사용자가 요청을 할 때 응답을 해주는게 RestController이다.
    어떤응답을 해주는 것이냐면 데이터를 응답해줄 것이다.
*/ 

@RestController
public class HttpControllerTest {
    /*
    함수 4개를 만들어보자. 리턴타입이 스트링이므로 우리가 리턴하는 것은 스트링이다.
    인터넷 브라우저 요청은 무조건 get 요청밖에 할 수 없다.
    get요청시에는 물음표 뒤에 쿼리스트링을 통해 데이터를 요청할 수 있다.
    post, put, delete 요청을 해보기 위해 postman 을 설치를 한 것이다.


    @GetMapping("/http/get") //date를 select 해달라.
    public String getTest(@RequestParam int id,@RequestParam String username){
        return "get 요청 :"+ id + ", " + username;
    }
    위에와 같이 한개한개 받지 않고, 모두 다 같이 받고 싶을 때. 위에 것이랑 똑같은 구조이긴 하다.
    */
    /*
        추가적으로 아래와 같은 문자열 리터럴을 하나 선언하고 시작한다.
    */

    /*
        lombok을 아래와 같이 활용한 예시 1 - AllArgsConstructor
    */
    // private static final String TAG = "HttpControllerTest :";
    
    // @GetMapping("/http/lombok")
    // public String lombokTest(){
    //     Member m = new Member(1, "greenux", "4567", "greenux@naver.com");
    //     System.out.println(TAG+"getter: "+m.getId());
    //     m.setId(5000);
    //     System.out.println(TAG+"setter: "+m.getId());
    //     return "lombok test완료";
        
    // }
    /*
        lombok 활용 예시 2 - Builder 로 만든 예시
        장점 - 생성자에 순서를 지키지 않아도 된다.
        장점 - 필드에 어떤 값이 잘못들어가는 일이 잘 발생하지 않는다.
    */
    private static final String TAG = "HttpControllerTest :";
    
    @GetMapping("/http/lombok")
    public String lombokTest(){
        Member m = Member.builder().password("1234").username("greenux").email("greenux@gmail.com").build();
        System.out.println(TAG+"getter: "+m.getId());
        System.out.println(TAG+"getter: " + m.getUsername());
        m.setId(5000);
        m.setUsername("gogogo");
        System.out.println(TAG+"setter: "+m.getId()+ m.getUsername());
        return "lombok test완료";
        
    }
    @GetMapping("/http/get")
    public String getTest(Member m){ //컨트롤러에서 쿼리스트링으로 들어온 값에 대해 멤버에 넣어주는 역할도 해준다.
        return "get 요청 : " +m.getId() + ", " + m.getUsername() + ", " +m.getPassword()+", "+ m.getEmail();
    }

    /*
        포스트 요청방시에 대해 잘 배워보자.
        get과 비슷하게 RequestParam 어노테이션을 사용해도 되지만, 좀더 편리하게 Member 클래스를 사용하자.

        post는 주소에 쿼리스트링을 보내는 것이 아니라, 데이터를 body에 담아 보내야 한다!!!
        방법이 되게 많은데.
        http에서 요청 했을 때에는
        <form>
            <input type="">
        </form>
        과 같은 방법으로 요청해본 사람이 있을 것이다. 요런 MIME TYPE 방식이
        x-www-form-urlencoded 방식이다.
    @PostMapping("/http/post") //insert
    public String postTest(Member m){
        return "post 요청 : " +m.getId() + ", " + m.getUsername() + ", " +m.getPassword()+", "+ m.getEmail();
    }
        -----------------------------
        raw로 요청을 해보자.(text) => 요건 text/plain MIME TYPE
        post로 요청할 때에는, RequestBody로 메소드 안을 꾸며야 하네... Get의 RequestParam과 다르네..
    @PostMapping("/http/post") //insert
    public String postTest(@RequestBody String text){
        return text;
    }
        -----------------------------
        raw로 요청을 하는데 MIME TYPE을 사용자가 application/json으로 요청하고 싶을 때.
        해당 RequestBody를 Member클래스를 활용하여 보내고 싶을 때, 파싱할 때 유리.
    @PostMapping("/http/post") //insert
    public String postTest(@RequestBody Member m){
        return "post 요청 : " +m.getId() + ", " + m.getUsername() + ", " +m.getPassword()+", "+ m.getEmail();
    }
    //html의 헤더의 raw 이지만, text/plain로 보냈을 때와 application/json 일 때와의 동작이 다르다.
    text/plain 일때에 해당 Member m에 대입을 할 수 없게 되고, application/json일 때에는 대입을 할 수 있게된다.
    이런 대입할 수 있도록 변화시켜주는 것을 스프링부트의 MessageConverter라는 것이 해준다.
    */
    @PostMapping("/http/post") //insert
    public String postTest(@RequestBody Member m){
        return "post 요청 : " +m.getId() + ", " + m.getUsername() + ", " +m.getPassword()+", "+ m.getEmail();
    }

    /*
        똑같이 MIME TYPE application/json으로 요청해보자.
        즉 스프링에서는 Body 에 데이터를 실어서 보내면, 스프링에서는 간단하게 @RequestBody 라는 오노테이션을 통해 오브젝트로 매핑해서 받을 수 있다.
    */
    @PutMapping("/http/put") //update
    public String putTest(@RequestBody Member m){
        return "put 요청" + m.getId() + "의 password 값이 " + (Integer.parseInt(m.getPassword())+ 5555) + m.getUsername()+ ", "+ m.getEmail();
    }
    @DeleteMapping("/http/delete") //delete
    public String deleteTest(@RequestBody Member m){
        return "delete 요청" + m.getId() +  ", "+ m.getUsername() + ", " +m.getPassword()+", "+ m.getEmail();
    }
}

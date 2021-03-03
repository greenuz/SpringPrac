package com.greenux.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import com.greenux.blog.model.Roletype;
import com.greenux.blog.model.User;
import com.greenux.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//회원가입이 잘 되었다 안되었다를 응답만 해줄 수 있기 해줘보자.
@RestController
public class DummyControllerTest {

    @Autowired //의존성 주입(DI)
    private UserRepository userRepository; 
    //스프링이 RestController롤 읽어서 DummyController를 읽어줄 때에는 NULL 일 것이다. 
    //이떄 Autowired라는 어노테이션을붙여 준다면, DummyControllerTest가 메모리에 뜰 때에, UserRepository 도 메모리에 같이 뜬다.
    //요 Autowired는 스프링이 관리하고 있는 객체중에 UserRepository 라는 타입이 있다면, 요것을 해당 userRepository 변수에 넣어서 메모리에 띄어준다.
    //스프링이 컴포넌트 스캔할 때, repository 패키지에  UserRepository 가 이미 인터페이스로 메모리에 띄어주므로 그냥 Autowired를 이용해서 사용하기만 하면된다.(의존성 주입)

    
    // http://localhost:8000/blog/dummy/join 에 요청을 하게되면,
    // http 의 body 에 username, password, email 데이터를 가지고 요청을 함.
    // @PostMapping("/dummy/join") //회원가입을 할 것이므로 insert를 해야하므로 postmapping
    // public String join(String username, String password, String email){ //key = value(약속된 규칙)
    //     //User 클래스를 보았을 때, username, password, email 만 받고 나머지는 default로 들어감.
    //     System.out.println("username :" + username);
    //     System.out.println("password :" + password);
    //     System.out.println("email:" + email);
    //     return "회원가입이 완료되었다.";
    // }
    @PostMapping("/dummy/join") //회원가입을 할 것이므로 insert를 해야하므로 postmapping
    public String join(User user){//object
        //User 클래스를 보았을 때, username, password, email 만 받고 나머지는 default로 들어감.
        System.out.println("id: :"  +user.getId());
        System.out.println("username :" + user.getUsername());
        System.out.println("password :" + user.getPassword());
        System.out.println("email:" + user.getEmail());
        System.out.println("role : " + user.getRole());
        System.out.println("createDate: " +user.getCreateDate());
        user.setRole(Roletype.USER); //user2 이런것도 만들 수 있다. 요런거 실수하지 않기 위해, enum이란것을 사용.
        userRepository.save(user); //객체를 집어넣어서 사용만 하면된다. 이미 메모리에 위에서 띄어져 있는것을 확인.
        /*
              Hibernate: 
                insert 
                into
                    User
                    (createDate, email, password, role, username) 
                values
                    (?, ?, ?, ?, ?)

        요런식으로 명령어가 들어가므로 role 값만 본다면, null이 들어가는데, 아얘 전달이 안되어야 default 값이 된다.
        이럴때에는 @DynamicInsert 어노테이션을 붙여서 null인 필드를 제외한다.
        User 클래스에 적용한다.
        하지만 이런 어노테이션을 하나하나 아는것도 팁이긴 한데, 실제적으로 할 수도 있어야지...
        따라서 주석처리 한다.그리고, user.setRole로 처리한다.
         */
        return "회원가입이 완료되었다.";
    }

    // 이 문법은 {id} 주소로 파라미터를 전달 받을 수 있다.
    // ex) http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        // 요건 옵셔널이다. 만약 /user/4을 찾는데, 내가 만약 데이터베이스에서 못찾으면 user 가 null이 될 것이다.
        // 그럼 return 할 때, null 이 된다. 그럼 프로그램에 문제가 발생할 수 있으니, Optional로 너의 User객체를 감싸서 가져올테니
        // null인지 아닌지를 판단해서 return 해!
        // .get() 일경우 그럴일이 전혀 없다. --사용하기엔 위험하다.
        // .orElseGet() 없으면 너가 객체하나 만들어서 넣어줘. 그러면 user가 null 이 아닐 것이잖아.
        // User user = userRepository.findById(id).orElseGet(new Supplier<User>(){
        //     //new 한다음 Supplier 치면 익명객체가 만들어 진다. 이 상태에서 이 인터페이스가 들고 있는 get함수를 오버라이딩 해준다.
        //     //supplier 정의에 가보면, 얘는 인터페이스고 get은 추상메소드이기 때문에, 인터페이스를 뉴할수는 없다.
        //     //인터페잇르르 new하려면, 익명 클래스를 만들어야 한다.
            
        //     //없는 id에 대하여, 빈 객체를 User()에 넣어주면, null은아니다.
        //     @Override
        //     public User get(){
        //         // TODO Auto-generated method stub
        //         return new User();
        //     }
        // });

        /*
            findById에 들어가보면, 선호하는 것은 throws를 사용하여 IllegalArgumentException을 날려달라고 한다.
            이말은 id에 이상한 값이 들어오면, throws를 해라는 말.
        */
        /*
            람다식이란 것도 있다. 서플라이어 타입, 리턴타입을 따로 몰라도 된다. 하지만 여기선 따로 사용하지 않겠다.
            User user = userRepository.findById(id).orElseThrow(()->{
                return new IllegalArgumentException("해당 사용자는 없습니다.");
            });
        */
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>(){
            @Override
            public IllegalArgumentException get(){
                // TODO Auto-generated method stub
                return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
            }
        });
        //return 부분에서 user객체가 반환되는데 요청은 웹부라우저에서 했다.
        //RestController이므로 html이 아니라 data를 리턴해주는 컨트롤러인데,
        // user객체가 자바 오브젝트이므로, 웹 브라우저가 이해를 못한다. (html 이나 javascript)같은것이 아니기 떄문에,
        // 이해할 수 있는 데이터로 변환해야 하는데, 가장 좋은 것이 json이다. json으로 변환해서 던저 줘야하는데,
        // 스프링부트에서는 MessageConverter 라는 애가 응답시에 자동 작동을하여 만약에 자바 오브젝트를 리턴하게 되면,
        // MessageConverter 가 jackson 라이브러리를 호출해서 user오브젝트를 json으로 변환해서 브라우저에게 던져준다.
        return user;
    }

    //여러건의 데이터를 select해보자.
    //http://localhost:8000/blog/dummy/users 파라미터 id 값을 받을 필요없이 전체를 받을 것이다.
    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }
    
    // 페이징을 하여 가져온다.
    //한 페이지당 2건의 데이터를 리턴받아 볼 예정, size로 조정
    //http://localhost:8000/blog/dummy/user
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2,sort="id", direction=Sort.Direction.DESC) Pageable pageable){
        Page<User> pagingUsers = userRepository.findAll(pageable);
        //json 데이터중 content 안에만 있는것을 리턴받을려고 한다면, .getContent를 사용하자.
        //이럴 때에는 Content가 Page 가  아닌 List 타입으로 리턴해야 함.

        /*
            if(pagingUser.isFirst()) OR if(pagingUser.isLast()) 
            등등의 것을 활용하여 Page 라는 클래스를 사용하여 분기도 활용할 수 있다.
        */
        List<User> users = pagingUsers.getContent();
        return users;
    }


    //업데이트에 대해서 알아보자. PutMapping 어노테이션을 써야함.
    // http://localhost:8000/blog/dummy/user
    // URI 주소는 동일 할 수 있으나, Get이랑 Put의 차이로 인하여 알아서 구분한다.
    // 수정 하고 싶은 것에 대한 정보를 parameter값으로 줘야 한다. 여기서는 email, password
    // json 데이터를 받을려면, RequestBody라는 어노테이션이 필요하다.
    @Transactional
    //Transactional 어노테이션을 붙이기 되면, 데이터베이스 Transaction이 시작하게 된다.
    //Transaction이 종료가 되는 시점은 updateUser 메소드가 종료가 될 때 트랜젝션이 종료가 된다.
    //그럼 자동으로 updateUser가 시작 될 때, Transaction이 호출 됐다가, updateUser가 끝나고 리턴할 때 Transaction이 함수 종료시에 자동 commit이 된다.
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){
        //스프링이 받을 때 json 데이터를 요청했는데, 스프링이 Java 오브젝트로 변환해서 받아준다.
        //이 때 MessageConverter의 Jackson 라이브러리가 변환해서 받아준다.
        System.out.println("id : " + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        /*
            방법 1. 람다식으로 표현. user를 데이터 베이스에서 불러와 사용.
            User user = userRepository.findById(id).orElseThrow(()->{
                return new IllegalArgumentException("수정에 실패하였다.");
            });
            user.setPassword(requestUser.getPassword());
            user.setEmail(requestUser.getEmail());
            userRepository.save(user);
            이 유저는 데이터 베이스에서 불러온 것이므로, null인 값이 없어 null 값을 저장할 일이 없다.
        */

        /*
            방법2. Transactional 어노테이션을 활용한 방법. - Dirty Checking
            save를 사용하지 않아도 업데이트가 된다.
         */
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        
        
        //userRepository.save(requestUser); //save 는 insert 할 때 사용하는 메서드였다. update 할때에도 사용할 수 있다. 없는 데이터 일 때에는 insert 없을 때에는 update.
        // save 함수는 id를 전달하지 않으면 insert를 한다.
        // save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
        // save 함수는 id를 전달하면 해당 id 에 대한 데이터가 없으면 insert를 한다.
        //그런데 업데이트할 때 requestUser의 제공되어지지 않는 파라미터를 제외하고서는 모두 null 값이 들어가서 하나하나 처리하기 귀찮아 진다.
        return user;

    }

    //dummy 데이터의 delete 해보기
    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){

        // try catch문으로 exception 처리.
        // try { 
        //     userRepository.deleteById(id);
        // } catch (EmptyResultDataAccessException e) {
        //     return "삭제 실패하였습니다. 해당 id는 db에 없습니다.";
        // }
        userRepository.deleteById(id); //핸들러 사용할 때. 자동으로 handler 패키지에서 만들어둔 GlobalExceptionHandler에서 처리.
        
        return id+" 가 삭제 완료되었다.";
    }    

}

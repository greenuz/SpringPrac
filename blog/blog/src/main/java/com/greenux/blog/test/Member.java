package com.greenux.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/*
    데이터 베이스에 아직 연동하지 않았지만 모델을 아래와 같이 만들어 보자.
    참고로 private 변수는 메소드에 의해서 변경되도록 설계되어야 객체 지향!
*/
    /*
        lombok을 활용한 예시
        Data = Getter + Setter 

        데이터 변수에 들고온 값을 불변성을 유지하기 위해 ,final로 유지하는 경우도 많다.
        public class Member{
            private final int id;
            private final String username;
            private final String password;
            private final String email;
        }
        이런 final 붙은 애들의 annotation을 활용할 때에는 (모두가 final 일때 유용.)
        @RequiredArgsConstructor

        추가적으로 @NoArgsConstructor -> 빈 생성자.
   
    @Getter 
    @Setter
    @Data
    @AllArgsConstructor //생성자.
    @NoArgsConstructor //빈생성자.
    @RequiredArgsConstructor
     */
//@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;

    @Builder //AllArgsConstructor 안사용 하고, 3개의 인자 받을 때, 
    public Member(int id, String username, String password, String email){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /*
        원래라면 아래와 같이 하나하나 Constructor, Getter, Setter를 만들어 줘야 하나.
        lombok을 활용하면, 편하게 구성할 수 있다.
    */

    // public Member(int id, String username, String password, String email){
    //     this.id = id;
    //     this.username = username;
    //     this.password = password;
    //     this.email = email;
    // }

    // public int getId() {
    //     return this.id;
    // }

    // public void setId(int id) {
    //     this.id = id;
    // }

    // public String getUsername() {
    //     return this.username;
    // }

    // public void setUsername(String username) {
    //     this.username = username;
    // }

    // public String getPassword() {
    //     return this.password;
    // }

    // public void setPassword(String password) {
    //     this.password = password;
    // }

    // public String getEmail() {
    //     return this.email;
    // }

    // public void setEmail(String email) {
    //     this.email = email;
    // }
}

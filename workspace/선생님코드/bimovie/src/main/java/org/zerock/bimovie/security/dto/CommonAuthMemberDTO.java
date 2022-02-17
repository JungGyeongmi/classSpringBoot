package org.zerock.bimovie.security.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Setter
@ToString
public class CommonAuthMemberDTO extends User implements OAuth2User {
  private Long mno;
  private String username;
  private String email;
  private String password;
  private String name;
  private String mobile;
  private boolean fromSocial;
  private Map<String, Object> attr;// Social에서 오는 OAuth정보
  
  // DB로부터 사용자를 초기화하는 생성자1
  public CommonAuthMemberDTO(
    Long mno,
    String username, String password,
    String name, String email, String mobile,
    boolean fromSocial,
    Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.mno = mno;
    this.username = username;
    this.password = password;
    this.name = name;
    this.email = username;
    this.mobile = mobile;
    this.fromSocial = fromSocial;
    log.info("ClubAuthMemberDTO 생성자 실행");
  }

  // OAuth로부터 사용자를 초기화하는 생성자2
  public CommonAuthMemberDTO(
    Long mno, 
    String username, String password,
    String name, String email, String mobile,
    boolean fromSocial,
    Collection<? extends GrantedAuthority> authorities,
    Map<String, Object> attr) {
    this(mno,username,password,name,email,mobile
              ,fromSocial,authorities);
    this.attr = attr;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return this.attr;
  }
}

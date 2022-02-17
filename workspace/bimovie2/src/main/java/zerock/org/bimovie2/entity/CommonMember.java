package org.zerock.bimovie.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommonMember extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long mno;
  
  private String email;
  private String password;
  private String name;
  private String mobile;
  private boolean fromSocial;

  //jpa에서 연결되는 객체가 collection일 경우 사용
  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  private Set<AuthorityRole> roleSet = new HashSet<>();

  public void addMemberRole(AuthorityRole authorityRole){
    roleSet.add(authorityRole);
  }
}

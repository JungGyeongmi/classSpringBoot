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
public class CommonMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;
    private String email;
    private String name;
    private String password;
    private String mobile;
    private boolean fromSocial;

    // jpa에서 연결되는 객체가 collection일 경우에
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<AuthorityRole> roleSet = new HashSet<>();
    // set 의 특징 : 중복허락 x, 순서 O
    // club role의 상수를 가져와서 list로 안 넣는거는 중복을 피하기 위함임

    public void addMemberRole(AuthorityRole clubMemberRole) {
        roleSet.add(clubMemberRole);
    }

    public void changePassword(String password){
        this.password = password;
    }
}

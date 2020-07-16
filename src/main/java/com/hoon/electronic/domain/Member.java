package com.hoon.electronic.domain;

import org.apache.commons.lang3.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
/**
 * JPA 는 구현 라이브러리가 객체를 생성할 때 리플랙션 같은 기술을 사용할 수
 * 있도록 지원해야 하기 때문에 기본 생성자가 필요하다.
 * 따라서 NoArgsConstructor 를 사용해서 기본 생성자를 생성하도록 한다.
 * <p>
 * 접근제한자를 protected 로 설정하여 new Member() 사용을 제한함으로써 무분별한
 * 객체 생성을 막고 객체의 일관성을 유지한다. 이는 메모리의 낭비를 줄이고 의도치
 * 않은 객체의 변경을 방지하기 위해서이다.
 * <p>
 * protected: 같은 패키지에서 접근 가능(단, 상속받을 경우 다른 패키지에서 접근 가능)
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    @Setter
    private String password;

    @Setter
    private String salt;

    private String name;

    private String phone;

    /**
     * enum type 을 정하기 위한 어노테이션이다.
     * <p>
     * 기본값은 ORDINAL 이지만 순서를 기준으로 값을 표시하기 때문에
     * 새로운 enum 값이 추가될 경우 순서가 뒤섞여서 알아보기 힘들다는
     * 단점을 가진다.
     * <p>
     * 따라서 type 을 string 으로 정해서 명확한 값을 표시한다.
     */
    @Enumerated(EnumType.STRING)
    private AccountPermissionLevel level;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Purchase> purchaseList = new ArrayList<>();

    private LocalDateTime createDateTime;

    public boolean isNotMatchPassword(String loginPassword) {
        return !StringUtils.equals(password, loginPassword);
    }
}

package com.hoon.electronic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Getter

/**
 * 상속 관계를 정의하고 전략을 설정한다.
 *
 * JOINED: 정규화된 테이블을 만들 수 있다. 하지만 조회 시 조인을 많이 사용하게 되고
 * 조회 쿼리가 복잡해진다는 단점이 있다.
 *
 * SINGLE: 조인이 필요없고 조회 쿼리가 단순하다. 하지만 자식 엔티티가 매핑한 컬럼은
 * null 을 허용하고 단일 테이블에 모든 것을 저장하기 때문에 테이블이 커질 수 있다.
 *
 * TABLE_PER_CLASS: 자식 엔티티를 명확하게 구분지어 처리할 수 있다. 하지만 여러 자식
 * 테이블을 함께 조회할 때 통합해서 쿼리를 처리하기 어렵다.
 */
@Inheritance(strategy = InheritanceType.JOINED)

/**
 * JOINED, SINGLE 상속 전략을 사용할 경우 컬럼을 식별하기 위한 어노테이션이다.
 * 기본값은 DTYPE 이며 원할 경우 name 값을 수정할 수 있다.
 */
@DiscriminatorColumn
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String brand;

    private int price;
}

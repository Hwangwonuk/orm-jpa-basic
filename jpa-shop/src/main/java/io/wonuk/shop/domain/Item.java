package io.wonuk.shop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @Inheritance 조인 전략 설정하는 어노테이션
 * 속성 strategy = InheritanceType.XXX
 * JOINED : 조인 전략
 * SINGLE_TABLE : 단일 테이블 전략
 * TABLE_PER_CLASS : 구현 클래스마다 테이블 전략
 *
 * @DiscriminatorColumn
 * 구분 컬럼명 default = DTYPE
 *
 * @DiscriminatorValue("A")
 * 자식 테이블에 Value로 들어갈 값을 설정하는 어노테이션
 */
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}

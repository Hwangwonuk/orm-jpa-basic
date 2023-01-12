/*
 * Created by Wonuk Hwang on 2023/01/12
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by infra Team <wonuk_hwang@bigin.io>, 2023/01/12
 */
package io.wonuk.shop.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * create on 2023/01/12. create by IntelliJ IDEA.
 *
 * <p> </p>
 * <p> {@link } and {@link } </p> *
 *
 * @author wonukHwang
 * @version 1.0
 * @see
 * @since (ex : 5 + 5)
 */
@Entity
@Getter
@Setter
public class Parent {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  /**
   * CASCADE
   * 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만들고 싶을 때
   * ex) 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장.
   *
   * 언제 CASCADE를 사용해야 하는가??
   * 1. 객체의 소유자가 하나만 있을 때 -> 부모 엔티티가 하나일 때, 단일 엔티티에 종속적일 때
   * 2. 라이프 사이클이 똑같을 때
   *
   * orphanRemoval = true 고아객체 제거
   * 참조하는 곳이 하나일 때 사용해야함!
   * OneToOne, OneToMany만 가능
   * CascadeType.REMOVE처럼 동작한다.
   */
  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Child> childList = new ArrayList<>();

  public void addChild(Child child) {
    childList.add(child);
    child.setParent(this);
  }
}

package io.wonuk.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @MappedSuperclass
 * 공통 매핑 정보가 필요할 때 사용
 * 같은 필드가 들어갈 Entity 들이 상속 받아서 사용.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;
}

package io.wonuk.shop.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable // 값 타입을 정의하는 곳
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Column(length = 10)
    private String city;

    @Column(length = 20)
    private String street;

    @Column(length = 5)
    private String zipcode;

    public String generateFullAddress() {
        return getCity() + ":" + getStreet() + ":" + getZipcode();
    }

    /**
     * 임베디드 타입의 경우에 비교할 일이 생기면 반드시 equals를 오버라이드 하여 사용하여야 한다.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) &&
            Objects.equals(getStreet(), address.getStreet()) &&
            Objects.equals(getZipcode(), address.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}

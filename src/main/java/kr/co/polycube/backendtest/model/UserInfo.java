package kr.co.polycube.backendtest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data //getter, setter, toString, equals, hashCode 메서드 자동 생성
@NoArgsConstructor //기본 생성자를 자동 생성
@AllArgsConstructor //모든 필드를 매개변수로 갖는 생성자를 자동 생성

public class UserInfo {
	@Id //기본키
	private Long id;
	private String name;

}

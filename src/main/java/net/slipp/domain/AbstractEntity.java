package net.slipp.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

	@Id
	@GeneratedValue // auto sequence
	@JsonProperty
	private Long id;
	
	/*
	 * @EnableJpaAuditing
		public class MySlippApplication 에 어노테이션 추가해야 적용됨, 날짜 자동 등록.
	 */
	@CreatedDate
	private LocalDateTime createDate;
	
	@LastModifiedDate
	private LocalDateTime modifyDate;

	public String getFormattedCreateDate() {
		if ( createDate == null) {
			return "";
		}
		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}


	public LocalDateTime getCreateDate() {
		return createDate;
	}


	@Override
	public String toString() {
		return "AbstractEntity [id=" + id + ", createDate=" + createDate + ", modifyDate=" + modifyDate + "]";
	}
	
}

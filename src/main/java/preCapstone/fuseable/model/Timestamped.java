package preCapstone.fuseable.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

    // 최초 생성
    @CreatedDate
    @Column(name="CREATED_DATE")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name="MODIFIED_DATE")
    private LocalDateTime modifiedDate;
}

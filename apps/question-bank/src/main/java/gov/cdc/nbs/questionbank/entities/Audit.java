package gov.cdc.nbs.questionbank.entities;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Audit {

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_time", nullable = false)
    private Instant createdTime;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_time")
    private Instant updatedTime;


    public static enum Status {
        ACTIVE,
        INACTIVE
    }

}

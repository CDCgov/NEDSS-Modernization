package gov.cdc.nbs.questionbank.entities;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AuditInfo implements Serializable {

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user", nullable = false)
    private Long addUserId;

    @Column(name = "last_update_time", nullable = false)
    private Instant lastUpdate;

    @Column(name = "last_update_user", nullable = false)
    private Long lastUpdateUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status;

    @Column(name = "status_time", nullable = false)
    private Instant statusTime;

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public AuditInfo(QuestionCommand command) {
        this(command.requestedOn(), command.userId());
    }

    public AuditInfo(Instant createTime, long userId) {
        this.addTime = createTime;
        this.addUserId = userId;
        this.lastUpdate = createTime;
        this.lastUpdateUserId = userId;
        this.status = Status.ACTIVE;
        this.statusTime = createTime;
    }

}

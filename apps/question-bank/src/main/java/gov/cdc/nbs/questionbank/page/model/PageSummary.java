package gov.cdc.nbs.questionbank.page.model;

import java.time.Instant;
import java.util.List;
import com.querydsl.core.annotations.QueryProjection;
import gov.cdc.nbs.questionbank.question.model.Condition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageSummary {
    private long id;
    private String eventType;
    private String name;
    private String state;
    private List<Condition> conditions;
    private Instant lastUpdate;
    private String lastUpdateBy;

    public PageSummary(long id, Instant lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdatedBy;
    }

    @QueryProjection
    public PageSummary(
            long id,
            String eventType,
            String name,
            String state,
            List<Condition> conditions,
            Instant lastUpdate,
            String lastUpdateBy) {
        this.id = id;
        this.eventType = eventType;
        this.name = name;
        this.state = state;
        this.conditions = conditions;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
}

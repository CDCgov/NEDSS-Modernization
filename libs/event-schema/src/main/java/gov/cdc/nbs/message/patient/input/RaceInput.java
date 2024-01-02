package gov.cdc.nbs.message.patient.input;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RaceInput {
    private long patient;
    private Instant asOf;
    private String category;
    private List<String> detailed;

    public RaceInput() {
        this.detailed = new ArrayList<>();
    }

    public long getPatient() {
        return patient;
    }

    public RaceInput setPatient(long patient) {
        this.patient = patient;
        return this;
    }

    public Instant getAsOf() {
        return asOf;
    }

    public RaceInput setAsOf(Instant asOf) {
        this.asOf = asOf;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public RaceInput setCategory(String category) {
        this.category = category;
        return this;
    }

    public List<String> getDetailed() {
        return detailed;
    }

    public RaceInput setDetailed(List<String> detailed) {
        this.detailed = detailed;
        return this;
    }

    public RaceInput patient(final long patient) {
        this.patient = patient;
        return this;
    }

    public RaceInput asOf(final Instant asOf) {
        this.asOf = asOf;
        return this;
    }

    public RaceInput category(final String category) {
        this.category = category;
        return this;
    }

    public RaceInput withDetail(final String detail) {
        this.detailed.add(detail);
        return this;
    }

}

package gov.cdc.nbs.search.criteria.date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DateCriteria(Equals equals, Between between) {

  public static DateCriteria equals(final LocalDate localDate) {
    return equals(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
  }

  public static DateCriteria equals(final Integer day, final Integer month, final Integer year) {
    return new DateCriteria(new Equals(day, month, year), null);
  }

  public static DateCriteria between(final LocalDate from, final LocalDate to) {
    return new DateCriteria(null, new Between(from, to));
  }

  public DateCriteria withEquals(final Equals equals) {
    return new DateCriteria(equals, null);
  }

  public DateCriteria withBetween(final Between between) {
    return new DateCriteria(null, between);
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public record Equals(Integer day, Integer month, Integer year) {

    public Equals withDay(final int day) {
      return new Equals(day, month, year);
    }

    public Equals withMonth(final int month) {
      return new Equals(day, month, year);
    }

    public Equals withYear(final int year) {
      return new Equals(day, month, year);
    }

    @JsonIgnore
    public boolean isPartialDate() {
      return (day == null || month == null || year == null) && !isBlank();
    }

    @JsonIgnore
    public boolean isCompleteDate() {
      return day != null && month != null && year != null;
    }

    @JsonIgnore
    public boolean isBlank() {
      return day == null && month == null && year == null;
    }
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public record Between(LocalDate from, LocalDate to) {
  }
}

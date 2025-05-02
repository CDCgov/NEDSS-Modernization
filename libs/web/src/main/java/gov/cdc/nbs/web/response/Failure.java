package gov.cdc.nbs.web.response;

public record Failure<P>(String reason) implements StandardResponse<P>{
}

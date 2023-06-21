package gov.cdc.nbs.questionbank.support;

import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ExceptionHolder {

    private Exception exception;
}

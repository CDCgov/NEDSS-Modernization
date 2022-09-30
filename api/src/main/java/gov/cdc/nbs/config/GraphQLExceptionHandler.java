package gov.cdc.nbs.config;

import org.springframework.stereotype.Component;

import gov.cdc.nbs.exception.QueryException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;

@Component
public class GraphQLExceptionHandler implements DataFetcherExceptionHandler {

    @Override
    // propogates exception message instead of generic: Internal server error
    public DataFetcherExceptionHandlerResult onException(DataFetcherExceptionHandlerParameters handlerParameters) {

        Throwable exception = handlerParameters.getException();
        String message = "Internal server error for request: "
                + handlerParameters.getDataFetchingEnvironment().getExecutionId();

        if (exception instanceof QueryException) {
            message = exception.getMessage();
        }

        GraphQLError error = GraphqlErrorBuilder
                .newError()
                .message(message)
                .path(handlerParameters.getPath())
                .location(handlerParameters.getSourceLocation())
                .build();

        return DataFetcherExceptionHandlerResult
                .newResult(error)
                .build();
    }
}

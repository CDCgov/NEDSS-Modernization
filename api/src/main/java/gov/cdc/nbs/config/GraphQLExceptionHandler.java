package gov.cdc.nbs.config;

import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(
            DataFetcherExceptionHandlerParameters handlerParameters) {

        Throwable exception = handlerParameters.getException();
        String message;
        if (exception instanceof QueryException) {
            message = exception.getMessage();
        } else {
            message = "Internal server error for request: "
                    + handlerParameters.getDataFetchingEnvironment().getExecutionId();
        }

        GraphQLError error = GraphqlErrorBuilder
                .newError()
                .message(message)
                .path(handlerParameters.getPath())
                .location(handlerParameters.getSourceLocation())
                .build();

        return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult
                .newResult(error)
                .build());
    }
}

package gov.cdc.nbs.config;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;

@Component
public class GraphQLExceptionHandler implements DataFetcherExceptionHandler {

    @Override
    // propagates exception message instead of generic: Internal server error
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(
            DataFetcherExceptionHandlerParameters handlerParameters) {

        Throwable exception = handlerParameters.getException().getCause();

        GraphQLError error = GraphqlErrorBuilder
                .newError()
                .message(exception.getMessage())
                .path(handlerParameters.getPath())
                .location(handlerParameters.getSourceLocation())
                .build();

        return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult
                .newResult(error)
                .build());
    }
}

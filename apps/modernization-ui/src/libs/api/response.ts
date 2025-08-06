type SuccessResponse = {};
type FailureResponse = { reason: string };

type StandardResponse = SuccessResponse | FailureResponse;

export type { StandardResponse, SuccessResponse, FailureResponse };

const isFailure = (response?: StandardResponse): response is FailureResponse =>
    response ? 'reason' in response : false;

export { isFailure };

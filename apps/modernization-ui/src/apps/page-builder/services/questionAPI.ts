import {
    CreateCodedQuestionRequest,
    CreateDateQuestionRequest,
    CreateNumericQuestionRequest,
    CreateTextQuestionRequest,
    QuestionControllerService
} from '../generated';

export const createTextQuestion = async (token: string, request: CreateTextQuestionRequest) => {
    return await QuestionControllerService.createTextQuestionUsingPost({
        authorization: token,
        request
    });
};

export const createNumericQuestion = async (token: string, request: CreateNumericQuestionRequest) => {
    return await QuestionControllerService.createNumericQuestionUsingPost({
        authorization: token,
        request
    });
};

export const createDateQuestion = async (token: string, request: CreateDateQuestionRequest) => {
    return await QuestionControllerService.createDateQuestionUsingPost({
        authorization: token,
        request
    });
};

export const createCodedQuestion = async (token: string, request: CreateCodedQuestionRequest) => {
    return await QuestionControllerService.createCodedQuestionUsingPost({
        authorization: token,
        request
    });
};

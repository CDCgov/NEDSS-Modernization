import {
    CreateCodedQuestionRequest,
    CreateDateQuestionRequest,
    CreateNumericQuestionRequest,
    CreateTextQuestionRequest,
    QuestionControllerService,
} from '../generated';

export const createTextQuestion = async (_token: string, request: CreateTextQuestionRequest) => {
    return await QuestionControllerService.createTextQuestion({
        requestBody: request,
    });
};

export const createNumericQuestion = async (_token: string, request: CreateNumericQuestionRequest) => {
    return await QuestionControllerService.createNumericQuestion({
        requestBody: request,
    });
};

export const createDateQuestion = async (_token: string, request: CreateDateQuestionRequest) => {
    return await QuestionControllerService.createDateQuestion({
        requestBody: request,
    });
};

export const createCodedQuestion = async (_token: string, request: CreateCodedQuestionRequest) => {
    return await QuestionControllerService.createCodedQuestion({
        requestBody: request,
    });
};

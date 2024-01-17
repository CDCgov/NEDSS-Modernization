import { ValueSetControllerService } from '../../generated';

export const useConceptAPI = (authorization: string, codeSetNm: string) => {
    return ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
        authorization,
        codeSetNm: codeSetNm
    }).then((response: any) => {
        return response || [];
    });
};

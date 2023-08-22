import { ValueSetControllerService } from '../../generated';

export const useConceptPI = (authorization: string, codeSetNm: string, sort: string) => {
    console.log('sort...', sort);
    return ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
        authorization,
        codeSetNm: codeSetNm || 'CONDITION_FAMILY'
    }).then((response: any) => {
        return response || [];
    });
};

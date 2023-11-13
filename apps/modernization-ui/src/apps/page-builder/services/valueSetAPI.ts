import { ValueSetControllerService } from '../generated';

export const fetchCodingSystemOptions = (token: string) => {
    return fetchValueSetOptions(token, 'CODE_SYSTEM');
};

export const fetchMMGOptions = (token: string) => {
    return fetchValueSetOptions(token, 'NBS_MSG_PROFILE');
};

export const fetchFamilyOptions = (token: string) => {
    return fetchValueSetOptions(token, 'CONDITION_FAMILY');
};

export const fetchGroupOptions = (token: string) => {
    return fetchValueSetOptions(token, 'COINFECTION_GROUP');
};

export const fetchValueSetOptions = (token: string, codeSet: string) => {
    return ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
        authorization: token,
        codeSetNm: codeSet
    });
};

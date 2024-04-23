import { ConceptControllerService } from '../generated';

export const fetchCodingSystemOptions = () => {
    return fetchValueSetOptions('CODE_SYSTEM');
};

export const fetchMMGOptions = () => {
    return fetchValueSetOptions('NBS_MSG_PROFILE');
};

export const fetchFamilyOptions = () => {
    return fetchValueSetOptions('CONDITION_FAMILY');
};

export const fetchGroupOptions = () => {
    return fetchValueSetOptions('COINFECTION_GROUP');
};

export const fetchValueSetOptions = (codeSet: string) => {
    return ConceptControllerService.findConcepts({
        codeSetNm: codeSet
    });
};

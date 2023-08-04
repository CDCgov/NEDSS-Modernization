import { ValueSetControllerService } from '../generated';

export const fetchCodingSystemOptions = (token: string) => {
    return new Promise<never[]>((resolve, reject) => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization: token,
            codeSetNm: 'CODE_SYSTEM'
        })
            .then((response: any) => {
                const data = response || [];
                resolve(data);
            })
            .catch((error) => {
                console.log(error.toJSON());
                reject(error);
            });
    });
};

export const fetchFamilyOptions = (token: string) => {
    return new Promise<never[]>((resolve, reject) => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization: token,
            codeSetNm: 'CONDITION_FAMILY'
        })
            .then((response: any) => {
                console.log('CONDITIONS', response);
                resolve(response);
            })
            .catch((error) => {
                console.log(error.toJSON());
                reject(error);
            });
    });
};

export const fetchGroupOptions = (token: string) => {
    ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
        authorization: token,
        codeSetNm: 'COINFECTION_GROUP'
    }).then((response: any) => {
        return response;
    });
};

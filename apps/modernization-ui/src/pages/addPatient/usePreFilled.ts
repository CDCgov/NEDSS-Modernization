import { internalizeDate } from 'date';
import { EncryptionControllerService } from 'generated';
import { DefaultNewPatentEntry, NewPatientEntry } from 'pages/patient/add';
import { useContext, useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { UserContext } from 'user';
import { orNull } from 'utils';

const usePreFilled = (initial: DefaultNewPatentEntry): NewPatientEntry => {
    const { state } = useContext(UserContext);
    const location = useLocation();
    const [prefilled, setPrefilled] = useState<NewPatientEntry>(initial);

    useEffect(() => {
        if (location?.state?.criteria) {
            decrypt(location.state.criteria, state.getToken).then(withCriteria(initial)).then(setPrefilled);
        }
    }, [location?.state?.criteria]);

    return prefilled;
};

const withCriteria =
    (initial: DefaultNewPatentEntry) =>
    (filter: any): NewPatientEntry => ({
        ...initial,
        firstName: orNull(filter?.firstName),
        lastName: orNull(filter?.lastName),
        dateOfBirth: internalizeDate(filter.dateOfBirth)
        // currentGender: filter?.gender ?? '',
        // streetAddress1: filter?.address ?? '',
        // city: filter?.city ?? '',
        // state: filter?.state ?? '',
        // zip: filter?.zip ?? '',
        // ethnicity: filter?.ethnicity ?? '',
        // homePhone: filter?.phoneNumber ?? '',
        // race: filter?.race ? [filter.race] : [],
        // identification: [
        //     {
        //         type: filter?.identification?.identificationType,
        //         value: filter?.identification?.identificationNumber,
        //         authority: null
        //     }
        // ],
        // emailAddresses: [{ email: filter?.email ?? '' }]
    });

const decrypt = (criteria: string, token: () => string | undefined) =>
    EncryptionControllerService.decryptUsingPost({
        encryptedString: criteria,
        authorization: `Bearer ${token()}`
    });

export { usePreFilled };

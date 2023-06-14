import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { CodedValue, GroupedCodedValue, indicators } from 'coded';
import { useLocationCodedValues } from 'location';
import { useEffect, useState } from 'react';

const Query = gql`
    query gender {
        genders {
            value
            name
        }
        preferredGenders {
            value
            name
        }
        genderUnknownReasons {
            value
            name
        }
    }
`;

type Variables = { [key: string]: never };

type Result = {
    __typename?: 'Query';
    genders: Array<CodedValue>;
    preferredGenders: Array<CodedValue>;
    genderUnknownReasons: Array<CodedValue>;
};

function useCodedValueQuery(baseOptions?: Apollo.QueryHookOptions<Result, Variables>) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<Result, Variables>(Query, options);
}

const initial = {
    genders: [],
    preferredGenders: [],
    genderUnknownReasons: [],
    multipleBirth: indicators,
    countries: [],
    states: [],
    counties: []
};

type PatientSexBirthCodedValue = {
    genders: CodedValue[];
    preferredGenders: CodedValue[];
    genderUnknownReasons: CodedValue[];
    multipleBirth: CodedValue[];
    countries: CodedValue[];
    states: CodedValue[];
    counties: GroupedCodedValue[];
};

const usePatientSexBirthCodedValues = () => {
    const [coded, setCoded] = useState<PatientSexBirthCodedValue>(initial);

    const locations = useLocationCodedValues();

    useEffect(() => {
        setCoded((existing) => ({
            ...existing,
            ...locations
        }));
    }, [locations]);

    const handleComplete = (data: Result) => {
        setCoded((existing) => ({
            ...existing,
            ...data
        }));
    };

    const [getCodedValues] = useCodedValueQuery({ onCompleted: handleComplete });

    useEffect(() => {
        getCodedValues();
    }, []);

    return coded;
};

export { usePatientSexBirthCodedValues };
export type { PatientSexBirthCodedValue };

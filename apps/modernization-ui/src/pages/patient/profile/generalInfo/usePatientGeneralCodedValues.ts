import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { CodedValue, indicators } from 'coded';
import { useEffect, useState } from 'react';

const Query = gql`
    query generalInformationValues {
        maritalStatuses {
            value
            name
        }
        primaryOccupations {
            value
            name
        }
        educationLevels {
            value
            name
        }
        primaryLanguages {
            value
            name
        }
    }
`;

type Variables = { [key: string]: never };

type Result = {
    __typename?: 'Query';
    maritalStatuses: Array<CodedValue>;
    primaryOccupations: Array<CodedValue>;
    educationLevels: Array<CodedValue>;
    primaryLanguages: Array<CodedValue>;
};

function useCodedValueQuery(baseOptions?: Apollo.QueryHookOptions<Result, Variables>) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<Result, Variables>(Query, options);
}

const initialCoded = {
    maritalStatuses: [],
    primaryOccupations: [],
    educationLevels: [],
    primaryLanguages: [],
    speaksEnglish: indicators
};

type PatientGeneralCodedValue = {
    maritalStatuses: CodedValue[];
    primaryOccupations: CodedValue[];
    educationLevels: CodedValue[];
    primaryLanguages: CodedValue[];
    speaksEnglish: CodedValue[];
};

const usePatientGeneralCodedValues = () => {
    const [coded, setCoded] = useState<PatientGeneralCodedValue>(initialCoded);

    const handleComplete = (data: Result) => {
        setCoded({
            ...data,
            speaksEnglish: indicators
        });
    };

    const [getCodedValues] = useCodedValueQuery({ onCompleted: handleComplete });

    useEffect(() => {
        getCodedValues();
    }, []);

    return coded;
};

export { usePatientGeneralCodedValues };
export type { PatientGeneralCodedValue };

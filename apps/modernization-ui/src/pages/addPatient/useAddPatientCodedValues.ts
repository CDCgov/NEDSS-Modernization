import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { CodedValue, indicators } from 'coded';
import { useEffect, useState } from 'react';

const Query = gql`
    query addPatientValues {
        raceCategories {
            value
            name
        }
        ethnicGroups {
            value
            name
        }
        identificationTypes {
            value
            name
        }
        assigningAuthorities {
            value
            name
        }
        suffixes {
            value
            name
        }
        maritalStatuses {
            value
            name
        }
        genders {
            value
            name
        }
    }
`;

type Variables = { [key: string]: never };

type Result = {
    __typename?: 'Query';
    raceCategories: CodedValue[];
    ethnicGroups: CodedValue[];
    identificationTypes: CodedValue[];
    assigningAuthorities: CodedValue[];
    suffixes: CodedValue[];
    genders: CodedValue[];
    maritalStatuses: CodedValue[];
    deceased: CodedValue[];
};

function useCodedValueQuery(baseOptions?: Apollo.QueryHookOptions<Result, Variables>) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<Result, Variables>(Query, options);
}

const initialCoded = {
    raceCategories: [],
    ethnicGroups: [],
    identificationTypes: [],
    assigningAuthorities: [],
    suffixes: [],
    maritalStatuses: [],
    deceased: [],
    genders: []
};

type PatietnAddCodedValues = {
    raceCategories: CodedValue[];
    ethnicGroups: CodedValue[];
    identificationTypes: CodedValue[];
    assigningAuthorities: CodedValue[];
    suffixes: CodedValue[];
    deceased: CodedValue[];
    genders: CodedValue[];
    maritalStatuses: CodedValue[];
};

const useAddPatientCodedValues = () => {
    const [coded, setCoded] = useState<PatietnAddCodedValues>(initialCoded);

    const handleComplete = (data: Result) => {
        setCoded({
            ...data,
            deceased: indicators
        });
    };

    const [getCodedValues] = useCodedValueQuery({ onCompleted: handleComplete });

    useEffect(() => {
        getCodedValues();
    }, []);

    return coded;
};

export { useAddPatientCodedValues };
export type { PatietnAddCodedValues };

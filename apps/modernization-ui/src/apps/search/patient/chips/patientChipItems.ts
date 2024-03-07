import { PersonFilter } from 'generated/graphql/schema';
import { SearchCriteria } from 'providers/SearchCriteriaContext';

export interface ChipItem {
    name: string;
    value: string;
    source: string;
}

const getNameAndValue = (
    key: string,
    value: string | { identificationType?: string; identificationNumber?: string },
    searchCriteria: SearchCriteria
): { name: string; value: string } => {
    const nameMap: { [key: string]: string } = {
        lastName: 'LAST',
        firstName: 'FIRST',
        dateOfBirth: 'DOB'
    };

    const dynamicValueLookup: { [key: string]: () => string | undefined } = {
        ethnicity: () => searchCriteria.ethnicities.find((e) => e.id.code === value)?.codeDescTxt,
        race: () => searchCriteria.races.find((r) => r.id.code === value)?.codeDescTxt,
        state: () => searchCriteria.states.find((s) => s.value === value)?.name
    };

    const dynamicValue = dynamicValueLookup[key] ? dynamicValueLookup[key]() : undefined;

    return {
        name: nameMap[key] || key.toUpperCase(),
        value: dynamicValue || value.toString()
    };
};

export const patientChipItems = (filter: PersonFilter, searchCriteria: SearchCriteria): ChipItem[] => {
    const items: ChipItem[] = [];

    Object.entries(filter).forEach(([key, originalValue]) => {
        if (!originalValue || key === 'recordStatus') return;

        if (key === 'identification' && typeof originalValue === 'object') {
            const { identificationType, identificationNumber } = originalValue;
            if (identificationType) {
                items.push({
                    name: 'ID TYPE',
                    source: 'identification.identificationType',
                    value:
                        searchCriteria.identificationTypes.find((i) => i.id.code === identificationType)?.codeDescTxt ??
                        ''
                });
            }
            if (identificationNumber) {
                items.push({
                    name: 'ID NUMBER',
                    source: 'identification.identificationNumber',
                    value: identificationNumber
                });
            }
        } else {
            const { name, value } = getNameAndValue(key, originalValue as string, searchCriteria);
            items.push({ name, value, source: key });
        }
    });

    return items;
};

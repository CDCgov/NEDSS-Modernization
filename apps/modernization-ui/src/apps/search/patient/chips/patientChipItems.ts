import { PersonFilter } from 'generated/graphql/schema';
import { SearchCriteria } from 'providers/SearchCriteriaContext';

export interface ChipItem {
    name: string;
    value: string;
    source: string;
}

export const patientChipItems = (filter: PersonFilter, searchCriteria: SearchCriteria): ChipItem[] => {
    const items: ChipItem[] = [];
    for (const key in filter) {
        if (Object.prototype.hasOwnProperty.call(filter, key)) {
            const value = filter[key as keyof PersonFilter];
            if (value && key !== 'recordStatus') {
                if (key === 'identification' && typeof value === 'object') {
                    const { identificationType, identificationNumber } = value;
                    if (identificationType) {
                        items.push({
                            name: 'ID TYPE',
                            source: 'identification.identificationType',
                            value:
                                searchCriteria.identificationTypes.find((i) => i.id.code === identificationType)
                                    ?.codeDescTxt ?? ''
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
                    const nameMap: { [key: string]: string } = {
                        lastName: 'Last name',
                        firstName: 'FIRST',
                        dateOfBirth: 'DOB',
                        gender: 'SEX'
                    };

                    const dynamicValueLookup: { [key: string]: () => string | undefined } = {
                        ethnicity: () => searchCriteria.ethnicities.find((e) => e.id.code === value)?.codeDescTxt,
                        race: () => searchCriteria.races.find((r) => r.id.code === value)?.codeDescTxt,
                        state: () => searchCriteria.states.find((s) => s.value === value)?.name
                    };

                    const dynamicValue = dynamicValueLookup[key] ? dynamicValueLookup[key]() : undefined;

                    items.push({
                        name: nameMap[key] || key.toUpperCase(),
                        value: dynamicValue || value.toString(),
                        source: key
                    });
                }
            }
        }
    }

    return items;
};

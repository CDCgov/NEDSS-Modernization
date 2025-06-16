import { DataElementToMatchingAttribute } from 'apps/deduplication/api/model/Conversion';
import { DataElements } from 'apps/deduplication/api/model/DataElement';
import { MatchingAttributeLabels } from 'apps/deduplication/api/model/Labels';
import { MatchingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { InUseDataElements } from './DataElementValidationError';

export const validateElementsInUse = (toValidate: DataElements, passes: Pass[]): InUseDataElements | undefined => {
    const invalidPasses: string[] = [];
    const invalidElements: string[] = [];
    const disabledElements = Object.entries(toValidate)
        .filter((value) => !value[1].active) // get all entries that are set to 'active: false'
        .map(([key]) => DataElementToMatchingAttribute[`${key as keyof DataElements}`]) // map to MatchingAttribute
        .filter((m) => m !== undefined);

    // Check each pass to see if its matchingCriteria includes any of the disabled elements
    passes.forEach((p) => {
        // get a list of in use matching and blocking criteria for the pass
        const passBlockingCriteria = p.blockingCriteria.map((p) => p as unknown as MatchingAttribute);
        const passMatchCriteria = p.matchingCriteria.map((m) => m.attribute);

        const criteriaInUse = [...passBlockingCriteria, ...passMatchCriteria];
        // check if any of the disabled data elements are in use in the pass
        const conflictingCriteria = criteriaInUse.filter((c) => disabledElements.includes(c));
        // if so, add pass to invalid pass list
        if (conflictingCriteria.length > 0) {
            invalidPasses.push(p.name);
            invalidElements.push(...conflictingCriteria.map((c) => MatchingAttributeLabels[c].label));
        }
    });
    if (invalidPasses.length === 0) {
        return;
    }

    return { passes: invalidPasses, fields: [...new Set(invalidElements)] };
};

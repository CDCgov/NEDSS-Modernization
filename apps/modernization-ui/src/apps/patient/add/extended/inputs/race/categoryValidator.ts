import { Selectable } from 'options';
import { categoryRequiredValidator, RaceEntry } from 'apps/patient/data/race';
import { exists, Predicate } from 'utils';
import { allOf } from 'utils/predicate';

const withCategory =
    (category: Selectable): Predicate<RaceEntry> =>
    (entry) =>
        entry.race?.value === category.value;

const withId = (id: number) => (entry: RaceEntry) => entry.id !== id;

/**
 *
 * Creates a RaceCategoryValidator that validates that the given race category is not already
 * present in the existing entries.  This is only intended to be used when creating a new
 * patient due to the validation only determining use using only local race entries.
 *
 * @param {RaceEntry[]} entries Any existing race entries
 * @return {RaceCategoryValidator} A validators that uses the provided entries for determining usage
 */
const categoryValidator = (entries: RaceEntry[]) => (id: number, category: Selectable | null) =>
    categoryRequiredValidator(id, category).then((result) => {
        if (typeof result === 'boolean' && result && exists(category)) {
            //  find any existing entries that have the same category but not the same if
            const resolved = entries.find(allOf(withCategory(category), withId(id)));

            //  validation fails
            if (resolved) {
                return `Race ${category?.name} has already been added to the repeating block. Please select another race to add.`;
            }
        }

        return result;
    });

export { categoryValidator };

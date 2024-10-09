import { Selectable } from 'options';
import { RaceEntry } from 'apps/patient/data/race';
import { Predicate } from 'utils';
import { allOf } from 'utils/predicate';

const withCategory =
    (category: Selectable): Predicate<RaceEntry> =>
    (entry) =>
        entry.race.value === category.value;

const withId = (id: number) => (entry: RaceEntry) => entry.id !== id;

const categoryValidator = (entries: RaceEntry[]) => (id: number, category: Selectable) =>
    new Promise<string | boolean>((resolve) => {
        //  find any existing entries that have the same category but not the same if
        const resolved = entries.find(allOf(withCategory(category), withId(id)));

        if (resolved) {
            //  validation fails
            resolve(
                `Race ${category.name} has already been added to the repeating block.Please select another race to add .`
            );
        } else {
            resolve(true);
        }
    });

export { categoryValidator };

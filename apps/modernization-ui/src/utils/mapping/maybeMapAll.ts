import { exists } from 'utils/exists';
import { Mapping } from './mapping';

type MapEach<I, O> = (values?: I[]) => NonNullable<O>[];

/**
 *
 * Maps each element of an array using the provided mapping function, excluding any elements that map to a non-existant value.
 *
 * @param {Mapping} mapping The mapping function to apply to each element.
 * @return {MapEnsuringEach} The function that maps each element producing the new array.
 */
const maybeMapAll =
    <R, S>(mapping: Mapping<R, S>): MapEach<R, S> =>
    (values?: R[]): NonNullable<S>[] => {
        if (values) {
            return values.reduce((previous: NonNullable<S>[], current) => {
                const mapped = mapping(current);

                if (exists(mapped)) {
                    return [...previous, mapped];
                }

                return previous;
            }, []);
        }

        return [];
    };

export { maybeMapAll };

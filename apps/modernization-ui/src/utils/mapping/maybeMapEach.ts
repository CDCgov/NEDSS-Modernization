import { Mapping } from './mapping';

const maybeMapEach =
    <R, S>(...mappings: Mapping<R, S>[]) =>
    (value?: R): NonNullable<S>[] => {
        if (value) {
            return mappings.reduce((previous, current) => {
                const mapped = current(value);
                return mapped ? [...previous, mapped] : previous;
            }, [] as NonNullable<S>[]);
        }

        return [];
    };

export { maybeMapEach };

import { ReactNode, useEffect, useState } from 'react';
import {
    mappingComparator,
    withDirection,
    Direction,
    nullsLast,
    defaultComparator,
    Comparator,
    SortingInteraction
} from 'libs/sorting';
import { Mapping, Maybe } from 'utils';

const sortUsing = <R, C>(data: R[], mapping: Mapping<R, Maybe<C>>, comparator: Comparator<Maybe<C>>): R[] => {
    return data.slice().sort(withDirection(mappingComparator(mapping, comparator)));
};

type RenderContext<T> = {
    /**
     * The Sorting Interaction that triggers sorting.
     */
    sorting: SortingInteraction;
    /**
     * The data sorted according to the Sorting Interaction.
     */
    sorted: T[];
};

type RenderFunction<T> = (context: RenderContext<T>) => ReactNode | ReactNode[] | undefined;

type SortHandlerProps<T, U> = {
    /**
     * The Sorting Interaction to react to.
     */
    sorting: SortingInteraction;
    /**
     * A resolver for a mapping function to pull the U value of T that will be sorted on.
     *
     * @param {string} property - The property being sorted
     * @return {Mapping<T, Maybe<U>>} - A mapping function if it exists
     */
    resolver: (property: string) => Maybe<Mapping<T, Maybe<U>>>;
    /**
     * The comparator used to sort the U data elements of T.
     */
    comparator?: Comparator<Maybe<U>>;
    /**
     * A list of values to sort on.
     */
    data: T[];
    /**
     * A function that provides the Sorting Interaction to render child components.
     */
    children: RenderFunction<T>;
};

/**
 * Reacts to the SortingInteraction to sort the data using the supplied Comparator.  If a Comparator is not defined
 * then the default comparator is used with values sorted as nulls last.
 *
 * @param {SortHandlerProps} props
 * @return {JSX.Element} - The result of children rendering function.
 */
const SortHandler = <T, U>({
    resolver,
    data,
    sorting,
    comparator = nullsLast(defaultComparator),
    children
}: SortHandlerProps<T, U>) => {
    const [sorted, setSorted] = useState(data);

    useEffect(() => {
        if (sorting.property && sorting.direction && sorting.direction !== Direction.None) {
            const mapping = resolver(sorting.property);
            if (mapping) {
                //  sort using the value of the column
                setSorted(sortUsing(data, mapping, withDirection(comparator, sorting.direction)));
            }
        } else {
            //  no sorting needed, pass the data as is
            setSorted(data);
        }
    }, [sorting.property, sorting.direction]);

    return children({ sorting, sorted });
};

export { SortHandler };

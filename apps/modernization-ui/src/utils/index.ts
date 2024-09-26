export * from './orNull';
export * from './mapNonNull';
export * from './maybeNumber';
export * from './formattedName';
export * from './objectOrUndefined';
export * from './exists';
export * from './focusedTarget';
export * from './mapIf';

type Predicate<T> = (item: T) => boolean;
type Mapping<I, O> = (input: I) => O;
type Maybe<V> = V | null | undefined;
type EffectiveDated = {
    asOf: string;
};

type HasComments = {
    comment?: string;
};

export type { Predicate, Mapping, Maybe, EffectiveDated, HasComments };

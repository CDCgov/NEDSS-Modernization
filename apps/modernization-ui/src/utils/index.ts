export * from './orNull';
export * from './mapNonNull';
export * from './maybeNumber';
export * from './formattedName';
export * from './objectOrUndefined';
export * from './exists';
export * from './focusedTarget';
export * from './mapIf';

export type { Predicate } from './predicate';

export type { Mapping } from './mapping';

type Maybe<V> = V | null | undefined;
type EffectiveDated = {
    asOf: string;
};

type HasComments = {
    comment?: string;
};

export type { Maybe, EffectiveDated, HasComments };

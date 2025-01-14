export * from './orNull';
export * from './orUndefined';
export * from './mapNonNull';
export * from './maybeNumber';
export * from './formattedName';
export * from './objectOrUndefined';
export * from './exists';
export * from './focusedTarget';
export * from './mapIf';
export * from './isEmpty';
export * from './text';

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

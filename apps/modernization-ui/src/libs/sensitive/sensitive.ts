type Allowed<V> = {
    value?: V | null;
};

type Restricted = {
    reason?: string;
};

type Sensitive<V> = Allowed<V> | Restricted;

export type { Sensitive };

const isAllowed = <A>(sensitive?: Sensitive<A>): sensitive is Allowed<A> => (sensitive ? 'value' in sensitive : false);

export { isAllowed };

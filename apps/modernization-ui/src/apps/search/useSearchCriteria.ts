import { useCallback, useEffect, useMemo, useReducer } from 'react';
import { decrypt, encrypt } from 'cryptography';
import { useSearchParams } from 'react-router-dom';

const CRITERIA_PARAMETER = 'q';

type Initializing = { status: 'initializing' };
type Evaluating = { status: 'evaluating'; found: string };
type Preparing<V> = { status: 'preparing'; criteria: V };
type Navigating = { status: 'navigating'; encrypted: string };
type Clearing = { status: 'clearing' };
type Waiting<V> = { status: 'waiting'; criteria?: V };

type State<V> = Initializing | Evaluating | Preparing<V> | Navigating | Clearing | Waiting<V>;

type Reset = { type: 'reset' };
type Evaluate = { type: 'evaluate'; found: string };
type Prepare<V> = { type: 'prepare'; criteria: V };
type Navigate = { type: 'navigate'; encrypted: string };
type Clear = { type: 'clear' };
type Wait<V> = { type: 'wait'; criteria?: V };

type Action<V> = Reset | Evaluate | Prepare<V> | Navigate | Clear | Wait<V>;

const reducer = <V>(current: State<V>, action: Action<V>): State<V> => {
    if (action.type === 'evaluate') {
        return { status: 'evaluating', found: action.found };
    } else if (action.type === 'prepare') {
        return { status: 'preparing', criteria: action.criteria };
    } else if (action.type === 'navigate') {
        return { status: 'navigating', encrypted: action.encrypted };
    } else if (action.type === 'clear') {
        return { status: 'clearing' };
    } else if (action.type === 'wait') {
        return { status: 'waiting', criteria: action.criteria };
    } else if (action.type === 'reset') {
        return { status: 'initializing' };
    } else {
        return current;
    }
};

type Options<V> = {
    defaultValues?: V;
};

type Interaction<V> = {
    criteria?: V;
    clear: () => void;
    change: (criteria: V) => void;
};

const useSearchCritiera = <C extends object>({ defaultValues }: Options<C>): Interaction<C> => {
    const [searchParams, setSearchParams] = useSearchParams();
    const found = useMemo(() => searchParams.get(CRITERIA_PARAMETER), [searchParams]);

    const [state, dispatch] = useReducer(reducer<C>, { status: 'initializing' });

    useEffect(() => {
        if (found) {
            //  an encrypted query was found, evaluate it
            dispatch({ type: 'evaluate', found });
        } else {
            //  no criteria was found wait
            dispatch({ type: 'wait' });
        }
    }, [dispatch, found]);

    useEffect(() => {
        if (state.status === 'navigating') {
            setSearchParams((current) => ({ ...current, [CRITERIA_PARAMETER]: state.encrypted }), { replace: true });
        } else if (state.status === 'clearing') {
            setSearchParams((current) => {
                current.delete(CRITERIA_PARAMETER);
                return current;
            });
        }
    }, [state.status, setSearchParams]);

    useEffect(() => {
        if (state.status === 'evaluating') {
            //  decrypt the query and then wait with the decrypted result
            decrypt(state.found)
                .then((decrypted) => ({ ...defaultValues, ...(decrypted as C) }))
                .then((criteria) => dispatch({ type: 'wait', criteria }));
        } else if (state.status === 'preparing') {
            //  encrypt the new criteria
            encrypt(state.criteria)
                .then((encrypted) => encrypted.value)
                .then((encrypted) => {
                    if (encrypted) {
                        //  the criteria was encrypted, navigate to it
                        dispatch({ type: 'navigate', encrypted });
                    } else {
                        //  the critieria was not encrypted, clear it
                        dispatch({ type: 'clear' });
                    }
                });
        }
    }, [state.status, dispatch]);

    const criteria = useMemo(() => (state.status === 'waiting' ? state.criteria : undefined), [state.status]);
    const clear = useCallback(() => dispatch({ type: 'clear' }), [dispatch]);
    const change = useCallback((criteria: C) => dispatch({ type: 'prepare', criteria }), [dispatch]);

    return {
        criteria,
        clear,
        change
    };
};

export { useSearchCritiera };

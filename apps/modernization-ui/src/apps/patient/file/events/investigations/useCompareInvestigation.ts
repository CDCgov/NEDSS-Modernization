import { useReducer } from 'react';
import { PatientFileInvestigation } from './investigation';

type Action =
    | { type: 'reset' }
    | { type: 'select'; investigation: PatientFileInvestigation }
    | { type: 'deselect'; investigation: PatientFileInvestigation };

type Idle = { status: 'waiting' };
type Active = {
    status: 'selecting' | 'comparable' | 'uncomparable';
    condition: string;
    investigations: PatientFileInvestigation[];
};

type ComparisonState = Idle | Active;

const initial: ComparisonState = { status: 'waiting' };

const reducer = (existing: ComparisonState, action: Action): ComparisonState => {
    switch (action.type) {
        case 'select':
            return including(existing, action.investigation);
        case 'deselect':
            return removing(existing, action.investigation);
        default:
            return initial;
    }
};

const including = (existing: ComparisonState, investigation: PatientFileInvestigation): ComparisonState => {
    if (existing.status === 'waiting') {
        return {
            status: 'selecting',
            condition: investigation.condition,
            investigations: [investigation]
        };
    } else if (existing.status === 'selecting') {
        //  need to evaluate against existing selections
        return verify(existing, investigation);
    } else {
        //  uncomparable
        return { ...existing, status: 'uncomparable', investigations: [...existing.investigations, investigation] };
    }
};

const verify = (existing: Active, investigation: PatientFileInvestigation): ComparisonState => {
    if (existing.condition !== investigation.condition) {
        return {
            ...existing,
            status: 'uncomparable',
            investigations: [...existing.investigations, investigation]
        };
    } else {
        return {
            ...existing,
            status: 'comparable',
            investigations: [...existing.investigations, investigation]
        };
    }
};

const removing = (existing: ComparisonState, investigation: PatientFileInvestigation) => {
    if (existing.status === 'waiting') {
        return existing;
    } else {
        //  remove the investigation and evaluate the remaining
        const remaining = existing.investigations.filter((i) => i.identifier !== investigation.identifier);
        return evaluate(remaining);
    }
};

const evaluate = (investigations: PatientFileInvestigation[]): ComparisonState => {
    if (investigations.length === 1) {
        return {
            status: 'selecting',
            condition: investigations[0].condition,
            investigations
        };
    } else {
        return investigations.reduce(including, initial);
    }
};

const useCompareInvestigation = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    const reset = () => dispatch({ type: 'reset' });

    const select = (investigation: PatientFileInvestigation) => dispatch({ type: 'select', investigation });

    const deselect = (investigation: PatientFileInvestigation) => dispatch({ type: 'deselect', investigation });

    const comparable = state.status === 'comparable';

    const selected = comparable ? state.investigations : [];

    return {
        comparable,
        selected,
        reset,
        select,
        deselect
    };
};

export { useCompareInvestigation };

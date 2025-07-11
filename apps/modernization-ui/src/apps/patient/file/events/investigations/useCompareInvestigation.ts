import { useCallback, useReducer } from 'react';
import { PatientFileInvestigation } from './investigation';

type Action =
    | { type: 'reset' }
    | { type: 'select'; investigation: PatientFileInvestigation }
    | { type: 'deselect'; investigation: PatientFileInvestigation };

type Idle = { status: 'waiting' };

type Selecting = {
    status: 'selecting';
    condition: string;
    selected: number;
};

type Comparable = {
    status: 'comparable';
    condition: string;
    selected: number;
    comparedTo: number;
};

type ComparisonState = Idle | Selecting | Comparable;

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
            selected: investigation.identifier
        };
    } else if (existing.status === 'selecting' && investigation.condition === existing.condition) {
        return {
            ...existing,
            status: 'comparable',
            comparedTo: investigation.identifier
        };
    } else {
        return existing;
    }
};

const removing = (existing: ComparisonState, investigation: PatientFileInvestigation): ComparisonState => {
    if (existing.status === 'selecting' && existing.selected === investigation.identifier) {
        //  removing the selected investigation, reset to waiting
        return initial;
    } else if (existing.status === 'comparable' && existing.selected === investigation.identifier) {
        // removing the selected investigation that is already comparable, make the comparison the selected investigation
        return {
            status: 'selecting',
            condition: existing.condition,
            selected: existing.comparedTo
        };
    } else if (existing.status === 'comparable' && existing.comparedTo === investigation.identifier) {
        // removing the comparison, revert to selecting
        return {
            status: 'selecting',
            condition: existing.condition,
            selected: existing.selected
        };
    } else {
        return existing;
    }
};

type CompareInvestigationInteraction = {
    comparison?: Omit<Comparable, 'status' | 'condition'>;
    reset: () => void;
    select: (investigation: PatientFileInvestigation) => void;
    deselect: (investigation: PatientFileInvestigation) => void;
    isComparable: (Investigation: PatientFileInvestigation) => boolean;
    isSelected: (Investigation: PatientFileInvestigation) => boolean;
};

const useCompareInvestigation = (): CompareInvestigationInteraction => {
    const [state, dispatch] = useReducer(reducer, initial);

    const reset = useCallback(() => dispatch({ type: 'reset' }), [dispatch]);

    const select = useCallback(
        (investigation: PatientFileInvestigation) => dispatch({ type: 'select', investigation }),
        [dispatch]
    );

    const deselect = useCallback(
        (investigation: PatientFileInvestigation) => dispatch({ type: 'deselect', investigation }),
        [dispatch]
    );

    const isSelected = useCallback(
        (investigation: PatientFileInvestigation) =>
            (state.status === 'selecting' && investigation.identifier === state.selected) ||
            (state.status === 'comparable' &&
                (investigation.identifier === state.selected || investigation.identifier === state.comparedTo)),
        [state.status, (state.status === 'selecting' || state.status === 'comparable') && state.selected]
    );

    const isComparable = useCallback(
        (investigation: PatientFileInvestigation) =>
            investigation.comparable &&
            (state.status === 'waiting' ||
                (state.status === 'selecting' && state.condition === investigation.condition)),
        [state.status, state.status === 'selecting' && state.condition]
    );

    const comparison =
        state.status === 'comparable' ? { selected: state.selected, comparedTo: state.comparedTo } : undefined;

    return {
        comparison,
        reset,
        select,
        deselect,
        isComparable,
        isSelected
    };
};

export { useCompareInvestigation };
export type { CompareInvestigationInteraction };

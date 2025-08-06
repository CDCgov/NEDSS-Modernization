import { useCallback, useEffect, useMemo, useReducer } from 'react';
import { PatientDemographics } from 'libs/patient/demographics';
import { transformer } from 'libs/patient/demographics/transformer';
import { isFailure, put, RequestError } from 'libs/api';
import { PatientDemographicsRequest } from 'libs/patient/demographics/request';

type Step =
    | { status: 'requesting'; patient: number; demographics: PatientDemographics }
    | { status: 'completed' }
    | { status: 'error'; reason: string }
    | { status: 'waiting' };

type Action =
    | { type: 'request'; patient: number; demographics: PatientDemographics }
    | { type: 'complete' }
    | { type: 'fail'; reason: string };

const reducer = (current: Step, action: Action): Step => {
    switch (action.type) {
        case 'request': {
            return { status: 'requesting', patient: action.patient, demographics: action.demographics };
        }
        case 'complete': {
            return { status: 'completed' };
        }
        default: {
            return current;
        }
    }
};

type EditPatientState = { status: 'waiting' | 'requesting' | 'completed' } | { status: 'error'; reason: string };

type EditPatientInteraction = {
    edit: (patient: number, demographics: PatientDemographics) => void;
} & EditPatientState;

const useEditPatient = (): EditPatientInteraction => {
    const [step, dispatch] = useReducer(reducer, { status: 'waiting' });

    useEffect(() => {
        if (step.status === 'requesting') {
            const body = transformer(step.demographics);

            resolver({ patient: step.patient, demographics: body })
                .then(() => dispatch({ type: 'complete' }))
                .catch((error) => dispatch({ type: 'fail', reason: error.message }));
        }
    }, [step.status, dispatch]);

    const edit = useCallback(
        (patient: number, demographics: PatientDemographics) => dispatch({ type: 'request', patient, demographics }),
        [dispatch]
    );

    const state = useMemo(() => evaluateState(step), [step]);

    return {
        ...state,
        edit
    };
};

const evaluateState = (step: Step): EditPatientState => {
    if (step.status === 'error') {
        return { status: step.status, reason: step.reason };
    } else {
        return step;
    }
};

type Request = { patient: number; demographics: PatientDemographicsRequest };

const resolver = (request: Request) =>
    fetch(put(`/nbs/api/patients/${request.patient}`, request.demographics)).then(async (response) => {
        if (response.ok) {
            return Promise.resolve();
        } else {
            const message = await response
                .json()
                .then((failed) => (isFailure(failed) ? failed.reason : 'An unexpected error occurred.'));

            console.log('ehgar!', message);

            throw new RequestError(response.status, message);
        }
    });

export { useEditPatient };
export type { EditPatientInteraction };

import { useEffect, useReducer } from 'react';
import { VerifiableAdddress } from './VerifiableAddress';
import { StateCodedValues } from 'location';
import { AddressVerificationState } from './useAddressVerification';

type Candidate = {
    delivery_line_1: string;
    components: {
        primary_number: string;
        street_name: string;
        steet_suffix: string;
        city_name: string;
        state_abbreviation: string;
        zipcode: string;
    };
    metadata: {
        county_name: string;
    };
};

type Action =
    | { type: 'reset' }
    | { type: 'verifying'; input: VerifiableAdddress }
    | { type: 'verified'; input: VerifiableAdddress }
    | { type: 'unverified'; input: VerifiableAdddress }
    | { type: 'not-verifiable'; input: VerifiableAdddress }
    | { type: 'verified-found'; input: VerifiableAdddress; alternative: VerifiableAdddress };

const reducer = (_state: AddressVerificationState, action: Action): AddressVerificationState => {
    switch (action.type) {
        case 'verifying':
            return { status: 'verifying', input: action.input };
        case 'verified':
            return { status: 'verified', input: action.input };
        case 'unverified':
            return { status: 'unverified', input: action.input };
        case 'verified-found':
            return {
                status: 'verified-found',
                input: action.input,
                alternative: action.alternative
            };
        case 'not-verifiable':
            return { status: 'not-verifiable', input: action.input };
        default:
            return { status: 'idle', input: null };
    }
};

const hasAddress = (input: VerifiableAdddress) => {
    return input.address1 || input.zip || input.city || input.state;
};

const verifiable = (input: VerifiableAdddress) => {
    return (input.address1 && input.zip) || (input.address1 && input.city && input.state);
};

const isVerified = (input: VerifiableAdddress, candidate: VerifiableAdddress) => {
    return (
        candidate.address1 === input.address1 &&
        candidate.city === input.city &&
        candidate.zip === input.zip &&
        candidate.state?.abbreviation === input.state?.abbreviation
    );
};

const asVerifiableAddress =
    (states: StateCodedValues) =>
    (candidate: Candidate): VerifiableAdddress => {
        return {
            address1: candidate.delivery_line_1,
            city: candidate.components.city_name,
            zip: candidate.components.zipcode,
            state: states.byAbbreviation(candidate.components.state_abbreviation)
        };
    };

type Props = {
    key: string;
    states: StateCodedValues;
};

const useAddressVerificationAPI = ({ key, states }: Props) => {
    const [state, dispatch] = useReducer(reducer, { status: 'idle', input: null });

    const verify = (input: VerifiableAdddress) => {
        dispatch({ type: 'verifying', input });
    };

    const reset = () => {
        dispatch({ type: 'reset' });
    };

    const evaluateCandidate = (input: VerifiableAdddress, candidate: Candidate) => {
        const alternative = asVerifiableAddress(states)(candidate);
        if (isVerified(input, alternative)) {
            dispatch({ type: 'verified', input });
        } else {
            dispatch({ type: 'verified-found', input, alternative });
        }
    };

    const evaluateResponse = (input: VerifiableAdddress) => (candidates: Candidate[]) => {
        if (candidates.length === 0) {
            dispatch({ type: 'unverified', input });
        } else {
            evaluateCandidate(input, candidates[0]);
        }
    };

    useEffect(() => {
        if (key && state.status === 'verifying' && state.input) {
            if (verifiable(state.input)) {
                const criteria = [];

                if (state.input.address1) {
                    criteria.push('street=' + state.input.address1);
                }
                if (state.input.city) {
                    criteria.push('city=' + state.input.city);
                }
                if (state.input.state) {
                    criteria.push('state=' + state.input.state.abbreviation);
                }
                if (state.input.zip) {
                    criteria.push('zipcode=' + state.input.zip);
                }

                const query = criteria.join('&');

                fetch(`https://us-street.api.smartystreets.com/street-address?key=${key}&${query}`)
                    .then((resp) => resp.json())
                    .then(evaluateResponse(state.input));
            } else if (hasAddress(state.input)) {
                dispatch({ type: 'unverified', input: state.input });
            } else {
                dispatch({ type: 'not-verifiable', input: state.input });
            }
        }
    }, [state.status, state.input]);

    return { state, verify, reset };
};

export { useAddressVerificationAPI };

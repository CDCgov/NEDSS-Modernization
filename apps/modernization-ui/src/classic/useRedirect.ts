import { useEffect, useReducer } from 'react';
import { Destination } from './Destination';

type Redirect =
    | { status: 'idle' }
    | { status: 'redirecting'; url: string }
    | { status: 'redirected'; location: string };

type Action = { type: 'redirect'; url: string } | { type: 'redirected'; location: string } | { type: 'reset' };

const initial: Redirect = { status: 'idle' };

const reducer = (_state: Redirect, action: Action): Redirect => {
    switch (action.type) {
        case 'redirect':
            return { status: 'redirecting', url: action.url };
        case 'redirected':
            return { status: 'redirected', location: action.location };
        default:
            return initial;
    }
};

const resolveRedirect = (url: string) => fetch(url).then((response) => response.headers.get('Location'));

type Props = {
    destination?: Destination;
};

const useRedirect = ({ destination = 'current' }: Props) => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'redirecting') {
            resolveRedirect(state.url).then((location) => {
                if (location) {
                    dispatch({ type: 'redirected', location });
                }
            });
        } else if (state.status == 'redirected' && destination != 'none') {
            navigate(destination, state.location);
            dispatch({ type: 'reset' });
        }
    }, [state.status]);

    return {
        redirecting: state.status === 'redirecting',
        location: state.status === 'redirected' && state.location,
        redirect: (url: string) => dispatch({ type: 'redirect', url }),
        reset: () => dispatch({ type: 'reset' })
    };
};

export { useRedirect };

const navigateTo = (location: string) => (window.location.href = location);

const openWindow = (location: string) => {
    window.open(location, '_blank', 'noreferrer');
};

const navigate = (destination: Destination, location: string) => {
    if (location) {
        switch (destination) {
            case 'current':
                navigateTo(location);
                break;
            case 'window':
                openWindow(location);
        }
    }
};

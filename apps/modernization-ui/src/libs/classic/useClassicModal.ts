import { useContext, useReducer, useEffect } from 'react';
import { ClassicModalContext } from './ClassicModalContext';

type ClassicModal = {
    state: ModalState;
    open: (location: string) => void;
    reset: () => void;
};

enum Status {
    Idle,
    Opening,
    Open,
    Closed
}

type ModalState = {
    /**
     * The URL displayed in the classic modal.
     */
    url?: string;
    status: Status;
    /**
     * The form action to be performed when the modal is closed, if any.
     */
    action?: string;
};

type Action =
    | { type: 'open'; location: string }
    | { type: 'opened' }
    | { type: 'closed'; action?: string }
    | { type: 'reset' };

const classicModalReducer = (state: ModalState, action: Action) => {
    switch (action.type) {
        case 'open':
            return { url: action.location, status: Status.Opening };
        case 'opened':
            return { ...state, status: Status.Open };
        case 'closed':
            return { ...state, status: Status.Closed, action: action.action };
        case 'reset':
            return { status: Status.Idle };
    }
};

const features = 'width=980px, height=900px, status=no, unadorned=yes, scroll=yes, help=no, resizable=no';

const useClassicModal = (): ClassicModal => {
    const context = useContext(ClassicModalContext);

    if (context === undefined) {
        throw new Error('useClassicModal must be used within a ClassicModalProvider');
    }

    const form = context.current;

    const [state, dispatch] = useReducer(classicModalReducer, { status: Status.Idle });

    const open = (location: string) => {
        dispatch({ type: 'open', location });
    };

    const reset = () => {
        dispatch({ type: 'reset' });
    };

    const showModal = (location: string) => {
        if (!context.current) {
            throw new Error('The receiving form for the Classic Modal could not be found');
        }

        if (window.document.forms[0] != form) {
            throw new Error(
                'The receiving form for the Classic Modal must be the first form available in the document'
            );
        }

        const modal = window.open(location, form.id, features);
        if (modal) {
            dispatch({ type: 'opened' });
        }
    };

    useEffect(() => {
        if (form && state.url && state.status === Status.Opening) {
            //  NBS Classic will invoke the submit method directly, the default handling of the form
            // should be disabled to prevent it from triggering navigation.  The form action will be
            // set by the modal to perform an action when the modal is closed.  If an action was set
            // then store it in the state so that it can be used by the caller
            form.submit = () => dispatch({ type: 'closed', action: form.action });
            showModal(state.url);
        } else if (form && state.status === Status.Closed) {
            form.submit = () => {};
            form.action = '';
        }
    }, [form, state]);

    return { state, open, reset };
};

export { useClassicModal, Status };

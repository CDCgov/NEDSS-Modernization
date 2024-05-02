import React, { ReactNode, useEffect, useReducer } from 'react';

import { Config } from 'config';
import { User } from 'user';

type InternalState = { status: 'waiting' } | { status: 'ready'; user: User } | { status: 'logout' };
const waiting: InternalState = {
    status: 'waiting'
};

type Action = { type: 'ready'; user: User } | { type: 'logout' };

const reducer = (_state: InternalState, action: Action): InternalState => {
    switch (action.type) {
        case 'ready':
            return { status: 'ready', user: action.user };
        case 'logout':
            return { status: 'logout' };
    }
};

type LoginInteraction = {
    user?: User;
    isLoggedIn: boolean;
};

type Interaction = {
    state: LoginInteraction;
    logout: () => void;
};

const UserContext = React.createContext<Interaction>({
    state: { isLoggedIn: false },
    logout: () => {}
});

const initialize = (user?: User): InternalState => (user ? { status: 'ready', user } : waiting);

type Props = {
    children: ReactNode;
    initial?: User;
};

const UserContextProvider = ({ initial, children }: Props) => {
    const [state, dispatch] = useReducer(reducer, initial, initialize);

    useEffect(() => {
        if (state.status === 'logout') {
            // delete cookies
            document.cookie = 'nbs_user=; Max-Age=0; path=/;';
            document.cookie = 'nbs_token=; Max-Age=0; path=/;';
            if (Config.enableLogin) {
                // loading external page will clear state
                window.location.href = '/logout';
            }
        }
    }, [dispatch, state.status]);

    const logout = () => dispatch({ type: 'logout' });

    const value = { state: { ...state, isLoggedIn: state.status === 'ready' }, logout };

    return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
};

export { UserContextProvider, UserContext };

export type { User, LoginInteraction as LoginState };

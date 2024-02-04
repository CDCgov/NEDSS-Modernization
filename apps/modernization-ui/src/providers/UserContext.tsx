import React, { ReactNode, useReducer } from 'react';

import { Config } from 'config';
import { User } from 'user';

const USER_ID = 'nbs_user=';
const TOKEN = 'nbs_token=';

type TokenProvider = () => string | undefined;

type InternalState = {
    status: 'waiting' | 'ready';
    user?: User;
};

type LoginState = {
    user?: User;
    isLoggedIn: boolean;
    getToken: TokenProvider;
};

// Grab token from cookie as it is updated on every request
const getToken: TokenProvider = () => {
    if (document.cookie.includes(TOKEN)) {
        const tokenStart = document.cookie.indexOf(TOKEN) + TOKEN.length;
        const tokenEnd = document.cookie.indexOf(';', tokenStart);
        return document.cookie.substring(tokenStart, tokenEnd > -1 ? tokenEnd : document.cookie.length);
    } else {
        return undefined;
    }
};

const waiting: InternalState = {
    status: 'waiting'
};

type Action = { type: 'ready'; user: User } | { type: 'logout' };

const reducer = (_state: InternalState, action: Action): InternalState => {
    switch (action.type) {
        case 'ready':
            return { status: 'ready', user: action.user };
        case 'logout':
            return waiting;
    }
};

export const UserContext = React.createContext<{
    state: LoginState;
    logout: () => void;
}>({
    state: { isLoggedIn: false, getToken },
    logout: () => {}
});

const initialize = (user?: User): InternalState => {
    if (user) {
        return { status: 'ready', user };
    } else {
        return waiting;
    }
};

type Props = {
    children: ReactNode;
    user?: User;
};

const UserContextProvider = ({ user, children }: Props) => {
    const [state, dispatch] = useReducer(reducer, user, initialize);

    const logout = () => {
        // delete cookies
        document.cookie = USER_ID + '=; Max-Age=0; path=/;';
        // load appropriate page
        if (Config.enableLogin) {
            // reset state
            dispatch({ type: 'logout' });
        } else {
            // loading external page will clear state
            window.location.href = `${Config.nbsUrl}/logOut`;
        }
    };

    const value = { state: { ...state, isLoggedIn: state.status === 'ready', getToken }, logout };

    return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
};

export { UserContextProvider };

export type { User, LoginState };

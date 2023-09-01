import React, { useEffect, useReducer } from 'react';
import { ApiError, UserService } from '../generated';
import { UserControllerService } from '../generated/services/UserControllerService';
import { Config } from 'config';
import { useNavigate } from 'react-router-dom';
const USER_ID = 'nbs_user=';
const TOKEN = 'nbs_token=';

type User = {
    identifier: number;
    name: {
        first: string;
        last: string;
        display: string;
    };
    permissions: string[];
};

type LoginState = {
    isLoggedIn: boolean;
    isLoginPending: boolean;
    loginError: string | undefined;
    userId: string | undefined;
    user?: User;
    getToken: () => string | undefined;
};

// Grab token from cookie as it is updated on every request
const getToken = (): string | undefined => {
    if (document.cookie.includes(TOKEN)) {
        const tokenStart = document.cookie.indexOf(TOKEN) + TOKEN.length;
        const tokenEnd = document.cookie.indexOf(';', tokenStart);
        return document.cookie.substring(tokenStart, tokenEnd > -1 ? tokenEnd : document.cookie.length);
    } else {
        return undefined;
    }
};

// proxied requests set the USER_ID cookie. use it on initialization to log in
const getUserIdFromCookie = (): string | undefined => {
    if (document.cookie.includes(USER_ID)) {
        const userIdStart = document.cookie.indexOf(USER_ID) + USER_ID.length;
        const userIdEnd = document.cookie.indexOf(';', userIdStart);
        return document.cookie.substring(userIdStart, userIdEnd > -1 ? userIdEnd : document.cookie.length);
    } else {
        return undefined;
    }
};

const initialState: LoginState = {
    isLoggedIn: false,
    isLoginPending: false,
    loginError: undefined,
    userId: undefined,
    getToken
};

type Action =
    | { type: 'login'; name: string }
    | { type: 'error'; error: string }
    | { type: 'success'; user: User }
    | { type: 'logout' };

const reducer = (state: LoginState, action: Action) => {
    switch (action.type) {
        case 'login':
            return { ...initialState, isLoginPending: true, userId: action.name };
        case 'error':
            return { ...state, isLoginPending: false, loginError: action.error };
        case 'success':
            return { ...state, isLoginPending: false, isLoggedIn: true, user: action.user };
        case 'logout':
            return { ...initialState };
    }
};

export const UserContext = React.createContext<{
    state: LoginState;
    login: (username: string) => void;
    logout: () => void;
}>({
    state: initialState,
    login: () => {},
    logout: () => {}
});

const UserContextProvider = (props: any) => {
    const nav = useNavigate();

    const [state, dispatch] = useReducer(reducer, initialState);

    const login = (username: string) => {
        dispatch({ type: 'login', name: username });
    };

    const logout = () => {
        // delete cookies
        document.cookie = USER_ID + '=; Max-Age=0; path=/;';
        document.cookie = TOKEN + '=; Max-Age=0; path=/;';
        // load appropriate page
        if (Config.enableLogin) {
            // reset state
            dispatch({ type: 'logout' });
            nav('/dev/login');
        } else {
            // loading external page will clear state
            window.location.href = `${Config.nbsUrl}/logOut`;
        }
    };

    // on init attempt to login using USER_ID cookie
    useEffect(() => {
        const resolved = getUserIdFromCookie();
        if (resolved) {
            login(resolved);
        }
    }, []);

    useEffect(() => {
        if (state.userId && state.isLoginPending) {
            UserControllerService.loginUsingPost({
                request: { username: state.userId, password: '' }
            })
                .then(() => UserService.meUsingGet({ authorization: `Bearer ${state.getToken()}` }))
                .then(({ identifier, firstName, lastName, permissions }) => {
                    const user = {
                        identifier,
                        name: {
                            first: firstName,
                            last: lastName,
                            display: firstName + ' ' + lastName
                        },
                        permissions
                    };
                    dispatch({ type: 'success', user });
                })
                .catch((ex: ApiError) => {
                    if (ex.status === 401) {
                        dispatch({ type: 'error', error: 'Incorrect username or password' });
                    } else {
                        dispatch({ type: 'error', error: 'Login failed' });
                    }
                });
        }
    }, [state.isLoginPending, state.userId]);

    const value = { state, login, logout };

    return <UserContext.Provider value={value}>{props.children}</UserContext.Provider>;
};

export { UserContextProvider };

export type { User, LoginState };

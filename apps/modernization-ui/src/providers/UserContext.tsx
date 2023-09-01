import React, { ReactNode, useEffect, useReducer } from 'react';
import { ApiError, Me, UserService } from '../generated';
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

type TokenProvider = () => string | undefined;

type InternalState = {
    status: 'idle' | 'pending' | 'logged-in' | 'ready' | 'error';
    isLoggedIn: boolean;
    isLoginPending: boolean;
    error?: string | undefined;
    username?: string | undefined;
    user?: User;
    getToken: TokenProvider;
};

type LoginState = {
    isLoggedIn: boolean;
    isLoginPending: boolean;
    error?: string | undefined;
    username?: string | undefined;
    user?: User;
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

// proxied requests set the USER_ID cookie. use it on initialization to log in
const getUserNameFromCookie = (): string | undefined => {
    if (document.cookie.includes(USER_ID)) {
        const userIdStart = document.cookie.indexOf(USER_ID) + USER_ID.length;
        const userIdEnd = document.cookie.indexOf(';', userIdStart);
        return document.cookie.substring(userIdStart, userIdEnd > -1 ? userIdEnd : document.cookie.length);
    } else {
        return undefined;
    }
};

const initialState: InternalState = {
    status: 'idle',
    isLoggedIn: false,
    isLoginPending: false,
    error: undefined,
    username: undefined,
    getToken
};

type Action =
    | { type: 'login'; name: string }
    | { type: 'initialize'; name?: string }
    | { type: 'error'; error: string }
    | { type: 'ready'; user: User }
    | { type: 'logout' };

const reducer = (state: InternalState, action: Action): InternalState => {
    switch (action.type) {
        case 'login':
            return { ...initialState, status: 'pending', isLoginPending: true, username: action.name };
        case 'initialize':
            return { ...state, status: 'logged-in' };
        case 'error':
            return { ...state, status: 'error', isLoginPending: false, error: action.error };
        case 'ready':
            return { ...state, status: 'ready', isLoginPending: false, isLoggedIn: true, user: action.user };
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

type Props = {
    children: ReactNode;
};

const UserContextProvider = ({ children }: Props) => {
    const navigateTo = useNavigate();

    const [state, dispatch] = useReducer(reducer, initialState);

    const login = (username: string) => {
        if (Config.enableLogin) {
            dispatch({ type: 'login', name: username });
        }
    };

    const logout = () => {
        // delete cookies
        document.cookie = USER_ID + '=; Max-Age=0; path=/;';
        // load appropriate page
        if (Config.enableLogin) {
            // reset state
            dispatch({ type: 'logout' });
            navigateTo('/dev/login');
        } else {
            // loading external page will clear state
            window.location.href = `${Config.nbsUrl}/logOut`;
        }
    };

    // on init attempt to login using USER_ID cookie
    useEffect(() => {
        const resolved = getUserNameFromCookie();
        if (resolved) {
            dispatch({ type: 'initialize', name: resolved });
        }
    }, []);

    const handleMeSuccess = ({ identifier, firstName, lastName, permissions }: Me) => {
        const user = {
            identifier,
            name: {
                first: firstName,
                last: lastName,
                display: firstName + ' ' + lastName
            },
            permissions
        };
        dispatch({ type: 'ready', user });
    };

    const handleError = (error: ApiError) => {
        if (error.status === 401) {
            dispatch({ type: 'error', error: 'Incorrect username or password' });
        } else {
            dispatch({ type: 'error', error: 'Login failed' });
        }
    };

    useEffect(() => {
        if (state.status === 'logged-in' && Config.enableLogin) {
            document.cookie = `nbs_user=${state.username}`;
        }

        if (state.status === 'pending' && state.username) {
            UserControllerService.loginUsingPost({
                request: { username: state.username, password: '' }
            })
                .then(() => dispatch({ type: 'initialize' }))
                .catch(handleError);
        }

        if (state.status === 'logged-in') {
            UserService.meUsingGet({ authorization: `Bearer ${state.getToken()}` })
                .then(handleMeSuccess)
                .catch(handleError);
        }
    }, [state.status, state.username]);

    const value = { state, login, logout };

    return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
};

export { UserContextProvider };

export type { User, LoginState };

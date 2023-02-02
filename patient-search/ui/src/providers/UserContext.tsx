import React, { useEffect } from 'react';
import { ApiError } from '../generated';
import { UserControllerService } from '../generated/services/UserControllerService';
const USER_ID = 'nbs_user=';
const TOKEN = 'nbs_token=';

interface UserState {
    isLoggedIn: boolean;
    isLoginPending: boolean;
    loginError: string | undefined;
    userId: string | undefined;
    displayName: string | undefined;
    getToken: () => string | undefined;
}

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

const initialState: UserState = {
    isLoggedIn: false,
    isLoginPending: false,
    loginError: undefined,
    userId: undefined,
    displayName: undefined,
    getToken
};

export const UserContext = React.createContext<{
    state: UserState;
    login: (username: string, password: string) => Promise<boolean>;
    logout: () => void;
}>({
    state: initialState,
    login: async () => {
        return true;
    },
    logout: () => {}
});

export const UserContextProvider = (props: any) => {
    const [state, setState] = React.useState({ ...initialState });
    const setLoginPending = (isLoginPending: boolean) => setState({ ...state, isLoginPending, loginError: undefined });
    const setLoginSuccess = (userId: string, displayName: string) =>
        setState({ ...initialState, isLoginPending: false, isLoggedIn: true, userId, displayName });
    const setLoginError = (loginError: string) => setState({ ...initialState, isLoginPending: false, loginError });
    const login = async (username: string, password: string): Promise<boolean> => {
        setLoginPending(true);
        const success: boolean = await UserControllerService.loginUsingPost({
            request: { username, password }
        })
            .then((response) => {
                setLoginSuccess(response.username, response.displayName);
                return true;
            })
            .catch((ex: ApiError) => {
                if (ex.status === 401) {
                    setLoginError('Incorrect username or password');
                } else {
                    setLoginError('Login failed');
                }
                return false;
            });
        return success;
    };

    const logout = () => {
        // delete cookies
        document.cookie = USER_ID + '=; Max-Age=0; path=/;';
        document.cookie = TOKEN + '=; Max-Age=0; path=/;';
        // reset state
        setState({ ...initialState });
    };

    // on init attempt to login using USER_ID cookie
    useEffect(() => {
        const userIdFromCookie = getUserIdFromCookie();
        if (userIdFromCookie) {
            login(userIdFromCookie, '');
        }
    }, []);

    return <UserContext.Provider value={{ state, login, logout }}>{props.children}</UserContext.Provider>;
};

import React, { useEffect } from 'react';
import { ApiError, LoginResponse } from '../generated';
import { UserControllerService } from '../generated/services/UserControllerService';
const USER_ID = 'nbsUserId=';
const TOKEN = 'token';

interface UserState {
    isLoggedIn: boolean;
    isLoginPending: boolean;
    loginError: string | undefined;
    userId: string | undefined;
    token: string | undefined;
}

const initialState: UserState = {
    isLoggedIn: false,
    isLoginPending: false,
    loginError: undefined,
    userId: undefined,
    token: undefined
};

export const UserContext = React.createContext<{
    state: UserState;
    login: (username: string, password: string) => Promise<boolean>;
    logout: () => void;
}>({
    state: initialState,
    login: async (a, b) => {
        return true;
    },
    logout: () => {}
});

const getUserIdFromCookie = (): string | undefined => {
    if (document.cookie.includes(USER_ID)) {
        const userIdStart = document.cookie.indexOf(USER_ID) + USER_ID.length;
        const userIdEnd = document.cookie.indexOf(';', userIdStart);
        return document.cookie.substring(userIdStart, userIdEnd > -1 ? userIdEnd : document.cookie.length);
    } else {
        return undefined;
    }
};

const getUserFromLocalStorage = (): LoginResponse | undefined => {
    const username = localStorage.getItem(USER_ID);
    const token = localStorage.getItem(TOKEN);
    if (username && token) {
        return {
            username,
            token
        };
    }
    return undefined;
};

const saveUserToLocalStorage = (user: LoginResponse): void => {
    if (user.token && user.username) {
        localStorage.setItem(USER_ID, user.username);
        localStorage.setItem(TOKEN, user.token);
    }
};

export const UserContextProvider = (props: any) => {
    const [state, setState] = React.useState({ ...initialState });

    const setLoginPending = (isLoginPending: boolean) => setState({ ...initialState, isLoginPending });
    const setLoginSuccess = (userId: string, token: string) =>
        setState({ ...initialState, isLoggedIn: true, userId, token });
    const setLoginError = (loginError: string) => setState({ ...initialState, loginError });

    const login = async (username: string, password: string): Promise<boolean> => {
        setLoginPending(true);

        const response: LoginResponse = await UserControllerService.loginUsingPost({
            request: { username, password }
        }).catch((ex: ApiError) => {
            if (ex.status === 401) {
                setLoginError('Incorrect username or password');
            } else {
                setLoginError('Login failed');
            }
            return false;
        });
        saveUserToLocalStorage(response);
        setLoginSuccess(response.username, response.token);
        return true;
    };

    const logout = () => {
        setLoginPending(false);
    };

    // on creation attempt to set user from localStorage or from cookie
    useEffect(() => {
        const userFromLocalStorage = getUserFromLocalStorage();
        const userIdFromCookie = getUserIdFromCookie();
        if (userFromLocalStorage) {
            setLoginSuccess(userFromLocalStorage.username, userFromLocalStorage.token);
        } else if (userIdFromCookie) {
            login(userIdFromCookie, '');
        }
    }, []);

    return <UserContext.Provider value={{ state, login, logout }}>{props.children}</UserContext.Provider>;
};

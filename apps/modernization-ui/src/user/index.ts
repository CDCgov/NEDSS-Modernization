export { useUser } from './useUser';
export { UserContext, UserContextProvider } from 'providers/UserContext';
export type { LoginState } from 'providers/UserContext';
export * from './currentUser';

type User = {
    identifier: number;
    name: {
        first: string;
        last: string;
        display: string;
    };
    permissions: string[];
};

export type { User };

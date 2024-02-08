import { authorization } from 'authorization';
import { Me, UserService } from 'generated';
import { User } from 'user';

const asUser = ({ identifier, firstName, lastName, permissions }: Me) => ({
    identifier,
    name: {
        first: firstName,
        last: lastName,
        display: firstName + ' ' + lastName
    },
    permissions
});

const currentUser = (): Promise<User> => UserService.meUsingGet({ authorization: authorization() }).then(asUser);

type CurrentUserResponse = Awaited<ReturnType<typeof currentUser>>;

type CurrentUserLoaderResult = { currentUser: CurrentUserResponse };
export { currentUser };
export type { CurrentUserResponse, CurrentUserLoaderResult };

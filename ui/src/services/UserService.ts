import { LoginResponse, UserControllerService } from '../generated';

export default class UserService {
    private static readonly USER_ID_COOKIE = 'nbsUserId=';
    private static user: LoginResponse | undefined;

    public static getUser(): LoginResponse | undefined {
        return UserService.user;
    }

    public static async login(username: string, password: string): Promise<LoginResponse | undefined> {
        const response = await UserControllerService.loginUsingPost({ request: { username, password } });
        UserService.user = response;
        return UserService.user;
    }

    public static getUserIdFromCookie(): string | undefined {
        if (document.cookie.includes(this.USER_ID_COOKIE)) {
            const userIdStart = document.cookie.indexOf(this.USER_ID_COOKIE) + this.USER_ID_COOKIE.length;
            const userIdEnd = document.cookie.indexOf(';', userIdStart);
            return document.cookie.substring(userIdStart, userIdEnd > -1 ? userIdEnd : document.cookie.length);
        } else {
            return undefined;
        }
    }
}

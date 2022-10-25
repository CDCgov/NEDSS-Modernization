import { LoginResponse, UserControllerService } from '../generated';

export default class UserService {
    private static user: LoginResponse | undefined;

    public getUser(): LoginResponse | undefined {
        return UserService.user;
    }

    public async login(username: string, password: string): Promise<LoginResponse | undefined> {
        const response = await UserControllerService.loginUsingPost({ request: { username, password } });
        UserService.user = response;
        return UserService.user;
    }
}

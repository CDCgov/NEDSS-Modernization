import { ProgramAreaControllerService } from '../generated';

export const fetchProgramAreaOptions = (token: string) => {
    return ProgramAreaControllerService.getProgramAreasUsingGet({
        authorization: token
    })
        .then((response: any) => {
            return response;
        })
        .catch((error) => {
            console.error('Error fetching program area options:', error);
        });
};

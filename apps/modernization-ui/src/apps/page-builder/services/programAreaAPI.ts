import { ProgramAreaControllerService } from "../generated";

export const fetchProgramAreaOptions = (
    token: string
) => {
    ProgramAreaControllerService.getProgramAreasUsingGet({
        authorization: token
    }).then((response: any) => {
        return response;
    });
};
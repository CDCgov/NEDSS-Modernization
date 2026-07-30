import { logErrorToUserConsole } from 'utils/logging';
import { ProgramAreaControllerService } from '../generated';

export const fetchProgramAreaOptions = () => {
    return ProgramAreaControllerService.getProgramAreas()
        .then((response) => {
            return response;
        })
        .catch((error) => {
            logErrorToUserConsole('Error fetching program area options:', error);
        });
};

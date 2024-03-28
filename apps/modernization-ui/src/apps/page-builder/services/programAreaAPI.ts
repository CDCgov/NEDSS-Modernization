import { ProgramAreaControllerService } from '../generated';

export const fetchProgramAreaOptions = () => {
    return ProgramAreaControllerService.getProgramAreas()
        .then((response) => {
            return response;
        })
        .catch((error) => {
            console.error('Error fetching program area options:', error);
        });
};

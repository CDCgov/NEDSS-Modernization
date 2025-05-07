import { PatientFileService } from 'generated';

export type DeletePatientFileResponse = {
    success: boolean;
    message?: string;
};

// type DeletePatientFileParams = {
//     onDeleteComplete: (data: DeletePatientFileResponse) => void;
// };

// type DeletePatientFile = {
//     delete: (patientId: number) => Promise<DeletePatientFileResponse>;
// };

export const useDeletePatientFile = (
    onDeleteComplete: (data: DeletePatientFileResponse) => void
): ((patientId: number) => Promise<DeletePatientFileResponse>) => {
    return async (patientId: number) => {
        try {
            PatientFileService.delete({ patient: patientId });
            console.log('Deleting patient file with ID:', patientId);
            const response = {
                success: true,
                message: 'Patient file deleted successfully'
            };
            onDeleteComplete(response);
            return response;
        } catch (error) {
            console.error('Error deleting patient file:', error);
            return {
                success: false,
                message: 'Failed to delete patient file'
            };
        }
    };
};

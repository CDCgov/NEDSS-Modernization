import { useCallback } from 'react';
import { PatientFileService } from 'generated';

export type DeletePatientFileResponse = {
    success: boolean;
    message?: string;
};

/**
 * Hook to delete a patient file.
 * @param onDeleteComplete Callback function to be called after deletion is complete
 * @return A function that takes a patient ID (long) and returns a promise with the deletion result
 */
export const useDeletePatientFile = (
    onDeleteComplete: (data: DeletePatientFileResponse) => void
): ((patientId: number) => Promise<void>) => {
    const deletePatientFile = useCallback(
        async (patientId: number) => {
            try {
                console.log('Deleting patient file with ID:', patientId);
                // const response = {
                //     success: true,
                //     message: 'Patient file deleted successfully'
                // };
                await PatientFileService.delete({ patient: patientId });
                onDeleteComplete({ success: true });
            } catch (error) {
                console.error('Error deleting patient file:', error);
                onDeleteComplete({
                    success: false,
                    message: 'Failed to delete patient file.'
                });
            }
        },
        [onDeleteComplete]
    );

    return deletePatientFile;
};

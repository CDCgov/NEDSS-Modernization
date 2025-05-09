import { useCallback } from 'react';
import { PatientFileService } from 'generated';

export type DeletePatientResponse = {
    success: boolean;
    message?: string;
};

/**
 * Hook to delete a patient.
 * @param onDeleteComplete Callback function to be called after deletion is complete
 * @return A function that takes a patient ID (long) and returns a promise with the deletion result
 */
export const useDeletePatient = (
    onDeleteComplete: (data: DeletePatientResponse) => void
): ((patientId: number) => Promise<void>) => {
    const deletePatient = useCallback(
        async (patientId: number) => {
            try {
                console.log('Deleting patient file with ID:', patientId);
                await PatientFileService.delete({ patient: patientId });
                onDeleteComplete({ success: true });
            } catch (error) {
                console.error('Error deleting patient:', error);
                onDeleteComplete({
                    success: false,
                    message: 'Failed to delete patient.'
                });
            }
        },
        [onDeleteComplete]
    );

    return deletePatient;
};

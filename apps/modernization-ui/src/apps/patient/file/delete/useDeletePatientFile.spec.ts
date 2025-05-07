import { renderHook } from '@testing-library/react';
import { useDeletePatientFile } from './useDeletePatientFile';
import { PatientFileService } from 'generated';

jest.mock('generated', () => ({
    PatientFileService: {
        delete: jest.fn()
    }
}));

describe('useDeletePatientFile', () => {
    let onDeleteCompleteMock: jest.Mock;

    beforeEach(() => {
        onDeleteCompleteMock = jest.fn();
        jest.clearAllMocks();
    });

    it('should call PatientFileService.delete and onDeleteComplete with success when deletion is successful', async () => {
        // useDeletePatientFile(onDeleteCompleteMock);
        (PatientFileService.delete as jest.Mock).mockResolvedValueOnce({});
        const { result } = renderHook(() => useDeletePatientFile(onDeleteCompleteMock));
        const deletePatientFile = result.current;
        await deletePatientFile(123);

        expect(PatientFileService.delete).toHaveBeenCalledWith({ patient: 123 });
        expect(onDeleteCompleteMock).toHaveBeenCalledWith({ success: true });
    });

    it('should call onDeleteComplete with failure when deletion fails', async () => {
        (PatientFileService.delete as jest.Mock).mockRejectedValueOnce(new Error('Deletion failed'));
        const { result } = renderHook(() => useDeletePatientFile(onDeleteCompleteMock));
        const deletePatientFile = result.current;

        await deletePatientFile(123);

        expect(PatientFileService.delete).toHaveBeenCalledWith({ patient: 123 });
        expect(onDeleteCompleteMock).toHaveBeenCalledWith({
            success: false,
            message: 'Failed to delete patient file.'
        });
    });
});

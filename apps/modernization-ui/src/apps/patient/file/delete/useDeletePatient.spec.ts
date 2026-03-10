import { Mock } from 'vitest';
import { renderHook } from '@testing-library/react';
import { useDeletePatient } from './useDeletePatient';
import { PatientFileService } from 'generated';

vi.mock('generated', () => ({
    PatientFileService: {
        delete: vi.fn()
    }
}));

describe('useDeletePatient', () => {
    let onDeleteCompleteMock: Mock;

    beforeEach(() => {
        onDeleteCompleteMock = vi.fn();
        vi.clearAllMocks();
    });

    it('should call PatientFileService.delete and onDeleteComplete with success when deletion is successful', async () => {
        // useDeletePatientFile(onDeleteCompleteMock);
        (PatientFileService.delete as Mock).mockResolvedValueOnce({});
        const { result } = renderHook(() => useDeletePatient(onDeleteCompleteMock));
        const deletePatientFile = result.current;
        await deletePatientFile(123);

        expect(PatientFileService.delete).toHaveBeenCalledWith({ patient: 123 });
        expect(onDeleteCompleteMock).toHaveBeenCalledWith({ success: true });
    });

    it('should call onDeleteComplete with failure when deletion fails', async () => {
        (PatientFileService.delete as Mock).mockRejectedValueOnce(new Error('Deletion failed'));
        const { result } = renderHook(() => useDeletePatient(onDeleteCompleteMock));
        const deletePatientFile = result.current;

        await deletePatientFile(123);

        expect(PatientFileService.delete).toHaveBeenCalledWith({ patient: 123 });
        expect(onDeleteCompleteMock).toHaveBeenCalledWith({
            success: false,
            message: 'Failed to delete patient.'
        });
    });
});

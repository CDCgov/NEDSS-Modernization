import { renderHook, act } from '@testing-library/react';
import { PatientInvestigationsService } from 'generated';
import { usePatientInvestigations } from './usePatientInvestigations';

jest.mock('generated', () => ({
    PatientInvestigationsService: {
        patientInvestigations: jest.fn()
    }
}));

describe('usePatientInvestigations', () => {
    const mockPatientId = 123;
    const mockInvestigations = [
        { id: 1, name: 'Investigation 1' },
        { id: 2, name: 'Investigation 2' }
    ];

    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('should initialize in idle state when no patientId is provided', () => {
        const { result } = renderHook(() => usePatientInvestigations(0));

        expect(result.current).toEqual({
            error: undefined,
            isLoading: false,
            data: undefined,
            fetch: expect.any(Function)
        });
    });

    it('should fetch and return investigations data successfully', async () => {
        (PatientInvestigationsService.patientInvestigations as jest.Mock).mockResolvedValueOnce(mockInvestigations);

        const { result } = renderHook(() => usePatientInvestigations(mockPatientId));

        expect(result.current.isLoading).toBe(true);
        expect(result.current.data).toBeUndefined();

        await act(async () => {
            await Promise.resolve();
        });

        expect(result.current.isLoading).toBe(false);
        expect(result.current.data).toEqual(mockInvestigations);
        expect(result.current.error).toBeUndefined();

        expect(PatientInvestigationsService.patientInvestigations).toHaveBeenCalledWith({
            patientId: mockPatientId
        });
    });

    it('should handle API errors', async () => {
        const errorMessage = 'Failed to fetch investigation data';
        (PatientInvestigationsService.patientInvestigations as jest.Mock).mockRejectedValueOnce(
            new Error(errorMessage)
        );

        const { result } = renderHook(() => usePatientInvestigations(mockPatientId));

        expect(result.current.isLoading).toBe(true);

        await act(async () => {
            await Promise.resolve();
        });

        expect(result.current.isLoading).toBe(false);
        expect(result.current.data).toBeUndefined();
        expect(result.current.error).toBe(errorMessage);
    });
});

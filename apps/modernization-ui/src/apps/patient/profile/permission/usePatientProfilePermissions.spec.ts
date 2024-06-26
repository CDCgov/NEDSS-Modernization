import { renderHook } from '@testing-library/react-hooks';

import { usePatientProfilePermissions } from './usePatientProfilePermissions';

let mockedState = {};

jest.mock('user', () => ({
    useUser: () => ({ state: mockedState })
}));

describe('when there is no user', () => {
    it('should return default permissions', () => {
        const { result } = renderHook(() => usePatientProfilePermissions());

        const actual = result.current;

        expect(actual).toEqual({ delete: false, compareInvestigation: false });
    });
});

describe('when the user has no permissions', () => {
    it('should return default permissions', () => {
        mockedState = { user: {} };

        const { result } = renderHook(() => usePatientProfilePermissions());

        const actual = result.current;

        expect(actual).toEqual({ delete: false, compareInvestigation: false });
    });
});

describe('when the user has permissions', () => {
    it('should not allow patient delete when the DELETE-PATIENT permission is not present', () => {
        mockedState = { user: { permissions: ['VIEW-PATIENT', 'VIEW-INVESTIGATION'] } };

        const { result } = renderHook(() => usePatientProfilePermissions());

        const actual = result.current;

        expect(actual).toEqual({ delete: false, compareInvestigation: false });
    });

    it('should allow patient delete when the DELETE-PATIENT permission is present', () => {
        mockedState = { user: { permissions: ['VIEW-PATIENT', 'DELETE-PATIENT', 'VIEW-INVESTIGATION'] } };

        const { result } = renderHook(() => usePatientProfilePermissions());

        const actual = result.current;

        expect(actual).toEqual({ delete: true, compareInvestigation: false });
    });
});

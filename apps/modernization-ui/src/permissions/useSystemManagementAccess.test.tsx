import { renderHook } from '@testing-library/react-hooks';
import { useSystemManagementAccess } from './useSystemManagementAccess';

describe('useSystemManagementAccess', () => {
    it('should return true if the user has any of the system management permissions', () => {
        const permissions = ['VIEWPHCRACTIVITY-CASEREPORTING'];
        const { result } = renderHook(() => useSystemManagementAccess(permissions));
        expect(result.current).toBe(true);
    });

    it('should return false if the user does not have any of the system management permissions', () => {
        const permissions = ['SOMEOTHER-PERMISSION'];
        const { result } = renderHook(() => useSystemManagementAccess(permissions));
        expect(result.current).toBe(false);
    });

    it('should return false if the permissions are undefined', () => {
        const { result } = renderHook(() => useSystemManagementAccess(undefined));
        expect(result.current).toBe(false);
    });

    it('should return false if the permissions are null', () => {
        const { result } = renderHook(() => useSystemManagementAccess(null));
        expect(result.current).toBe(false);
    });
});

import { renderHook } from '@testing-library/react';
import { useNavigationBarPermissions } from './useNavigationBarPermissions';
import { useUser } from 'user';
import { vi } from 'vitest';

// Mock the useUser hook to control the returned user and their permissions
vi.mock('user', () => ({
    useUser: vi.fn()
}));

describe('useNavigationBarPermissions', () => {
    it('should return systemManagementAccess as true if the user has any of the system management permissions', () => {
        (useUser as ReturnType<typeof vi.fn>).mockReturnValue({
            state: {
                user: {
                    permissions: ['VIEWPHCRACTIVITY-CASEREPORTING']
                }
            }
        });

        const { result } = renderHook(() => useNavigationBarPermissions());
        expect(result.current.systemManagementAccess).toBe(true);
    });

    it('should return systemManagementAccess as false if the user does not have any of the system management permissions', () => {
        (useUser as ReturnType<typeof vi.fn>).mockReturnValue({
            state: {
                user: {
                    permissions: ['SOMEOTHER-PERMISSION']
                }
            }
        });

        const { result } = renderHook(() => useNavigationBarPermissions());
        expect(result.current.systemManagementAccess).toBe(false);
    });

    it('should return systemManagementAccess as false if the user’s permissions are undefined', () => {
        (useUser as ReturnType<typeof vi.fn>).mockReturnValue({
            state: {
                user: {
                    permissions: undefined
                }
            }
        });

        const { result } = renderHook(() => useNavigationBarPermissions());
        expect(result.current.systemManagementAccess).toBe(false);
    });
});

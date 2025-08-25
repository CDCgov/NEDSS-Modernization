import { renderHook } from '@testing-library/react';
import { useAddressCodedValues } from './useAddressCodedValues';
import { useConceptOptions } from 'options/concepts';
import { Selectable } from 'options';

jest.mock('options/concepts', () => ({
    useConceptOptions: jest.fn()
}));

describe('useAddressCodedValues', () => {
    const mockTypes: Selectable[] = [
        { value: 'H', name: 'Home' },
        { value: 'W', name: 'Work' }
    ];

    const mockUses: Selectable[] = [
        { value: 'H', name: 'Home' },
        { value: 'W', name: 'Work' },
        { value: 'BIR', name: 'Birth place' },
        { value: 'DTH', name: 'Death place' },
        { value: 'TMP', name: 'Temporary' }
    ];

    beforeEach(() => {
        jest.clearAllMocks();
        (useConceptOptions as jest.Mock).mockImplementation((valueSet: string) => {
            if (valueSet === 'EL_TYPE_PST_PAT') {
                return { options: mockTypes };
            }
            if (valueSet === 'EL_USE_PST_PAT') {
                return { options: mockUses };
            }
            return { options: [] };
        });
    });

    it('should return address types and filtered uses', () => {
        const { result } = renderHook(() => useAddressCodedValues());

        expect(result.current.types).toEqual(mockTypes);
        expect(result.current.uses).toHaveLength(3);
    });

    it('should filter out BIR (Birth place) option from uses', () => {
        const { result } = renderHook(() => useAddressCodedValues());

        const birthPlaceOption = result.current.uses.find((option) => option.value === 'BIR');
        expect(birthPlaceOption).toBeUndefined();
    });

    it('should filter out DTH (Death place) option from uses', () => {
        const { result } = renderHook(() => useAddressCodedValues());

        const deathPlaceOption = result.current.uses.find((option) => option.value === 'DTH');
        expect(deathPlaceOption).toBeUndefined();
    });

    it('should include other valid use options', () => {
        const { result } = renderHook(() => useAddressCodedValues());

        const homeOption = result.current.uses.find((option) => option.value === 'H');
        const workOption = result.current.uses.find((option) => option.value === 'W');
        const tempOption = result.current.uses.find((option) => option.value === 'TMP');

        expect(homeOption).toBeDefined();
        expect(workOption).toBeDefined();
        expect(tempOption).toBeDefined();
    });
});

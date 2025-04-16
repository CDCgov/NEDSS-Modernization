import { renderHook } from '@testing-library/react';
import { useConceptOptions } from './useConceptOptions';
import { ConceptOptionsService } from 'generated';
import { useSelectableOptions } from 'options/useSelectableOptions';

jest.mock('generated', () => ({
    ConceptOptionsService: {
        concepts: jest.fn()
    }
}));

jest.mock('options/useSelectableOptions', () => ({
    useSelectableOptions: jest.fn()
}));

describe('useConceptOptions', () => {
    const mockOptions = [
        { id: '1', label: 'Option 1' },
        { id: '2', label: 'Option 2' }
    ];
    const mockResponse = { options: mockOptions };

    beforeEach(() => {
        jest.clearAllMocks();
        (ConceptOptionsService.concepts as jest.Mock).mockResolvedValue(mockResponse);
        (useSelectableOptions as jest.Mock).mockReturnValue({
            options: mockOptions,
            load: jest.fn()
        });
    });

    it('should return options and load function', () => {
        const { result } = renderHook(() => useConceptOptions('valueSet', { lazy: true }));

        expect(result.current.options).toEqual(mockOptions);
        expect(typeof result.current.load).toBe('function');
    });

    it('should call useSelectableOptions with correct resolver and lazy value', () => {
        renderHook(() => useConceptOptions('valueSet', { lazy: false }));

        expect(useSelectableOptions).toHaveBeenCalledWith({
            resolver: expect.any(Function),
            lazy: false
        });
    });

    it('should resolve options using ConceptOptionsService', async () => {
        renderHook(() => useConceptOptions('valueSet', { lazy: false }));

        const resolver = (useSelectableOptions as jest.Mock).mock.calls[0][0].resolver;
        const resolvedOptions = await resolver();

        expect(ConceptOptionsService.concepts).toHaveBeenCalledWith({ name: 'valueSet' });
        expect(resolvedOptions).toEqual(mockOptions);
    });
});

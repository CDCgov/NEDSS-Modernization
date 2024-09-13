import { act, renderHook } from '@testing-library/react-hooks';
import { Settings, useAddExtendedPatient } from './useAddExtendedPatient';
import { ExtendedNewPatientEntry } from './entry';
import { NewPatient } from './api';

const setup = (settings?: Partial<Settings>) => {
    const transformer = settings?.transformer ?? jest.fn();
    const creator = settings?.creator ?? jest.fn();

    return renderHook(() => useAddExtendedPatient({ transformer, creator }));
};

describe('when adding patients with extended data', () => {
    it('should default to waiting', () => {
        const { result } = setup();

        expect(result.current.status).toEqual('waiting');
    });

    it('should transition to created when creation is completed', async () => {
        const input: NewPatient = { administrative: { asOf: '04/13/2017', comment: 'transformed' } };

        const transformer = jest.fn();

        transformer.mockReturnValue(input);

        const creator = jest.fn();

        creator.mockResolvedValue({ id: 101, shortId: 691 });

        const { result } = setup({ transformer, creator });

        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017', comment: 'entered' }
        };

        await act(async () => {
            result.current.create(entry);
        });

        expect(transformer).toHaveBeenCalledWith(
            expect.objectContaining({ administrative: { asOf: '04/13/2017', comment: 'entered' } })
        );

        expect(creator).toHaveBeenCalledWith(
            expect.objectContaining({ administrative: { asOf: '04/13/2017', comment: 'transformed' } })
        );

        expect(result.current.status).toEqual('created');
    });
});

import { act, renderHook } from '@testing-library/react-hooks';
import { Settings, useAddExtendedPatient } from './useAddExtendedPatient';
import { ExtendedNewPatientEntry } from './entry';
import { NewPatient } from './api';
import { Invalid } from './useAddExtendedPatientInteraction';

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

    it('should validate name sub form is not dirty when attempting to create', async () => {
        const { result } = setup();

        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017', comment: 'entered' }
        };

        await act(async () => {
            result.current.setSubFormState({ name: true });
            result.current.create(entry);
        });

        expect(result.current.status).toBe('invalid');
        const validationErrors = (result.current as Invalid).validationErrors;
        expect(validationErrors.dirtySections.name).toBeTruthy();
        expect(validationErrors.dirtySections.address).toBeFalsy();
        expect(validationErrors.dirtySections.phone).toBeFalsy();
        expect(validationErrors.dirtySections.identification).toBeFalsy();
        expect(validationErrors.dirtySections.race).toBeFalsy();
    });

    it('should validate address sub form is not dirty when attempting to create', async () => {
        const { result } = setup();

        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017', comment: 'entered' }
        };

        await act(async () => {
            result.current.setSubFormState({ address: true });
            result.current.create(entry);
        });

        expect(result.current.status).toBe('invalid');

        const validationErrors = (result.current as Invalid).validationErrors;
        expect(validationErrors.dirtySections.name).toBeFalsy();
        expect(validationErrors.dirtySections.address).toBeTruthy();
        expect(validationErrors.dirtySections.phone).toBeFalsy();
        expect(validationErrors.dirtySections.identification).toBeFalsy();
        expect(validationErrors.dirtySections.race).toBeFalsy();
    });

    it('should validate phone sub form is not dirty when attempting to create', async () => {
        const { result } = setup();

        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017', comment: 'entered' }
        };

        await act(async () => {
            result.current.setSubFormState({ phone: true });
            result.current.create(entry);
        });

        expect(result.current.status).toBe('invalid');
        const validationErrors = (result.current as Invalid).validationErrors;
        expect(validationErrors.dirtySections.name).toBeFalsy();
        expect(validationErrors.dirtySections.address).toBeFalsy();
        expect(validationErrors.dirtySections.phone).toBeTruthy();
        expect(validationErrors.dirtySections.identification).toBeFalsy();
        expect(validationErrors.dirtySections.race).toBeFalsy();
    });

    it('should validate identification sub form is not dirty when attempting to create', async () => {
        const { result } = setup();

        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017', comment: 'entered' }
        };

        await act(async () => {
            result.current.setSubFormState({ identification: true });
            result.current.create(entry);
        });

        expect(result.current.status).toBe('invalid');
        const validationErrors = (result.current as Invalid).validationErrors;
        expect(validationErrors.dirtySections.name).toBeFalsy();
        expect(validationErrors.dirtySections.address).toBeFalsy();
        expect(validationErrors.dirtySections.phone).toBeFalsy();
        expect(validationErrors.dirtySections.identification).toBeTruthy();
        expect(validationErrors.dirtySections.race).toBeFalsy();
    });

    it('should validate race sub form is not dirty when attempting to create', async () => {
        const { result } = setup();

        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017', comment: 'entered' }
        };

        await act(async () => {
            result.current.setSubFormState({ race: true });
            result.current.create(entry);
        });

        expect(result.current.status).toBe('invalid');
        const validationErrors = (result.current as Invalid).validationErrors;
        expect(validationErrors.dirtySections.name).toBeFalsy();
        expect(validationErrors.dirtySections.address).toBeFalsy();
        expect(validationErrors.dirtySections.phone).toBeFalsy();
        expect(validationErrors.dirtySections.identification).toBeFalsy();
        expect(validationErrors.dirtySections.race).toBeTruthy();
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

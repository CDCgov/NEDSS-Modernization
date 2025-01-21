import { act, renderHook } from '@testing-library/react-hooks';
import { useAddExtendedPatient } from './useAddExtendedPatient';
import { ExtendedNewPatientEntry } from './entry';

const setup = () => {
    return renderHook(() => useAddExtendedPatient());
};

describe('when adding patients with extended data', () => {
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

        if (result.current.status === 'invalid') {
            const { validationErrors } = result.current;
            expect(validationErrors.dirtySections.name).toBeTruthy();
            expect(validationErrors.dirtySections.address).toBeFalsy();
            expect(validationErrors.dirtySections.phone).toBeFalsy();
            expect(validationErrors.dirtySections.identification).toBeFalsy();
            expect(validationErrors.dirtySections.race).toBeFalsy();
        }
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

        if (result.current.status === 'invalid') {
            const { validationErrors } = result.current;
            expect(validationErrors.dirtySections.name).toBeFalsy();
            expect(validationErrors.dirtySections.address).toBeTruthy();
            expect(validationErrors.dirtySections.phone).toBeFalsy();
            expect(validationErrors.dirtySections.identification).toBeFalsy();
            expect(validationErrors.dirtySections.race).toBeFalsy();
        }
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

        if (result.current.status === 'invalid') {
            const { validationErrors } = result.current;
            expect(validationErrors.dirtySections.name).toBeFalsy();
            expect(validationErrors.dirtySections.address).toBeFalsy();
            expect(validationErrors.dirtySections.phone).toBeTruthy();
            expect(validationErrors.dirtySections.identification).toBeFalsy();
            expect(validationErrors.dirtySections.race).toBeFalsy();
        }
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

        if (result.current.status === 'invalid') {
            const { validationErrors } = result.current;
            expect(validationErrors.dirtySections.name).toBeFalsy();
            expect(validationErrors.dirtySections.address).toBeFalsy();
            expect(validationErrors.dirtySections.phone).toBeFalsy();
            expect(validationErrors.dirtySections.identification).toBeTruthy();
            expect(validationErrors.dirtySections.race).toBeFalsy();
        }
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

        if (result.current.status === 'invalid') {
            const { validationErrors } = result.current;
            expect(validationErrors.dirtySections.name).toBeFalsy();
            expect(validationErrors.dirtySections.address).toBeFalsy();
            expect(validationErrors.dirtySections.phone).toBeFalsy();
            expect(validationErrors.dirtySections.identification).toBeFalsy();
            expect(validationErrors.dirtySections.race).toBeTruthy();
        }
    });

    it('should revalidate when dirty state changed', async () => {
        const { result } = setup();

        const entry: ExtendedNewPatientEntry = {
            administrative: { asOf: '04/13/2017', comment: 'entered' }
        };

        await act(async () => {
            result.current.setSubFormState({ race: true });
            result.current.create(entry);
        });

        expect(result.current.status).toBe('invalid');

        await act(async () => {
            result.current.setSubFormState({ race: false });
        });

        expect(result.current.status).toBe('waiting');
    });
});

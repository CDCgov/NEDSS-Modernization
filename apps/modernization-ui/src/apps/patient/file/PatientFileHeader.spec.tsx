import { render, screen } from '@testing-library/react';
import { PatientFileHeader } from './PatientFileHeader';
import { Patient } from './patient';

const mockNow = jest.fn();

jest.mock('design-system/date/clock', () => ({
    now: () => mockNow()
}));

describe('when displaying the demographics summary of a patient', () => {
    beforeEach(() => {
        mockNow.mockReturnValue(new Date('2020-01-25T00:00:00'));
    });

    it('should display "---" when a name is not present', () => {
        const patient: Patient = {
            id: 17,
            patientId: 397,
            local: 'local-id-value',
            status: 'ACTIVE',
            deletability: 'Deletable'
        };

        render(<PatientFileHeader patient={patient} actions={<></>} />);

        expect(screen.getByRole('heading', { name: '---' })).toBeInTheDocument();
        expect(screen.queryByText('status value')).not.toBeInTheDocument();
    });

    it('should display the patient ID', () => {
        const patient: Patient = {
            id: 17,
            patientId: 397,
            local: 'local-id-value',
            status: 'ACTIVE',
            deletability: 'Deletable'
        };

        render(<PatientFileHeader patient={patient} actions={<></>} />);

        expect(screen.getByText('Patient ID: 397')).toBeInTheDocument();
    });

    it('should display gender of the patient', () => {
        const patient: Patient = {
            id: 17,
            patientId: 397,
            local: 'local-id-value',
            status: 'ACTIVE',
            deletability: 'Deletable',
            sex: 'gender-value'
        };

        render(<PatientFileHeader patient={patient} actions={<></>} />);

        expect(screen.getByText('gender-value')).toBeInTheDocument();
    });

    it('should include the patient actions', () => {
        const patient: Patient = {
            id: 17,
            patientId: 397,
            local: 'local-id-value',
            status: 'ACTIVE',
            deletability: 'Deletable'
        };

        render(<PatientFileHeader patient={patient} actions={'actions'} />);

        expect(screen.getByText('actions')).toBeInTheDocument();
    });

    it('should display status when a patient is INACTIVE', () => {
        const patient: Patient = {
            id: 17,
            patientId: 397,
            local: 'local-id-value',
            status: 'INACTIVE',
            deletability: 'Deletable'
        };

        render(<PatientFileHeader patient={patient} actions={<></>} />);

        expect(screen.getByText('INACTIVE')).toBeInTheDocument();
    });

    describe('and a name is present', () => {
        it('should display the full name', () => {
            const patient: Patient = {
                id: 17,
                patientId: 397,
                local: 'local-id-value',
                status: 'ACTIVE',
                deletability: 'Deletable',
                name: {
                    first: 'first-name-value',
                    middle: 'middle-name-value',
                    last: 'last-name-value',
                    suffix: 'suffix-value'
                }
            };

            render(<PatientFileHeader patient={patient} actions={<></>} />);

            expect(
                screen.getByRole('heading', {
                    name: 'last-name-value, first-name-value middle-name-value, suffix-value'
                })
            ).toBeInTheDocument();
        });

        it('should display only the first name', () => {
            const patient: Patient = {
                id: 17,
                patientId: 397,
                local: 'local-id-value',
                status: 'ACTIVE',
                deletability: 'Deletable',
                name: {
                    first: 'first-name-value'
                }
            };

            render(<PatientFileHeader patient={patient} actions={<></>} />);

            expect(
                screen.getByRole('heading', {
                    name: '--, first-name-value'
                })
            ).toBeInTheDocument();
        });

        it('should display only the middle name', () => {
            const patient: Patient = {
                id: 17,
                patientId: 397,
                local: 'local-id-value',
                status: 'ACTIVE',
                deletability: 'Deletable',
                name: {
                    middle: 'middle-name-value'
                }
            };

            render(<PatientFileHeader patient={patient} actions={<></>} />);

            expect(
                screen.getByRole('heading', {
                    name: '--, -- middle-name-value'
                })
            ).toBeInTheDocument();
        });

        it('should display only the last name', () => {
            const patient: Patient = {
                id: 17,
                patientId: 397,
                local: 'local-id-value',
                status: 'ACTIVE',
                deletability: 'Deletable',
                name: {
                    last: 'last-name-value'
                }
            };

            render(<PatientFileHeader patient={patient} actions={<></>} />);

            expect(
                screen.getByRole('heading', {
                    name: 'last-name-value, --'
                })
            ).toBeInTheDocument();
        });

        it('should display the name suffix', () => {
            const patient: Patient = {
                id: 17,
                patientId: 397,
                local: 'local-id-value',
                status: 'ACTIVE',
                deletability: 'Deletable',
                name: {
                    suffix: 'suffix-value'
                }
            };

            render(<PatientFileHeader patient={patient} actions={<></>} />);

            expect(
                screen.getByRole('heading', {
                    name: '--, --, suffix-value'
                })
            ).toBeInTheDocument();
        });
    });

    describe('and the birthday is present', () => {
        it('should display the age based on today', () => {
            mockNow.mockReturnValue(new Date('2022-01-25T00:00:00'));

            const patient: Patient = {
                id: 17,
                patientId: 397,
                local: 'local-id-value',
                status: 'ACTIVE',
                deletability: 'Deletable',
                birthday: '08/09/1979'
            };

            render(<PatientFileHeader patient={patient} actions={<></>} />);

            expect(screen.getByText('08/09/1979 (42 years)')).toBeInTheDocument();
        });

        it('should display the age based on the date of death', () => {
            mockNow.mockReturnValue(new Date('2022-01-25T00:00:00'));

            const patient: Patient = {
                id: 17,
                patientId: 397,
                local: 'local-id-value',
                status: 'ACTIVE',
                deletability: 'Deletable',
                birthday: '08/09/1979',
                deceasedOn: '11/18/2010'
            };

            render(<PatientFileHeader patient={patient} actions={<></>} />);

            expect(screen.getByText('08/09/1979 (31 years)')).toBeInTheDocument();
        });
    });
});

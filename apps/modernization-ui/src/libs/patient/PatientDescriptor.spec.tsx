import { render, screen } from '@testing-library/react';
import { PatientDescriptor } from './PatientDescriptor';

const mockNow = jest.fn();

jest.mock('design-system/date/clock', () => ({
    now: () => mockNow()
}));

describe('when displaying the demographics summary of a patient', () => {
    beforeEach(() => {
        mockNow.mockReturnValue(new Date('2020-01-25T00:00:00'));
    });

    it('should display "---" when a name is not present', () => {
        const patient = {
            id: 17,
            patientId: 397,
            status: 'ACTIVE'
        };

        render(<PatientDescriptor patient={patient} headingLevel={1} />);

        expect(screen.getByRole('heading', { name: '---' })).toBeInTheDocument();
        expect(screen.queryByText('status value')).not.toBeInTheDocument();
    });

    it('should display the patient ID', () => {
        const patient = {
            id: 17,
            patientId: 397,
            status: 'status value'
        };

        render(<PatientDescriptor patient={patient} headingLevel={1} />);

        expect(screen.getByText('Patient ID: 397')).toBeInTheDocument();
    });

    it('should display gender of the patient', () => {
        const patient = {
            id: 17,
            patientId: 397,
            status: 'status value',
            sex: 'gender-value'
        };

        render(<PatientDescriptor patient={patient} headingLevel={1} />);

        expect(screen.getByText('gender-value')).toBeInTheDocument();
    });

    it('should display status when a patient is INACTIVE', () => {
        const patient = {
            id: 17,
            patientId: 397,
            status: 'INACTIVE'
        };

        render(<PatientDescriptor patient={patient} headingLevel={1} />);

        expect(screen.getByText('INACTIVE')).toBeInTheDocument();
    });

    describe('and a name is present', () => {
        it('should display the full name', () => {
            const patient = {
                id: 17,
                patientId: 397,
                status: 'status value',
                name: {
                    first: 'first-name-value',
                    middle: 'middle-name-value',
                    last: 'last-name-value',
                    suffix: 'suffix-value'
                }
            };

            render(<PatientDescriptor patient={patient} headingLevel={1} />);

            expect(
                screen.getByRole('heading', {
                    name: 'last-name-value, first-name-value middle-name-value, suffix-value'
                })
            ).toBeInTheDocument();
        });

        it('should display only the first name', () => {
            const patient = {
                id: 17,
                patientId: 397,
                status: 'status value',
                name: {
                    first: 'first-name-value'
                }
            };

            render(<PatientDescriptor patient={patient} headingLevel={1} />);

            expect(
                screen.getByRole('heading', {
                    name: '--, first-name-value'
                })
            ).toBeInTheDocument();
        });

        it('should display only the middle name', () => {
            const patient = {
                id: 17,
                patientId: 397,
                status: 'status value',
                name: {
                    middle: 'middle-name-value'
                }
            };

            render(<PatientDescriptor patient={patient} headingLevel={1} />);

            expect(
                screen.getByRole('heading', {
                    name: '--, -- middle-name-value'
                })
            ).toBeInTheDocument();
        });

        it('should display only the last name', () => {
            const patient = {
                id: 17,
                patientId: 397,
                status: 'status value',
                name: {
                    last: 'last-name-value'
                }
            };

            render(<PatientDescriptor patient={patient} headingLevel={1} />);

            expect(
                screen.getByRole('heading', {
                    name: 'last-name-value, --'
                })
            ).toBeInTheDocument();
        });

        it('should display the name suffix', () => {
            const patient = {
                id: 17,
                patientId: 397,
                status: 'status value',
                name: {
                    suffix: 'suffix-value'
                }
            };

            render(<PatientDescriptor patient={patient} headingLevel={1} />);

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

            const patient = {
                id: 17,
                patientId: 397,
                status: 'status value',
                birthday: '08/09/1979'
            };

            render(<PatientDescriptor patient={patient} headingLevel={1} />);

            expect(screen.getByText('08/09/1979 (42 years)')).toBeInTheDocument();
        });

        it('should display the age based on the date of death', () => {
            mockNow.mockReturnValue(new Date('2022-01-25T00:00:00'));

            const patient = {
                id: 17,
                patientId: 397,
                status: 'status value',
                birthday: '08/09/1979',
                deceasedOn: '11/18/2010'
            };

            render(<PatientDescriptor patient={patient} headingLevel={1} />);

            expect(screen.getByText('08/09/1979 (31 years)')).toBeInTheDocument();
        });
    });
});

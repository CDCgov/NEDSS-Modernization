import { render, waitFor } from '@testing-library/react';
import { MockedProvider } from '@apollo/client/testing';
import { FindVaccinationsForPatientDocument } from 'generated/graphql/schema';
import { ClassicModalProvider } from 'classic';
import { PatientProfileVaccinations } from './PatientProfileVaccinations';

describe('when the patient has not been vaccinated', () => {
    const response = {
        request: {
            query: FindVaccinationsForPatientDocument,
            variables: {
                patient: '73',
                page: {
                    pageNumber: 0,
                    pageSize: 5
                }
            }
        },
        result: {
            data: {
                findContactsNamedByPatient: {
                    content: [],
                    total: 0,
                    number: 0
                }
            }
        }
    };

    it('should display No Data', async () => {
        const { findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <ClassicModalProvider>
                    <PatientProfileVaccinations patient={'73'} pageSize={5}></PatientProfileVaccinations>
                </ClassicModalProvider>
            </MockedProvider>
        );

        expect(await findByText('No Data')).toBeInTheDocument();
    });
});

describe('when the patient has been vaccinated', () => {
    it('should display the contacts', async () => {
        const response = {
            request: {
                query: FindVaccinationsForPatientDocument,
                variables: {
                    patient: '1823',
                    page: {
                        pageNumber: 0,
                        pageSize: 5
                    }
                }
            },
            result: {
                data: {
                    findVaccinationsForPatient: {
                        content: [
                            {
                                vaccination: '1583',
                                createdOn: '2023-03-17T20:08:45Z',
                                provider: 'provider-value',
                                administeredOn: '2023-01-17T00:00:00Z',
                                administered: 'administered-value',
                                event: 'event-value',
                                associatedWith: {
                                    id: 'associated-id',
                                    local: 'associated-local',
                                    condition: 'associated-condition'
                                }
                            }
                        ],
                        total: 1,
                        number: 0
                    }
                }
            }
        };

        const { container, findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <ClassicModalProvider>
                    <PatientProfileVaccinations patient={'1823'} pageSize={5}></PatientProfileVaccinations>
                </ClassicModalProvider>
            </MockedProvider>
        );

        const tableData = container.getElementsByClassName('table-data');

        const dateCreated = await findByText(/03\/17\/2023/);

        expect(dateCreated).toHaveTextContent('03:08 PM');

        expect(tableData[0]).toContainElement(dateCreated);
        expect(tableData[1]).toHaveTextContent('provider-value');
        expect(tableData[2]).toHaveTextContent('01/17/2023');
        expect(tableData[3]).toHaveTextContent('administered-value');

        //  Associated With
        const association = await findByText('associated-local');

        expect(tableData[4]).toContainElement(association);
        const associationCondition = await findByText('associated-condition');

        expect(tableData[4]).toContainElement(associationCondition);

        expect(tableData[5]).toHaveTextContent('event-value');
    });
});

describe('when the patient has been vaccinated without an association', () => {
    it('should display a No Data for Associated With', async () => {
        const response = {
            request: {
                query: FindVaccinationsForPatientDocument,
                variables: {
                    patient: '1823',
                    page: {
                        pageNumber: 0,
                        pageSize: 5
                    }
                }
            },
            result: {
                data: {
                    findVaccinationsForPatient: {
                        content: [
                            {
                                vaccination: '1583',
                                createdOn: '2023-03-17T20:08:45Z',
                                provider: 'provider-value',
                                administeredOn: '2023-01-17T00:00:00Z',
                                administered: 'administered-value',
                                event: 'event-value'
                            }
                        ],
                        total: 1,
                        number: 0
                    }
                }
            }
        };

        const { container, findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <ClassicModalProvider>
                    <PatientProfileVaccinations patient={'1823'} pageSize={5}></PatientProfileVaccinations>
                </ClassicModalProvider>
            </MockedProvider>
        );

        const tableData = container.getElementsByClassName('table-data');

        await waitFor(() => expect(tableData[4]).toHaveTextContent('No Data'));
    });
});

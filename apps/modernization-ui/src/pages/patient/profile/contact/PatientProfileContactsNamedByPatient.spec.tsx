import { render } from '@testing-library/react';
import { MockedProvider } from '@apollo/client/testing';
import { FindContactsNamedByPatientDocument } from 'generated/graphql/schema';

import { PatientProfileContactsNamedByPatient } from './PatientProfileContactsNamedByPatient';
import { MemoryRouter } from 'react-router-dom';
import { ClassicModalProvider } from 'classic';

describe('when the patient has not been named by a contact', () => {
    const response = {
        request: {
            query: FindContactsNamedByPatientDocument,
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

    it('should display No data', async () => {
        const { findByText } = render(
            <MemoryRouter>
                <MockedProvider mocks={[response]} addTypename={false}>
                    <ClassicModalProvider>
                        <PatientProfileContactsNamedByPatient
                            patient={'73'}
                            pageSize={5}></PatientProfileContactsNamedByPatient>
                    </ClassicModalProvider>
                </MockedProvider>
            </MemoryRouter>
        );

        expect(await findByText('No data')).toBeInTheDocument();
    });
});

describe('when the patient has been named by a contact', () => {
    it('should display the contacts', async () => {
        const response = {
            request: {
                query: FindContactsNamedByPatientDocument,
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
                    findContactsNamedByPatient: {
                        content: [
                            {
                                contactRecord: '10055380',
                                createdOn: '2023-03-17T20:08:45.213Z',
                                contact: { id: '10000008', name: 'Surma Singh' },
                                namedOn: '2023-01-17T05:00:00Z',
                                condition: {
                                    id: 'condition-id',
                                    description: 'condition-description'
                                },
                                priority: 'priority-value',
                                disposition: 'disposition-value',
                                event: 'CON10000002GA01',
                                associatedWith: {
                                    id: '10000013',
                                    local: 'CAS10000000GA01',
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
            <MemoryRouter>
                <MockedProvider mocks={[response]} addTypename={false}>
                    <ClassicModalProvider>
                        <PatientProfileContactsNamedByPatient
                            patient={'1823'}
                            pageSize={5}></PatientProfileContactsNamedByPatient>
                    </ClassicModalProvider>
                </MockedProvider>
            </MemoryRouter>
        );

        const tableData = container.getElementsByClassName('table-data');

        const dateCreated = await findByText(/03\/17\/2023/);

        expect(dateCreated).toHaveTextContent('03:08 PM');

        expect(tableData[0]).toContainElement(dateCreated);
        expect(tableData[1]).toHaveTextContent('Surma Singh');
        expect(tableData[2]).toHaveTextContent('01/17/2023');

        //  Description fields
        const conditionValue = await findByText(/condition-description/);
        expect(tableData[3]).toContainElement(conditionValue);

        const dispositionLabel = await findByText(/Disposition:/);
        expect(tableData[3]).toContainElement(dispositionLabel);

        const dispositionValue = await findByText(/disposition-value/);
        expect(tableData[3]).toContainElement(dispositionValue);

        const priorityLabel = await findByText(/Priority:/);
        expect(tableData[3]).toContainElement(priorityLabel);

        const priorityValue = await findByText(/priority-value/);
        expect(tableData[3]).toContainElement(priorityValue);

        //  Associated With
        const association = await findByText('CAS10000000GA01');

        expect(tableData[4]).toContainElement(association);
        const associationCondition = await findByText('associated-condition');

        expect(tableData[4]).toContainElement(associationCondition);

        expect(tableData[5]).toHaveTextContent('CON10000002GA01');
    });
});

describe('when the patient has been named by a contact without an association', () => {
    it('should display a No Data for Associated With', async () => {
        const response = {
            request: {
                query: FindContactsNamedByPatientDocument,
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
                    findContactsNamedByPatient: {
                        content: [
                            {
                                contactRecord: '10055380',
                                createdOn: '2023-03-17T20:08:45.213Z',
                                contact: { id: '10000008', name: 'Surma Singh' },
                                namedOn: '2023-01-17T05:00:00Z',
                                condition: {
                                    id: 'condition-id',
                                    description: 'condition-description'
                                },
                                priority: 'priority-value',
                                disposition: 'disposition-value',
                                event: 'CON10000002GA01',
                                associatedWith: null
                            }
                        ],
                        total: 1,
                        number: 0
                    }
                }
            }
        };

        const { container, findByText } = render(
            <MemoryRouter>
                <MockedProvider mocks={[response]} addTypename={false}>
                    <ClassicModalProvider>
                        <PatientProfileContactsNamedByPatient
                            patient={'1823'}
                            pageSize={5}></PatientProfileContactsNamedByPatient>
                    </ClassicModalProvider>
                </MockedProvider>
            </MemoryRouter>
        );

        expect(await findByText('Showing 1 of 1')).toBeInTheDocument();

        const tableData = container.getElementsByClassName('table-data');

        expect(tableData[4]).toHaveTextContent('No data');
    });
});

describe('when the patient has been named by a contact without a priority', () => {
    it('should display a description without Priority', async () => {
        const response = {
            request: {
                query: FindContactsNamedByPatientDocument,
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
                    findContactsNamedByPatient: {
                        content: [
                            {
                                contactRecord: '10055380',
                                createdOn: '2023-03-17T20:08:45.213Z',
                                contact: { id: '10000008', name: 'Surma Singh' },
                                namedOn: '2023-01-17T05:00:00Z',
                                condition: {
                                    id: 'condition-id',
                                    description: 'condition-description'
                                },
                                priority: null,
                                disposition: 'disposition-value',
                                event: 'CON10000002GA01',
                                associatedWith: {
                                    id: '10000013',
                                    local: 'CAS10000000GA01',
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
            <MemoryRouter>
                <MockedProvider mocks={[response]} addTypename={false}>
                    <ClassicModalProvider>
                        <PatientProfileContactsNamedByPatient
                            patient={'1823'}
                            pageSize={5}></PatientProfileContactsNamedByPatient>
                    </ClassicModalProvider>
                </MockedProvider>
            </MemoryRouter>
        );

        const tableData = container.getElementsByClassName('table-data');

        //  Description fields
        const conditionValue = await findByText(/condition-description/);
        expect(tableData[3]).toContainElement(conditionValue);

        const dispositionLabel = await findByText(/Disposition:/);
        expect(tableData[3]).toContainElement(dispositionLabel);

        const dispositionValue = await findByText(/disposition-value/);
        expect(tableData[3]).toContainElement(dispositionValue);
    });
});

describe('when the patient has been named by a contact without a disposition', () => {
    it('should display a description without Disposition', async () => {
        const response = {
            request: {
                query: FindContactsNamedByPatientDocument,
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
                    findContactsNamedByPatient: {
                        content: [
                            {
                                contactRecord: '10055380',
                                createdOn: '2023-03-17T20:08:45.213Z',
                                contact: { id: '10000008', name: 'Surma Singh' },
                                namedOn: '2023-01-17T05:00:00Z',
                                condition: {
                                    id: 'condition-id',
                                    description: 'condition-description'
                                },
                                priority: 'priority-value',
                                disposition: null,
                                event: 'CON10000002GA01',
                                associatedWith: {
                                    id: '10000013',
                                    local: 'CAS10000000GA01',
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
            <MemoryRouter>
                <MockedProvider mocks={[response]} addTypename={false}>
                    <ClassicModalProvider>
                        <PatientProfileContactsNamedByPatient
                            patient={'1823'}
                            pageSize={5}></PatientProfileContactsNamedByPatient>
                    </ClassicModalProvider>
                </MockedProvider>
            </MemoryRouter>
        );

        const tableData = container.getElementsByClassName('table-data');

        //  Description fields
        const conditionValue = await findByText(/condition-description/);
        expect(tableData[3]).toContainElement(conditionValue);

        const priorityLabel = await findByText(/Priority:/);
        expect(tableData[3]).toContainElement(priorityLabel);

        const priorityValue = await findByText(/priority-value/);
        expect(tableData[3]).toContainElement(priorityValue);
    });
});

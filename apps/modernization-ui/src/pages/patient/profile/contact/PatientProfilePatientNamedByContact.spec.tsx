import { render } from '@testing-library/react';
import { MockedProvider } from '@apollo/client/testing';
import { FindPatientNamedByContactDocument } from 'generated/graphql/schema';

import { PatientProfilePatientNamedByContact } from './PatientProfilePatientNamedByContact';
import { MemoryRouter } from 'react-router-dom';
import { ClassicModalProvider } from 'classic';

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const { container } = render(
            <MemoryRouter>
                <MockedProvider addTypename={false}>
                    <ClassicModalProvider>
                        <PatientProfilePatientNamedByContact pageSize={3}></PatientProfilePatientNamedByContact>
                    </ClassicModalProvider>
                </MockedProvider>
            </MemoryRouter>
        );

        const tableHeader = container.getElementsByClassName('table-header');
        expect(tableHeader[0].innerHTML).toBe('Contact records (patient named by contacts)');

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[0].innerHTML).toBe('Date created');
        expect(tableHeads[1].innerHTML).toBe('Named by');
        expect(tableHeads[2].innerHTML).toBe('Date named');
        expect(tableHeads[3].innerHTML).toBe('Description');
        expect(tableHeads[4].innerHTML).toBe('Associated with');
        expect(tableHeads[5].innerHTML).toBe('Event #');
    });
});

describe('when the patient has been named by a contact', () => {
    it('should display the contacts', async () => {
        const response = {
            request: {
                query: FindPatientNamedByContactDocument,
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
                    findPatientNamedByContact: {
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
                        <PatientProfilePatientNamedByContact
                            patient={'1823'}
                            pageSize={5}></PatientProfilePatientNamedByContact>
                    </ClassicModalProvider>
                </MockedProvider>
            </MemoryRouter>
        );

        const tableData = container.getElementsByClassName('table-data');

        const dateCreated = await findByText(/03\/17\/2023/);

        expect(dateCreated).toHaveTextContent('03:08 PM');

        expect(tableData[0]).toContainElement(dateCreated);

        expect(tableData[1].innerHTML).toContain('Surma Singh');
        expect(tableData[2].innerHTML).toContain('01/17/2023');

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
                query: FindPatientNamedByContactDocument,
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
                    findPatientNamedByContact: {
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
                        <PatientProfilePatientNamedByContact
                            patient={'1823'}
                            pageSize={5}></PatientProfilePatientNamedByContact>
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
                query: FindPatientNamedByContactDocument,
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
                    findPatientNamedByContact: {
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
                        <PatientProfilePatientNamedByContact
                            patient={'1823'}
                            pageSize={5}></PatientProfilePatientNamedByContact>
                    </ClassicModalProvider>
                </MockedProvider>
            </MemoryRouter>
        );

        const tableData = container.getElementsByClassName('table-data');

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
                query: FindPatientNamedByContactDocument,
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
                    findPatientNamedByContact: {
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
                        <PatientProfilePatientNamedByContact
                            patient={'1823'}
                            pageSize={5}></PatientProfilePatientNamedByContact>
                    </ClassicModalProvider>
                </MockedProvider>
            </MemoryRouter>
        );

        const tableData = container.getElementsByClassName('table-data');

        const conditionValue = await findByText(/condition-description/);
        expect(tableData[3]).toContainElement(conditionValue);

        const priorityLabel = await findByText(/Priority:/);
        expect(tableData[3]).toContainElement(priorityLabel);

        const priorityValue = await findByText(/priority-value/);
        expect(tableData[3]).toContainElement(priorityValue);
    });
});

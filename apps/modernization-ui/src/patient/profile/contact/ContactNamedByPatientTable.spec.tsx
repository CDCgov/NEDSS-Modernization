import { ContactNamedByPatientTable } from './ContactNamedByPatientTable';
import { render } from '@testing-library/react';
import { FindContactsNamedByPatientDocument } from 'generated/graphql/schema';

import { MockedProvider } from '@apollo/client/testing';

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const { container } = render(
            <MockedProvider addTypename={false}>
                <ContactNamedByPatientTable pageSize={3}></ContactNamedByPatientTable>
            </MockedProvider>
        );

        const tableHeader = container.getElementsByClassName('table-header');
        expect(tableHeader[0].innerHTML).toBe('Contact records (contacts named by patient)');

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[0].innerHTML).toBe('Date created');
        expect(tableHeads[1].innerHTML).toBe('Named by');
        expect(tableHeads[2].innerHTML).toBe('Date named');
        expect(tableHeads[3].innerHTML).toBe('Description');
        expect(tableHeads[4].innerHTML).toBe('Associated with');
        expect(tableHeads[5].innerHTML).toBe('Event #');
    });
});

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

    it('should display Not Available', async () => {
        const { findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <ContactNamedByPatientTable patient={'73'} pageSize={5}></ContactNamedByPatientTable>
            </MockedProvider>
        );

        expect(await findByText('Not Available')).toBeInTheDocument();
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
                                condition: 'condition-value',
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
            <MockedProvider mocks={[response]} addTypename={false}>
                <ContactNamedByPatientTable patient={'1823'} pageSize={5}></ContactNamedByPatientTable>
            </MockedProvider>
        );

        expect(await findByText('Showing 1 of 1')).toBeInTheDocument();

        const tableData = container.getElementsByClassName('table-data');

        expect(tableData[0]).toContainHTML('<span class="link">03/17/2023 <br /> 03:08 PM</span>');
        expect(tableData[1].innerHTML).toContain('Surma Singh');
        expect(tableData[2].innerHTML).toContain('01/17/2023');
        expect(tableData[3]).toContainHTML(
            '<b>condition-value</b><br/><b>Priority:</b> priority-value<br/><b>Disposition:</b> disposition-value<br/>'
        );
        expect(tableData[4]).toContainHTML(
            '<div><p class="margin-0 text-primary text-bold link" style="word-break: break-word;">CAS10000000GA01</p><p class="margin-0">associated-condition</p></div></span>'
        );
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
                                condition: 'condition-value',
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
            <MockedProvider mocks={[response]} addTypename={false}>
                <ContactNamedByPatientTable patient={'1823'} pageSize={5}></ContactNamedByPatientTable>
            </MockedProvider>
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
                                condition: 'condition-value',
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
            <MockedProvider mocks={[response]} addTypename={false}>
                <ContactNamedByPatientTable patient={'1823'} pageSize={5}></ContactNamedByPatientTable>
            </MockedProvider>
        );

        expect(await findByText('Showing 1 of 1')).toBeInTheDocument();

        const tableData = container.getElementsByClassName('table-data');

        expect(tableData[3]).toContainHTML('<b>condition-value</b><br/><b>Disposition:</b> disposition-value<br/>');
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
                                condition: 'condition-value',
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
            <MockedProvider mocks={[response]} addTypename={false}>
                <ContactNamedByPatientTable patient={'1823'} pageSize={5}></ContactNamedByPatientTable>
            </MockedProvider>
        );

        expect(await findByText('Showing 1 of 1')).toBeInTheDocument();

        const tableData = container.getElementsByClassName('table-data');

        expect(tableData[3]).toContainHTML('<b>condition-value</b><br/><b>Priority:</b> priority-value<br/>');
    });
});

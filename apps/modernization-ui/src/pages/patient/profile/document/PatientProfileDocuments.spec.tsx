import { PatientProfileDocuments } from './PatientProfileDocuments';
import { render } from '@testing-library/react';
import { FindDocumentsForPatientDocument } from 'generated/graphql/schema';

import { MockedProvider } from '@apollo/client/testing';
import { PageProvider } from 'page';

describe('when rendered', () => {
    it('should display sentence cased document headers', async () => {
        const { container } = render(
            <MockedProvider addTypename={false}>
                <PatientProfileDocuments nbsBase={'base'} pageSize={1}></PatientProfileDocuments>
            </MockedProvider>
        );

        const tableHeader = container.getElementsByClassName('table-header');
        expect(tableHeader[0].innerHTML).toBe('Documents');

        const tableHeads = container.getElementsByClassName('head-name');

        expect(tableHeads[0].innerHTML).toBe('Date received');
        expect(tableHeads[1].innerHTML).toBe('Type');
        expect(tableHeads[2].innerHTML).toBe('Sending facility');
        expect(tableHeads[3].innerHTML).toBe('Date reported');
        expect(tableHeads[4].innerHTML).toBe('Condition');
        expect(tableHeads[5].innerHTML).toBe('Associated with');
        expect(tableHeads[6].innerHTML).toBe('Event ID');
    });
});

describe('when documents are No data for a patient', () => {
    const response = {
        request: {
            query: FindDocumentsForPatientDocument,
            variables: {
                patient: '73',
                page: {
                    pageNumber: 0,
                    pageSize: 10
                }
            }
        },
        result: {
            data: {
                findDocumentsForPatient: {
                    content: [],
                    total: 0,
                    number: 0
                }
            }
        }
    };

    it('should display No data', async () => {
        const { findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientProfileDocuments patient={'73'} nbsBase={'base'} pageSize={10}></PatientProfileDocuments>
            </MockedProvider>
        );

        expect(await findByText('No data')).toBeInTheDocument();
    });
});

describe('when at least one document is available for a patient', () => {
    const response = {
        request: {
            query: FindDocumentsForPatientDocument,
            variables: {
                patient: '1823',
                page: {
                    pageNumber: 0,
                    pageSize: 10
                }
            }
        },
        result: {
            data: {
                findDocumentsForPatient: {
                    content: [
                        {
                            document: 'document-id',
                            receivedOn: '2021-10-07T15:01:10Z',
                            type: 'document-type-value',
                            sendingFacility: 'sending-facility-value',
                            reportedOn: '2021-09-21T17:04:11Z',
                            condition: 'condition-value',
                            event: 'event-value',
                            associatedWith: {
                                id: 'associated-id',
                                local: 'associated-local-value',
                                condition: 'associated-condition-value'
                            }
                        }
                    ],
                    total: 1,
                    number: 0
                }
            }
        }
    };

    it('should display the documents', async () => {
        const { container, findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientProfileDocuments patient={'1823'} nbsBase={'base'} pageSize={10}></PatientProfileDocuments>
            </MockedProvider>
        );

        expect(await findByText('Showing 1 of 1')).toBeInTheDocument();

        const tableData = container.getElementsByClassName('table-data');

        const dateCreated = await findByText(/10\/07\/2021/);

        expect(dateCreated).toHaveTextContent('10:01 AM');
        expect(dateCreated).toHaveAttribute(
            'href',
            'base/ViewFile1.do?ContextAction=DocumentIDOnEvents&nbsDocumentUid=document-id'
        );

        expect(tableData[0]).toContainElement(dateCreated);

        expect(tableData[1]).toHaveTextContent('document-type-value');
        expect(tableData[2]).toHaveTextContent('sending-facility-value');
        expect(tableData[3]).toHaveTextContent('09/21/2021');
        expect(tableData[4]).toHaveTextContent('condition-value');

        //  Associated With
        const association = await findByText('associated-local-value');

        expect(tableData[5]).toContainElement(association);

        expect(tableData[6]).toHaveTextContent('event-value');
    });
});

describe('when documents are available for a patient', () => {
    const response = {
        request: {
            query: FindDocumentsForPatientDocument,
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
                findDocumentsForPatient: {
                    content: [
                        {
                            document: 'document-id',
                            receivedOn: '2021-10-07T15:01:10Z',
                            type: 'document-type-value',
                            sendingFacility: 'sending-facility-value',
                            reportedOn: '2021-09-21T17:04:11Z',
                            condition: 'condition-value',
                            event: 'event-value',
                            associatedWith: {
                                id: 'associated-id',
                                local: 'associated-local-value',
                                condition: 'associated-condition-value'
                            }
                        }
                    ],
                    total: 6,
                    number: 1
                }
            }
        }
    };

    it('should display the documents', async () => {
        const { container, findByText } = render(
            <MockedProvider mocks={[response]} addTypename={false}>
                <PatientProfileDocuments patient={'1823'} nbsBase={'base'} pageSize={5}></PatientProfileDocuments>
            </MockedProvider>
        );

        expect(await findByText('Showing 1 of 6')).toBeInTheDocument();
    });
});

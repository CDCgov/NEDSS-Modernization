import { Investigation } from 'generated/graphql/schema';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { InvestigationSearchResultListItem } from './InvestigationSearchResultListItem';

describe('when showing an investigation search results', () => {
    it('should display the patient ID', () => {
        const result: Investigation = {
            relevance: 63.1,
            personParticipations: [
                {
                    shortId: 797,
                    personCd: 'PAT',
                    typeCd: 'SubjOfPHC'
                }
            ]
        };

        const { getByText } = render(
            <MemoryRouter>
                <InvestigationSearchResultListItem result={result} notificationStatusResolver={jest.fn()} />
            </MemoryRouter>
        );

        expect(getByText('Patient ID')).toBeInTheDocument();
        expect(getByText('797')).toBeInTheDocument();
    });

    it('should display the patient legal name', () => {
        const result: Investigation = {
            relevance: 63.1,
            personParticipations: [
                {
                    personCd: 'PAT',
                    typeCd: 'SubjOfPHC',
                    firstName: 'legal-first-name',
                    lastName: 'legal-last-name'
                }
            ]
        };

        const { getByText } = render(
            <MemoryRouter>
                <InvestigationSearchResultListItem
                    result={result}
                    notificationStatusResolver={jest.fn()}></InvestigationSearchResultListItem>
            </MemoryRouter>
        );

        expect(getByText('Legal name')).toBeInTheDocument();
        expect(getByText('legal-first-name legal-last-name')).toBeInTheDocument();
    });

    it('should display the patient date of birth when present', () => {
        const result: Investigation = {
            relevance: 63.1,
            personParticipations: [
                {
                    personCd: 'PAT',
                    typeCd: 'SubjOfPHC',
                    birthTime: '1976-12-01'
                }
            ]
        };

        const { getByText } = render(
            <MemoryRouter>
                <InvestigationSearchResultListItem
                    result={result}
                    notificationStatusResolver={jest.fn()}></InvestigationSearchResultListItem>
            </MemoryRouter>
        );

        expect(getByText('Date of birth')).toBeInTheDocument();
        expect(getByText('12/01/1976')).toBeInTheDocument();
    });

    it('should display the patient gender when present', () => {
        const result: Investigation = {
            relevance: 63.1,
            personParticipations: [
                {
                    personCd: 'PAT',
                    typeCd: 'SubjOfPHC',
                    currSexCd: 'F'
                }
            ]
        };

        const { getByText } = render(
            <MemoryRouter>
                <InvestigationSearchResultListItem
                    result={result}
                    notificationStatusResolver={jest.fn()}></InvestigationSearchResultListItem>
            </MemoryRouter>
        );

        expect(getByText('Current sex')).toBeInTheDocument();
        expect(getByText('Female')).toBeInTheDocument();
    });

    it('should display the condition under investigation', () => {
        const result: Investigation = {
            relevance: 63.1,
            id: '1063',
            cdDescTxt: 'investigated-condition',
            localId: 'local-id-value',
            personParticipations: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <InvestigationSearchResultListItem
                    result={result}
                    notificationStatusResolver={jest.fn()}></InvestigationSearchResultListItem>
            </MemoryRouter>
        );

        expect(getByText('Condition')).toBeInTheDocument();
        expect(getByText('investigated-condition')).toBeInTheDocument();
        expect(getByText('local-id-value')).toBeInTheDocument();
    });

    it('should display the investigation start date', () => {
        const result: Investigation = {
            relevance: 63.1,
            addTime: '2023-07-21T15:21:03.770Z',
            personParticipations: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <InvestigationSearchResultListItem
                    result={result}
                    notificationStatusResolver={jest.fn()}></InvestigationSearchResultListItem>
            </MemoryRouter>
        );

        expect(getByText('Start date')).toBeInTheDocument();
        expect(getByText('07/21/2023')).toBeInTheDocument();
    });

    it('should display the Jurisdiction', () => {
        const result: Investigation = {
            relevance: 63.1,
            jurisdictionCodeDescTxt: 'jurisdication name',
            personParticipations: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <InvestigationSearchResultListItem
                    result={result}
                    notificationStatusResolver={jest.fn()}></InvestigationSearchResultListItem>
            </MemoryRouter>
        );

        expect(getByText('Jurisdiction')).toBeInTheDocument();
        expect(getByText('jurisdication name')).toBeInTheDocument();
    });

    it('should display the investigator', () => {
        const result: Investigation = {
            relevance: 63.1,
            personParticipations: [
                {
                    typeCd: 'InvestgrOfPHC',
                    personCd: 'PRV',
                    firstName: 'investigator-first-name',
                    lastName: 'investigator-last-name'
                }
            ]
        };

        const { getByText } = render(
            <MemoryRouter>
                <InvestigationSearchResultListItem
                    result={result}
                    notificationStatusResolver={jest.fn()}></InvestigationSearchResultListItem>
            </MemoryRouter>
        );

        expect(getByText('Investigator')).toBeInTheDocument();
        expect(getByText('investigator-first-name investigator-last-name')).toBeInTheDocument();
    });

    it('should display the open investigation status', () => {
        const result: Investigation = {
            relevance: 63.1,
            investigationStatusCd: 'O',
            personParticipations: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <InvestigationSearchResultListItem
                    result={result}
                    notificationStatusResolver={jest.fn()}></InvestigationSearchResultListItem>
            </MemoryRouter>
        );

        expect(getByText('Status')).toBeInTheDocument();
        expect(getByText('open')).toBeInTheDocument();
    });

    it('should display the closed investigation status', () => {
        const result: Investigation = {
            relevance: 63.1,
            investigationStatusCd: 'C',
            personParticipations: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <InvestigationSearchResultListItem
                    result={result}
                    notificationStatusResolver={jest.fn()}></InvestigationSearchResultListItem>
            </MemoryRouter>
        );

        expect(getByText('Status')).toBeInTheDocument();
        expect(getByText('closed')).toBeInTheDocument();
    });

    it('should display the notification status when present', () => {
        const result: Investigation = {
            relevance: 63.1,
            notificationRecordStatusCd: 'notification-status',
            personParticipations: []
        };

        const notificationStatusResolver = jest.fn();
        notificationStatusResolver.mockReturnValue({
            value: 'notification-status',
            name: 'notification-status-display',
            label: 'notification-status-display'
        });

        const { getByText } = render(
            <MemoryRouter>
                <InvestigationSearchResultListItem
                    result={result}
                    notificationStatusResolver={notificationStatusResolver}></InvestigationSearchResultListItem>
            </MemoryRouter>
        );

        expect(getByText('Notification')).toBeInTheDocument();
        expect(getByText('notification-status-display')).toBeInTheDocument();

        expect(notificationStatusResolver).toBeCalledWith('notification-status');
    });

    it('should display the provided notification status unknown value is given', () => {
        const result: Investigation = {
            relevance: 63.1,
            notificationRecordStatusCd: 'unknown-value',
            personParticipations: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <InvestigationSearchResultListItem
                    result={result}
                    notificationStatusResolver={jest.fn()}></InvestigationSearchResultListItem>
            </MemoryRouter>
        );

        expect(getByText('Notification')).toBeInTheDocument();
        expect(getByText('unknown-value')).toBeInTheDocument();
    });
});

import { fireEvent, render } from '@testing-library/react';
import {
    CaseStatus,
    Gender,
    InvestigationEventDateType,
    InvestigationEventIdType,
    InvestigationFilter,
    InvestigationStatus,
    NotificationStatus,
    PregnancyStatus,
    ProcessingStatus,
    ReportingEntityType
} from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { InvestigationChips } from './InvestigationChips';

describe('InvestigationChips component tests', () => {
    it('should render condition chips', () => {
        const filter: InvestigationFilter = { conditions: ['condition1', 'condition2'] };
        let updatedFilter: InvestigationFilter = {};
        const { getAllByTestId } = render(
            <SearchCriteriaContext.Provider
                value={{
                    searchCriteria: {
                        states: [],
                        programAreas: [],
                        conditions: [{ id: 'condition1', conditionDescTxt: 'condition display' }],
                        jurisdictions: [],
                        userResults: [],
                        outbreaks: [],
                        ethnicities: [],
                        races: [],
                        identificationTypes: []
                    }
                }}>
                <InvestigationChips
                    filter={filter}
                    onChange={(e) => {
                        updatedFilter = e;
                    }}
                />
            </SearchCriteriaContext.Provider>
        );
        const chips = getAllByTestId('CONDITION-chip');
        expect(chips[0].children[0]).toHaveTextContent('CONDITION');
        expect(chips[0].children[2]).toHaveTextContent('condition display');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.conditions).toEqual(['condition2']);
    });

    it('should render program area chips', () => {
        const filter: InvestigationFilter = { programAreas: ['pa1', 'pa2'] };
        let updatedFilter: InvestigationFilter = {};
        const { getAllByTestId } = render(
            <SearchCriteriaContext.Provider
                value={{
                    searchCriteria: {
                        states: [],
                        programAreas: [{ id: 'pa1', progAreaDescTxt: 'program area display' }],
                        conditions: [],
                        jurisdictions: [],
                        userResults: [],
                        outbreaks: [],
                        ethnicities: [],
                        races: [],
                        identificationTypes: []
                    }
                }}>
                <InvestigationChips
                    filter={filter}
                    onChange={(e) => {
                        updatedFilter = e;
                    }}
                />
            </SearchCriteriaContext.Provider>
        );
        const chips = getAllByTestId('PROGRAM AREA-chip');
        expect(chips[0].children[0]).toHaveTextContent('PROGRAM AREA');
        expect(chips[0].children[2]).toHaveTextContent('program area display');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.programAreas).toEqual(['pa2']);
    });

    it('should render jurisdiction chips', () => {
        const filter: InvestigationFilter = { jurisdictions: ['jd1', 'jd2'] };
        let updatedFilter: InvestigationFilter = {};
        const { getAllByTestId } = render(
            <SearchCriteriaContext.Provider
                value={{
                    searchCriteria: {
                        states: [],
                        programAreas: [],
                        conditions: [],
                        jurisdictions: [{ id: 'jd1', codeDescTxt: 'jurisdiction display', typeCd: '' }],
                        userResults: [],
                        outbreaks: [],
                        ethnicities: [],
                        races: [],
                        identificationTypes: []
                    }
                }}>
                <InvestigationChips
                    filter={filter}
                    onChange={(e) => {
                        updatedFilter = e;
                    }}
                />
            </SearchCriteriaContext.Provider>
        );
        const chips = getAllByTestId('JURISDICTION-chip');
        expect(chips[0].children[0]).toHaveTextContent('JURISDICTION');
        expect(chips[0].children[2]).toHaveTextContent('jurisdiction display');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.jurisdictions).toEqual(['jd2']);
    });

    it('should render PREGNANCY chip', () => {
        const filter: InvestigationFilter = { pregnancyStatus: PregnancyStatus.Unknown };
        let updatedFilter: InvestigationFilter = {};
        const { getByTestId } = render(
            <InvestigationChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('PREGNANCY STATUS-chip');
        expect(chip.children[0]).toHaveTextContent('PREGNANCY STATUS');
        expect(chip.children[2]).toHaveTextContent('UNKNOWN');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.pregnancyStatus).not.toBeDefined();
    });

    it('should render event Id type chip', () => {
        const filter: InvestigationFilter = {
            eventId: { investigationEventType: InvestigationEventIdType.AbcsCaseId, id: '1234' }
        };
        let updatedFilter: InvestigationFilter = {};
        const { getByTestId } = render(
            <InvestigationChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('INVESTIGATION EVENT TYPE-chip');
        expect(chip.children[0]).toHaveTextContent('INVESTIGATION EVENT TYPE');
        expect(chip.children[2]).toHaveTextContent('ABCS CASE ID');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.eventId).not.toBeDefined();
    });

    it('should render event Id chip', () => {
        const filter: InvestigationFilter = {
            eventId: { investigationEventType: InvestigationEventIdType.AbcsCaseId, id: '1234' }
        };
        let updatedFilter: InvestigationFilter = {};
        const { getByTestId } = render(
            <InvestigationChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('EVENT ID-chip');
        expect(chip.children[0]).toHaveTextContent('EVENT ID');
        expect(chip.children[2]).toHaveTextContent('1234');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.eventId).not.toBeDefined();
    });

    it('should render date type chip', () => {
        const filter: InvestigationFilter = {
            eventDate: { type: InvestigationEventDateType.DateOfReport, from: '01/01/1990', to: '02/02/2000' }
        };
        let updatedFilter: InvestigationFilter = {};
        const { getByTestId } = render(
            <InvestigationChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('DATE TYPE-chip');
        expect(chip.children[0]).toHaveTextContent('DATE TYPE');
        expect(chip.children[2]).toHaveTextContent('DATE OF REPORT');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.eventDate).not.toBeDefined();
    });

    it('should render date from chip', () => {
        const filter: InvestigationFilter = {
            eventDate: { type: InvestigationEventDateType.DateOfReport, from: '01/01/1990', to: '02/02/2000' }
        };
        let updatedFilter: InvestigationFilter = {};
        const { getByTestId } = render(
            <InvestigationChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('FROM-chip');
        expect(chip.children[0]).toHaveTextContent('FROM');
        expect(chip.children[2]).toHaveTextContent('01/01/1990');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.eventDate).not.toBeDefined();
    });

    it('should render date to chip', () => {
        const filter: InvestigationFilter = {
            eventDate: { type: InvestigationEventDateType.DateOfReport, from: '01/01/1990', to: '02/02/2000' }
        };
        let updatedFilter: InvestigationFilter = {};
        const { getByTestId } = render(
            <InvestigationChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('TO-chip');
        expect(chip.children[0]).toHaveTextContent('TO');
        expect(chip.children[2]).toHaveTextContent('02/02/2000');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.eventDate).not.toBeDefined();
    });

    it('should render created by chips', () => {
        const filter: InvestigationFilter = { createdBy: 'nedssId' };
        let updatedFilter: InvestigationFilter = {};
        const { getAllByTestId } = render(
            <SearchCriteriaContext.Provider
                value={{
                    searchCriteria: {
                        states: [],
                        programAreas: [],
                        conditions: [],
                        jurisdictions: [],
                        userResults: [
                            { userId: 'userId', userFirstNm: 'firstNm', userLastNm: 'lastNm', nedssEntryId: 'nedssId' }
                        ],
                        outbreaks: [],
                        ethnicities: [],
                        races: [],
                        identificationTypes: []
                    }
                }}>
                <InvestigationChips
                    filter={filter}
                    onChange={(e) => {
                        updatedFilter = e;
                    }}
                />
            </SearchCriteriaContext.Provider>
        );
        const chips = getAllByTestId('CREATED BY-chip');
        expect(chips[0].children[0]).toHaveTextContent('CREATED BY');
        expect(chips[0].children[2]).toHaveTextContent('lastNm, firstNm');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.createdBy).not.toBeDefined();
    });

    it('should render updated by chips', () => {
        const filter: InvestigationFilter = { lastUpdatedBy: 'nedssId' };
        let updatedFilter: InvestigationFilter = {};
        const { getAllByTestId } = render(
            <SearchCriteriaContext.Provider
                value={{
                    searchCriteria: {
                        states: [],
                        programAreas: [],
                        conditions: [],
                        jurisdictions: [],
                        userResults: [
                            { userId: 'userId', userFirstNm: 'firstNm', userLastNm: 'lastNm', nedssEntryId: 'nedssId' }
                        ],
                        outbreaks: [],
                        ethnicities: [],
                        races: [],
                        identificationTypes: []
                    }
                }}>
                <InvestigationChips
                    filter={filter}
                    onChange={(e) => {
                        updatedFilter = e;
                    }}
                />
            </SearchCriteriaContext.Provider>
        );
        const chips = getAllByTestId('LAST UPDATED BY-chip');
        expect(chips[0].children[0]).toHaveTextContent('LAST UPDATED BY');
        expect(chips[0].children[2]).toHaveTextContent('lastNm, firstNm');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.lastUpdatedBy).not.toBeDefined();
    });

    it('should render facility/provider chip', () => {
        const filter: InvestigationFilter = {
            providerFacilitySearch: { entityType: ReportingEntityType.Facility, id: '4441' }
        };
        let updatedFilter: InvestigationFilter = {};
        const { getByTestId } = render(
            <InvestigationChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('ENTITY TYPE-chip');
        expect(chip.children[0]).toHaveTextContent('ENTITY TYPE');
        expect(chip.children[2]).toHaveTextContent('FACILITY');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.providerFacilitySearch).not.toBeDefined();
    });

    it('should render facility/provider Id chip', () => {
        const filter: InvestigationFilter = {
            providerFacilitySearch: { entityType: ReportingEntityType.Facility, id: '4441' }
        };
        let updatedFilter: InvestigationFilter = {};
        const { getByTestId } = render(
            <InvestigationChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('ENTITY ID-chip');
        expect(chip.children[0]).toHaveTextContent('ENTITY ID');
        expect(chip.children[2]).toHaveTextContent('4441');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.providerFacilitySearch).not.toBeDefined();
    });

    it('should render investigation status chip', () => {
        const filter: InvestigationFilter = {
            investigationStatus: InvestigationStatus.Closed
        };
        let updatedFilter: InvestigationFilter = {};
        const { getByTestId } = render(
            <InvestigationChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('INVESTIGATION STATUS-chip');
        expect(chip.children[0]).toHaveTextContent('INVESTIGATION STATUS');
        expect(chip.children[2]).toHaveTextContent('CLOSED');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.investigationStatus).not.toBeDefined();
    });

    it('should render outbreak chips', () => {
        const filter: InvestigationFilter = { outbreakNames: ['outbreak1', 'outbreak2'] };
        let updatedFilter: InvestigationFilter = {};
        const { getAllByTestId } = render(
            <SearchCriteriaContext.Provider
                value={{
                    searchCriteria: {
                        states: [],
                        programAreas: [],
                        conditions: [],
                        jurisdictions: [],
                        userResults: [],
                        outbreaks: [
                            { id: { code: 'outbreak1', codeSetNm: 'codeSetNm' }, codeShortDescTxt: 'outbreak display' }
                        ],
                        ethnicities: [],
                        races: [],
                        identificationTypes: []
                    }
                }}>
                <InvestigationChips
                    filter={filter}
                    onChange={(e) => {
                        updatedFilter = e;
                    }}
                />
            </SearchCriteriaContext.Provider>
        );
        const chips = getAllByTestId('OUTBREAK NAME-chip');
        expect(chips[0].children[0]).toHaveTextContent('OUTBREAK NAME');
        expect(chips[0].children[2]).toHaveTextContent('outbreak display');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.outbreakNames).toEqual(['outbreak2']);
    });

    it('should render case status chip', () => {
        const filter: InvestigationFilter = {
            caseStatuses: [CaseStatus.Confirmed, CaseStatus.Suspect]
        };
        let updatedFilter: InvestigationFilter = {};
        const { getAllByTestId } = render(
            <InvestigationChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chips = getAllByTestId('CASE STATUS-chip');
        expect(chips[0].children[0]).toHaveTextContent('CASE STATUS');
        expect(chips[0].children[2]).toHaveTextContent('CONFIRMED');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.caseStatuses).toEqual([CaseStatus.Suspect]);
    });

    it('should render processing status chip', () => {
        const filter: InvestigationFilter = {
            processingStatuses: [ProcessingStatus.AwaitingInterview, ProcessingStatus.Unassigned]
        };
        let updatedFilter: InvestigationFilter = {};
        const { getAllByTestId } = render(
            <InvestigationChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chips = getAllByTestId('PROCESSING STATUS-chip');
        expect(chips[0].children[0]).toHaveTextContent('PROCESSING STATUS');
        expect(chips[0].children[2]).toHaveTextContent('AWAITING INTERVIEW');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.processingStatuses).toEqual([ProcessingStatus.Unassigned]);
    });

    it('should render notification status chip', () => {
        const filter: InvestigationFilter = {
            notificationStatuses: [NotificationStatus.Approved, NotificationStatus.Unassigned]
        };
        let updatedFilter: InvestigationFilter = {};
        const { getAllByTestId } = render(
            <InvestigationChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chips = getAllByTestId('NOTIFICATION STATUS-chip');
        expect(chips[0].children[0]).toHaveTextContent('NOTIFICATION STATUS');
        expect(chips[0].children[2]).toHaveTextContent('APPROVED');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.notificationStatuses).toEqual([NotificationStatus.Unassigned]);
    });
});

import { fireEvent, render } from '@testing-library/react';
import {
    EntryMethod,
    EventStatus,
    LabReportFilter,
    LaboratoryEventIdType,
    LaboratoryReportEventDateType,
    LaboratoryReportStatus,
    PregnancyStatus,
    ProviderType,
    UserType
} from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { LabReportChips } from './LabReportChips';

describe('LabReportChips component tests', () => {
    it('should render program area chips', () => {
        const filter: LabReportFilter = { programAreas: ['pa1', 'pa2'] };
        let updatedFilter: LabReportFilter = {};
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
                <LabReportChips
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
        const filter: LabReportFilter = { jurisdictions: ['jd1', 'jd2'] };
        let updatedFilter: LabReportFilter = {};
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
                <LabReportChips
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
        const filter: LabReportFilter = { pregnancyStatus: PregnancyStatus.Unknown };
        let updatedFilter: LabReportFilter = {};
        const { getByTestId } = render(
            <LabReportChips
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
        const filter: LabReportFilter = {
            eventId: { labEventType: LaboratoryEventIdType.AccessionNumber, labEventId: '1234' }
        };
        let updatedFilter: LabReportFilter = {};
        const { getByTestId } = render(
            <LabReportChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('INVESTIGATION EVENT TYPE-chip');
        expect(chip.children[0]).toHaveTextContent('INVESTIGATION EVENT TYPE');
        expect(chip.children[2]).toHaveTextContent('ACCESSION NUMBER');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.eventId).not.toBeDefined();
    });

    it('should render event Id chip', () => {
        const filter: LabReportFilter = {
            eventId: { labEventType: LaboratoryEventIdType.AccessionNumber, labEventId: '1234' }
        };
        let updatedFilter: LabReportFilter = {};
        const { getByTestId } = render(
            <LabReportChips
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
        const filter: LabReportFilter = {
            eventDate: { type: LaboratoryReportEventDateType.DateOfReport, from: '01/01/1990', to: '02/02/2000' }
        };
        let updatedFilter: LabReportFilter = {};
        const { getByTestId } = render(
            <LabReportChips
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
        const filter: LabReportFilter = {
            eventDate: { type: LaboratoryReportEventDateType.DateOfReport, from: '01/01/1990', to: '02/02/2000' }
        };
        let updatedFilter: LabReportFilter = {};
        const { getByTestId } = render(
            <LabReportChips
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
        const filter: LabReportFilter = {
            eventDate: { type: LaboratoryReportEventDateType.DateOfReport, from: '01/01/1990', to: '02/02/2000' }
        };
        let updatedFilter: LabReportFilter = {};
        const { getByTestId } = render(
            <LabReportChips
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

    it('should render entry method chip', () => {
        const filter: LabReportFilter = {
            entryMethods: [EntryMethod.Electronic, EntryMethod.Manual]
        };
        let updatedFilter: LabReportFilter = {};
        const { getAllByTestId } = render(
            <LabReportChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chips = getAllByTestId('ENTRY METHOD-chip');
        expect(chips[0].children[0]).toHaveTextContent('ENTRY METHOD');
        expect(chips[0].children[2]).toHaveTextContent('ELECTRONIC');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.entryMethods).toEqual([EntryMethod.Manual]);
    });

    it('should render entered by chip', () => {
        const filter: LabReportFilter = {
            enteredBy: [UserType.External, UserType.Internal]
        };
        let updatedFilter: LabReportFilter = {};
        const { getAllByTestId } = render(
            <LabReportChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chips = getAllByTestId('ENTERED BY-chip');
        expect(chips[0].children[0]).toHaveTextContent('ENTERED BY');
        expect(chips[0].children[2]).toHaveTextContent('EXTERNAL');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.enteredBy).toEqual([UserType.Internal]);
    });

    it('should render event status chip', () => {
        const filter: LabReportFilter = {
            eventStatus: [EventStatus.New, EventStatus.Update]
        };
        let updatedFilter: LabReportFilter = {};
        const { getAllByTestId } = render(
            <LabReportChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chips = getAllByTestId('EVENT STATUS-chip');
        expect(chips[0].children[0]).toHaveTextContent('EVENT STATUS');
        expect(chips[0].children[2]).toHaveTextContent('NEW');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.eventStatus).toEqual([EventStatus.Update]);
    });

    it('should render processing status chip', () => {
        const filter: LabReportFilter = {
            processingStatus: [LaboratoryReportStatus.Processed, LaboratoryReportStatus.Unprocessed]
        };
        let updatedFilter: LabReportFilter = {};
        const { getAllByTestId } = render(
            <LabReportChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chips = getAllByTestId('PROCESSING STATUS-chip');
        expect(chips[0].children[0]).toHaveTextContent('PROCESSING STATUS');
        expect(chips[0].children[2]).toHaveTextContent('PROCESSED');
        fireEvent.click(chips[0].children[3]);
        expect(updatedFilter.processingStatus).toEqual([LaboratoryReportStatus.Unprocessed]);
    });

    it('should render created by chips', () => {
        const filter: LabReportFilter = { createdBy: 'nedssId' };
        let updatedFilter: LabReportFilter = {};
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
                <LabReportChips
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
        const filter: LabReportFilter = { lastUpdatedBy: 'nedssId' };
        let updatedFilter: LabReportFilter = {};
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
                <LabReportChips
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
        const filter: LabReportFilter = {
            providerSearch: { providerType: ProviderType.OrderingFacility, providerId: '4441' }
        };
        let updatedFilter: LabReportFilter = {};
        const { getByTestId } = render(
            <LabReportChips
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
        expect(updatedFilter.providerSearch).not.toBeDefined();
    });

    it('should render facility/provider Id chip', () => {
        const filter: LabReportFilter = {
            providerSearch: { providerType: ProviderType.OrderingFacility, providerId: '4441' }
        };
        let updatedFilter: LabReportFilter = {};
        const { getByTestId } = render(
            <LabReportChips
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
        expect(updatedFilter.providerSearch).not.toBeDefined();
    });

    it('should render resulted test chip', () => {
        const filter: LabReportFilter = {
            resultedTest: 'resultedTest'
        };
        let updatedFilter: LabReportFilter = {};
        const { getByTestId } = render(
            <LabReportChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('RESULTED TEST-chip');
        expect(chip.children[0]).toHaveTextContent('RESULTED TEST');
        expect(chip.children[2]).toHaveTextContent('resultedTest');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.resultedTest).not.toBeDefined();
    });

    it('should render coded result chip', () => {
        const filter: LabReportFilter = {
            codedResult: 'codedResult'
        };
        let updatedFilter: LabReportFilter = {};
        const { getByTestId } = render(
            <LabReportChips
                filter={filter}
                onChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('CODED RESULT-chip');
        expect(chip.children[0]).toHaveTextContent('CODED RESULT');
        expect(chip.children[2]).toHaveTextContent('codedResult');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.codedResult).not.toBeDefined();
    });
});

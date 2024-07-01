import { LabReportFilterEntry } from 'apps/search/laboratory-report/labReportFormTypes';
import { EntryMethod, EventStatus, LaboratoryReportStatus, UserType } from 'generated/graphql/schema';
import { initialEntry } from './initiaEntry';

describe('initialEntry', () => {
    let result: LabReportFilterEntry;

    beforeEach(() => {
        result = initialEntry();
    });

    it('should return an object', () => {
        expect(result).toBeInstanceOf(Object);
    });

    it('should have undefined codedResult', () => {
        expect(result.codedResult).toBeUndefined();
    });

    it('should have undefined createdBy', () => {
        expect(result.createdBy).toBeUndefined();
    });

    it('should have correct enteredBy values', () => {
        expect(result.enteredBy).toEqual([
            { name: UserType.External, value: UserType.External, label: UserType.External },
            { name: UserType.Internal, value: UserType.Internal, label: UserType.Internal }
        ]);
    });

    it('should have correct entryMethods values', () => {
        expect(result.entryMethods).toEqual([
            { name: EntryMethod.Electronic, value: EntryMethod.Electronic, label: EntryMethod.Electronic }
        ]);
    });

    it('should have undefined eventDate', () => {
        expect(result.eventDate).toBeUndefined();
    });

    it('should have undefined eventId', () => {
        expect(result.eventId).toBeUndefined();
    });

    it('should have correct eventStatus values', () => {
        expect(result.eventStatus).toEqual([{ name: EventStatus.New, value: EventStatus.New, label: EventStatus.New }]);
    });

    it('should have empty jurisdictions array', () => {
        expect(result.jurisdictions).toEqual([]);
    });

    it('should have undefined lastUpdatedBy', () => {
        expect(result.lastUpdatedBy).toBeUndefined();
    });

    it('should have undefined patientId', () => {
        expect(result.patientId).toBeUndefined();
    });

    it('should have undefined pregnancyStatus', () => {
        expect(result.pregnancyStatus).toBeUndefined();
    });

    it('should have correct processingStatus values', () => {
        expect(result.processingStatus).toEqual([
            {
                name: LaboratoryReportStatus.Unprocessed,
                value: LaboratoryReportStatus.Unprocessed,
                label: LaboratoryReportStatus.Unprocessed
            }
        ]);
    });

    it('should have empty programAreas array', () => {
        expect(result.programAreas).toEqual([]);
    });

    it('should have undefined providerSearch', () => {
        expect(result.providerSearch).toBeUndefined();
    });

    it('should have undefined resultedTest', () => {
        expect(result.resultedTest).toBeUndefined();
    });
});

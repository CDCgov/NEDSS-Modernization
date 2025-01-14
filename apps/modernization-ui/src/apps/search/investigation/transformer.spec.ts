import {
    CaseStatus,
    InvestigationEventDateType,
    InvestigationStatus,
    NotificationStatus,
    PregnancyStatus,
    ProcessingStatus
} from 'generated/graphql/schema';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { transformObject } from './transformer';

describe('transformObject', () => {
    it('should tranform with Conditions', () => {
        const input: InvestigationFilterEntry = {
            conditions: [
                { name: 'Condition One Name', label: 'Condition One Label', value: 'condition-one' },
                { name: 'Condition Two Name', label: 'Condition Two Label', value: 'condition-two' }
            ]
        };
        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                conditions: ['condition-one', 'condition-two']
            })
        );
    });

    it('should tranform with Program Areas', () => {
        const input: InvestigationFilterEntry = {
            programAreas: [
                { name: 'Area One Name', label: 'Area One Label', value: 'area-one' },
                { name: 'Area Two Name', label: 'Area Two Label', value: 'area-two' }
            ]
        };
        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                programAreas: ['area-one', 'area-two']
            })
        );
    });

    it('should tranform with Jurisdictions', () => {
        const input: InvestigationFilterEntry = {
            jurisdictions: [
                { name: 'Jurisdiction One Name', label: 'Jurisdiction One Label', value: '181' },
                { name: 'Jurisdiction Two Name', label: 'Jurisdiction Two Label', value: '239' }
            ]
        };
        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                jurisdictions: [181, 239]
            })
        );
    });

    it('should transform with Event Id', () => {
        const input: InvestigationFilterEntry = {
            identification: {
                type: { name: 'ID Type Name', label: 'ID Type Label', value: 'LAST_UPDATE_DATE' },
                value: 'identification-value'
            }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                eventId: {
                    id: 'identification-value',
                    investigationEventType: InvestigationEventDateType.LastUpdateDate
                }
            })
        );
    });

    it('should transform with Event date', () => {
        const input = {
            eventDate: {
                type: { name: 'Date Type Name', label: 'Date Type Label', value: 'DATE_OF_REPORT' },
                from: 'from-date',
                to: 'to-date'
            }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                eventDate: expect.objectContaining({
                    type: 'DATE_OF_REPORT',
                    from: 'from-date',
                    to: 'to-date'
                })
            })
        );
    });

    it('should transform with Created by', () => {
        const input = {
            createdBy: { name: 'Created Name', label: 'Created Label', value: 'created-value' }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                createdBy: 'created-value'
            })
        );
    });

    it('should transform with Last updated by', () => {
        const input = {
            updatedBy: { name: 'Updated Name', label: 'Updated Label', value: 'updated-value' }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                lastUpdatedBy: 'updated-value'
            })
        );
    });

    it('should transform with Investigator', () => {
        const input = {
            investigator: { name: 'Investigator Name', label: 'Investigator Label', value: 'investigator-value' }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                investigatorId: 'investigator-value'
            })
        );
    });

    it('should transform with Reporting facility', () => {
        const input = {
            reportingFacilityId: { name: 'st. joseph hospital', label: 'st. joseph hospital', value: '2341234' }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                reportingFacilityId: '2341234'
            })
        );
    });

    it('should transform with Reporting provider', () => {
        const input = {
            reportingProviderId: { name: 'st. joseph hospital', label: 'st. joseph hospital', value: '2341234' }
        };

        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                reportingProviderId: '2341234'
            })
        );
    });

    it('should tranform with Outbreaks', () => {
        const input: InvestigationFilterEntry = {
            outbreaks: [
                { name: 'Outbreak One Name', label: 'Outbreak One Label', value: 'outbreak-one' },
                { name: 'Outbreak Two Name', label: 'Outbreak Two Label', value: 'outbreak-two' }
            ]
        };
        const actual = transformObject(input);

        expect(actual).toEqual(
            expect.objectContaining({
                outbreakNames: ['outbreak-one', 'outbreak-two']
            })
        );
    });

    it('should include processing status when present', () => {
        const criteria = {
            processingStatuses: [
                { name: 'Closed Case Name', label: 'Closed Case Label', value: 'CLOSED_CASE' },
                { name: 'Open Case Name', label: 'Open Case Label', value: 'OPEN_CASE' }
            ]
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                processingStatuses: [ProcessingStatus.ClosedCase, ProcessingStatus.OpenCase]
            })
        );
    });

    it('should include notification status when present', () => {
        const criteria = {
            notificationStatuses: [
                { name: 'Rejected Name', label: 'Rejected Label', value: 'REJECTED' },
                { name: 'Completed Name', label: 'Completed Label', value: 'COMPLETED' }
            ]
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                notificationStatuses: [NotificationStatus.Rejected, NotificationStatus.Completed]
            })
        );
    });

    it('should include investigation status when present', () => {
        const criteria = {
            investigationStatus: { name: 'Open Name', label: 'Open Label', value: 'OPEN' }
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                investigationStatus: InvestigationStatus.Open
            })
        );
    });

    it('should include case statuses when present', () => {
        const criteria = {
            caseStatuses: [{ name: 'Confirmed', label: 'Confirmed', value: 'CONFIRMED' }]
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                caseStatuses: expect.arrayContaining([CaseStatus.Confirmed])
            })
        );
    });

    it('should include pregnancy status when present', () => {
        const criteria = {
            pregnancyStatus: { name: 'Unknown', label: 'Unknown', value: 'UNKNOWN' }
        };

        const result = transformObject(criteria);

        expect(result).toEqual(
            expect.objectContaining({
                pregnancyStatus: PregnancyStatus.Unknown
            })
        );
    });

    it('should handle an empty object', () => {
        const input = {};

        const expected = {};

        const result = transformObject(input);
        expect(result).toEqual(expected);
    });
});

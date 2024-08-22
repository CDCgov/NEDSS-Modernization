import { investigationTermsResolver } from './investigationTermsResolver';

describe('when an Investigation Seach contains General search criteria', () => {
    it('should resolve terms with Conditions', () => {
        const input = {
            conditions: [
                { name: 'Condition One Name', label: 'Condition One Label', value: 'condition-one' },
                { name: 'Condition Two Name', label: 'Condition Two Label', value: 'condition-two' }
            ]
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'conditions', title: 'CONDITION', name: 'Condition One Name', value: 'condition-one' },
                { source: 'conditions', title: 'CONDITION', name: 'Condition Two Name', value: 'condition-two' }
            ])
        );
    });

    it('should resolve terms with Program Areas', () => {
        const input = {
            programAreas: [
                { name: 'Area One Name', label: 'Area One Label', value: 'area-one' },
                { name: 'Area Two Name', label: 'Area Two Label', value: 'area-two' }
            ]
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'programAreas', title: 'PROGRAM AREA', name: 'Area One Name', value: 'area-one' },
                { source: 'programAreas', title: 'PROGRAM AREA', name: 'Area Two Name', value: 'area-two' }
            ])
        );
    });

    it('should resolve terms with Jurisdictions', () => {
        const input = {
            jurisdictions: [
                { name: 'Jurisdiction One Name', label: 'Jurisdiction One Label', value: 'jurisdiction-one' },
                { name: 'Jurisdiction Two Name', label: 'Jurisdiction Two Label', value: 'jurisdiction-two' }
            ]
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'jurisdictions',
                    title: 'JURISDICTION',
                    name: 'Jurisdiction One Name',
                    value: 'jurisdiction-one'
                },
                {
                    source: 'jurisdictions',
                    title: 'JURISDICTION',
                    name: 'Jurisdiction Two Name',
                    value: 'jurisdiction-two'
                }
            ])
        );
    });

    it('should resolve terms with Pregnancy test', () => {
        const input = {
            pregnancyStatus: { name: 'Pregnancy Name', label: 'Pregnancy Label', value: 'pregnancy-value' }
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'pregnancyStatus',
                    title: 'PREGNANCY STATUS',
                    name: 'Pregnancy Name',
                    value: 'pregnancy-value'
                }
            ])
        );
    });

    it('should resolve terms with Event id', () => {
        const input = {
            identification: {
                type: { name: 'ID Type Name', label: 'ID Type Label', value: 'id-type-value' },
                value: 'identification-value'
            }
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'identification.type',
                    title: 'EVENT ID TYPE',
                    name: 'ID Type Name',
                    value: 'id-type-value'
                },
                {
                    source: 'identification.value',
                    title: 'EVENT ID',
                    name: 'identification-value',
                    value: 'identification-value'
                }
            ])
        );
    });

    it('should resolve terms with Event date', () => {
        const input = {
            eventDate: {
                type: { name: 'Date Type Name', label: 'Date Type Label', value: 'date-type-value' },
                from: 'from-date',
                to: 'to-date'
            }
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'eventDate.type',
                    title: 'EVENT DATE TYPE',
                    name: 'Date Type Name',
                    value: 'date-type-value'
                },
                { source: 'eventDate.from', title: 'FROM', name: 'from-date', value: 'from-date' },
                { source: 'eventDate.to', title: 'TO', name: 'to-date', value: 'to-date' }
            ])
        );
    });

    it('should resolve terms with Created by', () => {
        const input = {
            createdBy: { name: 'Created Name', label: 'Created Label', value: 'created-value' }
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'createdBy', title: 'CREATED BY', name: 'Created Name', value: 'created-value' }
            ])
        );
    });

    it('should resolve terms with Last updated by', () => {
        const input = {
            updatedBy: { name: 'Updated Name', label: 'Updated Label', value: 'updated-value' }
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'updatedBy', title: 'LAST UPDATED BY', name: 'Updated Name', value: 'updated-value' }
            ])
        );
    });

    it('should resolve terms with Reporting facility', () => {
        const input = {
            reportingFacilityId: { name: 'st. joseph hospital', label: 'st. joseph hospital', value: '2341234' }
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'reportingFacilityId',
                    title: 'REPORTING FACILITY',
                    name: 'st. joseph hospital',
                    value: '2341234'
                }
            ])
        );
    });

    it('should resolve terms with Reporting provider', () => {
        const input = {
            reportingProviderId: { name: 'st. joseph hospital', label: 'st. joseph hospital', value: '2341234' }
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'reportingProviderId',
                    title: 'REPORTING PROVIDER',
                    name: 'st. joseph hospital',
                    value: '2341234'
                }
            ])
        );
    });
});

describe('when an Investigation Seach contains Investigation criteria', () => {
    it('should resolve terms with Investigation status', () => {
        const input = {
            investigationStatus: {
                name: 'Investigation status Name',
                label: 'Investigation status Label',
                value: 'investigation-status-value'
            }
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'investigationStatus',
                    title: 'INVESTIGATION STATUS',
                    name: 'Investigation status Name',
                    value: 'investigation-status-value'
                }
            ])
        );
    });

    it('should resolve terms with an Investigator', () => {
        const input = {
            investigator: {
                name: 'Investigator Name',
                label: 'Investigator Label',
                value: 'investigator-value'
            }
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'investigator',
                    title: 'INVESTIGATOR',
                    name: 'Investigator Name',
                    value: 'investigator-value'
                }
            ])
        );
    });

    it('should resolve terms with Outbreaks', () => {
        const input = {
            outbreaks: [
                { name: 'Outbreak One Name', label: 'Outbreak One Label', value: 'outbreak-one' },
                { name: 'Outbreak Two Name', label: 'Outbreak Two Label', value: 'outbreak-two' }
            ]
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'outbreaks',
                    title: 'OUTBREAK NAME',
                    name: 'Outbreak One Name',
                    value: 'outbreak-one'
                },
                {
                    source: 'outbreaks',
                    title: 'OUTBREAK NAME',
                    name: 'Outbreak Two Name',
                    value: 'outbreak-two'
                }
            ])
        );
    });

    it('should resolve terms with Case Status', () => {
        const input = {
            caseStatuses: [
                { name: 'Case Status One Name', label: 'Case Status One Label', value: 'case-status-one' },
                { name: 'Case Status Two Name', label: 'Case Status Two Label', value: 'case-status-two' }
            ]
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'caseStatuses',
                    title: 'CASE STATUS',
                    name: 'Case Status One Name',
                    value: 'case-status-one'
                },
                {
                    source: 'caseStatuses',
                    title: 'CASE STATUS',
                    name: 'Case Status Two Name',
                    value: 'case-status-two'
                }
            ])
        );
    });

    it('should resolve terms with Processing Status', () => {
        const input = {
            processingStatuses: [
                {
                    name: 'Processing Status One Name',
                    label: 'Processing Status One Label',
                    value: 'processing-status-one'
                },
                {
                    name: 'Processing Status Two Name',
                    label: 'Processing Status Two Label',
                    value: 'processing-status-two'
                }
            ]
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'processingStatuses',
                    title: 'PROCESSING STATUS',
                    name: 'Processing Status One Name',
                    value: 'processing-status-one'
                },
                {
                    source: 'processingStatuses',
                    title: 'PROCESSING STATUS',
                    name: 'Processing Status Two Name',
                    value: 'processing-status-two'
                }
            ])
        );
    });

    it('should resolve terms with Notification Status', () => {
        const input = {
            notificationStatuses: [
                {
                    name: 'Notification Status One Name',
                    label: 'Notification Status One Label',
                    value: 'notification-status-one'
                },
                {
                    name: 'Notification Status Two Name',
                    label: 'Processing Status Two Label',
                    value: 'notification-status-two'
                }
            ]
        };

        const actual = investigationTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'notificationStatuses',
                    title: 'NOTIFICATION STATUS',
                    name: 'Notification Status One Name',
                    value: 'notification-status-one'
                },
                {
                    source: 'notificationStatuses',
                    title: 'NOTIFICATION STATUS',
                    name: 'Notification Status Two Name',
                    value: 'notification-status-two'
                }
            ])
        );
    });
});

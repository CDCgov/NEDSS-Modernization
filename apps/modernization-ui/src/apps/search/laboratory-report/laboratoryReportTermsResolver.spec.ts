import { LabReportFilterEntry } from './labReportFormTypes';
import { laboratoryReportTermsResolver } from './laboratoryReportTermsResolver';

describe('when a Laboratory Seach contains General search criteria', () => {
    it('should resolve terms with Program Areas', () => {
        const input: LabReportFilterEntry = {
            programAreas: [
                { name: 'Area One Name', label: 'Area One Label', value: 'area-one' },
                { name: 'Area Two Name', label: 'Area Two Label', value: 'area-two' }
            ]
        };

        const actual = laboratoryReportTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'programAreas', title: 'PROGRAM AREA', name: 'Area One Name', value: 'area-one' },
                { source: 'programAreas', title: 'PROGRAM AREA', name: 'Area Two Name', value: 'area-two' }
            ])
        );
    });

    it('should resolve terms with Jurisdictions', () => {
        const input: LabReportFilterEntry = {
            jurisdictions: [
                { name: 'Jurisdiction One Name', label: 'Jurisdiction One Label', value: 'jurisdiction-one' },
                { name: 'Jurisdiction Two Name', label: 'Jurisdiction Two Label', value: 'jurisdiction-two' }
            ]
        };

        const actual = laboratoryReportTermsResolver(input);

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
        const input: LabReportFilterEntry = {
            pregnancyStatus: { name: 'Pregnancy Name', label: 'Pregnancy Label', value: 'pregnancy-value' }
        };

        const actual = laboratoryReportTermsResolver(input);

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
        const input: LabReportFilterEntry = {
            identification: {
                type: { name: 'ID Type Name', label: 'ID Type Label', value: 'id-type-value' },
                value: 'identification-value'
            }
        };

        const actual = laboratoryReportTermsResolver(input);

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
        const input: LabReportFilterEntry = {
            eventDate: {
                type: { name: 'Date Type Name', label: 'Date Type Label', value: 'date-type-value' },
                from: 'from-date',
                to: 'to-date'
            }
        };

        const actual = laboratoryReportTermsResolver(input);

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

    it('should resolve terms with Entry methods', () => {
        const input: LabReportFilterEntry = {
            entryMethods: [
                { name: 'Entry One Name', label: 'Entry One Label', value: 'entry-one-value' },
                { name: 'Entry Two Name', label: 'Entry Two Label', value: 'entry-two-value' }
            ]
        };

        const actual = laboratoryReportTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'entryMethods', title: 'ENTRY METHOD', name: 'Entry One Name', value: 'entry-one-value' },
                { source: 'entryMethods', title: 'ENTRY METHOD', name: 'Entry Two Name', value: 'entry-two-value' }
            ])
        );
    });

    it('should resolve terms with Entered by', () => {
        const input: LabReportFilterEntry = {
            enteredBy: [
                { name: 'Entered One Name', label: 'Entered One Label', value: 'entered-one-value' },
                { name: 'Entered Two Name', label: 'Entered Two Label', value: 'entered-two-value' }
            ]
        };

        const actual = laboratoryReportTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'enteredBy', title: 'ENTERED BY', name: 'Entered One Name', value: 'entered-one-value' },
                { source: 'enteredBy', title: 'ENTERED BY', name: 'Entered Two Name', value: 'entered-two-value' }
            ])
        );
    });

    it('should resolve terms with Event status', () => {
        const input: LabReportFilterEntry = {
            eventStatus: [
                { name: 'Event One Name', label: 'Event One Label', value: 'event-one-value' },
                { name: 'Event Two Name', label: 'Event Two Label', value: 'event-two-value' }
            ]
        };

        const actual = laboratoryReportTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'eventStatus', title: 'EVENT STATUS', name: 'Event One Name', value: 'event-one-value' },
                { source: 'eventStatus', title: 'EVENT STATUS', name: 'Event Two Name', value: 'event-two-value' }
            ])
        );
    });

    it('should resolve terms with Processing status', () => {
        const input: LabReportFilterEntry = {
            processingStatus: [
                { name: 'Processing One Name', label: 'Processing One Label', value: 'processing-one-value' },
                { name: 'Processing Two Name', label: 'Processing Two Label', value: 'processing-two-value' }
            ]
        };

        const actual = laboratoryReportTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    source: 'processingStatus',
                    title: 'PROCESSING STATUS',
                    name: 'Processing One Name',
                    value: 'processing-one-value'
                },
                {
                    source: 'processingStatus',
                    title: 'PROCESSING STATUS',
                    name: 'Processing Two Name',
                    value: 'processing-two-value'
                }
            ])
        );
    });

    it('should resolve terms with Created by', () => {
        const input: LabReportFilterEntry = {
            createdBy: { name: 'Created Name', label: 'Created Label', value: 'created-value' }
        };

        const actual = laboratoryReportTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'createdBy', title: 'CREATED BY', name: 'Created Name', value: 'created-value' }
            ])
        );
    });

    it('should resolve terms with Last updated by', () => {
        const input: LabReportFilterEntry = {
            updatedBy: { name: 'Updated Name', label: 'Updated Label', value: 'updated-value' }
        };

        const actual = laboratoryReportTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                { source: 'updatedBy', title: 'LAST UPDATED BY', name: 'Updated Name', value: 'updated-value' }
            ])
        );
    });

    it('should resolve terms with Ordering facility', () => {
        const input: LabReportFilterEntry = {
            orderingFacility: {
                name: 'Ordering facility Name',
                label: 'Ordering facility Label',
                value: 'ordering-facility-value'
            }
        };

        const actual = laboratoryReportTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    name: 'Ordering facility Name',
                    source: 'orderingFacility',
                    title: 'ORDERING FACILITY',
                    value: 'ordering-facility-value'
                }
            ])
        );
    });

    it('should resolve terms with Ordering provider', () => {
        const input: LabReportFilterEntry = {
            orderingProvider: {
                name: 'Ordering provider Name',
                label: 'Ordering provider Label',
                value: 'ordering-provider-value'
            }
        };

        const actual = laboratoryReportTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    name: 'Ordering provider Name',
                    source: 'orderingProvider',
                    title: 'ORDERING PROVIDER',
                    value: 'ordering-provider-value'
                }
            ])
        );
    });

    it('should resolve terms with Reporting facility', () => {
        const input: LabReportFilterEntry = {
            reportingFacility: {
                name: 'Reporting facility Name',
                label: 'Reporting facility Label',
                value: 'reporting-facility-value'
            }
        };

        const actual = laboratoryReportTermsResolver(input);

        expect(actual).toEqual(
            expect.arrayContaining([
                {
                    name: 'Reporting facility Name',
                    source: 'reportingFacility',
                    title: 'REPORTING FACILITY',
                    value: 'reporting-facility-value'
                }
            ])
        );
    });
});

describe('when a Laboratory Seach contains Lab report criteria', () => {
    it('should resolve terms with Coded result', () => {
        const input: LabReportFilterEntry = {
            codedResult: 'coded-result'
        };

        const actual = laboratoryReportTermsResolver(input);

        expect(actual).toEqual([
            { name: 'coded-result', source: 'codedResult', title: 'CODED RESULT', value: 'coded-result' }
        ]);
    });

    it('should resolve terms with Resulted test', () => {
        const input: LabReportFilterEntry = {
            resultedTest: 'resulted-value'
        };

        const actual = laboratoryReportTermsResolver(input);

        expect(actual).toEqual([
            { source: 'resultedTest', title: 'RESULTED TEST', name: 'resulted-value', value: 'resulted-value' }
        ]);
    });
});

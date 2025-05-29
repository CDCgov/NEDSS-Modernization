import { PatientLabReport } from 'generated';

export const mockPatientLabReports: PatientLabReport[] = [
    {
        eventId: 'ELR-2024-0123',
        receivedDate: '2024-05-28T00:00:00.000Z',
        processingDecision: 'Complete',
        facilityProviders: {
            reportingFacility: 'Central Medical Laboratory',
            orderingProvider: {
                prefix: 'Dr.',
                first: 'Sarah',
                last: 'Johnson',
                suffix: 'MD'
            },
            sendingFacility: 'Central Medical Laboratory'
        },
        collectedDate: '2024-05-27T14:30:00.000Z',
        testResults: [
            {
                resultedTest: 'COVID-19 PCR',
                codedResult: 'Negative',
                numericResult: '',
                units: '',
                highRange: '',
                lowRange: '',
                statusDetails: 'Final'
            }
        ],
        associatedInvestigation: {
            id: 'INV-2024-0456',
            condition: 'COVID-19',
            status: 'Closed'
        },
        programArea: 'Communicable Disease',
        jurisdiction: 'State Health Department',
        id: 1
    },
    {
        eventId: 'ELR-2024-0124',
        receivedDate: '2024-05-29T09:15:33.101Z',
        processingDecision: 'Complete',
        facilityProviders: {
            reportingFacility: 'Regional Health Center',
            orderingProvider: {
                prefix: 'Dr.',
                first: 'Michael',
                last: 'Chen',
                suffix: 'MD'
            },
            sendingFacility: 'Regional Health Center Lab'
        },
        collectedDate: '2024-05-28T08:45:00.000Z',
        testResults: [
            {
                resultedTest: 'Complete Blood Count',
                codedResult: 'Abnormal',
                numericResult: '15.2',
                units: 'g/dL',
                highRange: '16.0',
                lowRange: '12.0',
                statusDetails: 'Final'
            }
        ],
        associatedInvestigation: {
            id: 'INV-2024-0457',
            condition: 'Anemia',
            status: 'Active'
        },
        programArea: 'Clinical Assessment',
        jurisdiction: 'County Health Department',
        id: 2
    },
    {
        eventId: 'ELR-2024-0125',
        receivedDate: '2024-05-30T11:42:55.444Z',
        processingDecision: 'Complete',
        facilityProviders: {
            reportingFacility: 'Urban Medical Center',
            orderingProvider: {
                prefix: 'Dr.',
                first: 'Emily',
                last: 'Rodriguez',
                suffix: 'DO'
            },
            sendingFacility: 'Urban Medical Center'
        },
        collectedDate: '2024-05-30T10:15:00.000Z',
        testResults: [
            {
                resultedTest: 'Hepatitis B Surface Antigen',
                codedResult: 'Positive',
                numericResult: '',
                units: '',
                highRange: '',
                lowRange: '',
                statusDetails: 'Final'
            }
        ],
        associatedInvestigation: {
            id: 'INV-2024-0458',
            condition: 'Hepatitis B',
            status: 'Active'
        },
        programArea: 'Communicable Disease',
        jurisdiction: 'State Health Department',
        id: 3
    },
    {
        eventId: 'ELR-2024-0126',
        receivedDate: '2024-05-31T14:18:22.891Z',
        processingDecision: 'Complete',
        facilityProviders: {
            reportingFacility: 'Metro Reference Lab',
            orderingProvider: {
                prefix: 'Dr.',
                first: 'Robert',
                last: 'Smith',
                suffix: 'MD'
            },
            sendingFacility: 'Metro Reference Lab'
        },
        collectedDate: '2024-05-31T13:00:00.000Z',
        testResults: [
            {
                resultedTest: 'Lead Blood Level',
                codedResult: 'Elevated',
                numericResult: '12',
                units: 'µg/dL',
                highRange: '5',
                lowRange: '0',
                statusDetails: 'Final'
            }
        ],
        associatedInvestigation: {
            id: 'INV-2024-0459',
            condition: 'Lead Poisoning',
            status: 'Active'
        },
        programArea: 'Environmental Health',
        jurisdiction: 'City Health Department',
        id: 4
    },
    {
        eventId: 'ELR-2024-0127',
        receivedDate: '2024-06-01T08:55:17.333Z',
        processingDecision: 'Complete',
        facilityProviders: {
            reportingFacility: 'Community Health Clinic',
            orderingProvider: {
                prefix: 'Dr.',
                first: 'Lisa',
                last: 'Wong',
                suffix: 'MD'
            },
            sendingFacility: 'Community Health Clinic'
        },
        collectedDate: '2024-06-01T08:00:00.000Z',
        testResults: [
            {
                resultedTest: 'Salmonella Culture',
                codedResult: 'Positive',
                numericResult: '',
                units: '',
                highRange: '',
                lowRange: '',
                statusDetails: 'Final'
            }
        ],
        associatedInvestigation: {
            id: 'INV-2024-0460',
            condition: 'Salmonellosis',
            status: 'New'
        },
        programArea: 'Communicable Disease',
        jurisdiction: 'State Health Department',
        id: 5
    }
];

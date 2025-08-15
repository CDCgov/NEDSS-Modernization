const routing = [
    {
        path: '/patient/add',
        lazy: {
            Component: async () => (await import('./PatientDataEntryProvider')).PatientDataEntryProvider
        },
        children: [
            {
                index: true,
                lazy: {
                    Component: async () => (await import('./basic/AddPatientBasic')).AddPatientBasic
                }
            },
            {
                path: '/patient/add/extended',
                lazy: {
                    Component: async () => (await import('./extended/AddPatientExtended')).AddPatientExtended
                }
            }
        ]
    }
];

export { routing };

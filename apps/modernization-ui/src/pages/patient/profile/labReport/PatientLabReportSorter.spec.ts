import { Direction } from 'sorting';
import { sort } from './PatientLabReportSorter';
import { Headers } from './PatientLabReport';

describe('when sorting reports', () => {
    it('should default sorting to by Start date descending', () => {
        const reports = [
            {
                report: '10057339',
                receivedOn: new Date('2023-12-21T05:56:58.607Z'),
                reportingFacility: 'Piedmont Hospital',
                orderingProvider: 'Jane Cohn',
                orderingFacility: "St. Joseph's Hospital",
                collectedOn: new Date('2023-12-15T05:00:00.000Z'),
                results: [
                    {
                        test: 'C. botulinum toxin by mouse bioassay',
                        result: 'abnormally high'
                    },
                    {
                        test: 'Chlamydia - Result',
                        result: 'moderate growth'
                    }
                ],
                associatedWith: [
                    {
                        id: '10057347',
                        local: 'CAS10003000GA01',
                        condition: 'Hansen disease (Leprosy)'
                    }
                ],
                programArea: 'ARBO',
                jurisdiction: 'HTT',
                event: 'ZBS10003000GA01'
            },
            {
                report: '10055332',
                receivedOn: new Date('2023-12-13T23:53:24.393Z'),
                reportingFacility: 'Amory University Hospital',
                orderingProvider: null,
                orderingFacility: null,
                collectedOn: new Date('2023-11-26T05:00:00.000Z'),
                results: [
                    {
                        test: 'Hepatitis B virus (HBV)',
                        result: 'abnormal presence of'
                    }
                ],
                associatedWith: [],
                programArea: 'BMIRD',
                jurisdiction: 'ATL',
                event: 'OBS10001000GA01'
            },
            {
                report: '10056345',
                receivedOn: new Date('2023-12-14T06:38:07.997Z'),
                reportingFacility: 'Emory University Hospital',
                orderingProvider: null,
                orderingFacility: null,
                collectedOn: new Date('1970-01-01T05:00:00.000Z'),
                results: [
                    {
                        test: 'Acanthamoeba antibody, CSF',
                        result: 'abnormal result'
                    }
                ],
                associatedWith: [
                    {
                        id: '10056421',
                        local: 'CAS10002000GA01',
                        condition: 'Dengue'
                    }
                ],
                programArea: 'HEP',
                jurisdiction: 'ZAP',
                event: 'ABS10002005GA01'
            }
        ];
        const actual = sort(reports, {});

        expect(actual).toEqual([
            expect.objectContaining({ report: '10057339' }),
            expect.objectContaining({ report: '10056345' }),
            expect.objectContaining({ report: '10055332' })
        ]);
    });
});

describe('when sorting reports by receivedOn date', () => {
    const reports = [
        {
            report: '10057339',
            receivedOn: new Date('2023-12-21T05:56:58.607Z'),
            reportingFacility: 'Piedmont Hospital',
            orderingProvider: 'Jane Cohn',
            orderingFacility: "St. Joseph's Hospital",
            collectedOn: new Date('2023-12-15T05:00:00.000Z'),
            results: [
                {
                    test: 'C. botulinum toxin by mouse bioassay',
                    result: 'abnormally high'
                },
                {
                    test: 'Chlamydia - Result',
                    result: 'moderate growth'
                }
            ],
            associatedWith: [
                {
                    id: '10057347',
                    local: 'CAS10003000GA01',
                    condition: 'Hansen disease (Leprosy)'
                }
            ],
            programArea: 'ARBO',
            jurisdiction: 'HTT',
            event: 'ZBS10003000GA01'
        },
        {
            report: '10055332',
            receivedOn: new Date('2023-12-13T23:53:24.393Z'),
            reportingFacility: 'Amory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('2023-11-26T05:00:00.000Z'),
            results: [
                {
                    test: 'Hepatitis B virus (HBV)',
                    result: 'abnormal presence of'
                }
            ],
            associatedWith: [],
            programArea: 'BMIRD',
            jurisdiction: 'ATL',
            event: 'OBS10001000GA01'
        },
        {
            report: '10056345',
            receivedOn: new Date('2023-12-14T06:38:07.997Z'),
            reportingFacility: 'Emory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('1970-01-01T05:00:00.000Z'),
            results: [
                {
                    test: 'Acanthamoeba antibody, CSF',
                    result: 'abnormal result'
                }
            ],
            associatedWith: [
                {
                    id: '10056421',
                    local: 'CAS10002000GA01',
                    condition: 'Dengue'
                }
            ],
            programArea: 'HEP',
            jurisdiction: 'ZAP',
            event: 'ABS10002005GA01'
        }
    ];
    it('should sort ascending', () => {
        const actual = sort(reports, { name: Headers.DateReceived, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10055332' }),
            expect.objectContaining({ report: '10056345' }),
            expect.objectContaining({ report: '10057339' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(reports, { name: Headers.DateReceived, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10057339' }),
            expect.objectContaining({ report: '10056345' }),
            expect.objectContaining({ report: '10055332' })
        ]);
    });
});

describe('when sorting reports by DateCollected', () => {
    const reports = [
        {
            report: '10057339',
            receivedOn: new Date('2023-12-21T05:56:58.607Z'),
            reportingFacility: 'Piedmont Hospital',
            orderingProvider: 'Jane Cohn',
            orderingFacility: "St. Joseph's Hospital",
            collectedOn: new Date('2023-12-15T05:00:00.000Z'),
            results: [
                {
                    test: 'C. botulinum toxin by mouse bioassay',
                    result: 'abnormally high'
                },
                {
                    test: 'Chlamydia - Result',
                    result: 'moderate growth'
                }
            ],
            associatedWith: [
                {
                    id: '10057347',
                    local: 'CAS10003000GA01',
                    condition: 'Hansen disease (Leprosy)'
                }
            ],
            programArea: 'ARBO',
            jurisdiction: 'HTT',
            event: 'ZBS10003000GA01'
        },
        {
            report: '10055332',
            receivedOn: new Date('2023-12-13T23:53:24.393Z'),
            reportingFacility: 'Amory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('2023-11-26T05:00:00.000Z'),
            results: [
                {
                    test: 'Hepatitis B virus (HBV)',
                    result: 'abnormal presence of'
                }
            ],
            associatedWith: [],
            programArea: 'BMIRD',
            jurisdiction: 'ATL',
            event: 'OBS10001000GA01'
        },
        {
            report: '10056345',
            receivedOn: new Date('2023-12-14T06:38:07.997Z'),
            reportingFacility: 'Emory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('1970-01-01T05:00:00.000Z'),
            results: [
                {
                    test: 'Acanthamoeba antibody, CSF',
                    result: 'abnormal result'
                }
            ],
            associatedWith: [
                {
                    id: '10056421',
                    local: 'CAS10002000GA01',
                    condition: 'Dengue'
                }
            ],
            programArea: 'HEP',
            jurisdiction: 'ZAP',
            event: 'ABS10002005GA01'
        }
    ];
    it('should sort ascending', () => {
        const actual = sort(reports, { name: Headers.DateCollected, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10056345' }),
            expect.objectContaining({ report: '10055332' }),
            expect.objectContaining({ report: '10057339' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(reports, { name: Headers.DateCollected, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10057339' }),
            expect.objectContaining({ report: '10055332' }),
            expect.objectContaining({ report: '10056345' })
        ]);
    });
});

describe('when sorting reports by TestResults', () => {
    const reports = [
        {
            report: '10057339',
            receivedOn: new Date('2023-12-21T05:56:58.607Z'),
            reportingFacility: 'Piedmont Hospital',
            orderingProvider: 'Jane Cohn',
            orderingFacility: "St. Joseph's Hospital",
            collectedOn: new Date('2023-12-15T05:00:00.000Z'),
            results: [
                {
                    test: 'C. botulinum toxin by mouse bioassay',
                    result: 'abnormally high'
                },
                {
                    test: 'Chlamydia - Result',
                    result: 'moderate growth'
                }
            ],
            associatedWith: [
                {
                    id: '10057347',
                    local: 'CAS10003000GA01',
                    condition: 'Hansen disease (Leprosy)'
                }
            ],
            programArea: 'ARBO',
            jurisdiction: 'HTT',
            event: 'ZBS10003000GA01'
        },
        {
            report: '10055332',
            receivedOn: new Date('2023-12-13T23:53:24.393Z'),
            reportingFacility: 'Amory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('2023-11-26T05:00:00.000Z'),
            results: [
                {
                    test: 'Hepatitis B virus (HBV)',
                    result: 'abnormal presence of'
                }
            ],
            associatedWith: [],
            programArea: 'BMIRD',
            jurisdiction: 'ATL',
            event: 'OBS10001000GA01'
        },
        {
            report: '10056345',
            receivedOn: new Date('2023-12-14T06:38:07.997Z'),
            reportingFacility: 'Emory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('1970-01-01T05:00:00.000Z'),
            results: [
                {
                    test: 'Acanthamoeba antibody, CSF',
                    result: 'abnormal result'
                }
            ],
            associatedWith: [
                {
                    id: '10056421',
                    local: 'CAS10002000GA01',
                    condition: 'Dengue'
                }
            ],
            programArea: 'HEP',
            jurisdiction: 'ZAP',
            event: 'ABS10002005GA01'
        }
    ];
    it('should sort ascending', () => {
        const actual = sort(reports, { name: Headers.TestResults, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10056345' }),
            expect.objectContaining({ report: '10057339' }),
            expect.objectContaining({ report: '10055332' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(reports, { name: Headers.TestResults, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10055332' }),
            expect.objectContaining({ report: '10057339' }),
            expect.objectContaining({ report: '10056345' })
        ]);
    });
});

describe('when sorting reports by AssociatedWith', () => {
    const reports = [
        {
            report: '10057339',
            receivedOn: new Date('2023-12-21T05:56:58.607Z'),
            reportingFacility: 'Piedmont Hospital',
            orderingProvider: 'Jane Cohn',
            orderingFacility: "St. Joseph's Hospital",
            collectedOn: new Date('2023-12-15T05:00:00.000Z'),
            results: [
                {
                    test: 'C. botulinum toxin by mouse bioassay',
                    result: 'abnormally high'
                },
                {
                    test: 'Chlamydia - Result',
                    result: 'moderate growth'
                }
            ],
            associatedWith: [
                {
                    id: '10057347',
                    local: 'CAS10003000GA01',
                    condition: 'Hansen disease (Leprosy)'
                }
            ],
            programArea: 'ARBO',
            jurisdiction: 'HTT',
            event: 'ZBS10003000GA01'
        },
        {
            report: '10055332',
            receivedOn: new Date('2023-12-13T23:53:24.393Z'),
            reportingFacility: 'Amory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('2023-11-26T05:00:00.000Z'),
            results: [
                {
                    test: 'Hepatitis B virus (HBV)',
                    result: 'abnormal presence of'
                }
            ],
            associatedWith: [],
            programArea: 'BMIRD',
            jurisdiction: 'ATL',
            event: 'OBS10001000GA01'
        },
        {
            report: '10056345',
            receivedOn: new Date('2023-12-14T06:38:07.997Z'),
            reportingFacility: 'Emory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('1970-01-01T05:00:00.000Z'),
            results: [
                {
                    test: 'Acanthamoeba antibody, CSF',
                    result: 'abnormal result'
                }
            ],
            associatedWith: [
                {
                    id: '10056421',
                    local: 'CAS10002000GA01',
                    condition: 'Dengue'
                }
            ],
            programArea: 'HEP',
            jurisdiction: 'ZAP',
            event: 'ABS10002005GA01'
        }
    ];
    it('should sort ascending', () => {
        const actual = sort(reports, { name: Headers.AssociatedWith, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10055332' }),
            expect.objectContaining({ report: '10056345' }),
            expect.objectContaining({ report: '10057339' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(reports, { name: Headers.AssociatedWith, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10057339' }),
            expect.objectContaining({ report: '10056345' }),
            expect.objectContaining({ report: '10055332' })
        ]);
    });
});

describe('when sorting reports by ProgramArea', () => {
    const reports = [
        {
            report: '10057339',
            receivedOn: new Date('2023-12-21T05:56:58.607Z'),
            reportingFacility: 'Piedmont Hospital',
            orderingProvider: 'Jane Cohn',
            orderingFacility: "St. Joseph's Hospital",
            collectedOn: new Date('2023-12-15T05:00:00.000Z'),
            results: [
                {
                    test: 'C. botulinum toxin by mouse bioassay',
                    result: 'abnormally high'
                },
                {
                    test: 'Chlamydia - Result',
                    result: 'moderate growth'
                }
            ],
            associatedWith: [
                {
                    id: '10057347',
                    local: 'CAS10003000GA01',
                    condition: 'Hansen disease (Leprosy)'
                }
            ],
            programArea: 'ARBO',
            jurisdiction: 'HTT',
            event: 'ZBS10003000GA01'
        },
        {
            report: '10055332',
            receivedOn: new Date('2023-12-13T23:53:24.393Z'),
            reportingFacility: 'Amory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('2023-11-26T05:00:00.000Z'),
            results: [
                {
                    test: 'Hepatitis B virus (HBV)',
                    result: 'abnormal presence of'
                }
            ],
            associatedWith: [],
            programArea: 'BMIRD',
            jurisdiction: 'ATL',
            event: 'OBS10001000GA01'
        },
        {
            report: '10056345',
            receivedOn: new Date('2023-12-14T06:38:07.997Z'),
            reportingFacility: 'Emory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('1970-01-01T05:00:00.000Z'),
            results: [
                {
                    test: 'Acanthamoeba antibody, CSF',
                    result: 'abnormal result'
                }
            ],
            associatedWith: [
                {
                    id: '10056421',
                    local: 'CAS10002000GA01',
                    condition: 'Dengue'
                }
            ],
            programArea: 'HEP',
            jurisdiction: 'ZAP',
            event: 'ABS10002005GA01'
        }
    ];
    it('should sort ascending', () => {
        const actual = sort(reports, { name: Headers.ProgramArea, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10057339' }),
            expect.objectContaining({ report: '10055332' }),
            expect.objectContaining({ report: '10056345' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(reports, { name: Headers.ProgramArea, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10056345' }),
            expect.objectContaining({ report: '10055332' }),
            expect.objectContaining({ report: '10057339' })
        ]);
    });
});

describe('when sorting reports by Jurisdiction', () => {
    const reports = [
        {
            report: '10057339',
            receivedOn: new Date('2023-12-21T05:56:58.607Z'),
            reportingFacility: 'Piedmont Hospital',
            orderingProvider: 'Jane Cohn',
            orderingFacility: "St. Joseph's Hospital",
            collectedOn: new Date('2023-12-15T05:00:00.000Z'),
            results: [
                {
                    test: 'C. botulinum toxin by mouse bioassay',
                    result: 'abnormally high'
                },
                {
                    test: 'Chlamydia - Result',
                    result: 'moderate growth'
                }
            ],
            associatedWith: [
                {
                    id: '10057347',
                    local: 'CAS10003000GA01',
                    condition: 'Hansen disease (Leprosy)'
                }
            ],
            programArea: 'ARBO',
            jurisdiction: 'HTT',
            event: 'ZBS10003000GA01'
        },
        {
            report: '10055332',
            receivedOn: new Date('2023-12-13T23:53:24.393Z'),
            reportingFacility: 'Amory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('2023-11-26T05:00:00.000Z'),
            results: [
                {
                    test: 'Hepatitis B virus (HBV)',
                    result: 'abnormal presence of'
                }
            ],
            associatedWith: [],
            programArea: 'BMIRD',
            jurisdiction: 'ATL',
            event: 'OBS10001000GA01'
        },
        {
            report: '10056345',
            receivedOn: new Date('2023-12-14T06:38:07.997Z'),
            reportingFacility: 'Emory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('1970-01-01T05:00:00.000Z'),
            results: [
                {
                    test: 'Acanthamoeba antibody, CSF',
                    result: 'abnormal result'
                }
            ],
            associatedWith: [
                {
                    id: '10056421',
                    local: 'CAS10002000GA01',
                    condition: 'Dengue'
                }
            ],
            programArea: 'HEP',
            jurisdiction: 'ZAP',
            event: 'ABS10002005GA01'
        }
    ];
    it('should sort ascending', () => {
        const actual = sort(reports, { name: Headers.Jurisdiction, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10055332' }),
            expect.objectContaining({ report: '10057339' }),
            expect.objectContaining({ report: '10056345' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(reports, { name: Headers.Jurisdiction, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10056345' }),
            expect.objectContaining({ report: '10057339' }),
            expect.objectContaining({ report: '10055332' })
        ]);
    });
});

describe('when sorting reports by EventID', () => {
    const reports = [
        {
            report: '10057339',
            receivedOn: new Date('2023-12-21T05:56:58.607Z'),
            reportingFacility: 'Piedmont Hospital',
            orderingProvider: 'Jane Cohn',
            orderingFacility: "St. Joseph's Hospital",
            collectedOn: new Date('2023-12-15T05:00:00.000Z'),
            results: [
                {
                    test: 'C. botulinum toxin by mouse bioassay',
                    result: 'abnormally high'
                },
                {
                    test: 'Chlamydia - Result',
                    result: 'moderate growth'
                }
            ],
            associatedWith: [
                {
                    id: '10057347',
                    local: 'CAS10003000GA01',
                    condition: 'Hansen disease (Leprosy)'
                }
            ],
            programArea: 'ARBO',
            jurisdiction: 'HTT',
            event: 'ZBS10003000GA01'
        },
        {
            report: '10055332',
            receivedOn: new Date('2023-12-13T23:53:24.393Z'),
            reportingFacility: 'Amory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('2023-11-26T05:00:00.000Z'),
            results: [
                {
                    test: 'Hepatitis B virus (HBV)',
                    result: 'abnormal presence of'
                }
            ],
            associatedWith: [],
            programArea: 'BMIRD',
            jurisdiction: 'ATL',
            event: 'OBS10001000GA01'
        },
        {
            report: '10056345',
            receivedOn: new Date('2023-12-14T06:38:07.997Z'),
            reportingFacility: 'Emory University Hospital',
            orderingProvider: null,
            orderingFacility: null,
            collectedOn: new Date('1970-01-01T05:00:00.000Z'),
            results: [
                {
                    test: 'Acanthamoeba antibody, CSF',
                    result: 'abnormal result'
                }
            ],
            associatedWith: [
                {
                    id: '10056421',
                    local: 'CAS10002000GA01',
                    condition: 'Dengue'
                }
            ],
            programArea: 'HEP',
            jurisdiction: 'ZAP',
            event: 'ABS10002005GA01'
        }
    ];
    it('should sort ascending', () => {
        const actual = sort(reports, { name: Headers.EventID, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10056345' }),
            expect.objectContaining({ report: '10055332' }),
            expect.objectContaining({ report: '10057339' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(reports, { name: Headers.EventID, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ report: '10057339' }),
            expect.objectContaining({ report: '10055332' }),
            expect.objectContaining({ report: '10056345' })
        ]);
    });
});

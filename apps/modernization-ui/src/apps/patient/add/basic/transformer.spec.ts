import { BasicNewPatientEntry } from './entry';
import { transformer } from './transformer';

describe('when transforming entered basic patient data', () => {
    it('should transform general information to a format accepted by the API', () => {
        const entry: BasicNewPatientEntry = {
            administrative: { asOf: '04/13/2017', comment: 'entered-value' }
        };

        const actual = transformer(entry);

        expect(actual).toEqual(
            expect.objectContaining({ administrative: { asOf: '04/13/2017', comment: 'entered-value' } })
        );
    });

    describe('that contains Name information ', () => {
        it('should transform last name to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '11/07/2019' },
                name: {
                    last: 'last-value'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    names: expect.arrayContaining([
                        {
                            asOf: '11/07/2019',
                            type: 'L',
                            last: 'last-value'
                        }
                    ])
                })
            );
        });

        it('should transform first name to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '11/07/2019' },
                name: {
                    first: 'first-value'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    names: expect.arrayContaining([
                        {
                            asOf: '11/07/2019',
                            type: 'L',
                            first: 'first-value'
                        }
                    ])
                })
            );
        });

        it('should transform middle name to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '11/07/2019' },
                name: {
                    middle: 'middle-value'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    names: expect.arrayContaining([
                        {
                            asOf: '11/07/2019',
                            type: 'L',
                            middle: 'middle-value'
                        }
                    ])
                })
            );
        });

        it('should transform suffix to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '11/07/2019' },
                name: {
                    suffix: { value: 'suffix-value', name: 'suffix-name' }
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    names: expect.arrayContaining([
                        {
                            asOf: '11/07/2019',
                            type: 'L',
                            suffix: 'suffix-value'
                        }
                    ])
                })
            );
        });
    });

    describe('that contains Other information ', () => {
        it('should transform birth values to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                other: {
                    bornOn: '08/05/1990',
                    birthSex: { value: 'birth-sex-value', name: 'birth-sex-name' }
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    birth: expect.objectContaining({ asOf: '04/13/2017', bornOn: '08/05/1990', sex: 'birth-sex-value' })
                })
            );
        });

        it('should transform gender values to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                other: {
                    currentSex: { value: 'current-sex-value', name: 'current-sex-name' }
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    gender: expect.objectContaining({ asOf: '04/13/2017', current: 'current-sex-value' })
                })
            );
        });

        it('should transform mortality values to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                other: {
                    deceased: { value: 'deceased-value', name: 'deceased-name' },
                    deceasedOn: '05/08/1790'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    mortality: expect.objectContaining({
                        asOf: '04/13/2017',
                        deceasedOn: '05/08/1790',
                        deceased: 'deceased-value'
                    })
                })
            );
        });

        it('should transform general values to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                other: {
                    maritalStatus: { value: 'marital-status-value', name: 'marital-status-name' },
                    stateHIVCase: 'state-hiv-case-value'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    general: expect.objectContaining({
                        asOf: '04/13/2017',
                        maritalStatus: 'marital-status-value',
                        stateHIVCase: 'state-hiv-case-value'
                    })
                })
            );
        });
    });

    describe('that contains Address', () => {
        it('should transform Street address 1 to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                address: {
                    address1: 'address-1-value'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    addresses: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'H',
                            use: 'H',
                            address1: 'address-1-value'
                        })
                    ])
                })
            );
        });

        it('should transform Street address 2 to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                address: {
                    address2: 'address-2-value'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    addresses: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'H',
                            use: 'H',

                            address2: 'address-2-value'
                        })
                    ])
                })
            );
        });

        it('should transform city to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                address: {
                    city: 'city-value'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    addresses: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'H',
                            use: 'H',
                            city: 'city-value'
                        })
                    ])
                })
            );
        });

        it('should transform country to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                address: {
                    county: { value: 'county-value', name: 'county-name' }
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    addresses: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'H',
                            use: 'H',
                            county: 'county-value'
                        })
                    ])
                })
            );
        });

        it('should transform zipcode to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                address: {
                    zipcode: 'zipcode-value'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    addresses: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'H',
                            use: 'H',
                            zipcode: 'zipcode-value'
                        })
                    ])
                })
            );
        });

        it('should transform state to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                address: {
                    state: { value: 'state-value', name: 'state-name' }
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    addresses: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'H',
                            use: 'H',
                            state: 'state-value'
                        })
                    ])
                })
            );
        });

        it('should transform country to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                address: {
                    country: { value: 'country-value', name: 'country-name' }
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    addresses: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'H',
                            use: 'H',
                            country: 'country-value'
                        })
                    ])
                })
            );
        });

        it('should transform census tract to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                address: {
                    censusTract: 'census-tract-value'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    addresses: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'H',
                            use: 'H',
                            censusTract: 'census-tract-value'
                        })
                    ])
                })
            );
        });
    });

    describe('that contains Phone & email ', () => {
        it('should transform home phone to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                phoneEmail: {
                    home: 'home-value'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    phoneEmails: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'PH',
                            use: 'H',
                            phoneNumber: 'home-value'
                        })
                    ])
                })
            );
        });

        it('should transform work phone to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                phoneEmail: {
                    work: { phone: 'work-phone-value' }
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    phoneEmails: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'PH',
                            use: 'WP',
                            phoneNumber: 'work-phone-value'
                        })
                    ])
                })
            );
        });

        it('should transform work phone with extension to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                phoneEmail: {
                    work: { phone: 'work-phone-value', extension: 'extension-value' }
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    phoneEmails: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'PH',
                            use: 'WP',
                            phoneNumber: 'work-phone-value',
                            extension: 'extension-value'
                        })
                    ])
                })
            );
        });

        it('should transform cell phone to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                phoneEmail: {
                    cell: 'cell-value'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    phoneEmails: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'CP',
                            use: 'MC',
                            phoneNumber: 'cell-value'
                        })
                    ])
                })
            );
        });

        it('should transform email address to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                phoneEmail: {
                    email: 'email-value'
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    phoneEmails: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '04/13/2017',
                            type: 'NET',
                            use: 'H',
                            email: 'email-value'
                        })
                    ])
                })
            );
        });
    });

    describe('that contains Ethnicity and race', () => {
        it('should transform ethnicity to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                ethnicityRace: {
                    ethnicity: { value: 'ethnicity-value', name: 'ethnicity-name' }
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    ethnicity: {
                        asOf: '04/13/2017',
                        ethnicGroup: 'ethnicity-value',
                        detailed: []
                    }
                })
            );
        });

        it('should transform each race into a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '04/13/2017' },
                ethnicityRace: {
                    races: [
                        { value: 'race-one-value', name: 'race-one-name' },
                        { value: 'race-two-value', name: 'race-two-name' }
                    ]
                }
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    races: expect.arrayContaining([
                        { asOf: '04/13/2017', race: 'race-one-value', detailed: [] },
                        { asOf: '04/13/2017', race: 'race-two-value', detailed: [] }
                    ])
                })
            );
        });
    });

    describe('that contains Identifications ', () => {
        it('should transform identifications to a format accepted by the API', () => {
            const entry: BasicNewPatientEntry = {
                administrative: { asOf: '11/07/2019' },
                identifications: [
                    {
                        id: 'id-value',
                        type: { value: 'identification-type-value', name: 'identification-type-name' },
                        issuer: { value: 'issuer-value', name: 'issuer-name' }
                    }
                ]
            };

            const actual = transformer(entry);

            expect(actual).toEqual(
                expect.objectContaining({
                    identifications: expect.arrayContaining([
                        expect.objectContaining({
                            asOf: '11/07/2019',
                            id: 'id-value',
                            type: 'identification-type-value',
                            issuer: 'issuer-value'
                        })
                    ])
                })
            );
        });
    });
});

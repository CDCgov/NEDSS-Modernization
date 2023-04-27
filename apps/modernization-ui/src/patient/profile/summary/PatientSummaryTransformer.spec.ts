import { transform } from './PatientSummaryTransformer';

describe('when the result is empty', () => {
    it('should return null when null', () => {
        const actual = transform(null);

        expect(actual).toBeUndefined();
    });
});

describe('when the result has content', () => {
    it('should return a Patient Summary', () => {
        const result = {
            birthday: 'birthday',
            age: 33,
            gender: 'gender',
            ethnicity: 'ethnicity',
            race: 'race'
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                birthday: 'birthday',
                age: 33,
                gender: 'gender',
                ethnicity: 'ethnicity',
                race: 'race',
                legalName: null,
                address: null,
                phone: [],
                email: []
            })
        );
    });
});

describe('when a result contains a legalName', () => {
    it('should return a Patient Summary with legal name', () => {
        const result = {
            legalName: {
                prefix: 'prefix',
                first: 'first',
                middle: 'middle',
                last: 'last',
                suffix: 'suffix'
            }
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                legalName: expect.objectContaining({
                    prefix: 'prefix',
                    first: 'first',
                    middle: 'middle',
                    last: 'last',
                    suffix: 'suffix'
                })
            })
        );
    });

    it('should return a Patient Summary with legal name containing only prefix', () => {
        const result = {
            legalName: {
                prefix: 'prefix'
            }
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                legalName: expect.objectContaining({
                    prefix: 'prefix',
                    first: null,
                    middle: null,
                    last: null,
                    suffix: null
                })
            })
        );
    });

    it('should return a Patient Summary with legal name containing only first', () => {
        const result = {
            legalName: {
                first: 'first'
            }
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                legalName: expect.objectContaining({
                    prefix: null,
                    first: 'first',
                    middle: null,
                    last: null,
                    suffix: null
                })
            })
        );
    });

    it('should return a Patient Summary with legal name containing only middle', () => {
        const result = {
            legalName: {
                middle: 'middle'
            }
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                legalName: expect.objectContaining({
                    prefix: null,
                    first: null,
                    middle: 'middle',
                    last: null,
                    suffix: null
                })
            })
        );
    });

    it('should return a Patient Summary with legal name containing only last', () => {
        const result = {
            legalName: {
                last: 'last'
            }
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                legalName: expect.objectContaining({
                    prefix: null,
                    first: null,
                    middle: null,
                    last: 'last',
                    suffix: null
                })
            })
        );
    });

    it('should return a Patient Summary with legal name containing only suffix', () => {
        const result = {
            legalName: {
                suffix: 'suffix'
            }
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                legalName: expect.objectContaining({
                    prefix: null,
                    first: null,
                    middle: null,
                    last: null,
                    suffix: 'suffix'
                })
            })
        );
    });
});

describe('when a result contains an address', () => {
    it('should return a Patient Summary with an address', () => {
        const result = {
            address: {
                street: 'street',
                city: 'city',
                state: 'state',
                zipcode: 'zipcode',
                country: 'country'
            }
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                address: expect.objectContaining({
                    street: 'street',
                    city: 'city',
                    state: 'state',
                    zipcode: 'zipcode',
                    country: 'country'
                })
            })
        );
    });

    it('should return a Patient Summary with an address containing only street', () => {
        const result = {
            address: {
                street: 'street'
            }
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                address: expect.objectContaining({
                    street: 'street',
                    city: null,
                    state: null,
                    zipcode: null,
                    country: null
                })
            })
        );
    });

    it('should return a Patient Summary with an address containing only city', () => {
        const result = {
            address: {
                city: 'city'
            }
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                address: expect.objectContaining({
                    street: null,
                    city: 'city',
                    state: null,
                    zipcode: null,
                    country: null
                })
            })
        );
    });

    it('should return a Patient Summary with an address containing only state', () => {
        const result = {
            address: {
                state: 'state'
            }
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                address: expect.objectContaining({
                    street: null,
                    city: null,
                    state: 'state',
                    zipcode: null,
                    country: null
                })
            })
        );
    });

    it('should return a Patient Summary with an address containing only zipcode', () => {
        const result = {
            address: {
                zipcode: 'zipcode'
            }
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                address: expect.objectContaining({
                    street: null,
                    city: null,
                    state: null,
                    zipcode: 'zipcode',
                    country: null
                })
            })
        );
    });

    it('should return a Patient Summary with an address containing only country', () => {
        const result = {
            address: {
                country: 'country'
            }
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                address: expect.objectContaining({
                    street: null,
                    city: null,
                    state: null,
                    zipcode: null,
                    country: 'country'
                })
            })
        );
    });
});

describe('when a result contains phones', () => {
    it('should return a Patient Summary with a single phone', () => {
        const result = {
            phone: [
                {
                    use: 'use',
                    number: 'number'
                }
            ]
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                phone: expect.arrayContaining([
                    expect.objectContaining({
                        use: 'use',
                        number: 'number'
                    })
                ])
            })
        );
    });

    it('should return a Patient Summary with a multple phones', () => {
        const result = {
            phone: [
                {
                    use: 'use-one',
                    number: 'number-one'
                },
                {
                    use: 'use-two',
                    number: 'number-two'
                }
            ]
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                phone: expect.arrayContaining([
                    expect.objectContaining({
                        use: 'use-one',
                        number: 'number-one'
                    }),
                    expect.objectContaining({
                        use: 'use-two',
                        number: 'number-two'
                    })
                ])
            })
        );
    });

    it('should return a Patient Summary without a phone if use is not present', () => {
        const result = {
            phone: [
                {
                    number: 'number'
                }
            ]
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                phone: []
            })
        );
    });

    it('should return a Patient Summary without a phone if number is not present', () => {
        const result = {
            phone: [
                {
                    use: 'use'
                }
            ]
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                phone: []
            })
        );
    });

    it('should return a Patient Summary without null phones', () => {
        const result = {
            phone: [
                {
                    use: 'use',
                    number: 'number'
                },
                null
            ]
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.objectContaining({
                phone: expect.arrayContaining([
                    expect.objectContaining({
                        use: 'use',
                        number: 'number'
                    })
                ])
            })
        );
    });

    describe('when a result contains emails', () => {
        it('should return a Patient Summary with a single email', () => {
            const result = {
                email: [
                    {
                        use: 'use',
                        address: 'address'
                    }
                ]
            };

            const actual = transform(result);

            expect(actual).toEqual(
                expect.objectContaining({
                    email: expect.arrayContaining([
                        expect.objectContaining({
                            use: 'use',
                            address: 'address'
                        })
                    ])
                })
            );
        });

        it('should return a Patient Summary with a multple emails', () => {
            const result = {
                email: [
                    {
                        use: 'use-one',
                        address: 'address-one'
                    },
                    {
                        use: 'use-two',
                        address: 'address-two'
                    }
                ]
            };

            const actual = transform(result);

            expect(actual).toEqual(
                expect.objectContaining({
                    email: expect.arrayContaining([
                        expect.objectContaining({
                            use: 'use-one',
                            address: 'address-one'
                        }),
                        expect.objectContaining({
                            use: 'use-two',
                            address: 'address-two'
                        })
                    ])
                })
            );
        });

        it('should return a Patient Summary without a email if use is not present', () => {
            const result = {
                email: [
                    {
                        address: 'address'
                    }
                ]
            };

            const actual = transform(result);

            expect(actual).toEqual(
                expect.objectContaining({
                    email: []
                })
            );
        });

        it('should return a Patient Summary without a email if address is not present', () => {
            const result = {
                email: [
                    {
                        use: 'use'
                    }
                ]
            };

            const actual = transform(result);

            expect(actual).toEqual(
                expect.objectContaining({
                    email: []
                })
            );
        });

        it('should return a Patient Summary without null emails', () => {
            const result = {
                email: [
                    {
                        use: 'use',
                        address: 'address'
                    },
                    null
                ]
            };

            const actual = transform(result);

            expect(actual).toEqual(
                expect.objectContaining({
                    email: expect.arrayContaining([
                        expect.objectContaining({
                            use: 'use',
                            address: 'address'
                        })
                    ])
                })
            );
        });
    });
});

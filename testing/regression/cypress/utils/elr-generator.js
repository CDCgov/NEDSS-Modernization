import UtilityFunctionsPage from '@pages/utilityFunctions.page';

export const generateUniqueELR = () => {
    const firstName = UtilityFunctionsPage.generateRandomName();
    const lastName = UtilityFunctionsPage.generateRandomName();
    const SSN = UtilityFunctionsPage.generateRandomLengthNumber(9);
    const streetAddressNumber = UtilityFunctionsPage.generateRandomLengthNumber(4);

    const randomBirthDate = UtilityFunctionsPage.generateRandomBirthDate();
    const birthYear = randomBirthDate.year;
    const birthMonth = randomBirthDate.month;
    const birthDay = randomBirthDate.day;

    const fillerNumber = UtilityFunctionsPage.generateRandomFillerNumber();
    const placerNumber = UtilityFunctionsPage.generateRandomLengthNumber(8);
    const specimenNumber = UtilityFunctionsPage.generateRandomLengthNumber(8);
    const idNumber = UtilityFunctionsPage.generateRandomHL7IDNumber();
    const messageId = UtilityFunctionsPage.generateRandomLengthNumber(11);

    return {
        fillerNumber,
        message: `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <Container xmlns="http://www.cdc.gov/NEDSS">
    <HL7LabReport>
    <HL7MSH>
    <FieldSeparator>|</FieldSeparator>
<EncodingCharacters>^~&amp;</EncodingCharacters>
<SendingApplication>
    <HL7NamespaceID>HL7 Generator</HL7NamespaceID>
</SendingApplication>
<SendingFacility>
    <HL7NamespaceID>ST MUNGOS HOSPITAL</HL7NamespaceID>
    <HL7UniversalID>11D2222222</HL7UniversalID>
    <HL7UniversalIDType>CLIA</HL7UniversalIDType>
</SendingFacility>
<ReceivingApplication>
    <HL7NamespaceID>GADPH</HL7NamespaceID>
    <HL7UniversalID>2.16.840.1.114222.4.1.3661</HL7UniversalID>
    <HL7UniversalIDType>ISO</HL7UniversalIDType>
</ReceivingApplication>
<ReceivingFacility>
    <HL7NamespaceID>GA</HL7NamespaceID>
    <HL7UniversalID>2.16.840.1.114222</HL7UniversalID>
    <HL7UniversalIDType>ISO</HL7UniversalIDType>
</ReceivingFacility>
<DateTimeOfMessage>
    <year>2024</year>
    <month>11</month>
    <day>27</day>
    <hours>11</hours>
    <minutes>45</minutes>
    <seconds>0</seconds>
    <gmtOffset></gmtOffset>
</DateTimeOfMessage>
<Security></Security>
<MessageType>
    <MessageCode>ORU</MessageCode>
    <TriggerEvent>R01</TriggerEvent>
    <MessageStructure>ORU_R01</MessageStructure>
</MessageType>
<MessageControlID>${messageId}</MessageControlID>
<ProcessingID>
    <HL7ProcessingID>P</HL7ProcessingID>
    <HL7ProcessingMode></HL7ProcessingMode>
</ProcessingID>
<VersionID>
    <HL7VersionID>2.5.1</HL7VersionID>
</VersionID>
</HL7MSH>
<HL7PATIENT_RESULT>
    <PATIENT>
        <PatientIdentification>
            <SetIDPID>
                <HL7SequenceID>1</HL7SequenceID>
            </SetIDPID>
            <PatientID>
                <HL7IDNumber>${idNumber}</HL7IDNumber>
                <HL7AssigningAuthority>
                    <HL7NamespaceID>St Mungos Hospital</HL7NamespaceID>
                    <HL7UniversalID>11D2222222</HL7UniversalID>
                    <HL7UniversalIDType>CLIA</HL7UniversalIDType>
                </HL7AssigningAuthority>
            </PatientID>
            <PatientIdentifierList>
                <HL7IDNumber>${idNumber}</HL7IDNumber>
                <HL7AssigningAuthority>
                    <HL7NamespaceID>Social Security Administration</HL7NamespaceID>
                    <HL7UniversalID>SSA</HL7UniversalID>
                    <HL7UniversalIDType>CLIA</HL7UniversalIDType>
                </HL7AssigningAuthority>
                <HL7IdentifierTypeCode>SS</HL7IdentifierTypeCode>
            </PatientIdentifierList>
            <PatientName>
                <HL7FamilyName>
                    <HL7Surname>${lastName}</HL7Surname>
                </HL7FamilyName>
                <HL7GivenName>${firstName}</HL7GivenName>
                <HL7SecondAndFurtherGivenNamesOrInitialsThereof>Jean</HL7SecondAndFurtherGivenNamesOrInitialsThereof>
                <HL7Prefix>Ms</HL7Prefix>
                <HL7Degree>BA</HL7Degree>
            </PatientName>
            <MothersMaidenName>
                <HL7FamilyName>
                    <HL7Surname>${lastName}</HL7Surname>
                </HL7FamilyName>
            </MothersMaidenName>
            <DateTimeOfBirth>
                <year>${birthYear}</year>
                <month>${birthMonth}</month>
                <day>${birthDay}</day>
                <hours>0</hours>
                <minutes>0</minutes>
                <gmtOffset></gmtOffset>
            </DateTimeOfBirth>
            <AdministrativeSex>F</AdministrativeSex>
            <Race>
                <HL7Identifier>2106-3</HL7Identifier>
                <HL7Text>White</HL7Text>
                <HL7NameofCodingSystem>CDCREC</HL7NameofCodingSystem>
            </Race>
            <PatientAddress>
                <HL7StreetAddress>
                    <HL7StreetOrMailingAddress>${streetAddressNumber} The Burrow</HL7StreetOrMailingAddress>
                </HL7StreetAddress>
                <HL7City>Ottery St Catchpole</HL7City>
                <HL7StateOrProvince>GA</HL7StateOrProvince>
                <HL7ZipOrPostalCode>30322</HL7ZipOrPostalCode>
                <HL7Country>USA</HL7Country>
            </PatientAddress>
            <PhoneNumberHome>
                <HL7EmailAddress>test@hogwarts.edu</HL7EmailAddress>
                <HL7CountryCode/>
                <HL7AreaCityCode>
                    <HL7Numeric>912</HL7Numeric>
                </HL7AreaCityCode>
                <HL7LocalNumber>
                    <HL7Numeric>5551002</HL7Numeric>
                </HL7LocalNumber>
                <HL7Extension>
                    <HL7Numeric>1002</HL7Numeric>
                </HL7Extension>
            </PhoneNumberHome>
            <PrimaryLanguage>
                <HL7Identifier>ENG</HL7Identifier>
            </PrimaryLanguage>
            <MaritalStatus>
                <HL7Identifier>S</HL7Identifier>
            </MaritalStatus>
            <SSNNumberPatient>${SSN}</SSNNumberPatient>
            <EthnicGroup>
                <HL7Identifier>N</HL7Identifier>
                <HL7Text>Not Hispanic or Latino</HL7Text>
            </EthnicGroup>
            <BirthPlace>Savannah</BirthPlace>
            <MultipleBirthIndicator>N</MultipleBirthIndicator>
            <BirthOrder>
                <HL7Numeric>2</HL7Numeric>
            </BirthOrder>
            <Nationality>
                <HL7Identifier>USA</HL7Identifier>
            </Nationality>
            <PatientDeathDateAndTime>
                <year>2024</year>
                <month>11</month>
                <day>10</day>
                <hours>10</hours>
                <minutes>15</minutes>
                <seconds>0</seconds>
                <gmtOffset></gmtOffset>
            </PatientDeathDateAndTime>
            <PatientDeathIndicator>Y</PatientDeathIndicator>
        </PatientIdentification>
    </PATIENT>
    <ORDER_OBSERVATION>
        <CommonOrder>
            <OrderControl>RE</OrderControl>
            <FillerOrderNumber>
                <HL7EntityIdentifier>LAB${fillerNumber}</HL7EntityIdentifier>
                <HL7NamespaceID>Diagon Alley Clinic</HL7NamespaceID>
                <HL7UniversalID>11D3333333</HL7UniversalID>
                <HL7UniversalIDType>CLIA</HL7UniversalIDType>
            </FillerOrderNumber>
            <OrderStatus>CM</OrderStatus>
            <OrderingFacilityName>
                <HL7OrganizationName>St Mungos Hospital</HL7OrganizationName>
                <HL7IDNumber/>
                <HL7CheckDigit/>
            </OrderingFacilityName>
            <OrderingFacilityAddress>
                <HL7StreetAddress>
                    <HL7StreetOrMailingAddress>Purge and Dowse Ltd</HL7StreetOrMailingAddress>
                </HL7StreetAddress>
                <HL7City>London</HL7City>
                <HL7StateOrProvince>GA</HL7StateOrProvince>
                <HL7ZipOrPostalCode>30329</HL7ZipOrPostalCode>
                <HL7Country>USA</HL7Country>
            </OrderingFacilityAddress>
            <OrderingFacilityPhoneNumber>
                <HL7CountryCode/>
                <HL7AreaCityCode/>
                <HL7LocalNumber>
                    <HL7Numeric>404</HL7Numeric>
                </HL7LocalNumber>
                <HL7Extension>
                    <HL7Numeric>5552002</HL7Numeric>
                </HL7Extension>
                <HL7AnyText>2002</HL7AnyText>
            </OrderingFacilityPhoneNumber>
            <OrderingProviderAddress>
                <HL7StreetAddress>
                    <HL7StreetOrMailingAddress>Spell Damage Ward</HL7StreetOrMailingAddress>
                </HL7StreetAddress>
                <HL7City>London</HL7City>
                <HL7StateOrProvince>GA</HL7StateOrProvince>
                <HL7ZipOrPostalCode>30329</HL7ZipOrPostalCode>
                <HL7Country>USA</HL7Country>
            </OrderingProviderAddress>
        </CommonOrder>
        <ObservationRequest>
            <SetIDOBR>
                <HL7SequenceID>1</HL7SequenceID>
            </SetIDOBR>
            <PlacerOrderNumber>
                <HL7EntityIdentifier>ORD${placerNumber}</HL7EntityIdentifier>
                <HL7NamespaceID>Wizarding Diagnostics</HL7NamespaceID>
                <HL7UniversalID>WD</HL7UniversalID>
            </PlacerOrderNumber>
            <FillerOrderNumber>
                <HL7EntityIdentifier>${fillerNumber}</HL7EntityIdentifier>
                <HL7NamespaceID>ST MUNGOS HOSPITAL</HL7NamespaceID>
                <HL7UniversalID>11D2222222</HL7UniversalID>
                <HL7UniversalIDType>CLIA</HL7UniversalIDType>
            </FillerOrderNumber>
            <UniversalServiceIdentifier>
                <HL7Identifier>630-4</HL7Identifier>
                <HL7Text>Salmonella sp identified in Stool by Culture</HL7Text>
                <HL7NameofCodingSystem>LN</HL7NameofCodingSystem>
                <HL7AlternateIdentifier>SAL-CULT</HL7AlternateIdentifier>
                <HL7AlternateText>Bacteria Culture</HL7AlternateText>
                <HL7NameofAlternateCodingSystem>L</HL7NameofAlternateCodingSystem>
            </UniversalServiceIdentifier>
            <PriorityOBR>R</PriorityOBR>
            <ObservationDateTime>
                <year>2024</year>
                <month>11</month>
                <day>27</day>
                <hours>11</hours>
                <minutes>45</minutes>
                <seconds>0</seconds>
                <gmtOffset></gmtOffset>
            </ObservationDateTime>
            <ObservationEndDateTime>
                <year>2024</year>
                <month>11</month>
                <day>28</day>
                <hours>16</hours>
                <minutes>20</minutes>
                <seconds>0</seconds>
                <gmtOffset></gmtOffset>
            </ObservationEndDateTime>
            <DangerCode>
                <HL7Identifier>STL</HL7Identifier>
            </DangerCode>
            <RelevantClinicalInformation>Patient presented with severe diarrhea and abdominal cramping after eating at Leaky Cauldron.</RelevantClinicalInformation>
            <SpecimenReceivedDateTime>
                <year>2024</year>
                <month>11</month>
                <day>27</day>
                <hours>11</hours>
                <minutes>45</minutes>
                <seconds>0</seconds>
                <gmtOffset></gmtOffset>
            </SpecimenReceivedDateTime>
            <OrderingProvider>
                <HL7IDNumber>2345678901</HL7IDNumber>
                <HL7FamilyName>
                    <HL7Surname>Bones</HL7Surname>
                </HL7FamilyName>
                <HL7GivenName>Amelia</HL7GivenName>
                <HL7Suffix>Madam</HL7Suffix>
                <HL7Prefix>MD</HL7Prefix>
            </OrderingProvider>
            <OrderCallbackPhoneNumber>
                <HL7TelephoneNumber>HealerBones@stmungos.org</HL7TelephoneNumber>
                <HL7CountryCode/>
                <HL7AreaCityCode/>
                <HL7LocalNumber/>
                <HL7Extension/>
            </OrderCallbackPhoneNumber>
            <ResultsRptStatusChngDateTime>
                <year>2024</year>
                <month>11</month>
                <day>28</day>
                <hours>16</hours>
                <minutes>20</minutes>
                <seconds>0</seconds>
                <gmtOffset></gmtOffset>
            </ResultsRptStatusChngDateTime>
            <ResultStatus>F</ResultStatus>
            <ResultCopiesTo>
                <HL7IDNumber>SAL001</HL7IDNumber>
                <HL7FamilyName>
                    <HL7Surname>Salmonellosis</HL7Surname>
                </HL7FamilyName>
                <HL7GivenName>A02.0</HL7GivenName>
            </ResultCopiesTo>
            <NumberofSampleContainers/>
        </ObservationRequest>
        <PatientResultOrderObservation>
            <OBSERVATION>
                <ObservationResult>
                    <SetIDOBX>
                        <HL7SequenceID>1</HL7SequenceID>
                    </SetIDOBX>
                    <ValueType>CE</ValueType>
                    <ObservationIdentifier>
                        <HL7Identifier>630-4</HL7Identifier>
                        <HL7Text>Salmonella sp identified in Stool by Culture</HL7Text>
                        <HL7NameofCodingSystem>LN</HL7NameofCodingSystem>
                    </ObservationIdentifier>
                    <ObservationSubID>1</ObservationSubID>
                    <ObservationValue>27268008^Salmonella enterica^SCT</ObservationValue>
                    <ReferencesRange>Negative</ReferencesRange>
                    <Probability/>
                    <ObservationResultStatus>F</ObservationResultStatus>
                    <DateTimeOftheAnalysis>
                        <year>2024</year>
                        <month>11</month>
                        <day>28</day>
                        <hours>16</hours>
                        <minutes>20</minutes>
                        <seconds>0</seconds>
                        <gmtOffset></gmtOffset>
                    </DateTimeOftheAnalysis>
                </ObservationResult>
            </OBSERVATION>
        </PatientResultOrderObservation>
        <PatientResultOrderSPMObservation>
            <SPECIMEN>
                <SPECIMEN>
                    <SetIDSPM>
                        <HL7SequenceID>1</HL7SequenceID>
                    </SetIDSPM>
                    <SpecimenID>
                        <HL7PlacerAssignedIdentifier>
                            <HL7EntityIdentifier>SPEC${specimenNumber}</HL7EntityIdentifier>
                            <HL7NamespaceID>Wizarding Diagnostics</HL7NamespaceID>
                            <HL7UniversalID>WD</HL7UniversalID>
                        </HL7PlacerAssignedIdentifier>
                        <HL7FillerAssignedIdentifier>
                            <HL7EntityIdentifier>${idNumber}</HL7EntityIdentifier>
                            <HL7NamespaceID>ST MUNGOS HOSPITAL</HL7NamespaceID>
                            <HL7UniversalID>11D2222222</HL7UniversalID>
                            <HL7UniversalIDType>CLIA</HL7UniversalIDType>
                        </HL7FillerAssignedIdentifier>
                    </SpecimenID>
                    <SpecimenType>
                        <HL7Identifier>STL</HL7Identifier>
                        <HL7Text>Stool</HL7Text>
                        <HL7NameofCodingSystem>HL70487</HL7NameofCodingSystem>
                        <HL7AlternateIdentifier>119339001</HL7AlternateIdentifier>
                        <HL7AlternateText>Stool specimen</HL7AlternateText>
                        <HL7NameofAlternateCodingSystem>SCT</HL7NameofAlternateCodingSystem>
                        <HL7CodingSystemVersionID>2.5.1</HL7CodingSystemVersionID>
                        <HL7AlternateCodingSystemVersionID>Stool</HL7AlternateCodingSystemVersionID>
                    </SpecimenType>
                    <SpecimenCollectionMethod>
                        <HL7Identifier>119339001</HL7Identifier>
                        <HL7Text>Stool</HL7Text>
                        <HL7NameofCodingSystem>SCT</HL7NameofCodingSystem>
                    </SpecimenCollectionMethod>
                    <SpecimenSourceSite>
                        <HL7Identifier>Clean catch</HL7Identifier>
                        <HL7Text>Clean catch</HL7Text>
                        <HL7NameofCodingSystem>Method</HL7NameofCodingSystem>
                    </SpecimenSourceSite>
                    <SpecimenCollectionAmount>
                        <HL7Quantity>
                            <HL7Numeric>5</HL7Numeric>
                        </HL7Quantity>
                        <HL7Units>
                            <HL7Identifier>g</HL7Identifier>
                        </HL7Units>
                    </SpecimenCollectionAmount>
                    <GroupedSpecimenCount/>
                    <SpecimenDescription>Specimen collected at Herbology greenhouse facilities.</SpecimenDescription>
                    <SpecimenCollectionDateTime>
                        <HL7RangeStartDateTime>
                            <year>2024</year>
                            <month>11</month>
                            <day>27</day>
                            <hours>11</hours>
                            <minutes>45</minutes>
                            <seconds>0</seconds>
                            <gmtOffset></gmtOffset>
                        </HL7RangeStartDateTime>
                    </SpecimenCollectionDateTime>
                    <SpecimenReceivedDateTime>
                        <year>2024</year>
                        <month>11</month>
                        <day>27</day>
                        <hours>11</hours>
                        <minutes>45</minutes>
                        <seconds>0</seconds>
                        <gmtOffset></gmtOffset>
                    </SpecimenReceivedDateTime>
                    <NumberOfSpecimenContainers/>
                </SPECIMEN>
            </SPECIMEN>
        </PatientResultOrderSPMObservation>
    </ORDER_OBSERVATION>
</HL7PATIENT_RESULT>
</HL7LabReport>
</Container>`,
    };
};

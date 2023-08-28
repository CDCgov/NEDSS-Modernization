import { Grid, Pagination } from '@trussworks/react-uswds';
import { useEffect, useRef, useState } from 'react';
import { Maybe, Person, PersonName } from '../../../generated/graphql/schema';
import { calculateAge } from '../../../utils/util';
import '../AdvancedSearch.scss';
import { useNavigate } from 'react-router';
import NoData from 'components/NoData/NoData';

type SearchItemsProps = {
    data: Person[];
    totalResults: number;
    handlePagination: (page: number) => void;
    currentPage: number;
};

export const PatientResults = ({ data, totalResults, handlePagination, currentPage }: SearchItemsProps) => {
    const searchItemsRef: any = useRef();

    const [num, setNum] = useState<any>([]);
    const [email, setEmail] = useState<any>('');
    const navigate = useNavigate();

    useEffect(() => {
        const newArrOfNumbers: any = [];
        const newArrOfEmails: any = [];
        data.map((item: any) => {
            const tempNumbers: any = [];
            const tempEmails: any = [];
            item?.nbsEntity?.entityLocatorParticipations?.forEach((element: any) => {
                if (element.locator.phoneNbrTxt) {
                    tempNumbers.push(element.locator.phoneNbrTxt);
                }
                if (element.locator.emailAddress) {
                    tempEmails.push(element.locator.emailAddress);
                }
            });
            newArrOfNumbers.push(tempNumbers);
            newArrOfEmails.push(tempEmails);
        });
        setNum(newArrOfNumbers);
        setEmail(newArrOfEmails);
    }, [data]);

    const handleNext = (page: number) => {
        handlePagination(page);
    };

    const getListSize = () => {
        return searchItemsRef.current?.clientHeight;
    };

    useEffect(() => {
        getListSize();
    }, [data]);

    // Update 'width' and 'height' when the window resizes
    useEffect(() => {
        window.addEventListener('resize', getListSize);
    }, []);

    function getOtherNames(item: Person, names: Maybe<Maybe<PersonName>[]> | undefined): String | undefined {
        if (!names) {
            return undefined;
        }
        let otherNames = '';
        names
            .filter((n) => n?.firstNm != item.firstNm || n?.lastNm != item.lastNm)
            .forEach((n) => (otherNames = otherNames + ` ${n?.firstNm ?? ''} ${n?.lastNm ?? ''}`));
        return otherNames;
    }

    const OrderedData = ({ data, type }: any) => {
        return (
            <Grid col={6} className="margin-bottom-2 margin-0">
                <div>
                    <h5 className="margin-0 text-normal text-gray-50 margin-bottom-05">{type}</h5>
                    {data && data.length > 0 ? (
                        data.map((add: string, ind: number) => (
                            <p
                                key={ind}
                                className="margin-0 font-sans-2xs text-normal"
                                style={{
                                    wordBreak: 'break-word',
                                    paddingRight: '15px',
                                    maxWidth: type === 'EMAIL' ? '165px' : 'auto'
                                }}>
                                {add}
                            </p>
                        ))
                    ) : (
                        <NoData />
                    )}
                </div>
            </Grid>
        );
    };

    const newOrderPhone = (data: any) => {
        const numbers: any = [];
        data?.map((item: any) => item.locator.phoneNbrTxt && numbers.push(item.locator.phoneNbrTxt));
        return <OrderedData data={numbers} type="PHONE NUMBER" />;
    };

    const newOrderEmail = (data: any) => {
        const emails: any = [];
        data?.map((item: any) => item.locator.emailAddress && emails.push(item.locator.emailAddress));
        return <OrderedData data={emails} type="EMAIL" />;
    };

    const newOrderAddress = (data: any) => {
        const address: any = [];
        data?.map(
            (item: any) =>
                item.classCd === 'PST' &&
                address.push(
                    `${item.locator.streetAddr1 ?? ''} ${item.locator.cityCd ?? ''} ${
                        item.locator.stateCode ? item.locator.stateCode.stateNm : ''
                    } ${item.locator.zipCd ?? ''} ${
                        item.locator.countryCode ? item.locator.countryCode.codeShortDescTxt : ''
                    }`
                )
        );
        return <OrderedData data={address} type="ADDRESS" />;
    };

    const styleObjHeight = (index: number) => {
        const em = email[index] ? email[index][0] : null;
        let emailHeight: any = em?.length > 17 ? 54 : null;
        const numHeight: any = num[index]?.length === 2 ? 54 : num[index]?.length === 3 ? 74 : null;
        em?.length > 33 && (emailHeight = 74);
        return {
            minHeight:
                emailHeight || numHeight
                    ? emailHeight > numHeight
                        ? `${emailHeight}px`
                        : emailHeight < numHeight
                        ? `${numHeight}px`
                        : 'auto'
                    : 'auto',
            height: 'auto'
        };
    };

    const filteredEnitityIds = (entity: any) => {
        const driverLicence = entity.filter((item: any) => item.typeCd === 'DRIVERS_LICENSE_NUMBER')[0];
        const socialSecurity = entity.filter((item: any) => item.typeCd === 'SOCIAL_SECURITY')[0];
        const newen = entity
            .filter((item: any) => item.typeCd !== 'DRIVERS_LICENSE_NUMBER')
            .filter((item: any) => item.typeCd !== 'SOCIAL_SECURITY');
        socialSecurity && newen.unshift(socialSecurity);
        driverLicence && newen.unshift(driverLicence);
        return newen;
    };

    const redirectPatientProfile = async (item: Person) => {
        navigate(`/patient-profile/${item.shortId}`);
    };

    return (
        <div className="margin-x-4">
            {Boolean(totalResults && data?.length > 0) && (
                <Grid row className="flex-align-center flex-justify">
                    <p className="margin-0 font-sans-3xs margin-top-05 text-normal text-base">
                        Showing {data.length} of {totalResults}
                    </p>
                    <Pagination
                        style={{ justifyContent: 'flex-end' }}
                        totalPages={Math.ceil(totalResults / 25)}
                        currentPage={currentPage}
                        pathname={'/advanced-search'}
                        onClickNext={() => handleNext(currentPage + 1)}
                        onClickPrevious={() => handleNext(currentPage - 1)}
                        onClickPageNumber={(_, page) => handleNext(page)}
                    />
                </Grid>
            )}
            <div ref={searchItemsRef}>
                {data &&
                    data?.length > 0 &&
                    data?.map((item: Person, index: number) => (
                        <div
                            key={index}
                            className="padding-x-3 padding-top-3 padding-bottom-2 margin-bottom-3 bg-white border border-base-light radius-md">
                            <Grid row gap={3}>
                                <Grid col={4}>
                                    <Grid row gap={3}>
                                        <Grid col={12} style={styleObjHeight(index)} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal font-sans-3xs text-gray-50">
                                                LEGAL NAME
                                            </h5>
                                            <a
                                                onClick={() => redirectPatientProfile(item)}
                                                tabIndex={0}
                                                className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                                                style={{
                                                    wordBreak: 'break-word',
                                                    cursor: 'pointer'
                                                }}>
                                                {!item.lastNm && !item.firstNm ? (
                                                    <NoData />
                                                ) : (
                                                    `${item.lastNm}, ${item.firstNm}`
                                                )}
                                            </a>
                                        </Grid>
                                        <Grid col={12} className="margin-bottom-2">
                                            <div className="grid-row flex-align-center">
                                                <p className="margin-0 text-normal font-sans-3xs text-gray-50 margin-right-1">
                                                    DATE OF BIRTH
                                                </p>
                                                <p className="margin-0 font-sans-2xs text-normal">
                                                    {item.birthTime && (
                                                        <>
                                                            {new Date(item.birthTime).toLocaleDateString('en-US', {
                                                                timeZone: 'UTC'
                                                            })}
                                                            <span className="font-sans-2xs">
                                                                {' '}
                                                                ({calculateAge(new Date(item.birthTime))})
                                                            </span>
                                                        </>
                                                    )}
                                                    {!item.birthTime && <NoData />}
                                                </p>
                                            </div>
                                            <div className="grid-row flex-align-center">
                                                <p className="margin-0 text-normal font-sans-3xs text-gray-50 margin-right-1">
                                                    SEX
                                                </p>
                                                <p className="margin-0 font-sans-2xs text-normal">
                                                    {item.currSexCd === 'M'
                                                        ? 'Male'
                                                        : item.currSexCd === 'F'
                                                        ? 'Female'
                                                        : 'Unknown'}
                                                </p>
                                            </div>
                                            <div className="grid-row flex-align-center">
                                                <p className="margin-0 text-normal font-sans-3xs text-gray-50 margin-right-1">
                                                    PATIENT ID
                                                </p>

                                                <p className="margin-0 font-sans-2xs text-normal">{item.shortId}</p>
                                            </div>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid col={5}>
                                    <Grid row gap={3}>
                                        {/* Locator entries */}
                                        {newOrderPhone(item?.nbsEntity?.entityLocatorParticipations)}
                                        {newOrderEmail(item?.nbsEntity?.entityLocatorParticipations)}
                                        <Grid col={6} className="margin-bottom-2">
                                            <p className="margin-0 text-normal font-sans-3xs text-gray-50">
                                                OTHER NAMES
                                            </p>
                                            <p
                                                className="margin-0 font-sans-1xs text-normal margin-top-05"
                                                style={{ wordBreak: 'break-word', paddingRight: '15px' }}>
                                                {getOtherNames(item, item.names) || <NoData />}
                                            </p>
                                        </Grid>
                                        {newOrderAddress(item?.nbsEntity?.entityLocatorParticipations)}
                                    </Grid>
                                </Grid>
                                <Grid col={3}>
                                    {/* Identifications */}
                                    {filteredEnitityIds(item.entityIds).map(
                                        (
                                            id: { typeDescTxt: String; rootExtensionTxt: String; typeCd: String },
                                            idIndex: number
                                        ) =>
                                            id.typeDescTxt && (
                                                <Grid
                                                    style={styleObjHeight(idIndex)}
                                                    key={idIndex}
                                                    col={12}
                                                    className="margin-bottom-2">
                                                    <p className="margin-0 text-normal font-sans-2xs text-gray-50 text-uppercase">
                                                        {id.typeDescTxt}
                                                    </p>
                                                    <p
                                                        className="margin-0 font-sans-2xs text-normal margin-top-05"
                                                        style={{ wordBreak: 'break-word', paddingRight: '15px' }}>
                                                        {id.rootExtensionTxt || '-'}
                                                    </p>
                                                </Grid>
                                            )
                                    )}
                                    {!item.entityIds ||
                                        (item.entityIds?.filter((ent) => ent?.typeDescTxt).length === 0 && (
                                            <Grid col={12} className="margin-bottom-2">
                                                <p className="margin-0 text-normal font-sans-3xs text-gray-50 text-uppercase">
                                                    Id Types
                                                </p>
                                                <NoData className="margin-top-05" />
                                            </Grid>
                                        ))}
                                </Grid>
                            </Grid>
                        </div>
                    ))}
            </div>
            {Boolean(totalResults && data?.length > 0) && (
                <Pagination
                    style={{ justifyContent: 'flex-end' }}
                    totalPages={Math.ceil(totalResults / 25)}
                    currentPage={currentPage}
                    pathname={'/advanced-search'}
                    onClickNext={() => handleNext(currentPage + 1)}
                    onClickPrevious={() => handleNext(currentPage - 1)}
                    onClickPageNumber={(_, page) => handleNext(page)}
                />
            )}
        </div>
    );
};

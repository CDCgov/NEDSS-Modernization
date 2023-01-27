import { Grid, Pagination } from '@trussworks/react-uswds';
import { useContext, useEffect, useRef, useState } from 'react';
import { PersonName } from '../../../generated/graphql/schema';
import { calculateAge } from '../../../utils/util';
import '../AdvancedSearch.scss';
import { useNavigate } from 'react-router';
import { EncryptionControllerService } from '../../../generated';
import { UserContext } from '../../../providers/UserContext';

type SearchItemsProps = {
    data: any;
    totalResults: number;
    validSearch: boolean;
    handlePagination: (page: number) => void;
    currentPage: number;
};

export const PatientResults = ({
    data,
    validSearch,
    totalResults,
    handlePagination,
    currentPage
}: SearchItemsProps) => {
    const searchItemsRef: any = useRef();

    const [num, setNum] = useState<any>([]);
    const [email, setEmail] = useState<any>('');
    const navigate = useNavigate();
    const { state } = useContext(UserContext);

    useEffect(() => {
        const newArrOfNumbers: any = [];
        const newArrOfEmails: any = [];
        data.map((item: any) => {
            const tempNumbers: any = [];
            const tempEmails: any = [];
            item.NBSEntity.entityLocatorParticipations.forEach((element: any) => {
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

    function getOtherNames(
        item: { firstNm: String; lastNm: String },
        names: Array<PersonName> | undefined
    ): String | undefined {
        if (!names) {
            return undefined;
        }
        let otherNames = '';
        names
            .filter((n) => n.firstNm != item.firstNm || n.lastNm != item.lastNm)
            .forEach((n) => (otherNames = otherNames + ` ${n.firstNm ?? ''} ${n.lastNm ?? ''}`));
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
                                className="margin-0 font-sans-1xs text-normal"
                                style={{
                                    wordBreak: 'break-word',
                                    paddingRight: '15px',
                                    maxWidth: type === 'EMAIL' ? '165px' : 'auto'
                                }}>
                                {add}
                            </p>
                        ))
                    ) : (
                        <p className="text-italic margin-0 text-gray-30">No Data</p>
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
                    `${item.locator.streetAddr1 ?? ''} ${item.locator.cityCd ?? ''} ${item.locator.stateCd ?? ''} ${
                        item.locator.zipCd ?? ''
                    } ${item.locator.cntryCd ?? ''}`
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

    const redirectPatientProfile = async (item: any) => {
        const encryptedFilter = await EncryptionControllerService.encryptUsingPost({
            authorization: `Bearer ${state.getToken()}`,
            object: item
        });
        navigate(`/patient-profile/${item.localId}?data=${encodeURIComponent(encryptedFilter.value)}`);
    };

    return (
        <div className="margin-x-4">
            {Boolean(validSearch && totalResults && data?.length > 0) && (
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
                    data?.map((item: any, index: number) => (
                        <div
                            key={index}
                            className="padding-x-3 padding-top-3 padding-bottom-2 margin-bottom-3 bg-white border border-base-light radius-md">
                            <Grid row gap={3}>
                                <Grid col={4}>
                                    <Grid row gap={3}>
                                        <Grid col={12} style={styleObjHeight(index)} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal text-gray-50">LEGAL NAME</h5>
                                            <p
                                                onClick={() => redirectPatientProfile(item)}
                                                className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                                                style={{ wordBreak: 'break-word', cursor: 'pointer' }}>
                                                {item.lastNm}, {item.firstNm}
                                            </p>
                                        </Grid>
                                        <Grid col={12} className="margin-bottom-2">
                                            <div className="grid-row flex-align-center">
                                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                                    DATE OF BIRTH
                                                </h5>
                                                <p className="margin-0 font-sans-1xs text-normal">
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
                                                    {!item.birthTime && <span className="font-sans-2xs">--</span>}
                                                </p>
                                            </div>
                                            <div className="grid-row flex-align-center">
                                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                                    SEX
                                                </h5>
                                                <p className="margin-0 font-sans-1xs text-normal">
                                                    {item.currSexCd === 'M'
                                                        ? 'Male'
                                                        : item.currSexCd === 'F'
                                                        ? 'Female'
                                                        : 'Unknown'}
                                                </p>
                                            </div>
                                            <div className="grid-row flex-align-center">
                                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                                    PATIENT ID
                                                </h5>
                                                <p className="margin-0 font-sans-1xs text-normal">{item.localId}</p>
                                            </div>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid col={5}>
                                    <Grid row gap={3}>
                                        {/* Locator entries */}
                                        {newOrderPhone(item?.NBSEntity?.entityLocatorParticipations)}
                                        {newOrderEmail(item?.NBSEntity?.entityLocatorParticipations)}
                                        <Grid col={6} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal text-gray-50">OTHER NAMES</h5>
                                            {getOtherNames(item, item.names) ? (
                                                <p
                                                    className="margin-0 font-sans-1xs text-normal margin-top-05"
                                                    style={{ wordBreak: 'break-word', paddingRight: '15px' }}>
                                                    {getOtherNames(item, item.names)}
                                                </p>
                                            ) : (
                                                <p className="text-italic margin-0 text-gray-30">No Data</p>
                                            )}
                                        </Grid>
                                        {newOrderAddress(item?.NBSEntity?.entityLocatorParticipations)}
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
                                                    <h5 className="margin-0 text-normal text-gray-50 text-uppercase">
                                                        {id.typeDescTxt}
                                                    </h5>
                                                    <p
                                                        className="margin-0 font-sans-1xs text-normal margin-top-05"
                                                        style={{ wordBreak: 'break-word', paddingRight: '15px' }}>
                                                        {id.rootExtensionTxt || '-'}
                                                    </p>
                                                </Grid>
                                            )
                                    )}
                                    {!item.entityIds ||
                                        (item.entityIds?.filter((ent: any) => ent.typeDescTxt).length === 0 && (
                                            <Grid col={12} className="margin-bottom-2">
                                                <h5 className="margin-0 text-normal text-gray-50 text-uppercase">
                                                    Id Types
                                                </h5>
                                                <p
                                                    className="margin-0 font-sans-1xs margin-top-05 text-italic margin-0 text-gray-30"
                                                    style={{ wordBreak: 'break-word', paddingRight: '15px' }}>
                                                    No Data
                                                </p>
                                            </Grid>
                                        ))}
                                </Grid>
                            </Grid>
                        </div>
                    ))}
            </div>
            {Boolean(validSearch && totalResults && data?.length > 0) && (
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

import { Grid, Pagination } from '@trussworks/react-uswds';
import { useEffect, useRef, useState } from 'react';
import { PersonName } from '../../generated/graphql/schema';
import './AdvancedSearch.scss';

type SearchItemsProps = {
    data: any;
    totalResults: number;
    initialSearch: boolean;
    handlePagination: (page: number) => void;
    currentPage: number;
};

export const SearchItems = ({ data, initialSearch, totalResults, handlePagination, currentPage }: SearchItemsProps) => {
    const searchItemsRef: any = useRef();
    const _calculateAge = (birthday: Date) => {
        // birthday is a date
        const ageDifMs = Date.now() - birthday.getTime();
        const ageDate = new Date(ageDifMs); // miliseconds from epoch
        return Math.abs(ageDate.getUTCFullYear() - 1970);
    };

    const handleNext = (page: number) => {
        handlePagination(page - 1);
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

    const [dynamicHeight, setDynamicHeight] = useState(0);
    const heightArr: any = [];
    const OrderedData = ({ data, type }: any) => {
        const heightRef: any = useRef();
        useEffect(() => {
            if (heightRef.current?.clientHeight) {
                heightArr.push(heightRef.current?.clientHeight);
                setDynamicHeight(Math.max(...heightArr));
            }
        }, [heightRef]);

        return (
            <Grid col={6} className="margin-bottom-2 margin-0">
                <div ref={heightRef}>
                    <h5 className="margin-0 text-normal text-gray-50">{type}</h5>
                    {data && data.length > 0 ? (
                        data.map((add: string, ind: number) => (
                            <p
                                key={ind}
                                className="margin-0 font-sans-1xs text-normal margin-top-05"
                                style={{ wordBreak: 'break-word', paddingRight: '15px' }}>
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
        data.map((item: any) => item.locator.phoneNbrTxt && numbers.push(item.locator.phoneNbrTxt));
        return <OrderedData data={numbers} type="Phone Number" />;
    };

    const newOrderEmail = (data: any) => {
        const emails: any = [];
        data.map((item: any) => item.locator.emailAddress && emails.push(item.locator.emailAddress));
        return <OrderedData data={emails} type="Email" />;
    };

    const newOrderAddress = (data: any) => {
        const address: any = [];
        data.map(
            (item: any) =>
                item.classCd === 'PST' &&
                address.push(
                    `${item.locator.streetAddr1 ?? ''} ${item.locator.cityCd ?? ''} ${item.locator.stateCd ?? ''} ${
                        item.locator.zipCd ?? ''
                    } ${item.locator.cntryCd ?? ''}`
                )
        );
        return <OrderedData data={address} type="Address" />;
    };

    return (
        <div className="margin-x-4">
            {Boolean(initialSearch && totalResults && data?.length > 0) && (
                <Grid row className="flex-align-center flex-justify">
                    <p className="margin-0 font-sans-3xs margin-top-05 text-normal text-base">
                        Showing {data.length} of {totalResults}
                    </p>
                    <Pagination
                        style={{ justifyContent: 'flex-end' }}
                        totalPages={Math.ceil(totalResults / 25)}
                        currentPage={currentPage + 1}
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
                            className="padding-3 margin-bottom-3 bg-white border border-base-light radius-md">
                            <Grid row gap={3}>
                                <Grid col={3}>
                                    <Grid row gap={3}>
                                        <Grid
                                            col={12}
                                            style={{
                                                minHeight: 'auto'
                                                // height: dynamicHeight || 'auto'
                                            }}
                                            className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal text-gray-50">LEGAL NAME</h5>
                                            <p
                                                className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                                                style={{ wordBreak: 'break-word' }}>
                                                {item.firstNm}, {item.lastNm}
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
                                                                ({_calculateAge(new Date(item.birthTime))} years)
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
                                <Grid col={6}>
                                    <Grid row gap={3}>
                                        {/* Locator entries */}
                                        {newOrderPhone(item.NBSEntity.entityLocatorParticipations)}
                                        {newOrderEmail(item.NBSEntity.entityLocatorParticipations)}
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
                                        {newOrderAddress(item.NBSEntity.entityLocatorParticipations)}
                                    </Grid>
                                </Grid>
                                <Grid col={3}>
                                    {/* Identifications */}
                                    {item.entityIds.map(
                                        (
                                            id: { typeDescTxt: String; rootExtensionTxt: String; typeCd: String },
                                            idIndex: number
                                        ) => (
                                            <Grid
                                                key={idIndex}
                                                col={12}
                                                className="margin-bottom-2"
                                                style={{
                                                    minHeight: 'auto',
                                                    height: dynamicHeight || 'auto'
                                                }}>
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
                                        (item.entityIds.length === 0 && (
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
            {Boolean(initialSearch && totalResults && data?.length > 0) && (
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

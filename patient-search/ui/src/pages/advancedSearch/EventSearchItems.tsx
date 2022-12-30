/* eslint-disable no-unused-vars */
import { Grid, Pagination } from '@trussworks/react-uswds';
import { useEffect, useRef, useState } from 'react';
import './AdvancedSearch.scss';

type SearchItemsProps = {
    data: any;
    totalResults: number;
    initialSearch: boolean;
    handlePagination: (page: number) => void;
    currentPage: number;
};

export const EventSearchItems = ({
    data,
    initialSearch,
    totalResults,
    handlePagination,
    currentPage
}: SearchItemsProps) => {
    console.log(data, 'data');
    const searchItemsRef: any = useRef();
    const [num, setNum] = useState<any>([]);
    const [email, setEmail] = useState<any>('');

    useEffect(() => {
        const newArrOfNumbers: any = [];
        const newArrOfEmails: any = [];
        data?.map((item: any) => {
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
                        currentPage={currentPage}
                        pathname={'/advanced-search'}
                        onClickNext={() => handleNext(currentPage + 1)}
                        onClickPrevious={() => handleNext(currentPage - 1)}
                        onClickPageNumber={(_, page) => handleNext(page)}
                    />
                </Grid>
            )}
            <div ref={searchItemsRef}>
                {/* {data &&
                    data?.length > 0 &&
                    data?.map((item: any, index: number) => (
                        
                    ))} */}
                <div
                    // key={index}
                    className="padding-x-3 padding-top-3 padding-bottom-2 margin-bottom-3 bg-white border border-base-light radius-md">
                    <Grid row gap={3}>
                        <Grid col={4}>
                            <Grid row gap={3}>
                                <Grid
                                    col={12}
                                    // style={styleObjHeight(index)}
                                    className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal text-gray-50">LEGAL NAME</h5>
                                    <p
                                        className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                                        style={{ wordBreak: 'break-word' }}>
                                        item.firstNm, item.lastNm
                                    </p>
                                </Grid>
                                <Grid col={12} className="margin-bottom-2">
                                    <div className="grid-row flex-align-center">
                                        <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                            DATE OF BIRTH
                                        </h5>
                                        <p className="margin-0 font-sans-1xs text-normal">
                                            11/12/2021
                                            {/* {item.birthTime && (
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
                                            {!item.birthTime && <span className="font-sans-2xs">--</span>} */}
                                            <span className="font-sans-2xs"> 10 years</span>
                                        </p>
                                    </div>
                                    <div className="grid-row flex-align-center">
                                        <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                            SEX
                                        </h5>
                                        <p className="margin-0 font-sans-1xs text-normal">
                                            {/* {item.currSexCd === 'M'
                                                ? 'Male'
                                                : item.currSexCd === 'F'
                                                ? 'Female'
                                                : 'Unknown'} */}
                                            Male
                                        </p>
                                    </div>
                                    <div className="grid-row flex-align-center">
                                        <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                            PATIENT ID
                                        </h5>
                                        <p className="margin-0 font-sans-1xs text-normal">item.localId</p>
                                    </div>
                                </Grid>
                            </Grid>
                        </Grid>
                        <Grid col={3}>
                            <Grid row gap={3}>
                                <Grid
                                    col={12}
                                    // style={styleObjHeight(index)}
                                    className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal text-gray-50">CONDITION</h5>
                                    <p
                                        className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                                        style={{ wordBreak: 'break-word' }}>
                                        Monkeypoxs
                                    </p>
                                </Grid>
                                <Grid col={12} className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                        START DATE
                                    </h5>
                                    <p className="margin-0 font-sans-1xs text-normal">11/12/2021</p>
                                </Grid>
                            </Grid>
                        </Grid>
                        <Grid col={3}>
                            <Grid row gap={3}>
                                <Grid
                                    col={12}
                                    // style={styleObjHeight(index)}
                                    className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal text-gray-50">JURISDICTION</h5>
                                    <p className="margin-0 font-sans-1xs text-normal">Fulton County</p>
                                </Grid>
                                <Grid col={12} className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                        INVESTIGATOR
                                    </h5>
                                    <p className="margin-0 font-sans-1xs text-normal">Appleseed, Jonathon</p>
                                </Grid>
                            </Grid>
                        </Grid>
                        <Grid col={2}>
                            <Grid row gap={3}>
                                <Grid
                                    col={12}
                                    // style={styleObjHeight(index)}
                                    className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal text-gray-50">STATUS</h5>
                                    <p
                                        className="margin-0 font-sans-1xs text-normal status"
                                        style={{ backgroundColor: '#2cb844' }}>
                                        Confirmed
                                    </p>
                                </Grid>
                                <Grid col={12} className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                        NOTIFICATION
                                    </h5>
                                    <p className="margin-0 font-sans-1xs text-normal">--</p>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </div>

                <div
                    // key={index}
                    className="padding-x-3 padding-top-3 padding-bottom-2 margin-bottom-3 bg-white border border-base-light radius-md">
                    <Grid row gap={3}>
                        <Grid col={4}>
                            <Grid row gap={3}>
                                <Grid
                                    col={12}
                                    // style={styleObjHeight(index)}
                                    className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal text-gray-50">LEGAL NAME</h5>
                                    <p
                                        className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                                        style={{ wordBreak: 'break-word' }}>
                                        item.firstNm, item.lastNm
                                    </p>
                                </Grid>
                                <Grid col={12} className="margin-bottom-2">
                                    <div className="grid-row flex-align-center">
                                        <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                            DATE OF BIRTH
                                        </h5>
                                        <p className="margin-0 font-sans-1xs text-normal">
                                            11/12/2021
                                            {/* {item.birthTime && (
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
                                            {!item.birthTime && <span className="font-sans-2xs">--</span>} */}
                                            <span className="font-sans-2xs"> 10 years</span>
                                        </p>
                                    </div>
                                    <div className="grid-row flex-align-center">
                                        <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                            SEX
                                        </h5>
                                        <p className="margin-0 font-sans-1xs text-normal">
                                            {/* {item.currSexCd === 'M'
                                                ? 'Male'
                                                : item.currSexCd === 'F'
                                                ? 'Female'
                                                : 'Unknown'} */}
                                            Male
                                        </p>
                                    </div>
                                    <div className="grid-row flex-align-center">
                                        <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                            PATIENT ID
                                        </h5>
                                        <p className="margin-0 font-sans-1xs text-normal">item.localId</p>
                                    </div>
                                </Grid>
                            </Grid>
                        </Grid>
                        <Grid col={3}>
                            <Grid row gap={3}>
                                <Grid
                                    col={12}
                                    // style={styleObjHeight(index)}
                                    className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal text-gray-50">DOCUMENT TYPE</h5>
                                    <p
                                        className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                                        style={{ wordBreak: 'break-word' }}>
                                        Lab Report
                                    </p>
                                </Grid>
                                <Grid col={12} className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                        DATE RECEIVED
                                    </h5>
                                    <p className="margin-0 font-sans-1xs text-normal">04/26/2019 12:00 AM</p>
                                </Grid>
                                <Grid col={12} className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                        DESCRIPTION
                                    </h5>
                                    <p className="margin-0 font-sans-1xs text-normal">
                                        RPR Titer reactive =1:2048 Ratio - (Final)
                                    </p>
                                </Grid>
                            </Grid>
                        </Grid>
                        <Grid col={3}>
                            <Grid row gap={3}>
                                <Grid
                                    col={12}
                                    // style={styleObjHeight(index)}
                                    className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal text-gray-50">REPORTING FACILITY</h5>
                                    <p className="margin-0 font-sans-1xs text-normal">Piedmont Hospital</p>
                                </Grid>
                                <Grid col={12} className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                        ORDERING PROVIDOR
                                    </h5>
                                    <p className="margin-0 font-sans-1xs text-normal">DR Indiana Jones</p>
                                </Grid>
                                <Grid col={12} className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                        JURISDICTION
                                    </h5>
                                    <p className="margin-0 font-sans-1xs text-normal">Fulton County</p>
                                </Grid>
                            </Grid>
                        </Grid>
                        <Grid col={2}>
                            <Grid row gap={3}>
                                <Grid
                                    col={12}
                                    // style={styleObjHeight(index)}
                                    className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal text-gray-50">STATUS</h5>
                                    <p
                                        className="margin-0 font-sans-1xs text-normal status"
                                        style={{ backgroundColor: '#2cb844' }}>
                                        Confirmed
                                    </p>
                                </Grid>
                                <Grid col={12} className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                        ASSOCIATED WITH
                                    </h5>
                                    <p className="margin-0 font-sans-1xs text-normal">--</p>
                                </Grid>
                                <Grid col={12} className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                        LOCAL ID
                                    </h5>
                                    <p className="margin-0 font-sans-1xs text-normal">OBS10012021GAC</p>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </div>
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

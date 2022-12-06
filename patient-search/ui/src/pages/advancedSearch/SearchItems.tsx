import { Grid, Pagination } from '@trussworks/react-uswds';
import { useEffect, useRef, useState } from 'react';
import { Locator, LocatorParticipations, Maybe, PersonName } from '../../generated/graphql/schema';
import './AdvancedSearch.scss';

type SearchItemsProps = {
    data: any;
    totalResults: number | undefined;
    initialSearch: boolean;
};

export const SearchItems = ({ data, initialSearch, totalResults }: SearchItemsProps) => {
    const searchItemsRef: any = useRef();
    const [currentPage, setCurrentPage] = useState<number>(1);
    const _calculateAge = (birthday: Date) => {
        // birthday is a date
        const ageDifMs = Date.now() - birthday.getTime();
        const ageDate = new Date(ageDifMs); // miliseconds from epoch
        return Math.abs(ageDate.getUTCFullYear() - 1970);
    };

    const handleNext = (type?: any, page?: number) => {
        switch (type) {
            case 'current':
                setCurrentPage(page || 1);
                break;
            case 'next':
                setCurrentPage(currentPage + 1);
                break;
            case 'prev':
                setCurrentPage(currentPage - 1);
                break;
        }
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

    useEffect(() => {
        if (currentPage >= 10) {
            return;
        }
        setCurrentPage(currentPage);
    }, [currentPage]);

    function getLocatorTypeDisplay(classCd: Maybe<String> | undefined, locator: Maybe<Locator> | undefined): String {
        switch (classCd) {
            case 'PST':
                return 'POSTAL ADDRESS';
            case 'TELE':
                if (locator?.phoneNbrTxt) {
                    return 'PHONE NUMBER';
                } else if (locator?.emailAddress) {
                    return 'EMAIL';
                } else {
                    return 'UNKNOWN';
                }
            default:
                return classCd || '';
        }
    }

    function getLocatorDisplayValue(classCd: Maybe<String> | undefined, locator: Maybe<Locator> | undefined): String {
        if (!locator) {
            return '-';
        }
        switch (classCd) {
            case 'TELE':
                return locator.phoneNbrTxt || locator.emailAddress || locator.streetAddr1 || '';
            case 'PST':
                return `${locator.streetAddr1 ?? ''} ${locator.cityCd ?? ''} ${locator.stateCd ?? ''} ${
                    locator.zipCd ?? ''
                } ${locator.cntryCd}`;
            default:
                return '';
        }
    }

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
            .forEach((n) => (otherNames = otherNames + ` ${n.firstNm} ${n.lastNm}`));
        return otherNames;
    }

    return (
        <div className="margin-x-4">
            {initialSearch && data?.total > 0 && (
                <Grid row className="flex-align-center flex-justify">
                    <p className="margin-0 font-sans-3xs margin-top-05 text-normal text-base">
                        Showing {data.content?.length} of {data.total}
                    </p>
                    <Pagination
                        style={{ justifyContent: 'flex-end' }}
                        totalPages={Math.ceil(data.content?.length / data.total)}
                        currentPage={currentPage}
                        pathname={'/advanced-search'}
                        onClickNext={() => handleNext('next')}
                        onClickPrevious={() => handleNext('prev')}
                        onClickPageNumber={(_, page) => handleNext('current', page)}
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
                            <Grid row gap={6}>
                                <Grid col={3} className="margin-bottom-2 large-col">
                                    <h5 className="margin-0 text-normal text-gray-50">LEGAL NAME</h5>
                                    <p className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break">
                                        {item.firstNm}, {item.lastNm}
                                    </p>
                                </Grid>
                                {/* Locator entries */}
                                {item.NBSEntity.entityLocatorParticipations.map(
                                    (locatorParticipation: LocatorParticipations, idIndex: number) => (
                                        <Grid key={idIndex} col={3} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal text-gray-50">
                                                {getLocatorTypeDisplay(
                                                    locatorParticipation.classCd,
                                                    locatorParticipation.locator
                                                )}
                                            </h5>
                                            <p className="margin-0 font-sans-1xs text-normal margin-top-05">
                                                {/* {new Date(item.addTime).toLocaleDateString('en-US')} */}
                                                {getLocatorDisplayValue(
                                                    locatorParticipation.classCd,
                                                    locatorParticipation.locator
                                                )}
                                            </p>
                                        </Grid>
                                    )
                                )}

                                {/* Identifications */}
                                {item.entityIds.map(
                                    (
                                        id: { typeDescTxt: String; rootExtensionTxt: String; typeCd: String },
                                        idIndex: number
                                    ) => (
                                        <Grid key={idIndex} col={3} className="margin-bottom-2">
                                            <h5 className="margin-0 text-normal text-gray-50">
                                                {id.typeCd.replaceAll('_', ' ')}
                                            </h5>
                                            <p className="margin-0 font-sans-1xs text-normal margin-top-05">
                                                {/* {new Date(item.addTime).toLocaleDateString('en-US')} */}
                                                {id.rootExtensionTxt || '-'}
                                            </p>
                                        </Grid>
                                    )
                                )}
                                <Grid col={3} className="margin-bottom-2 large-col">
                                    <div className="grid-row flex-align-center">
                                        <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                            DATE OF BIRTH
                                        </h5>
                                        <p className="margin-0 font-sans-1xs text-normal">
                                            {new Date(item.birthTime).toLocaleDateString('en-US')}
                                            <span className="font-sans-2xs">
                                                {' '}
                                                ({_calculateAge(new Date(item.birthTime))} years)
                                            </span>
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
                                <Grid col={3} className="margin-bottom-2">
                                    <h5 className="margin-0 text-normal text-gray-50">OTHER NAMES</h5>
                                    <p className="margin-0 font-sans-1xs text-gray-50 text-normal margin-top-05">
                                        {getOtherNames(item, item.names) || 'No data'}
                                    </p>
                                </Grid>
                            </Grid>
                        </div>
                    ))}
            </div>
            {initialSearch && totalResults && data?.length > 0 && (
                <Pagination
                    style={{ justifyContent: 'flex-end' }}
                    totalPages={Math.ceil(totalResults / data?.length)}
                    currentPage={currentPage}
                    pathname={'/advanced-search'}
                    onClickNext={() => handleNext('next')}
                    onClickPrevious={() => handleNext('prev')}
                    onClickPageNumber={(_, page) => handleNext('current', page)}
                />
            )}
        </div>
    );
};

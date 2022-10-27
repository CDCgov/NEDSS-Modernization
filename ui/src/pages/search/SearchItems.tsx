import { Grid, Pagination } from '@trussworks/react-uswds';
import { useEffect, useState } from 'react';
import './search.scss';

type SearchItemsProps = {
    data: any;
};

export const SearchItems = ({ data }: SearchItemsProps) => {
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

    useEffect(() => {
        if (currentPage >= 10) {
            return;
        }
        setCurrentPage(currentPage);
    }, [currentPage]);

    return (
        <div className="margin-4">
            <Pagination
                style={{ justifyContent: 'flex-end' }}
                totalPages={10}
                currentPage={currentPage}
                pathname={'/search'}
                onClickNext={() => handleNext('next')}
                onClickPrevious={() => handleNext('prev')}
                onClickPageNumber={(_, page) => handleNext('current', page)}
            />
            {data &&
                data.length > 0 &&
                data.map((item: any, index: number) => (
                    <div key={index} className="padding-5 margin-bottom-3 bg-white border border-base-light radius-md">
                        <Grid row>
                            <Grid col={3} className="margin-bottom-2">
                                <h5 className="margin-0 text-normal text-gray-50">LEGAL NAME</h5>
                                <p className="margin-0 font-sans-md margin-top-05 text-bold text-primary">
                                    {item.firstNm}, {item.lastNm}
                                </p>
                            </Grid>
                            <Grid col={3} className="margin-bottom-2">
                                <h5 className="margin-0 text-normal text-gray-50">START DATE</h5>
                                <p className="margin-0 font-sans-md margin-top-05 text-bold text-primary">
                                    {new Date(item.addTime).toLocaleDateString('en-US')}
                                </p>
                            </Grid>
                            <Grid col={3} className="margin-bottom-2">
                                <h5 className="margin-0 text-normal text-gray-50">JURISDICTION</h5>
                                <p className="margin-0 font-sans-md margin-top-05 text-bold text-primary">
                                    Fulton County
                                </p>
                            </Grid>
                            <Grid col={3} className="margin-bottom-2">
                                <h5 className="margin-0 text-normal text-gray-50">STATUS</h5>
                                <p className="margin-0 margin-top-05 padding-y-05 padding-x-1 confirm-bage text-semibold width radius-pill text-white bg-mint">
                                    Confirmed
                                </p>
                            </Grid>
                            <Grid col={3} className="margin-bottom-2">
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
                        </Grid>
                    </div>
                ))}
            <Pagination
                style={{ justifyContent: 'flex-end' }}
                totalPages={10}
                currentPage={currentPage}
                pathname={'/search'}
                onClickNext={() => handleNext('next')}
                onClickPrevious={() => handleNext('prev')}
                onClickPageNumber={(_, page) => handleNext('current', page)}
            />
        </div>
    );
};

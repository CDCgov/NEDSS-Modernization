import './PatientResult.scss';

import { ReactNode } from 'react';
import classNames from 'classnames';
import { Grid } from '@trussworks/react-uswds';
import {
    PatientSearchResultIdentification,
    PatientSearchResult,
    PatientSearchResultName,
    PatientSearchResultAddress
} from 'generated/graphql/schema';
import { formattedName } from 'utils';
import { internalizeDate } from 'date';
import { displayName } from 'name';
import { displayAddress } from 'address/display';
import { NoData } from 'components/NoData';

type PatientResultProps = {
    result: PatientSearchResult;
    onSelected: (result: PatientSearchResult) => void;
};

const PatientResult = ({ result, onSelected }: PatientResultProps) => {
    return (
        <Grid row gap={3}>
            <Grid col={4}>
                <Grid row gap={3}>
                    <Grid col={12} className="margin-bottom-2">
                        <h5 className="margin-0 text-normal font-sans-3xs text-gray-50">LEGAL NAME</h5>
                        <a
                            onClick={() => onSelected(result)}
                            tabIndex={0}
                            className="margin-0 font-sans-md margin-top-05 text-bold text-primary word-break"
                            style={{
                                wordBreak: 'break-word',
                                cursor: 'pointer'
                            }}>
                            {!result.legalName
                                ? 'No Data'
                                : formattedName(result.legalName?.last, result.legalName?.first)}
                        </a>
                    </Grid>
                    <Grid col={12}>
                        <ResultItem label="Date of birth">
                            {result.birthday && `${internalizeDate(result.birthday)} (${result.age})`}
                        </ResultItem>
                        <ResultItem label="Sex">{result.gender}</ResultItem>
                        <ResultItem label="Patient Id">{result.shortId}</ResultItem>
                    </Grid>
                </Grid>
            </Grid>
            <Grid col={5}>
                <Grid row gap={3}>
                    <Grid col={6}>
                        <OrderedData data={result.phones} type="PHONE NUMBER" />
                    </Grid>
                    <Grid col={6}>
                        <OrderedData data={result.emails} type="EMAIL" />
                    </Grid>
                    <Grid col={6}>
                        <ResultItem label="Other names" orientation="vertical">
                            {displayNames(result, result.names)}
                        </ResultItem>
                    </Grid>
                    <Grid col={6}>{renderAddresses(result.addresses)}</Grid>
                </Grid>
            </Grid>
            <Grid col={3}>
                {result.identification.map((id: PatientSearchResultIdentification, index: number) => (
                    <Grid key={index} col={12}>
                        <ResultItem label={id.type} orientation="vertical">
                            {id.value}
                        </ResultItem>
                    </Grid>
                ))}
                {result.identification.length === 0 && (
                    <Grid col={12}>
                        <ResultItem label="Id types" orientation="vertical" />
                    </Grid>
                )}
            </Grid>
        </Grid>
    );
};

const displayNames = (result: PatientSearchResult, names: PatientSearchResultName[]): string => {
    const legalName = result.legalName;
    return names
        .filter((name) => name?.first != legalName?.first || name?.last != legalName?.last)
        .map(displayName())
        .join('\n');
};

const renderAddresses = (addresses: PatientSearchResultAddress[]) => {
    const formatted: string[] = addresses.map(displayAddress);
    return <OrderedData data={formatted} type="ADDRESS" />;
};

const OrderedData = ({ data, type }: { data: string[]; type: string }) => {
    return (
        <ResultItem label={type} orientation="vertical">
            {data.length > 0 && data.map((value: string, index: number) => <p key={index}>{value}</p>)}
        </ResultItem>
    );
};

type ResultItemProps = {
    label: string;
    children?: ReactNode;
    orientation?: 'vertical' | 'horizontal';
};

const ResultItem = ({ label, orientation = 'horizontal', children }: ResultItemProps) => {
    return (
        <div className={classNames('patient-search-result-item', { vertical: orientation === 'vertical' })}>
            <ResultItemLabel>{label}</ResultItemLabel>
            <ResultItemValue>{children}</ResultItemValue>
        </div>
    );
};

type ResultItemLabelProps = { children: string };

const ResultItemLabel = ({ children }: ResultItemLabelProps) => (
    <span className="patient-search-result-item-label">{children}</span>
);

type ResultValueProps = {
    children?: ReactNode;
};

const ResultItemValue = ({ children }: ResultValueProps) => {
    if (!children) {
        return <NoData />;
    } else if (typeof children === 'string' || typeof children === 'number' || typeof children === 'boolean') {
        return <p>{children}</p>;
    } else {
        return <>{children}</>;
    }
};

export { PatientResult };

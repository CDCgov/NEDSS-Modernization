import { MaybeLabeledValue } from 'design-system/value';
import { ResultedTest } from 'generated';
import { displayProvider, Provider } from 'libs/provider';

export const renderMorbidity = (
    condition?: string,
    resultedTests?: Array<ResultedTest>,
    treatments?: Array<string>
) => {
    return (
        <>
            <strong>{condition}</strong>
            <br />
            {(resultedTests?.length ?? 0) > 0 && (
                <>
                    {resultedTests?.map((result) => (
                        <>
                            <br />
                            <strong>{result.name}:</strong>
                            <br />
                            {result.result}
                            <br />
                        </>
                    ))}
                </>
            )}

            {(treatments?.length ?? 0) > 0 && (
                <>
                    <br />
                    <strong>Treatment information: </strong>
                    <strong>
                        {treatments?.map((treatment) => (
                            <li key={treatment}>{treatment}</li>
                        ))}
                    </strong>
                </>
            )}
        </>
    );
};

export const renderFacilityProvider = (
    reportingFacility?: string,
    orderingProvider?: Provider,
    sendingFacility?: string,
    orderingFacility?: string
) => {
    return (
        <>
            <MaybeLabeledValue orientation="vertical" label="Reporting facility:">
                {reportingFacility}
            </MaybeLabeledValue>
            <MaybeLabeledValue orientation="vertical" label="Ordering provider:">
                {displayProvider(orderingProvider)}
            </MaybeLabeledValue>
            <MaybeLabeledValue orientation="vertical" label="Sending facility:">
                {sendingFacility}
            </MaybeLabeledValue>
            <MaybeLabeledValue orientation="vertical" label="Ordering facility:">
                {orderingFacility}
            </MaybeLabeledValue>
        </>
    );
};

export const renderResultedTests = (resultedTests?: Array<ResultedTest>) => {
    return (
        <>
            {resultedTests?.map((test) => (
                <>
                    <strong>{test.name}</strong>
                    <br />
                    {test.result}
                    <br />
                    {test.reference && (
                        <>
                            <strong>Reference Range: </strong>
                            {test.reference}
                            <br />
                        </>
                    )}
                    {resultedTests.length > 1 && <br />}
                </>
            ))}
        </>
    );
};

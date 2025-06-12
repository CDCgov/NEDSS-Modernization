import { DisplayableSimpleName, ResultedTest } from 'generated';

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
                    <strong>{treatments?.map((treatment) => <li key={treatment}>{treatment}</li>)}</strong>
                </>
            )}
        </>
    );
};

export const renderFacilityProvider = (
    reportingFacility?: string,
    orderingProvider?: DisplayableSimpleName,
    sendingFacility?: string,
    orderingFacility?: string
) => {
    return (
        <>
            {reportingFacility && (
                <>
                    <strong>Reporting facility:</strong>
                    <br />
                    {reportingFacility}
                    <br />
                </>
            )}

            {orderingProvider && (
                <>
                    <strong>Ordering provider:</strong>
                    <br />
                    {orderingProvider.prefix} {orderingProvider.first} {orderingProvider.last}
                    <br />
                </>
            )}
            {sendingFacility && (
                <>
                    <strong>Sending facility:</strong>
                    <br />
                    {sendingFacility}
                </>
            )}
            {orderingFacility && (
                <>
                    <strong>Ordering facility:</strong>
                    <br />
                    {orderingFacility}
                </>
            )}
        </>
    );
};

export const renderLabReports = (resultedTests?: Array<ResultedTest>) => {
    return (
        <>
            {resultedTests?.map((test) => (
                <>
                    <strong>{test.name}</strong>
                    <br />
                    {test.result}
                    {test.reference && (
                        <>
                            <br />
                            <strong>Reference Range: </strong>
                            {test.reference}
                        </>
                    )}
                    <br />
                </>
            ))}
        </>
    );
};

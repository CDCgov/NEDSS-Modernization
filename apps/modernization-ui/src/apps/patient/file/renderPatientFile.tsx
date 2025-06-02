import { DocumentRequiringReview } from 'generated';

export const renderMorbidity = (value: DocumentRequiringReview) => {
    return (
        <>
            <strong>{value.condition}</strong>
            <br />
            {(value.resultedTests?.length ?? 0) > 0 && (
                <>
                    {value.resultedTests?.map((result) => (
                        <>
                            <br />
                            <strong>{result.name}:</strong>
                            <br />
                            {result.result}
                            <br />
                            {result.reference}
                        </>
                    ))}
                </>
            )}

            {(value.treatments?.length ?? 0) > 0 && (
                <>
                    <br />
                    <strong>Treatment information: </strong>
                    <strong>{value.treatments?.map((treatment) => <li key={treatment}>{treatment}</li>)}</strong>
                </>
            )}
        </>
    );
};

export const renderFacilityProvider = (value: DocumentRequiringReview) => {
    return (
        <>
            {value.reportingFacility && (
                <>
                    <strong>Reporting facility:</strong>
                    <br />
                    {value.reportingFacility}
                    <br />
                </>
            )}

            {value.orderingProvider && (
                <>
                    <strong>Ordering provider:</strong>
                    <br />
                    {value.orderingProvider.prefix}
                    {value.orderingProvider.first} {value.orderingProvider.last}
                    <br />
                </>
            )}
            {value.sendingFacility && (
                <>
                    <strong>Sending facility:</strong>
                    <br />
                    {value.sendingFacility}
                </>
            )}
        </>
    );
};

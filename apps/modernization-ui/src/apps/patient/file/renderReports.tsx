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

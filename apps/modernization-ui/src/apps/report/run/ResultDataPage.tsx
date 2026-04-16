import { ReportResult } from 'generated';

const ResultDataPage = ({ result }: { result: ReportResult }) => {
    return (
        <main>
            <h1>{result.header}</h1>
            <h2>{result.subheader}</h2>
            <section>{result.description}</section>
            {result.content}
        </main>
    );
};

export { ResultDataPage };

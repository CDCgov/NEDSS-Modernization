import { Config } from 'config';

export const useExportMatches = () => {
    const exportMatchesCSV = async (sort?: string) => {
        try {
            const url = new URL(`${Config.deduplicationUrl}/merge/export/csv`);
            if (sort) {
                url.searchParams.append('sort', sort);
            }

            const response = await fetch(url.toString(), {
                method: 'GET',
                headers: {
                    Accept: 'text/csv'
                }
            });

            if (!response.ok) {
                console.error('Failed to export CSV');
                return;
            }

            const blob = await response.blob();
            const urlBlob = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = urlBlob;
            a.download = 'matches_requiring_review.csv';
            a.click();
            window.URL.revokeObjectURL(urlBlob);
        } catch (error) {
            console.error('Error exporting CSV:', error);
        }
    };

    const exportMatchesPDF = async (sort?: string) => {
        try {
            const url = new URL(`${Config.deduplicationUrl}/merge/export/pdf`);
            if (sort) {
                url.searchParams.append('sort', sort);
            }

            const response = await fetch(url.toString(), {
                method: 'GET',
                headers: {
                    Accept: 'application/pdf'
                }
            });

            if (!response.ok) {
                console.error('Failed to export PDF');
                return;
            }

            const blob = await response.blob();
            const urlBlob = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = urlBlob;
            a.download = 'matches_requiring_review.pdf';
            a.click();
            window.URL.revokeObjectURL(urlBlob);
        } catch (error) {
            console.error('Error exporting PDF:', error);
        }
    };

    return {
        exportCSV: exportMatchesCSV,
        exportPDF: exportMatchesPDF
    };
};

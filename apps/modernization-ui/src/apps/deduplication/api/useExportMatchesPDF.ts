import { Config } from 'config';

export const useExportMatchesPDF = () => {
    const exportMatchesPDF = async () => {
        try {
            const response = await fetch(`${Config.deduplicationUrl}/merge/export/pdf`, {
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
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'matches_requiring_review.pdf';
            a.click();
            window.URL.revokeObjectURL(url);
        } catch (error) {
            console.error('Error exporting PDF:', error);
        }
    };

    return {
        exportMatchesPDF
    };
};

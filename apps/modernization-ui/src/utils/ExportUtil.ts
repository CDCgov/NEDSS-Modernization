import { Filter, externalize } from 'filters';
import { OpenAPI } from '../generated/core/OpenAPI';

export const downloadPageLibraryPdf = (search: string, filters: Filter[], sort?: string) => {
    // auto generated methods dont allow direct conversion to blob
    fetch(`${OpenAPI.BASE}/nbs/page-builder/api/v1/pages/pdf?sort=${sort ?? 'id,asc'}`, {
        method: 'POST',
        headers: {
            Accept: 'application/pdf',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ search: search, filters: externalize(filters) })
    })
        .then((response) => response.blob())
        .then((blob) => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'PageLibrary.pdf';
            a.click();
        });
};

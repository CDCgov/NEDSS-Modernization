const savePassConfiguration = async (data: {
    name: string;
    active: boolean;
    blockingCriteria: { method: string; field: string }[];
    matchingCriteria: { method: string; field: string }[];
    lowerBound?: number;
    upperBound?: number;
}) => {
    console.log('Attempting to save pass configuration with data:', data);

    try {
        const response = await fetch('http://localhost:8080/api/configurations/save-pass', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        console.log('Response status:', response.status);
        console.log('Response headers:', response.headers);

        if (response.ok) {
            const result = await response.json();
            console.log('Pass configuration saved successfully:', result);
            return result;
        } else {
            const errorText = await response.text();
            console.error('Failed to save pass configuration. Status:', response.status, 'Error:', errorText);
            throw new Error(errorText);
        }
    } catch (error) {
        console.error('Network error or fetch failed:', error);
        throw error;
    }
};

export default savePassConfiguration;

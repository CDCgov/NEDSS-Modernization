// import axios from 'axios';
// // import { ActivityLog } from './schema';
//
// interface ApiResponse {
//     data: ActivityLog[];
//     success: boolean;
// }
//
// class ElrActivityLogService {
//     static async fetchActivityLogs(startTime: string, endTime: string): Promise<ActivityLog[]> {
//         try {
//             const docType = 11648804;
//             console.log(startTime);
//             // Define the headers that your API requires
//             const headers = {
//                 clientid: 'di-keycloak-client',
//                 clientsecret: 'nrh2xa8HtIn4qgczvGb8ITsahNeXDANU',
//                 'bearer-token':
//                     'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJwaUhUS0xuRUJwaTlDaTZmUmhzNGJlZV9xRjM4WVlqTXRWRUlNQ3NWR3JzIn0.eyJleHAiOjE3Mzk5NzQwMjksImlhdCI6MTczOTkzODAyOSwianRpIjoiZjJiZjM2MDMtYTBiNS00ODg4LTk5ODgtNWE3NjgxMGRhODUwIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDg0L3JlYWxtcy9OQlMiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiZmE1NzdhMDktZThkZC00ZmY4LTg4MjItOGQ0NDk4ZWMzOWY5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZGkta2V5Y2xvYWstY2xpZW50IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIvKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLW5icyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTkyLjE2OC42NS4xIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LWRpLWtleWNsb2FrLWNsaWVudCIsImNsaWVudEFkZHJlc3MiOiIxOTIuMTY4LjY1LjEiLCJjbGllbnRfaWQiOiJkaS1rZXljbG9hay1jbGllbnQifQ.frdFPbl8LJVnf1D5rVDlFJhW9R7Pn0Cfhi_3teHWp9j8ro1J83MVRKjPkm-N8w4QFxiaGp65RPeGaVPWDmVuywe-zKsxIMnZRpGQIV_oQ0R5PNP-EmRwBaA3V814dFcWC0HvTRmYhOyF0JUsA5WZJdrXSuBNqkgvwRg7qpv4MulXu0dV7CX2q6jOcJDYP9WTqABjtzIBk6yCfCzfFYnBNRndsONPSG1Q9jjQFlGc6nq-Oe2vwdHhrDUWcKAi8z0-cr2RHhck6oqSAqfcofkCbE95mW_nDDoupyOkijbwBZwkOlR5PRzapJgDGbbLmNUlTjthOM8Q4_QX2ilKOAtAZQ'
//             };
//
//             // Send the GET request with the query parameters and headers
//             const response: ApiResponse = await axios.get('http://localhost:8081/api/elrs/status-details', {
//                 params: {
//                     docType,
//                     startTime,
//                     endTime
//                 },
//                 headers
//             });
//
//             // Ensure the response is successful and contains data
//             if (response.success) {
//                 return response.data;
//             }
//
//             throw new Error('Failed to fetch activity logs');
//         } catch (error) {
//             console.error('Error fetching activity logs:', error);
//             return [];
//         }
//     }
// }
//
// export default ElrActivityLogService;

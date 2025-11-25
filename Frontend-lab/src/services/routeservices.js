import api from './api.js';

export default {


 createroute(routeData) {
        return api.post('/api/route/planroute', routeData);
    },


    inefficientRoutes() {
        return api.get('/api/route/inefficient-routes');
    },

    efficiency() {
        return api.get('/api/route/efficiency');
    },

    wasteperformance() {
        return api.get('/api/route/waste-performance');
    },

    updatecontainerwigth(routeId, routeData) {
        return api.put(`/api/route/update-container-weight/${routeId}`, routeData);
    }

};
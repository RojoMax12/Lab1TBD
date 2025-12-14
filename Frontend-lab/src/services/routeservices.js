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
    },

    getAllRoutes() {
        return api.get('/api/route/');
    },

    getAllRouterBydriverIdPending(Iddriver) {
        return api.get(`/api/route/driver/pending/${Iddriver}`);
    },

    getAllRouterBydriverIdFinish(Iddriver) {
        return api.get(`/api/route/driver/finish/${Iddriver}`);
    },

    getRouteById(routeId) {
        return api.get(`/api/route/${routeId}`);
    },

    deleteRoute(routeId) {
        return api.delete(`/api/route/${routeId}`);
    },

    updateRoute(routeId, routeData) {
        return api.put(`/api/route/${routeId}`, routeData);
    },

    updateRouteStatus(routeId, status) {
        return api.put(`/api/route/${routeId}/${status}`);
    }
}
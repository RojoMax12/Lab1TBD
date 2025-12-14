import api from './api.js';

export default {


    createRouteContainer(){
        return api.post('/api/routecontainer/create')
    },

    getAllRouteContainers(){
        return api.get('/api/routecontainer/')
    },

    getRouteContainerById(id){
        return api.get(`/api/routecontainer/${id}`);

    },

    getRouteContainerByContainer(id){
        return api.get(`/api/routecontainer/container/${id}`);

    },

    routecontainer(id){
        return api.delete(`/api/routecontainer/${id}`);
    }




}
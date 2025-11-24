import api from './api.js';

export default {

    // Obtener todos los administradores
    getAllAdmins() {
        return api.get('/admins');
    }

    // Obtener un administrador por ID
    ,
    getAdminById(adminId) {
        return api.get(`/admins/${adminId}`);

    }

    // Crear un nuevo administrador
    ,
    createAdmin(adminData) {
        return api.post('/admins/', adminData);
    }
};
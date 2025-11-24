import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/json',
    },
    withCredentials: true,
    timeout: 5000 // 5 segundos de timeout
});

// Interceptor para agregar el token JWT si existe
api.interceptors.request.use(
    config => {
        const token = localStorage.getItem('jwt');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => Promise.reject(error)
);

// Interceptor para manejar errores
api.interceptors.response.use(
    response => response,
    error => {
        if (error.code === 'ERR_NETWORK') {
            console.error('Error de conexión con el servidor. Por favor, verifica que el backend esté en ejecución.');
        } else if (error.response) {
            console.error('Error en la respuesta del servidor:', error.response.data);
        } else if (error.request) {
            console.error('No se recibió respuesta del servidor:', error.request);
        } else {
            console.error('Error en la petición:', error.message);
        }
        return Promise.reject(error);
    }
);

export default api;
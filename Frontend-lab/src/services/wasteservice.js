import api from './api.js';

export default {
  getAllWaste() {
    return api.get('/api/waste/');
  }
};

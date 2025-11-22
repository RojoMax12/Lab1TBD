import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: null
  }),
  actions: {
    initFromStorage() {
      try {
        this.token = localStorage.getItem('jwt') || null
      } catch (e) {
        this.token = null
      }
    },
    setToken(token) {
      this.token = token
      try { localStorage.setItem('jwt', token) } catch (e) { console.warn('No se pudo guardar token', e) }
    },
    clear() {
      this.token = null
      try { localStorage.removeItem('jwt') } catch (e) { console.warn('No se pudo eliminar token', e) }
    }
  }
})

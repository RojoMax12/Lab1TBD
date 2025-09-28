import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import HomeDriverView from '../views/HomeDriverView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'home', component: HomeView },
    { path: '/home-drive', name: 'home-drive', component: HomeDriverView }
  ]
})

export default router
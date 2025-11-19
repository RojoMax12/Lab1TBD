import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import HomeDriverView from '../views/HomeDriverView.vue'
import HomeAdminView from '../views/HomeAdminView.vue'
import RouteTakenView from '../views/RouteTakenView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'home', component: HomeView },
    { path: '/driver', name: 'home-driver', component: HomeDriverView },
    { path: '/admin', name: 'home-admin', component: HomeAdminView },
    { path: '/route-taken', name: 'route-taken', component: RouteTakenView }
  ]
})

export default router
import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '../views/HomeView.vue'
import HomeDriverView from '../views/User/HomeDriverView.vue'
import HomeAdminView from '../views/AdminView/HomeAdminView.vue'
import RouteTakenView from '../views/User/RouteTakenView.vue'
import UsersView from '../views/AdminView/UsersView.vue'
import ContainersView from '../views/AdminView/ContainerView.vue'
import RouteView from '../views/AdminView/RouteView.vue'
import AdminsView from '../views/AdminView/AdminsView.vue'
import RouteAssignedView from '@/views/User/RouteAssignedView.vue'
import CentralView from '@/views/AdminView/CentralView.vue'


const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'home', component: HomeView },
    { path: '/driver', name: 'home-driver', component: HomeDriverView },
    { path: '/admin', name: 'home-admin', component: HomeAdminView },
    { path: '/route-taken', name: 'route-taken', component: RouteTakenView },
    { path: '/users', name: 'users', component: UsersView },
    { path: '/container', name: 'containers', component: ContainersView },
    { path: '/route', name: 'route', component: RouteView },
    { path: '/admins', name: 'admins', component: AdminsView },
    { path: '/route-assigned', name: 'route-assigned', component: RouteAssignedView },
    { path: '/centrals', name: 'centrals', component: CentralView }
  ]
})

export default router

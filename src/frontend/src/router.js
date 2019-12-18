import Vue from 'vue'
import VueRouter from "vue-router";

import SignUpForm from "./components/user/SignUpForm";
import StudyGeneration from "./components/study/StudyGeneration";
import StudyRecruitment from "./components/study/StudyRecruitment";
import StudyOngoing from "./components/study/StudyOngoing";
import StudyFinished from "./components/study/StudyFinished";
import UserProfile from "./components/user/UserProfile";
import ParticipatingStudy from "./components/user/ParticipatingStudy";
import UserUpdateForm from "./components/user/UserUpdateForm";
import GitRedirect from "./components/git/GitRedirect";
import Map from "./components/map/Map";
import StudyDetail from "./components/study/StudyDetail";

import Main from "./components/Main";
import LoginForm from "./components/user/LoginForm";

Vue.use(VueRouter);

const mainChild = [
  {
    name: `StudyGeneration`,
    path: `generation`,
    component: StudyGeneration,
  },
  {
    name: `StudyRecruitment`,
    path: `recruitment`,
    component: StudyRecruitment,
  },
  {
    name: `StudyOngoing`,
    path: `ongoing`,
    component: StudyOngoing,
  },
  {
    name: `StudyFinished`,
    path: `finished`,
    component: StudyFinished,
  },
  {
    name: `StudyDetail`,
    path: `study-detail`,
    component: StudyDetail,
  },
  {
    name: `UserProfile`,
    path: `profile`,
    component: UserProfile,
  },
  {
    name: `ParticipatingStudy`,
    path: `participating-study`,
    component: ParticipatingStudy,
  },
  {
    name: `UserUpdateForm`,
    path: `update`,
    component: UserUpdateForm,
  },
];

const routes = [
  {
    name: `Main`,
    path: `/`,
    component: Main,
    children: mainChild,
  },
  {
    name: `LoginForm`,
    path: `/login`,
    component: LoginForm,
  },
  {
    name: `SignUpForm`,
    path: `/signup`,
    component: SignUpForm,
  },
  {
    name: `gitredirect`,
    path: `/gitredirect`,
    component: GitRedirect,
  },
  {
    name: `map`,
    path: `/map`,
    component: Map,
  },
];

export default new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: routes,
});
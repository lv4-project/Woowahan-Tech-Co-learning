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

import Main from "./components/Main";
import LoginForm from "./components/user/LoginForm";
import StudyOutputGeneration from "./components/studyoutput/StudyOutputGeneration";
import StudyOngoingDetail from "./components/study/StudyOngoingDetail";
import StudyOutputEdit from "./components/studyoutput/StudyOutputEdit";
import StudySearch from "./components/study/StudySearch";

import request from 'request'

Vue.use(VueRouter);

const mainChild = [
  {
    name: `StudyGeneration`,
    path: `/generation`,
    component: StudyGeneration,
  },
  {
    name: `StudyRecruitment`,
    path: `/recruitment`,
    component: StudyRecruitment,
  },
  {
    name: `StudyOngoing`,
    path: `/ongoing`,
    component: StudyOngoing,
  },
  {
    name: `StudyFinished`,
    path: `/finished`,
    component: StudyFinished,
  },
  {
    name: `StudySearch`,
    path: `/search`,
    component: StudySearch,
  },
  {
    name: `UserProfile`,
    path: `/profile`,
    component: UserProfile,
  },
  {
    name: `ParticipatingStudy`,
    path: `/participating-study`,
    component: ParticipatingStudy,
  },
  {
    name: `UserUpdateForm`,
    path: `/update`,
    component: UserUpdateForm,
  },
  {
    name: `StudyOngoingDetail`,
    path: `/studies/:studyId`,
    component: StudyOngoingDetail,
  },
];

const routes = [
  {
    name: `Main`,
    path: `/`,
    component: Main,
    meta: {authRequired: true},
    children: mainChild,
  },
  {
    name: `LoginForm`,
    path: `/login`,
    meta: {authRequired: false},
    component: LoginForm,
  },
  {
    name: `SignUpForm`,
    path: `/signup`,
    meta: {authRequired: false},
    component: SignUpForm,
  },
  {
    name: `StudyOutputGeneration`,
    path: `/studies/:studyId/outputs`,
    meta: {authRequired: true},

    component: StudyOutputGeneration,
  },
  {
    name: `StudyOutputEdit`,
    path: `/studies/:studyId/outputs/:outputId`,
    meta: {authRequired: true},
    component: StudyOutputEdit,
  },
];

const vueRouter = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: routes,
});

vueRouter.beforeEach(function (to, from, next) {
  request.get({
      url: `${window.location.origin}/api/users/loggedIn`,
    },
    (error, response) => {
      if ((response && response.statusCode) === 200) {
          next();
          return;
      }

      if (to.matched.some((routeInfo) => {
        return routeInfo.meta.authRequired;
      })) {
        next(`/login`);
      } else {
        next();
      }
    });
});

export default vueRouter;

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
    name: `StudyOutputGeneration`,
    path: `/studies/:studyId/outputs`,
    component: StudyOutputGeneration,
  },
  {
    name: `StudyOutputEdit`,
    path: `/studies/:studyId/outputs/:outputId`,
    component: StudyOutputEdit,
  },
];

export default new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: routes,
});